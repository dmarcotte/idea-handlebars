package com.dmarcotte.handlebars.psi.impl;

import com.dmarcotte.handlebars.HbIcons;
import com.dmarcotte.handlebars.psi.HbOpenBlockMustache;
import com.dmarcotte.handlebars.psi.HbPath;
import com.intellij.lang.ASTNode;
import com.intellij.psi.util.PsiTreeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;

public class HbOpenBlockMustacheImpl extends HbPsiElementImpl implements HbOpenBlockMustache {
    public HbOpenBlockMustacheImpl(@NotNull ASTNode astNode) {
        super(astNode);
    }

    @Override
    public String getName() {
        HbPath namePath = PsiTreeUtil.findChildOfType(this, HbPath.class);
        return namePath == null ? null : namePath.getName();
    }

    @Nullable
    @Override
    public Icon getIcon(int flags) {
        return HbIcons.OPEN_BLOCK;
    }
}
