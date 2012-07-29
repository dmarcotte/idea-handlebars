package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.psi.HbPsiFile;
import com.dmarcotte.handlebars.psi.HbPsiUtil;
import com.intellij.codeInsight.editorActions.enter.EnterHandlerDelegateAdapter;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.editor.highlighter.HighlighterIterator;
import com.intellij.openapi.util.Ref;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import org.jetbrains.annotations.NotNull;

public class HbEnterBetweenTagsHandler extends EnterHandlerDelegateAdapter {
    public Result preprocessEnter(@NotNull final PsiFile file, @NotNull final Editor editor, @NotNull final Ref<Integer> caretOffset, @NotNull final Ref<Integer> caretAdvance,
                                  @NotNull final DataContext dataContext, final EditorActionHandler originalHandler) {
        if (file instanceof HbPsiFile && isBetweenHbTags(editor, file, caretOffset.get())) {
            originalHandler.execute(editor, dataContext);
            return Result.DefaultForceIndent;
        }
        return Result.Continue;
    }

    private static boolean isBetweenHbTags(Editor editor, PsiFile file, int offset) {
        if (offset == 0) return false;
        CharSequence chars = editor.getDocument().getCharsSequence();
        if (chars.charAt(offset - 1) != '}') return false;

        EditorHighlighter highlighter = ((EditorEx)editor).getHighlighter();
        HighlighterIterator iterator = highlighter.createIterator(offset - 1);

        final PsiElement openerElement = file.findElementAt(iterator.getStart());

        PsiElement openTag = HbPsiUtil.findParentOpenTagElement(openerElement);

        if (openTag == null) {
            return false;
        }

        iterator.advance();

        final PsiElement closerElement = file.findElementAt(iterator.getStart());

        PsiElement closeTag = HbPsiUtil.findParentCloseTagElement(closerElement);

        // if we got this far, we're between open and close tags iff this is a close tag
        return closeTag != null;
    }
}
