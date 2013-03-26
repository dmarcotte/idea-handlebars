package com.dmarcotte.handlebars.psi;

import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

public class HbPsiUtil {

    /**
     * Used to determine if an element is part of an "open tag" (i.e. "{{#open}}" or "{{^openInverse}}")
     * <p>
     * If the given element is the descendant of an {@link HbOpenBlock}, this method returns
     * that parent.
     * <p>
     * Otherwise, returns null.
     *
     * @param element The element whose ancestors will be searched
     * @return An ancestor of type {@link HbOpenBlock} or null if none exists
     */
    public static HbOpenBlock findParentOpenTagElement(PsiElement element) {
        return (HbOpenBlock) PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement element) {
                return element != null
                        && element instanceof HbOpenBlock;
            }
        });
    }

    /**
     * Used to determine if an element is part of a "close tag" (i.e. "{{/closer}}")
     * <p>
     * If the given element is the descendant of an {@link HbCloseBlock}, this method returns that parent.
     * <p>
     * Otherwise, returns null.
     * <p>
     * @param element The element whose ancestors will be searched
     * @return An ancestor of type {@link HbCloseBlock} or null if none exists
     */
    public static HbCloseBlock findParentCloseTagElement(PsiElement element) {
        return (HbCloseBlock) PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement element) {
                return element != null
                        && element instanceof HbCloseBlock;
            }
        });
    }

    /**
     * Tests to see if the given element is not the "root" statements expression of the grammar
     */
    public static boolean isNonRootStatementsElement(PsiElement element) {
        PsiElement statementsParent = PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement element) {
                return element != null
                        && element instanceof HbStatements;
            }
        });

        // we're a non-root statements if we're of type statements, and we have a statements parent
        return element instanceof HbStatements
                && statementsParent != null;
    }
}
