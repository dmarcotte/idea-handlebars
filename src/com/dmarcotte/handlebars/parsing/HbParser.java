package com.dmarcotte.handlebars.parsing;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

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

    public static boolean parseProgram(PsiBuilder builder) {
        PsiBuilder.Marker programMarker = builder.mark();
        parseStatement(builder);
        programMarker.done(HbTokenTypes.PROGRAM);
        return true;
    }

    public static boolean parseStatement(PsiBuilder builder) {
        PsiBuilder.Marker statementMarker = builder.mark();
        if (parseMustache(builder) || parseContent(builder)) {
            statementMarker.done(HbTokenTypes.STATEMENT);
            return true;
        } else {
            statementMarker.error("Problems!");
            return false;
        }
    }

    public static boolean parseMustache(PsiBuilder builder) {
        PsiBuilder.Marker mustacheMarker = builder.mark();
        if (parseOpen(builder) && parseId(builder) && parseClose(builder)) {
            mustacheMarker.done(HbTokenTypes.MUSTACHE);
            return true;
        } else {
            mustacheMarker.done(HbTokenTypes.MUSTACHE);
            return false;
        }
    }

    public static boolean parseContent(PsiBuilder builder) {
        PsiBuilder.Marker contentMarker = builder.mark();
        if (builder.getTokenType() == HbTokenTypes.CONTENT) {
            builder.advanceLexer();
            contentMarker.done(HbTokenTypes.CONTENT);
            return true;
        } else {
            contentMarker.rollbackTo();
            return false;
        }
    }

    public static boolean parseOpen(PsiBuilder builder) {
        PsiBuilder.Marker openMark = builder.mark();
        if (builder.getTokenType() == HbTokenTypes.OPEN) {
            builder.advanceLexer();
            openMark.done(HbTokenTypes.OPEN);
            return true;
        } else {
            openMark.rollbackTo();
            return false;
        }
    }

    public static boolean parseClose(PsiBuilder builder) {
        PsiBuilder.Marker closeMarker = builder.mark();
        if (builder.getTokenType() == HbTokenTypes.CLOSE) {
            builder.advanceLexer();
            closeMarker.done(HbTokenTypes.CLOSE);
            return true;
        } else {
            closeMarker.rollbackTo();
            return false;
        }
    }

    public static boolean parseId(PsiBuilder builder) {
        PsiBuilder.Marker idMarker = builder.mark();
        if (builder.getTokenType() == HbTokenTypes.ID) {
            builder.advanceLexer();
            idMarker.done(HbTokenTypes.ID);
            return true;
        } else {
            builder.advanceLexer();
            idMarker.error("Expected an ID");
            return false;
        }
    }
}
