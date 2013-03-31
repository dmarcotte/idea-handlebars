package com.dmarcotte.handlebars.editor.lines;

import com.dmarcotte.handlebars.HbBundle;
import com.dmarcotte.handlebars.file.HbFileType;
import com.dmarcotte.handlebars.file.HbFileUtil;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.dmarcotte.handlebars.psi.HbPsiElement;
import com.dmarcotte.handlebars.psi.HbPsiFile;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerInfo;
import com.intellij.codeInsight.daemon.RelatedItemLineMarkerProvider;
import com.intellij.codeInsight.navigation.NavigationGutterIconBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.List;

public class HbLineMarkerProvider extends RelatedItemLineMarkerProvider {
	@Override
	protected void collectNavigationMarkers(@NotNull PsiElement element, Collection<? super RelatedItemLineMarkerInfo> result) {
		if (element instanceof HbPsiElement) {
			HbPsiElement hbElement = (HbPsiElement) element;
			IElementType nodeType = hbElement.getNode().getElementType();
			if (nodeType == HbTokenTypes.PARTIAL_NAME) {
				String fileName = hbElement.getText();
				Project project = element.getProject();
				PsiFile currentFile = hbElement.getContainingFile();
				String fileExtension = currentFile.getName().split("\\.")[1];
				final List<HbPsiFile> properties = HbFileUtil.findFiles(project, fileName+"."+fileExtension);
				if (properties.size() > 0) {
					NavigationGutterIconBuilder<PsiElement> builder =
							NavigationGutterIconBuilder.create(HbFileType.FILE_ICON).
									setTargets(properties).
									setTooltipText(HbBundle.message("hb.editor.lines.tooltip"));
					result.add(builder.createLineMarkerInfo(element));
				}
			}
		}
	}
}