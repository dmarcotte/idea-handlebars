package com.dmarcotte.handlebars;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.vfs.CharsetToolkit;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.encoding.EncodingManager;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

import javax.swing.Icon;
import java.nio.charset.Charset;

public class HbFileType extends LanguageFileType {
    public static final Icon FILE_ICON = IconLoader.getIcon("/fileTypes/properties.png");
    public static final LanguageFileType INSTANCE = new HbFileType();
    @NonNls
    public static final String DEFAULT_EXTENSION = "handlebars";
    @NonNls public static final String DOT_DEFAULT_EXTENSION = "."+DEFAULT_EXTENSION;

    private HbFileType() {
        super(HbLanguage.INSTANCE);
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
        Charset charset = EncodingManager.getInstance().getDefaultCharsetForPropertiesFiles(file);
        String defaultCharsetName = charset == null ? CharsetToolkit.getDefaultSystemCharset().name() : charset.name();
        return defaultCharsetName;
    }
}
