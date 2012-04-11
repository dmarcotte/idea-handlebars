package com.dmarcotte.handlebars.parsing;

import com.dmarcotte.handlebars.HbLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class HbCompositeElementType extends IElementType {
    public HbCompositeElementType(@NotNull @NonNls String debugName) {
        super(debugName, HbLanguage.INSTANCE);
    }
}
