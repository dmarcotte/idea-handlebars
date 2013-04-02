package com.dmarcotte.handlebars.psi.impl;

import com.dmarcotte.handlebars.HbIcons;
import com.dmarcotte.handlebars.psi.HbSimpleInverse;
import com.intellij.lang.ASTNode;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbSimpleInverseImpl extends HbMustacheImpl implements HbSimpleInverse {
    public HbSimpleInverseImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        return "else";
    }

    @Nullable
    @Override
    public Icon getIcon(@IconFlags int flags) {
        return HbIcons.OPEN_INVERSE;
    }
}
