package com.dmarcotte.handlebars;

import com.dmarcotte.handlebars.config.HbConfig;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.ASTNode;
import com.intellij.lang.folding.FoldingBuilder;
import com.intellij.lang.folding.FoldingDescriptor;
import com.intellij.openapi.editor.Document;
import com.intellij.psi.PsiElement;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class HbFoldingBuilder implements FoldingBuilder {

    @NotNull
    @Override
    public FoldingDescriptor[] buildFoldRegions(@NotNull ASTNode node, @NotNull Document document) {
        List<FoldingDescriptor> descriptors = new ArrayList<FoldingDescriptor>();
        appendDescriptors(node.getPsi(), document, descriptors);
        return descriptors.toArray(new FoldingDescriptor[descriptors.size()]);
    }

    private void appendDescriptors(PsiElement psi, Document document, List<FoldingDescriptor> descriptors) {
        ASTNode node = psi.getNode();
        if (node == null || !isMultiline(psi)) return;
        IElementType type = node.getElementType();

        if (HbTokenTypes.BLOCK_WRAPPER == type) {
            descriptors.add(new FoldingDescriptor(node, node.getTextRange()));
        }

        PsiElement child = psi.getFirstChild();
        while (child != null) {
            appendDescriptors(child, document, descriptors);
            child = child.getNextSibling();
        }
    }

    @Nullable
    @Override
    public String getPlaceholderText(@NotNull ASTNode node) {
        IElementType type = node.getElementType();
        String innerText = "...";
        if (type == HbTokenTypes.BLOCK_WRAPPER) {
            ASTNode child = node.getFirstChildNode();
            if (child != null && (
                    child.getElementType() == HbTokenTypes.OPEN_BLOCK_STACHE ||
                            child.getElementType() == HbTokenTypes.OPEN_INVERSE_BLOCK_STACHE)) {
                child = child.getFirstChildNode();
                if (child != null && (
                        child.getElementType() == HbTokenTypes.OPEN_BLOCK ||
                                child.getElementType() == HbTokenTypes.OPEN_INVERSE)) {
                    child = child.getTreeNext();
                    if (child != null) {
                        innerText = child.getText();
                    }
                }
            }
        }

        return String.format("{{ %s }}", innerText);
    }

    @Override
    public boolean isCollapsedByDefault(@NotNull ASTNode node) {
        return HbConfig.isAutoCollapseBlocksEnabled();
    }

    private static boolean isMultiline(PsiElement element) {
        String text = element.getText();
        return text.contains("\n") || text.contains("\r") || text.contains("\r\n");
    }
}
