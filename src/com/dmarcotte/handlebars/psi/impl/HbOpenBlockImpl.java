package com.dmarcotte.handlebars.psi.impl;

import com.dmarcotte.handlebars.psi.HbOpenBlock;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbOpenBlockImpl extends HbPsiElementImpl implements HbOpenBlock {
    public HbOpenBlockImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
