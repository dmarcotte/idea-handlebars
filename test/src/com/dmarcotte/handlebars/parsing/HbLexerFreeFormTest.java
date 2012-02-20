package com.dmarcotte.handlebars.parsing;

import java.util.List;

/**
 * Free form lexer tests to help develop the lexer, pin down regressions, etc.
 *
 * See {@link HbTokenizerSpecTest} for the tests based on the formal Handlebars description in its tokenizer_spec.rb
 */
public class HbLexerFreeFormTest extends HbLexerTest {
    public void testPlainMustache() {
        List<Token> tokens = tokenizeOld("{{mustacheContent}}");

        assertEquals(3, tokens.size());
        int tokenIdx = -1;
        assertEquals(HbTokenTypes.OPEN, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("mustacheContent", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.CLOSE, tokens.get(++tokenIdx).getElementType());
        assertEquals("}}", tokens.get(tokenIdx).getElementContent());
    }

    public void testPlainMustacheWithContentPreamble() {
        List<Token> tokens = tokenizeOld("Some content y'all {{mustacheContent}}");

        assertEquals(4, tokens.size());

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.CONTENT, tokens.get(++tokenIdx).getElementType());
        assertEquals("Some content y'all ", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.OPEN, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("mustacheContent", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.CLOSE, tokens.get(++tokenIdx).getElementType());
        assertEquals("}}", tokens.get(tokenIdx).getElementContent());
    }

    public void testNoMustaches() {
        List<Token> tokens = tokenizeOld("Some content y'all ");

        assertEquals(1, tokens.size());

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.CONTENT, tokens.get(++tokenIdx).getElementType());
        assertEquals("Some content y'all ", tokens.get(tokenIdx).getElementContent());
    }

    public void testPlainMustacheWithWhitespace() {
        List<Token> tokens = tokenizeOld("{{\tmustacheContent }}");

        assertEquals(5, tokens.size());

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.OPEN, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.WHITE_SPACE, tokens.get(++tokenIdx).getElementType());
        assertEquals("\t", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("mustacheContent", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.WHITE_SPACE, tokens.get(++tokenIdx).getElementType());
        assertEquals(" ", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.CLOSE, tokens.get(++tokenIdx).getElementType());
        assertEquals("}}", tokens.get(tokenIdx).getElementContent());
    }

    public void testComment() {
        List<Token> tokens = tokenizeOld("{{! this is a comment=true }}");

        assertEquals(1, tokens.size());

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.COMMENT, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{! this is a comment=true }}", tokens.get(tokenIdx).getElementContent());
    }

    public void testContentAfterComment() {
        List<Token> tokens = tokenizeOld("{{! this is a comment=true }}This here be non-Hb content!");

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.COMMENT, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{! this is a comment=true }}", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.CONTENT, tokens.get(++tokenIdx).getElementType());
        assertEquals("This here be non-Hb content!", tokens.get(tokenIdx).getElementContent());
    }

    public void testSquareBracketStuff() {
        List<Token> tokens = tokenizeOld("{{test\t[what] }}");

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.OPEN, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("test", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.WHITE_SPACE, tokens.get(++tokenIdx).getElementType());
        assertEquals("\t", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("[what]", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.WHITE_SPACE, tokens.get(++tokenIdx).getElementType());
        assertEquals(" ", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.CLOSE, tokens.get(++tokenIdx).getElementType());
        assertEquals("}}", tokens.get(tokenIdx).getElementContent());
    }

    public void testSeparator() {
        List<Token> tokens = tokenizeOld("{{dis/connected}}");

        int tokenIdx = -1;
        assertEquals(HbTokenTypes.OPEN, tokens.get(++tokenIdx).getElementType());
        assertEquals("{{", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("dis", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.SEP, tokens.get(++tokenIdx).getElementType());
        assertEquals("/", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.ID, tokens.get(++tokenIdx).getElementType());
        assertEquals("connected", tokens.get(tokenIdx).getElementContent());
        assertEquals(HbTokenTypes.CLOSE, tokens.get(++tokenIdx).getElementType());
        assertEquals("}}", tokens.get(tokenIdx).getElementContent());
    }
}
