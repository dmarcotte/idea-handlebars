package com.dmarcotte.handlebars.file;

import com.intellij.lang.Language;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.FileViewProviderFactory;
import com.intellij.psi.PsiManager;


public class HbFileViewProviderFactory implements FileViewProviderFactory {
    @Override
    public FileViewProvider createFileViewProvider(VirtualFile virtualFile, Language language, PsiManager psiManager, boolean physical) {
        return new HbFileViewProvider(psiManager, virtualFile, physical);
    }
}

