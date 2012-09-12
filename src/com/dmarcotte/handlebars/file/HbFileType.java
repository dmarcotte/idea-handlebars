package com.dmarcotte.handlebars.file;

import com.dmarcotte.handlebars.HbBundle;
import com.dmarcotte.handlebars.HbLanguage;
import com.dmarcotte.handlebars.HbTemplateHighlighter;
import com.intellij.ide.highlighter.HtmlFileType;
import com.intellij.openapi.editor.colors.EditorColorsScheme;
import com.intellij.openapi.editor.highlighter.EditorHighlighter;
import com.intellij.openapi.fileTypes.EditorHighlighterProvider;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.openapi.fileTypes.FileTypeEditorHighlighterProviders;
import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.Icon;
import java.nio.charset.Charset;

public class HbFileType extends LanguageFileType {
    public static final Icon FILE_ICON = IconLoader.getIcon("/icons/handlebars_icon.png");
    public static final LanguageFileType INSTANCE = new HbFileType();
    @NonNls
    public static final String DEFAULT_EXTENSION = "handlebars;hbs;mustache";

    private HbFileType() {
        super(HbLanguage.INSTANCE);

        FileTypeEditorHighlighterProviders.INSTANCE.addExplicitExtension(this, new EditorHighlighterProvider() {
            public EditorHighlighter getEditorHighlighter(@Nullable Project project,
                                                          @NotNull FileType fileType,
                                                          @Nullable VirtualFile virtualFile,
                                                          @NotNull EditorColorsScheme editorColorsScheme) {
                return new HbTemplateHighlighter(project, virtualFile, editorColorsScheme);
            }
        });
    }

    @NotNull
    public String getName() {
        return "Handlebars";
    }

    @NotNull
    public String getDescription() {
        return HbBundle.message("hb.files.file.type.description");
    }

    @NotNull
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    public Icon getIcon() {
        return FILE_ICON;
    }

    public String getCharset(@NotNull VirtualFile file, final byte[] content) {
        return HtmlFileType.INSTANCE.getCharset(file, content);
    }

    public Charset extractCharsetFromFileContent(@Nullable final Project project, @Nullable final VirtualFile file, @NotNull final String content) {
        return HtmlFileType.INSTANCE.extractCharsetFromFileContent(project, file, content);
    }
}
