package com.dmarcotte.handlebars.parsing;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.PlatformLiteFixture;

import java.util.ArrayList;
import java.util.List;


public class HbLexerTest extends PlatformLiteFixture {

    Lexer _lexer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _lexer = new HbLexer();
    }

    private List<Token> lex(String str) {
        List<Token> tokens = new ArrayList<Token>();
        IElementType currentElement;

        _lexer.start(str);

        while((currentElement = _lexer.getTokenType()) != null) {
            tokens.add(new Token(currentElement, _lexer.getTokenText()));
            _lexer.advance();
        }

        return tokens;
    }

    public void testMustache() {
        List<Token> tokens = lex("{{mustacheContent}}");

        assertEquals(3, tokens.size());
        assertEquals(HbTokenTypes.OPEN, tokens.get(0).getElementType());
        assertEquals("{{", tokens.get(0).getElementContent());

    }

    private static class Token {
        private IElementType _elementType;
        private String _elementContent;

        private Token(IElementType elementType, String elementContent) {
            _elementType = elementType;
            _elementContent = elementContent;
        }

        public IElementType getElementType() {
            return _elementType;
        }

        public String getElementContent() {
            return _elementContent;
        }
    }
}
