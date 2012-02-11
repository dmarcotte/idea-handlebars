package com.dmarcotte.handlebars.file;

import com.dmarcotte.handlebars.HbLanguage;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.Language;
import com.intellij.lang.LanguageParserDefinitions;
import com.intellij.openapi.fileTypes.PlainTextLanguage;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.LanguageSubstitutors;
import com.intellij.psi.MultiplePsiFilesPerDocumentFileViewProvider;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.PsiFileImpl;
import com.intellij.psi.templateLanguages.TemplateDataLanguageMappings;
import com.intellij.psi.templateLanguages.TemplateLanguage;
import com.intellij.psi.templateLanguages.TemplateLanguageFileViewProvider;
import gnu.trove.THashSet;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Set;

public class HbFileViewProvider extends MultiplePsiFilesPerDocumentFileViewProvider implements TemplateLanguageFileViewProvider {
    // main language of the file (HTML)
    private final Language myTemplateDataLanguage;


    // default constructor from parent
    public HbFileViewProvider(PsiManager manager, VirtualFile file, boolean physical) {
        super(manager, file, physical);

        // get the main language of the file
        Language dataLang = TemplateDataLanguageMappings.getInstance(manager.getProject()).getMapping(file);
        if(dataLang == null) dataLang = StdFileTypes.HTML.getLanguage();

        // some magic?
        if(dataLang instanceof TemplateLanguage) {
            myTemplateDataLanguage = PlainTextLanguage.INSTANCE;
        } else {
            myTemplateDataLanguage = LanguageSubstitutors.INSTANCE.substituteLanguage(dataLang, file, manager.getProject());
        }
    }

    // constructor to be used by self
    public HbFileViewProvider(PsiManager psiManager, VirtualFile virtualFile, boolean physical, Language myTemplateDataLanguage) {
        super(psiManager, virtualFile, physical);
        this.myTemplateDataLanguage = myTemplateDataLanguage;
    }


    @NotNull
    @Override
    public Language getBaseLanguage() {
        return HbLanguage.INSTANCE;
    }

    @NotNull
    @Override
    public Language getTemplateDataLanguage() {
        return myTemplateDataLanguage;
    }

    @NotNull
    @Override
    public Set<Language> getLanguages() {
        return new THashSet<Language>(Arrays.asList(new Language[] {HbLanguage.INSTANCE, myTemplateDataLanguage}));
    }

    @Override
    protected MultiplePsiFilesPerDocumentFileViewProvider cloneInner(VirtualFile virtualFile) {
        return new HbFileViewProvider(getManager(), virtualFile, false, myTemplateDataLanguage);
    }


    @Override
    protected PsiFile createFile(Language lang) {
        // creating file for main lang (HTML)
        if(lang == myTemplateDataLanguage) {
            PsiFileImpl file = (PsiFileImpl) LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
            file.setContentElementType(HbTokenTypes.CONTENT);
            return file;
        } else if(lang == HbLanguage.INSTANCE) {
            return LanguageParserDefinitions.INSTANCE.forLanguage(lang).createFile(this);
        } else {
            return null;
        }
    }
}

