package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.file.HbFileViewProvider;
import com.intellij.codeInsight.editorActions.TypedHandlerDelegate;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiFile;

public class HbTypedHandler extends TypedHandlerDelegate {
    @Override
    public Result beforeCharTyped(char c, Project project, Editor editor, PsiFile file, FileType fileType) {
        int offset = editor.getCaretModel().getOffset();

        if (offset == 0) {
            return Result.CONTINUE;
        }

        String previousChar = editor.getDocument().getText(new TextRange(offset - 1, offset));

        // for files handled by the Handlebars plugin, we suppress "}" auto-complete when we see "{{"
        if (file.getViewProvider() instanceof HbFileViewProvider
            && c == '{'
            && previousChar.equals("{")) {

            // since the "}" autocomplete is built in to IDEA, we need to hack around it a bit by
            // intercepting it before it is inserted, doing the work of inserting for the user
            // by inserting the '{' the user just typed...
            editor.getDocument().insertString(offset, Character.toString(c));
            // ... and position their caret after it as they'd expect...
            editor.getCaretModel().moveToOffset(offset + 1);
            // ... then finally telling subsequent responses to this charTyped to do nothing
            return Result.STOP;
        }

        return Result.CONTINUE;
    }
}
