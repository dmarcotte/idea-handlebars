package com.dmarcotte.handlebars.parsing;

import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.testFramework.PlatformLiteFixture;

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

    List<Token> lex(String str) {
        List<Token> tokens = new ArrayList<Token>();
        IElementType currentElement;

        _lexer.start(str);

        while((currentElement = _lexer.getTokenType()) != null) {
            tokens.add(new Token(currentElement, _lexer.getTokenText()));
            _lexer.advance();
        }

        return tokens;
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
}
