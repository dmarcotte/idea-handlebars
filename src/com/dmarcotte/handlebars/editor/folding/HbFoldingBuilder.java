package com.dmarcotte.handlebars.editor.folding;

import com.dmarcotte.handlebars.config.HbConfig;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.project.DumbAware;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HbFoldingBuilder implements FoldingBuilder, DumbAware {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        appendDescriptors(node.getPsi(), descriptors, document);
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    private void appendDescriptors(PsiElement psi, List<FoldingDescriptor> descriptors, Document document) {
        ASTNode node = psi.getNode();
        if (node == null || isSingleLine(psi, document)) {
            return;
        }

        if (HbTokenTypes.COMMENT == node.getElementType()) {
            String commentText = node.getText();

            // comment might be unclosed, so do a bit of sanity checking on its length and whether or not it's
            // got the requisite open/close tags before we allow folding
            if (commentText.length() > 5
                    && commentText.substring(0,3).equals("{{!")
                    && commentText.substring(commentText.length() - 2, commentText.length()).equals("}}")) {
                TextRange range = new TextRange(node.getTextRange().getStartOffset() + 3, node.getTextRange().getEndOffset() -2);
                descriptors.add(new FoldingDescriptor(node, range));
            }
        }

        if (HbTokenTypes.BLOCK_WRAPPER == node.getElementType()) {

            ASTNode endOpenBlockStache = getOpenBlockCloseStacheElement(node.getFirstChildNode());
            ASTNode endCloseBlockStache = getCloseBlockCloseStacheElement(node.getLastChildNode());

            // if we've got a well formed block with the open and close elems we need, define a region to fold
            if (endOpenBlockStache != null && endCloseBlockStache != null) {
                int endOfFirstOpenStacheLine
                        = document.getLineEndOffset(document.getLineNumber(node.getTextRange().getStartOffset()));

                // we set the start of the text we'll fold to be just before the close braces of the open stache,
                //     or, if the open stache spans multiple lines, to the end of the first line
                int foldingRangeStartOffset = Math.min(endOpenBlockStache.getTextRange().getStartOffset(), endOfFirstOpenStacheLine);
                // we set the end of the text we'll fold to be just before the final close braces in this block
                int foldingRangeEndOffset = endCloseBlockStache.getTextRange().getStartOffset();

                TextRange range = new TextRange(foldingRangeStartOffset, foldingRangeEndOffset);

                descriptors.add(new FoldingDescriptor(node, range));
            }
        }

        PsiElement child = psi.getFirstChild();
        while (child != null) {
            appendDescriptors(child, descriptors, document);
            child = child.getNextSibling();
        }
    }

    /**
     * If the given node is a {@link HbTokenTypes#OPEN_BLOCK_STACHE}, returns the close 'stache node ("}}")<br/>
     * <br/>
     * Otherwise, returns null.
     */
    private ASTNode getOpenBlockCloseStacheElement(ASTNode node) {
        if (node == null || node.getElementType() != HbTokenTypes.OPEN_BLOCK_STACHE) {
            return null;
        }

        ASTNode endOpenStache = node.getLastChildNode();
        if (endOpenStache == null || endOpenStache.getElementType() != HbTokenTypes.CLOSE) {
            return null;
        }

        return endOpenStache;
    }

    /**
     * If the given node is {@link HbTokenTypes#CLOSE_BLOCK_STACHE}, returns the close 'stache node ("{{")<br/>
     * <br/>
     * Otherwise, returns null
     */
    private ASTNode getCloseBlockCloseStacheElement(ASTNode node) {
        if (node == null || node.getElementType() != HbTokenTypes.CLOSE_BLOCK_STACHE) {
            return null;
        }

        ASTNode endCloseStache = node.getLastChildNode();
        if (endCloseStache == null || endCloseStache.getElementType() != HbTokenTypes.CLOSE) {
            return null;
        }

        return endCloseStache;
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        return "...";
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return HbConfig.isAutoCollapseBlocksEnabled();
    }

    /**
     * Return true if this psi element does not span more than one line in the given document
     */
    private static boolean isSingleLine(PsiElement element, Document document) {
        TextRange range = element.getTextRange();
        return document.getLineNumber(range.getStartOffset()) == document.getLineNumber(range.getEndOffset());
    }
}
