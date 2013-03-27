package com.dmarcotte.handlebars.structure;

import com.dmarcotte.handlebars.psi.HbBlockWrapper;
import com.dmarcotte.handlebars.psi.HbMustache;
import com.dmarcotte.handlebars.psi.HbPsiElement;
import com.dmarcotte.handlebars.psi.HbStatements;
import com.intellij.ide.structureView.StructureViewTreeElement;
import com.intellij.ide.structureView.impl.common.PsiTreeElementBase;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HbTreeElement extends PsiTreeElementBase<HbPsiElement> {

    private HbPsiElement myElement;

    public HbTreeElement(HbPsiElement psiElement) {
        super(psiElement);
        myElement = psiElement;
    }

    @NotNull
    @Override
    public Collection<StructureViewTreeElement> getChildrenBase() {
        return getStructureViewTreeElements(myElement);
    }

    static List<StructureViewTreeElement> getStructureViewTreeElements(PsiElement psiElement) {
        List<StructureViewTreeElement> children = new ArrayList<StructureViewTreeElement>();
        for (PsiElement childElement : psiElement.getChildren()) {
            if (!(childElement instanceof HbPsiElement)) {
                continue;
            }

            if (childElement instanceof HbStatements) {
                // HbStatments elements transparently wrap other elements, so we don't add
                // this element to the tree, but we add its children
                children.addAll(new HbTreeElement((HbPsiElement) childElement).getChildrenBase());
            }

            if(childElement instanceof HbBlockWrapper || childElement instanceof HbMustache) {
                children.add(new HbTreeElement((HbPsiElement) childElement));
            }
        }
        return children;
    }

    @Nullable
    @Override
    public String getPresentableText() {
        return myElement.getText();
    }

    @Override
    public Icon getIcon(boolean open) {
        return super.getIcon(open);
    }
}
