package com.dmarcotte.handlebars.parsing;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.PlatformLiteFixture;
import junit.framework.Assert;

import java.util.ArrayList;
import java.util.List;


/**
 * dm todo write tests based on revision of tokenizer_spec.rb which corresponds to the handlebars.l we used
 * https://github.com/wycats/handlebars.js/blob/932e2970ad29b16d6d6874ad0bfb44b07b4cd765/spec/tokenizer_spec.rb
 */
public abstract class HbLexerTest extends PlatformLiteFixture {

    Lexer _lexer;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        _lexer = new HbLexer();
    }

    /**
     * dm todo delete this if we like the new tokenizerResult
     */
    List<Token> tokenizeOld(String string) {
        List<Token> tokens = new ArrayList<Token>();
        IElementType currentElement;

        _lexer.start(string);

        while((currentElement = _lexer.getTokenType()) != null) {
            tokens.add(new Token(currentElement, _lexer.getTokenText()));
            _lexer.advance();
        }

        return tokens;
    }

    TokenizerResult tokenize(String string) {
        return new TokenizerResult(tokenizeOld(string));
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

        public void shouldMatchTokens(IElementType... tokenTypes) {
            Assert.assertEquals(tokenTypes.length, _tokens.size());

            for (int i = 0; i < _tokens.size(); i++) {
                Assert.assertEquals(tokenTypes[i], _tokens.get(i).getElementType());
            }
        }

        public void shouldBeToken(int tokenPosition, IElementType tokenType, String tokenContent) {
            Token token = _tokens.get(tokenPosition);

            Assert.assertEquals(tokenType, token.getElementType());
            Assert.assertEquals(tokenContent, token.getElementContent());
        }
    }
}
