package com.dmarcotte.handlebars.psi.impl;

import com.dmarcotte.handlebars.psi.HbOpenInverseBlock;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;

public class HbOpenInverseBlockImpl extends HbOpenBlockImpl implements HbOpenInverseBlock {
    public HbOpenInverseBlockImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }
}
