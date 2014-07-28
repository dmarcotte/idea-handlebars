package com.dmarcotte.handlebars.file;

import com.dmarcotte.handlebars.HbIcons;
import com.dmarcotte.handlebars.psi.HbPsiFile;
import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.Iconable;
import com.intellij.psi.PsiElement;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class HbIconProvider extends IconProvider {

  @Nullable
  @Override
  public Icon getIcon(@NotNull PsiElement element, @Iconable.IconFlags int flags) {
    if (element instanceof HbPsiFile) {
      return HbIcons.FILE_ICON;
    }

    return null;
  }
}
