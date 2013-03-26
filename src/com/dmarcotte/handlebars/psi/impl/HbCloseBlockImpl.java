package com.dmarcotte.handlebars.psi.impl;

import com.dmarcotte.handlebars.psi.HbCloseBlock;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbCloseBlockImpl extends HbPsiElementImpl implements HbCloseBlock {
    public HbCloseBlockImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
