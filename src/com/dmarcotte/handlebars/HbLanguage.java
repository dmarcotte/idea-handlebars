package com.dmarcotte.handlebars;

import com.intellij.lang.Language;

public class HbLanguage extends Language {
    public static final HbLanguage INSTANCE = new HbLanguage();

    public HbLanguage() {
        super("Handlebars", "text/x-handlebars-template");
        // dm todo addd SyntaxHighlighterFactory like PropertiesLanguage?
    }
}
