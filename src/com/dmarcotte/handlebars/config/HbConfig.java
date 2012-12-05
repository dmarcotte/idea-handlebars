package com.dmarcotte.handlebars.config;

import com.intellij.ide.util.PropertiesComponent;

import static com.dmarcotte.handlebars.config.Property.*;
import static com.dmarcotte.handlebars.config.PropertyValue.DISABLED;
import static com.dmarcotte.handlebars.config.PropertyValue.ENABLED;

public class HbConfig {

    public static boolean isAutoGenerateCloseTagEnabled() {
        return getPropertyValue(AUTO_GENERATE_CLOSE_TAG) == ENABLED;
    }

    public static void setAutoGenerateCloseTagEnabled(boolean enabled) {
        setPropertyValue(AUTO_GENERATE_CLOSE_TAG, enabled ? ENABLED : DISABLED);
    }

    public static boolean isFormattingEnabled() {
        return getPropertyValue(FORMATTER) == ENABLED;
    }

    public static void setFormattingEnabled(boolean enabled) {
        setPropertyValue(FORMATTER, enabled ? ENABLED : DISABLED);
    }

    public static boolean isCustomBlockEnabled() {
        return getPropertyValue(CUSTOM_OPEN_BLOCK) == ENABLED;
    }

    public static void setCustomBlockEnabled(boolean enabled) {
        setPropertyValue(CUSTOM_OPEN_BLOCK, enabled ? ENABLED : DISABLED);
    }

    public static boolean isAutoCollapseBlocksEnabled() {
        return getPropertyValue(AUTO_COLLAPSE_BLOCKS) == ENABLED;
    }

    public static void setAutoCollapseBlocks(boolean enabled) {
        setPropertyValue(AUTO_COLLAPSE_BLOCKS, enabled ? ENABLED : DISABLED);
    }

    private static PropertyValue getPropertyValue(Property property) {
        return new PropertyAccessor(PropertiesComponent.getInstance())
                .getPropertyValue(property);
    }

    private static void setPropertyValue(Property property, PropertyValue value) {
        new PropertyAccessor(PropertiesComponent.getInstance())
                .setPropertyValue(property, value);
    }
}
