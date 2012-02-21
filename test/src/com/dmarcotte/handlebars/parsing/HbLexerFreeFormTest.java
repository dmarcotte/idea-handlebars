package com.dmarcotte.handlebars.parsing;

import static com.dmarcotte.handlebars.parsing.HbTokenTypes.*;
import java.util.List;

/**
 * Free form lexer tests to help develop the lexer, pin down regressions, etc.
 *
 * See {@link HbTokenizerSpecTest} for the tests based on the formal Handlebars description in its tokenizer_spec.rb
 */
public class HbLexerFreeFormTest extends HbLexerTest {
    public void testPlainMustache() {
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
}
