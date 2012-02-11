package com.dmarcotte.handlebars;

import com.intellij.lang.Language;
import com.intellij.openapi.fileTypes.SingleLazyInstanceSyntaxHighlighterFactory;
import com.intellij.openapi.fileTypes.SyntaxHighlighter;
import com.intellij.openapi.fileTypes.SyntaxHighlighterFactory;
import org.jetbrains.annotations.NotNull;

public class HbLanguage extends Language {
    public static final HbLanguage INSTANCE = new HbLanguage();

    public HbLanguage() {
        super("Handlebars", "text/x-handlebars-template");
        SyntaxHighlighterFactory.LANGUAGE_FACTORY.addExplicitExtension(this, new SingleLazyInstanceSyntaxHighlighterFactory() {
            @NotNull
            protected SyntaxHighlighter createHighlighter() {
                return new HbHighlighter();
            }
        });
    }
}
