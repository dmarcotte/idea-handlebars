package com.dmarcotte.handlebars.pages;

import com.dmarcotte.handlebars.HbBundle;
import com.dmarcotte.handlebars.config.HbConfig;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class HbConfigurationPage implements SearchableConfigurable {
    private JCheckBox myAutoGenerateClosingTagCheckBox;
    private JPanel myWholePanel;
    private JCheckBox myFormattingCheckBox;
    private JCheckBox myCustomOpenBlock;

    @NotNull
    @Override
    public String getId() {
        return "editor.preferences.handlebarsOptions";
    }

    @Override
    public Runnable enableSearch(String option) {
        return null;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return HbBundle.message("hb.pages.options.title");
    }

    public Icon getIcon() {
        return null;
    }

    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public JComponent createComponent() {
        return myWholePanel;
    }

    @Override
    public boolean isModified() {
        return myAutoGenerateClosingTagCheckBox.isSelected() != HbConfig.isAutoGenerateCloseTagEnabled()
                || myFormattingCheckBox.isSelected() != HbConfig.isFormattingEnabled()
                || myCustomOpenBlock.isSelected() != HbConfig.isCustomBlockEnabled();

    }

    @Override
    public void apply() throws ConfigurationException {
        HbConfig.setAutoGenerateCloseTagEnabled(myAutoGenerateClosingTagCheckBox.isSelected());
        HbConfig.setFormattingEnabled(myFormattingCheckBox.isSelected());
        HbConfig.setCustomBlockEnabled(myCustomOpenBlock.isSelected());
    }

    @Override
    public void reset() {
        myAutoGenerateClosingTagCheckBox.setSelected(HbConfig.isAutoGenerateCloseTagEnabled());
        myFormattingCheckBox.setSelected(HbConfig.isFormattingEnabled());
        myCustomOpenBlock.setSelected(HbConfig.isCustomBlockEnabled());
    }

    @Override
    public void disposeUIResources() {
    }
}
