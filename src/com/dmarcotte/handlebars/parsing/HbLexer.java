package com.dmarcotte.handlebars.parsing;

import com.intellij.lexer.EmbedmentLexer;
import com.intellij.lexer.FlexAdapter;
import com.intellij.psi.tree.IElementType;

import java.io.Reader;

public class HbLexer extends FlexAdapter implements EmbedmentLexer {
    public HbLexer() {
        super(new _HbLexer((Reader) null));
    }

    @Override
    public int getEmbeddedInitialState(IElementType tokenType) {
        return _HbLexer.YYINITIAL;
    }
}