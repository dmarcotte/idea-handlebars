package com.dmarcotte.handlebars.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

/**
 * Base of all PsiElements
 */
public class HbPsiElement extends ASTWrapperPsiElement {
    public HbPsiElement(@NotNull ASTNode astNode) {
        super(astNode);
    }

    // some common logic should come here
}
