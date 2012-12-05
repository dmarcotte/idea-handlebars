package com.dmarcotte.handlebars.config;

import com.dmarcotte.handlebars.HbBundle;
import com.intellij.application.options.editor.CodeFoldingOptionsProvider;
import com.intellij.openapi.options.BeanConfigurable;

public class HbFoldingOptionsProvider
        extends BeanConfigurable<HbFoldingOptionsProvider.HbCodeFoldingOptionsBean> implements CodeFoldingOptionsProvider {

    public static class HbCodeFoldingOptionsBean {
        private boolean AUTO_COLLAPSE_BLOCKS = false;

        public boolean isAutoCollapseBlocks() {
            AUTO_COLLAPSE_BLOCKS = HbConfig.isAutoCollapseBlocksEnabled();
            return AUTO_COLLAPSE_BLOCKS;
        }

        public void setAutoCollapseBlocks(boolean value) {
            AUTO_COLLAPSE_BLOCKS = value;
            HbConfig.setAutoCollapseBlocks(value);
        }
    }

    public HbFoldingOptionsProvider() {
        super(new HbCodeFoldingOptionsBean());

        checkBox("autoCollapseBlocks", HbBundle.message("hb.pages.folding.auto.collapse.blocks"));
    }
}
