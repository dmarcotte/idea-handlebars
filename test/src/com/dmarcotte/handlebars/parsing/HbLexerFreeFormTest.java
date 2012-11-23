package com.dmarcotte.handlebars.parsing;

import static com.dmarcotte.handlebars.parsing.HbTokenTypes.CLOSE;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.COMMENT;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.CONTENT;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.ID;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.INVALID;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.OPEN;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.OPEN_PARTIAL;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.OPEN_UNESCAPED;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.SEP;
import static com.dmarcotte.handlebars.parsing.HbTokenTypes.WHITE_SPACE;

/**
 * Free form lexer tests to help develop the lexer, pin down regressions, etc.
 *
 * See {@link HbTokenizerSpecTest} for the tests based on the formal Handlebars description in its tokenizer_spec.rb
 */
public class HbLexerFreeFormTest extends HbLexerTest {
    public void testPlainMustache1() {
        TokenizerResult result = tokenize("{{mustacheContent}}");
        result.shouldMatchTokenTypes(OPEN, ID, CLOSE);
        result.shouldMatchTokenContent("{{", "mustacheContent", "}}");
    }

    public void testPlainMustacheWithContentPreamble() {
        TokenizerResult result = tokenize("Some content y'all {{mustacheContent}}");
        result.shouldMatchTokenTypes(CONTENT, OPEN, ID, CLOSE);
        result.shouldMatchTokenContent("Some content y'all ", "{{", "mustacheContent", "}}");
    }

    public void testNoMustaches() {
        TokenizerResult result = tokenize("Some content y'all ");
        result.shouldMatchTokenTypes(CONTENT);
        result.shouldMatchTokenContent("Some content y'all ");
    }

    public void testPlainMustacheWithWhitespace() {
        TokenizerResult result = tokenize("{{\tmustacheContent }}");
        result.shouldMatchTokenTypes(OPEN, WHITE_SPACE, ID, WHITE_SPACE, CLOSE);
        result.shouldMatchTokenContent("{{", "\t", "mustacheContent", " ", "}}");
    }

    public void testComment() {
        TokenizerResult result = tokenize("{{! this is a comment=true }}");
        result.shouldMatchTokenTypes(COMMENT);
        result.shouldMatchTokenContent("{{! this is a comment=true }}");
    }

    public void testContentAfterComment() {
        TokenizerResult result = tokenize("{{! this is a comment=true }}This here be non-Hb content!");
        result.shouldMatchTokenTypes(COMMENT, CONTENT);
        result.shouldMatchTokenContent("{{! this is a comment=true }}", "This here be non-Hb content!");
    }

    public void testSquareBracketStuff() {
        TokenizerResult result = tokenize("{{test\t[what] }}");
        result.shouldMatchTokenTypes(OPEN, ID, WHITE_SPACE, ID, WHITE_SPACE, CLOSE);
        result.shouldMatchTokenContent("{{", "test", "\t", "[what]", " ", "}}");
    }

    public void testSeparator() {
        TokenizerResult result = tokenize("{{dis/connected}}");
        result.shouldMatchTokenTypes(OPEN, ID, SEP, ID, CLOSE);
        result.shouldMatchTokenContent("{{", "dis", "/", "connected", "}}");
    }

    public void testSimplePartial() {
        TokenizerResult result = tokenize("{{>partialId}}");
        result.shouldMatchTokenTypes(OPEN_PARTIAL, ID, CLOSE);
        result.shouldMatchTokenContent("{{>", "partialId", "}}");
    }

    public void testSimpleUnescaped() {
        TokenizerResult result = tokenize("{{{partialId}}}");
        result.shouldMatchTokenTypes(OPEN_UNESCAPED, ID, CLOSE);
        result.shouldMatchTokenContent("{{{", "partialId", "}}}");
    }

    /**
     * Lexer was not interpreting mustaches ("{{") properly, resulting in bad behavior when perfectly
     * reasonable CONTENT contained a single "{" (i.e. when writing js in a script tag for instance),
     * the "{" was being lexed as INVALID.
     *
     * See https://github.com/dmarcotte/idea-handlebars/issues/4
     */
    public void testContentWithSingleBrace() {
        TokenizerResult result = tokenize("{");
        result.shouldMatchTokenTypes(CONTENT);
        result.shouldMatchTokenContent("{");

        // also check an example with more context just to be sure
        result = tokenize("{{stache}}\n<script type=\"text/javascript\">function test() { alert('hotttness') }</script>{{afterStache}}");
        result.shouldMatchTokenTypes(OPEN, ID, CLOSE, CONTENT, OPEN, ID, CLOSE);
    }

    public void testRegularMustacheFollowedByUnescaped() {
        TokenizerResult result = tokenize("{{reg}}{{{unesc}}}");
        result.shouldMatchTokenTypes(OPEN, ID, CLOSE, OPEN_UNESCAPED, ID, CLOSE);
        result.shouldMatchTokenContent("{{", "reg", "}}", "{{{", "unesc", "}}}");
    }

    public void testTooManyMustaches() {
        TokenizerResult result = tokenize("{{{{");
        result.shouldMatchTokenTypes(OPEN_UNESCAPED, INVALID);
        result.shouldMatchTokenContent("{{{", "{");
    }

    public void testTooManyCommentCloseStaches() {
        TokenizerResult result = tokenize("{{! ZOMG! A comment!!! }}}");
        result.shouldMatchTokenTypes(COMMENT, CONTENT);
        result.shouldMatchTokenContent("{{! ZOMG! A comment!!! }}", "}");

        result = tokenize("{{! ZOMG! A comment!!! }}}}");
        result.shouldMatchTokenTypes(COMMENT, CONTENT);
        result.shouldMatchTokenContent("{{! ZOMG! A comment!!! }}", "}}");
    }
}
