package com.dmarcotte.handlebars.format;

import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.formatting.Alignment;
import com.intellij.formatting.Block;
import com.intellij.formatting.ChildAttributes;
import com.intellij.formatting.FormattingModel;
import com.intellij.formatting.Indent;
import com.intellij.formatting.Wrap;
import com.intellij.formatting.templateLanguages.DataLanguageBlockWrapper;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlock;
import com.intellij.formatting.templateLanguages.TemplateLanguageBlockFactory;
import com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder;
import com.intellij.lang.ASTNode;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.formatter.DocumentBasedFormattingModel;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * Template aware formatter which provides formatting for Handlebars/Mustache syntax and delegates formatting
 * for the templated language to that languages formatter
 */
public class HbFormattingModelBuilder extends TemplateLanguageFormattingModelBuilder {
    @Override
    public TemplateLanguageBlock createTemplateLanguageBlock(@NotNull ASTNode node,
                                                             @Nullable Wrap wrap,
                                                             @Nullable Alignment alignment,
                                                             @Nullable List<DataLanguageBlockWrapper> foreignChildren,
                                                             @NotNull CodeStyleSettings codeStyleSettings) {
        // todo create and respect a CodeStyleSettings for this formatting
        return new HandlebarsBlock(this, codeStyleSettings, node, foreignChildren);
    }

    /**
     * We have to override {@link com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder#createModel}
     * since after we delegate to some templated languages, those languages (xml/html for sure, potentially others)
     * delegate right back to us to format the HbTokenTypes.OUTER_ELEMENT_TYPE token we tell them to ignore,
     * causing an stack-overflowing loop of polite format-delegation.
     */
    @NotNull
    public FormattingModel createModel(PsiElement element, CodeStyleSettings settings) {
        final PsiFile file = element.getContainingFile();
        Block rootBlock;

        ASTNode node = element.getNode();

        if (node.getElementType() == HbTokenTypes.OUTER_ELEMENT_TYPE) {
            // If we're looking at a HbTokenTypes.OUTER_ELEMENT_TYPE element, then we've been invoked by our templated
            // language.  Make a dummy block to allow that formatter to continue
            rootBlock = createDummyBlock(node);
        } else {
            rootBlock = getRootBlock(file, file.getViewProvider(), settings);
        }
        return new DocumentBasedFormattingModel(rootBlock, element.getProject(), settings, file.getFileType(), file);
    }

    /**
     * Do format my model!
     * @return false all the time to tell the {@link com.intellij.formatting.templateLanguages.TemplateLanguageFormattingModelBuilder}
     *              to not-not format our model (i.e. yes please!  Format away!)
     */
    @Override
    public boolean dontFormatMyModel() {
        return false;
    }

    private static class HandlebarsBlock extends TemplateLanguageBlock {

        protected HandlebarsBlock(@NotNull TemplateLanguageBlockFactory blockFactory, @NotNull CodeStyleSettings settings,
                                        @NotNull ASTNode node, @Nullable List<DataLanguageBlockWrapper> foreignChildren) {
            super(blockFactory, settings, node, foreignChildren);
        }

        /**
         * todo doc
         */
        @Override
        protected boolean shouldBuildBlockFor(ASTNode childNode) {
            return super.shouldBuildBlockFor(childNode)
                    && (childNode.getElementType() != HbTokenTypes.CONTENT
                    || (childNode.getTreeParent().getElementType() == HbTokenTypes.STATEMENTS
                    && childNode.getTreeParent().getTreeParent() != null
                    && childNode.getTreeParent().getTreeParent().getElementType() != HbTokenTypes.FILE));
        }

        /**
         * We indented the code in the following manner:
         *   * block expressions:
         *      {{#foo}}
         *          INDENTED_CONTENET
         *      {{/foo}}
         *   * inverse block expressions:
         *      {{^bar}}
         *          INDENTED_CONTENT
         *      {{/bar}}
         *   * conditional expressions use the "else" syntax:
         *      {{#if test}}
         *          INDENTED_CONTENT
         *      {{else}}
         *          INDENTED_CONTENT
         *      {{/if}}
         *   * conditional expressions use the "^" syntax:
         *
         *      {{#if test}}
         *          INDENTED_CONTENT
         *      {{^}}
         *          INDENTED_CONTENT
         *      {{/if}}
         *
         * This naturally maps to any "statements" expression in the grammar which is not a child of the
         * root "program" element.  See {@link com.dmarcotte.handlebars.parsing.HbParsing#parseProgram} and
         * {@link com.dmarcotte.handlebars.parsing.HbParsing#parseStatement(com.intellij.lang.PsiBuilder)} for the
         * relevant parts of the parser.
         */
        @Override
        public Indent getIndent() {
            // ignore whitespace
            if (getNode().getText().trim().length() == 0) {
                return Indent.getNoneIndent();
            }

            // todo formalize this and either make it more robust or make sure it's surrounded by a test (the assumption that FILE is two nodes up from BLOCK_WRAPPER is brittle)
            if (myNode.getTreeParent() != null
                    && myNode.getTreeParent().getElementType() == HbTokenTypes.STATEMENTS
                    && (getParent() instanceof DataLanguageBlockWrapper
                        || myNode.getTreeParent().getTreeParent().getElementType() != HbTokenTypes.FILE)) {
                return Indent.getNormalIndent();
            }

            return Indent.getNoneIndent();
        }

        /**
         * TODO implement alignment for "stacked" mustache content.  i.e.:
         *      {{foo bar="baz"
         *            bat="bam"}} <- note the alignment here
         */
        @Override
        public Alignment getAlignment() {
            return null;
        }

        @Override
        protected IElementType getTemplateTextElementType() {
            return HbTokenTypes.TEMPLATE_ELEMENT_TYPE;
        }

        @Override
        public boolean isRequiredRange(TextRange range) {
            // todo not sure if there's ever a case where we should say true
            return false;
        }

        /**
         * This method handles indent and alignment on Enter keypresses.
         * todo implement this
         *
         * todo if/when we implement alignment, update this method to do alignment properly
         *  {{#foo}}
         *      SOMESTUFF<ENTER>
         *      |<-- caret lands here, instead of indented
         *      TEXT
         */
        @NotNull
        @Override
        public ChildAttributes getChildAttributes(int newChildIndex) {
            //dummy impl for now
            return new ChildAttributes(Indent.getNoneIndent(), null);
        }
    }
}
