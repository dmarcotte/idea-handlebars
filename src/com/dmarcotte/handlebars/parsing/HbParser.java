package com.dmarcotte.handlebars.parsing;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import static com.dmarcotte.handlebars.parsing.HbTokenTypes.*;

public class HbParser implements PsiParser {
    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();

        parseProgram(builder);

        // eat any remaining tokens
        while (!builder.eof()) {
            builder.advanceLexer();
        }

        rootMarker.done(root);

        return builder.getTreeBuilt();
    }

    /**
     * program
     * : statements simpleInverse statements { $$ = new yy.ProgramNode($1, $3); }
     * | statements { $$ = new yy.ProgramNode($1); }
     * | "" { $$ = new yy.ProgramNode([]); }
     * ;
     */
    public static boolean parseProgram(PsiBuilder builder) {
        return parseStatements(builder) && parseSimpleInverse(builder) && parseStatements(builder)
                || parseStatements(builder);
    }

    /**
     * statements
     * : statement { $$ = [$1]; }
     * | statements statement { $1.push($2); $$ = $1; }
     * ;
     */
    public static boolean parseStatements(PsiBuilder builder) {
        PsiBuilder.Marker statementsMarker = builder.mark();

        // parse the required first statement
        boolean handled = parseStatement(builder);

        if (!handled) {
            statementsMarker.rollbackTo();
            return false;
        }

        // now parse the rest, rolling back if we hit any problems (since we don't require that there be statements,
        // there just might be)
        while (handled) {
            PsiBuilder.Marker mark = builder.mark();
            handled = parseStatement(builder);
            if (handled) {
                mark.drop();
            } else {
                mark.rollbackTo();
            }
        }

        statementsMarker.done(STATEMENTS);
        return true;
    }

    /**
     * statement
     * : openInverse program closeBlock
     * | openBlock program closeBlock
     * | mustache
     * | partial
     * | CONTENT
     * | COMMENT
     * ;
     */
    public static boolean parseStatement(PsiBuilder builder) {
        return parseOpenInverse(builder) && parseProgram(builder) && parseCloseBlock(builder)
                        || parseOpenBlock(builder) && parseProgram(builder) && parseCloseBlock(builder)
                        || parseMustache(builder)
                        || parsePartial(builder)
                        || parseLeafToken(builder, CONTENT)
                        || parseLeafToken(builder, COMMENT);
    }

    /**
     * openBlock
     * : OPEN_BLOCK inMustache CLOSE { $$ = new yy.MustacheNode($2[0], $2[1]); }
     * ;
     */
    public static boolean parseOpenBlock(PsiBuilder builder) {
        PsiBuilder.Marker openBlockMarker = builder.mark();

        if (!parseLeafToken(builder, OPEN_BLOCK)) {
            openBlockMarker.rollbackTo();
            return false;
        }

        parseInMustache(builder);
        parseLeafToken(builder, CLOSE);

        openBlockMarker.done(BLOCK_STACHE);
        return true;
    }

    /**
     * openInverse
     * : OPEN_INVERSE inMustache CLOSE
     * ;
     */
    public static boolean parseOpenInverse(PsiBuilder builder) {
        PsiBuilder.Marker openInverseMarker = builder.mark();

        if (!parseLeafToken(builder, OPEN_INVERSE)) {
            openInverseMarker.rollbackTo();
            return false;
        }

        parseInMustache(builder);
        parseLeafToken(builder, CLOSE);

        openInverseMarker.done(INVERSE_STACHE);
        return true;
    }

    /**
     * closeBlock
     * : OPEN_ENDBLOCK path CLOSE { $$ = $2; }
     * ;
     */
    public static boolean parseCloseBlock(PsiBuilder builder) {
        PsiBuilder.Marker closeBlockMarker = builder.mark();

        if (!parseLeafToken(builder, OPEN_ENDBLOCK)) {
            closeBlockMarker.rollbackTo();
            return false;
        }

        parsePath(builder);
        parseLeafToken(builder, CLOSE);

        closeBlockMarker.done(CLOSEBLOCK_STACHE);
        return true;
    }

    /**
     * mustache
     * : OPEN inMustache CLOSE { $$ = new yy.MustacheNode($2[0], $2[1]); }
     * | OPEN_UNESCAPED inMustache CLOSE { $$ = new yy.MustacheNode($2[0], $2[1], true); }
     * ;
     */
    public static boolean parseMustache(PsiBuilder builder) {
        return parseMustacheRegularHelper(builder)
                || parseMustacheUnescapedHelper(builder);
    }

    private static boolean parseMustacheRegularHelper(PsiBuilder builder) {
        PsiBuilder.Marker mustacheMarker = builder.mark();
        if (!parseLeafToken(builder, OPEN)) {
            mustacheMarker.rollbackTo();
            return false;
        }

        parseInMustache(builder);
        parseLeafToken(builder, CLOSE);

        mustacheMarker.done(MUSTACHE);
        return true;
    }

    private static boolean parseMustacheUnescapedHelper(PsiBuilder builder) {
        PsiBuilder.Marker mustacheMarker = builder.mark();
        if (!parseLeafToken(builder, OPEN_UNESCAPED)) {
            mustacheMarker.rollbackTo();
            return false;
        }

        parseInMustache(builder);
        parseLeafToken(builder, CLOSE);

        mustacheMarker.done(MUSTACHE);
        return true;
    }

    /**
     * partial
     * : OPEN_PARTIAL path CLOSE { $$ = new yy.PartialNode($2); }
     * | OPEN_PARTIAL path path CLOSE { $$ = new yy.PartialNode($2, $3); }
     * ;
     */
    public static boolean parsePartial(PsiBuilder builder) {
        PsiBuilder.Marker partialMarker = builder.mark();

        if (!parseLeafToken(builder, OPEN_PARTIAL)) {
            partialMarker.rollbackTo();
            return false;
        }

        parsePath(builder);

        PsiBuilder.Marker optionalPathMarker = builder.mark();
        boolean optionalSecondPath = parsePath(builder);
        if (optionalSecondPath) {
            optionalPathMarker.drop();
        } else {
            optionalPathMarker.rollbackTo();
        }

        parseLeafToken(builder, CLOSE);

        partialMarker.done(PARTIAL_STACHE);
        return true;
    }

    /**
     * simpleInverse
     * : OPEN_INVERSE CLOSE
     * ;
     */
    public static boolean parseSimpleInverse(PsiBuilder builder) {
        PsiBuilder.Marker simpleInverseMarker = builder.mark();

        boolean handled = parseLeafToken(builder, OPEN_INVERSE)
                && parseLeafToken(builder, CLOSE);

        if (!handled) {
            simpleInverseMarker.rollbackTo();
            return false;
        }

        simpleInverseMarker.done(SIMPLE_INVERSE);
        return true;
    }

    /**
     * inMustache
     * : path params hash { $$ = [[$1].concat($2), $3]; }
     * | path params { $$ = [[$1].concat($2), null]; }
     * | path hash { $$ = [[$1], $2]; }
     * | path { $$ = [[$1], null]; }
     * ;
     *
     * TODO need to implement the remaining cases here
     */
    public static boolean parseInMustache(PsiBuilder builder) {
        PsiBuilder.Marker inMustacheMarker = builder.mark();
        boolean handled = parsePath(builder);
        if (!handled) {
            inMustacheMarker.error("Expected a path");
            return true;
        }

        inMustacheMarker.done(IN_MUSTACHE);
        return true;
    }

    /**
     * path
     * : pathSegments { $$ = new yy.IdNode($1); }
     * ;
     */
    public static boolean parsePath(PsiBuilder builder) {
        return parsePathSegments(builder);
    }

    /**
     * pathSegments
     * : pathSegments SEP ID { $1.push($3); $$ = $1; }
     * | ID { $$ = [$1]; }
     * ;
     *
     * Refactored to eliminate left recursion:
     *
     * pathSegments
     * : ID pathSegments'
     *
     * pathSegements'
     * :
     * | SEP ID pathSegments
     */
    public static boolean parsePathSegments(PsiBuilder builder) {
        PsiBuilder.Marker pathSegmentsMarker = builder.mark();
        boolean handled = parseLeafToken(builder, ID);
        if (!handled) {
            pathSegmentsMarker.rollbackTo();
            return false;
        }

        parsePathSegmentsPrime(builder);

        pathSegmentsMarker.done(PATH_SEGMENTS);
        return true;
    }

    public static boolean parsePathSegmentsPrime(PsiBuilder builder) {
        PsiBuilder.Marker pathSegmentsPrimeMarker = builder.mark();

        if (!parseLeafToken(builder, SEP)) {
            pathSegmentsPrimeMarker.rollbackTo();
            return false;
        }

        boolean handled = parseLeafToken(builder, ID);
        if (handled) {
            parsePathSegments(builder);
        }

        pathSegmentsPrimeMarker.done(PATH_SEGMENTS);
        return true;
    }

    private static boolean parseLeafToken(PsiBuilder builder, IElementType leafTokenType) {
        PsiBuilder.Marker leafTokenMark = builder.mark();
        if (builder.getTokenType() == leafTokenType) {
            builder.advanceLexer();
            leafTokenMark.done(leafTokenType);
            return true;
        } else {
            leafTokenMark.error("Expected " + leafTokenType); // TODO pretty up these message and put in the resource bundle
            return false;
        }
    }
}
