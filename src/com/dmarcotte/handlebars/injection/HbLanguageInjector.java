package com.dmarcotte.handlebars.injection;

import com.intellij.openapi.fileTypes.FileTypeManager;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.InjectedLanguagePlaces;
import com.intellij.psi.LanguageInjector;
import com.intellij.psi.PsiLanguageInjectionHost;
import com.intellij.psi.impl.source.xml.XmlTextImpl;
import com.intellij.psi.xml.XmlAttribute;
import org.jetbrains.annotations.NotNull;

public class HbLanguageInjector implements LanguageInjector {
    @Override
    public void getLanguagesToInject(@NotNull PsiLanguageInjectionHost host, @NotNull InjectedLanguagePlaces injectionPlacesRegistrar) {
        if (host instanceof XmlTextImpl) {
            XmlTextImpl xmlText = (XmlTextImpl) host;
            if (xmlText.getParentTag() != null
                    && xmlText.getParentTag().getName().equals("script")) {
                XmlAttribute scriptTypeAttr = xmlText.getParentTag().getAttribute("type");
                if (scriptTypeAttr != null
                        && scriptTypeAttr.getValue() != null
                        && scriptTypeAttr.getValue().equals("text/x-handlebars")) {
                    injectionPlacesRegistrar.addPlace(((LanguageFileType) FileTypeManager.getInstance().getFileTypeByExtension("handlebars")).getLanguage(),
                            new TextRange(1, xmlText.getTextLength() - 1),
                            null,
                            null);
                }
            }
        }
    }
}
