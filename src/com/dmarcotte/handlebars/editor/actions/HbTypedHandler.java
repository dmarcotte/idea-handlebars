package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.HbLanguage;
import com.dmarcotte.handlebars.config.HbConfig;
import com.dmarcotte.handlebars.file.HbFileViewProvider;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.dmarcotte.handlebars.psi.HbPsiElement;
import com.dmarcotte.handlebars.psi.HbPsiUtil;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class HbTypedHandler extends TypedHandlerDelegate {
    @Override
    public Result beforeCharTyped(char c, Project project, Editor editor, PsiFile file, FileType fileType) {
        int offset = editor.getCaretModel().getOffset();

        if (offset == 0 || offset > editor.getDocument().getTextLength()) {
            return Result.CONTINUE;
        }

        String previousChar = editor.getDocument().getText(new TextRange(offset - 1, offset));

        if (file.getViewProvider() instanceof HbFileViewProvider) {
            PsiDocumentManager.getInstance(project).commitAllDocuments();

            // we suppress the built-in "}" auto-complete when we see "{{"
            if (c == '{' && previousChar.equals("{")) {
                // since the "}" autocomplete is built in to IDEA, we need to hack around it a bit by
                // intercepting it before it is inserted, doing the work of inserting for the user
                // by inserting the '{' the user just typed...
                editor.getDocument().insertString(offset, Character.toString(c));
                // ... and position their caret after it as they'd expect...
                editor.getCaretModel().moveToOffset(offset + 1);
                // ... then finally telling subsequent responses to this charTyped to do nothing
                return Result.STOP;
            }
        }

        return Result.CONTINUE;
    }

    @Override
    public Result charTyped(char c, Project project, Editor editor, @NotNull PsiFile file) {
        int offset = editor.getCaretModel().getOffset();
        FileViewProvider provider = file.getViewProvider();

        if (offset < 2 || offset > editor.getDocument().getTextLength()) {
            return Result.CONTINUE;
        }

        String previousChar = editor.getDocument().getText(new TextRange(offset - 2, offset - 1));

        if (file.getViewProvider() instanceof HbFileViewProvider) {
            PsiDocumentManager.getInstance(project).commitAllDocuments();

            autoInsertCloseTag(c, previousChar, offset, editor, provider);
        }

        return Result.CONTINUE;
    }

    /**
     * When appropriate, auto-inserts Handlebars close tags.  i.e.  When "{{#tagId}}" or "{{^tagId}} is typed,
     *      {{/tagId}} is automatically inserted
     */
    private void autoInsertCloseTag(char c, String previousChar, int offset, Editor editor, FileViewProvider provider) {
        if (!HbConfig.isAutoGenerateCloseTagEnabled()) {
            return;
        }

        // if we're looking at a close stache, we'll auto complete an end tag when appropriate
        if (c == '}' && previousChar.equals("}")) {
            PsiElement elementAtCaret = provider.findElementAt(offset - 1, HbLanguage.class);

            PsiElement openTag = HbPsiUtil.findParentOpenTagElement(elementAtCaret);

            if (openTag != null && openTag.getChildren().length > 1) {
                // we've got an open block type stache... find its ID
                HbPsiElement idElem = (HbPsiElement) openTag.getChildren()[1].getFirstChild();

                if (idElem != null
                        && idElem.getNode().getElementType() == HbTokenTypes.ID) {
                    // insert the corresponding close tag
                    editor.getDocument().insertString(offset, "{{/" + idElem.getText() + "}}");
                }
            }
        }
    }
}
