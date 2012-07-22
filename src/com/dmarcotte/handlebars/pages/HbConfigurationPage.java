package com.dmarcotte.handlebars.pages;

import com.dmarcotte.handlebars.HbBundle;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.options.SearchableConfigurable;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class HbConfigurationPage implements SearchableConfigurable {
    private JCheckBox myAutoGenerateClosingTagCheckBox;
    private JPanel myWholePanel;

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
        return HbBundle.message("hb.pages.options.generate.title");
    }

    @Override
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
        return false;
    }

    @Override
    public void apply() throws ConfigurationException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void reset() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void disposeUIResources() {
    }
}
