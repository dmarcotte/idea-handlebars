package com.dmarcotte.handlebars.psi;

import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.openapi.util.Condition;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;

public class HbPsiUtil {

    /**
     * Used to determine if an element is part of an "open tag" (i.e. "{{#open}}" or "{{^openInverse}}")
     * If the given element is the descendant of an {@link com.dmarcotte.handlebars.parsing.HbTokenTypes#OPEN_BLOCK_STACHE}
     * or an {@link com.dmarcotte.handlebars.parsing.HbTokenTypes#OPEN_INVERSE_BLOCK_STACHE}, this method returns
     * that parent.
     *
     * Otherwise, returns null.
     *
     * @param element The element whose ancestors will be searched
     * @return An ancestor of type {@link com.dmarcotte.handlebars.parsing.HbTokenTypes#OPEN_BLOCK_STACHE}
     * or {@link com.dmarcotte.handlebars.parsing.HbTokenTypes#OPEN_INVERSE_BLOCK_STACHE} or null if none exists
     */
    public static PsiElement findParentOpenTagElement(PsiElement element) {
        return PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement element) {
                return element != null
                        && element.getNode() != null
                        && (element.getNode().getElementType() == HbTokenTypes.OPEN_BLOCK_STACHE
                        || element.getNode().getElementType() == HbTokenTypes.OPEN_INVERSE_BLOCK_STACHE);
            }
        });
    }

    /**
     * Used to determine if an element is part of a "close tag" (i.e. "{{/closer}}")
     *
     * If the given element is the descendant of an {@link com.dmarcotte.handlebars.parsing.HbTokenTypes#CLOSE_BLOCK_STACHE},
     * this method returns that parent.
     *
     * Otherwise, returns null.
     *
     * @param element The element whose ancestors will be searched
     * @return An ancestor of type {@link com.dmarcotte.handlebars.parsing.HbTokenTypes#CLOSE_BLOCK_STACHE}
     * or null if none exists
     */
    public static PsiElement findParentCloseTagElement(PsiElement element) {
        return PsiTreeUtil.findFirstParent(element, true, new Condition<PsiElement>() {
            @Override
            public boolean value(PsiElement element) {
                return element != null
                        && element.getNode() != null
                        && element.getNode().getElementType() == HbTokenTypes.CLOSE_BLOCK_STACHE;
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
                        && element.getNode() != null
                        && element.getNode().getElementType() == HbTokenTypes.STATEMENTS;
            }
        });

        // we're a non-root statements if we're of type statements, and we have a statements parent
        return element.getNode().getElementType() == HbTokenTypes.STATEMENTS
                && statementsParent != null;
    }
}
