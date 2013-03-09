package com.dmarcotte.handlebars.file;

import com.dmarcotte.handlebars.parsing.HbLexer;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.HtmlScriptContentProvider;
import com.intellij.lexer.Lexer;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.Nullable;

public class HbHtmlScriptContentProvider implements HtmlScriptContentProvider {
    @Override
    public IElementType getScriptElementType() {
        return HbTokenTypes.STATEMENTS;
    }

    @Nullable
    @Override
    public Lexer getHighlightingLexer() {
        return new HbLexer();
    }
}
