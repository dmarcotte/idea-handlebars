package com.dmarcotte.handlebars.file;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.search.FileTypeIndex;
import com.intellij.psi.search.GlobalSearchScope;
import com.intellij.util.indexing.FileBasedIndex;
import com.dmarcotte.handlebars.psi.HbPsiFile;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HbFileUtil {
	public static List<HbPsiFile> findFiles(Project project, String key) {
		List<HbPsiFile> result = new ArrayList<HbPsiFile>();
		Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, HbFileType.INSTANCE,
				GlobalSearchScope.allScope(project));
		for (VirtualFile virtualFile : virtualFiles) {
			HbPsiFile hbFile = (HbPsiFile) PsiManager.getInstance(project).findFile(virtualFile);
			if (hbFile != null) {
				if (hbFile.getName().matches(key)) {
					result.add(hbFile);
				}
			}
		}
		return result;
	}

	public static List<HbPsiFile> findFiles(Project project) {
		List<HbPsiFile> result = new ArrayList<HbPsiFile>();
		Collection<VirtualFile> virtualFiles = FileBasedIndex.getInstance().getContainingFiles(FileTypeIndex.NAME, HbFileType.INSTANCE,
				GlobalSearchScope.allScope(project));
		for (VirtualFile virtualFile : virtualFiles) {
			HbPsiFile hbFile = (HbPsiFile) PsiManager.getInstance(project).findFile(virtualFile);
			if (hbFile != null) {
				Collections.addAll(result, hbFile);
			}
		}
		return result;
	}
}