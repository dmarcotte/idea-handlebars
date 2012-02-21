package com.dmarcotte.handlebars;

import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.BracePair;
import com.intellij.lang.PairedBraceMatcher;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class HbBraceMatcher implements PairedBraceMatcher {

    private static final BracePair[] PAIRS = {
            new BracePair(HbTokenTypes.OPEN, HbTokenTypes.CLOSE, false),
            new BracePair(HbTokenTypes.OPEN_BLOCK, HbTokenTypes.CLOSE, false),
            new BracePair(HbTokenTypes.OPEN_PARTIAL, HbTokenTypes.CLOSE, false),
            new BracePair(HbTokenTypes.OPEN_ENDBLOCK, HbTokenTypes.CLOSE, false),
            new BracePair(HbTokenTypes.OPEN_INVERSE, HbTokenTypes.CLOSE, false),
            new BracePair(HbTokenTypes.OPEN_UNESCAPED, HbTokenTypes.CLOSE, false)
    };

    public BracePair[] getPairs() {
        return PAIRS;
    }

    public boolean isPairedBracesAllowedBeforeType(@NotNull IElementType lbraceType, @Nullable IElementType contextType) {
        return false;  // never allow IDEA to automatically insert braces since it does not do the multiple character braces properly
    }

    public int getCodeConstructStart(PsiFile file, int openingBraceOffset) {
        return openingBraceOffset;
    }
}
