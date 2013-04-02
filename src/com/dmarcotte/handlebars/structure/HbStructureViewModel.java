package com.dmarcotte.handlebars.structure;

import com.dmarcotte.handlebars.psi.HbPsiFile;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.TextEditorBasedStructureViewModel;
import org.jetbrains.annotations.NotNull;

class HbStructureViewModel extends TextEditorBasedStructureViewModel {

    private final HbPsiFile myFile;

    public HbStructureViewModel(@NotNull HbPsiFile psiFile) {
        super(psiFile);
        this.myFile = psiFile;
    }

    @NotNull
    @Override
    public StructureViewTreeElement getRoot() {
        return new HbTreeElementFile(myFile);
    }
}
