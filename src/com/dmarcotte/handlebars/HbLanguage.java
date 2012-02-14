package com.dmarcotte.handlebars;

import com.intellij.lang.Language;
import com.intellij.psi.templateLanguages.TemplateLanguage;

public class HbLanguage extends Language implements TemplateLanguage {
    public static final HbLanguage INSTANCE = new HbLanguage();

    public HbLanguage() {
        super("Handlebars", "text/x-handlebars-template");
    }
}
