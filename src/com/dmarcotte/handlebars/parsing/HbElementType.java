package com.dmarcotte.handlebars.parsing;

import com.dmarcotte.handlebars.HbLanguage;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class HbElementType extends IElementType {
    public HbElementType(@NotNull @NonNls String debugName) {
        super(debugName, HbLanguage.INSTANCE);
    }

    @Override
    public String toString() {
        return "[Hb] " + super.toString();
    }
}
