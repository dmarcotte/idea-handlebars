package com.dmarcotte.handlebars.config;

import com.intellij.ide.util.PropertiesComponent;

public class HbConfig {
    private static final String HB_DISABLE_AUTO_GENERATE_CLOSE_TAG = "HbDisableAutoGenerateCloseTag";

    public static boolean isAutoGenerateCloseTagEnabled() {
        // the thing we store feels a bit backwards since we store the fact that we want to
        // disable this feature so that the default is enabled
        return !PropertiesComponent.getInstance().isValueSet(HB_DISABLE_AUTO_GENERATE_CLOSE_TAG);
    }

    public static void setAutoGenerateCloseTagEnabled(boolean enabled) {
        // as noted in the getter above, we store "disabled" so that unset defaults to enabled
        if (enabled) {
            PropertiesComponent.getInstance().unsetValue(HB_DISABLE_AUTO_GENERATE_CLOSE_TAG);
        } else {
            PropertiesComponent.getInstance().setValue(HB_DISABLE_AUTO_GENERATE_CLOSE_TAG, "disabled");
        }
    }
}
