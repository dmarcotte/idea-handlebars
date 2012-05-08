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
        String previousChar = editor.getDocument().getText(new TextRange(offset - 1, offset));

        // for files handled by the Handlebars plugin, we suppress "}" auto-complete when we see "{{"
        if (file.getViewProvider() instanceof HbFileViewProvider
            && c == '{'
            && previousChar.equals("{")) {
            editor.getDocument().insertString(offset, Character.toString(c));
            editor.getCaretModel().moveToOffset(offset + 1);
            return Result.STOP;
        }

        return Result.CONTINUE;
    }
}
