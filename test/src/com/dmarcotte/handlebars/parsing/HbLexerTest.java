package com.dmarcotte.handlebars.parsing;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.PlatformLiteFixture;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;

public abstract class HbLexerTest extends PlatformLiteFixture {

    Lexer _lexer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _lexer = new HbLexer();
    }

    TokenizerResult tokenize(String string) {
        List<Token> tokens = new ArrayList<Token>();
        IElementType currentElement;

        _lexer.start(string);

        while((currentElement = _lexer.getTokenType()) != null) {
            tokens.add(new Token(currentElement, _lexer.getTokenText()));
            _lexer.advance();
        }
        
        return new TokenizerResult(tokens);
    }

    protected static class Token {
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

    protected static class TokenizerResult {
        private List<Token> _tokens;

        public TokenizerResult(List<Token> tokens) {
            _tokens = tokens;
        }

        /**
         * @param tokenTypes The token types expected for the tokens in this TokenizerResult, in the order they are expected
         */
        public void shouldMatchTokenTypes(IElementType... tokenTypes) {
            Assert.assertEquals(tokenTypes.length, _tokens.size());

            for (int i = 0; i < _tokens.size(); i++) {
                Assert.assertEquals(tokenTypes[i], _tokens.get(i).getElementType());
            }
        }

        /**
         * @param tokenContent The content string expected for the tokens in this TokenizerResult, in the order they are expected
         */
        public void shouldMatchTokenContent(String... tokenContent) {
            Assert.assertEquals(tokenContent.length, _tokens.size());

            for (int i = 0; i < _tokens.size(); i++) {
                Assert.assertEquals(tokenContent[i], _tokens.get(i).getElementContent());
            }
        }

        /**
         * Convenience method for validating a specific token in this TokenizerResult
         */
        public void shouldBeToken(int tokenPosition, IElementType tokenType, String tokenContent) {
            Token token = _tokens.get(tokenPosition);

            Assert.assertEquals(tokenType, token.getElementType());
            Assert.assertEquals(tokenContent, token.getElementContent());
        }
    }
}
