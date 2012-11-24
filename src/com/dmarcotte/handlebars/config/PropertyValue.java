package com.dmarcotte.handlebars.config;

/**
 * Formalizes the values of properties which we will persist using {@link com.intellij.ide.util.PropertiesComponent}
 *
 * See also {@link Property}
 */
enum PropertyValue {
    ENABLED {
        @Override
        public String getStringValue() {
            return "enabled";
        }
    },
    DISABLED {
        @Override
        public String getStringValue() {
            return "disabled";
        }
    };

    /**
     * The String which will actually be persisted in a user's properties using {@link com.intellij.ide.util.PropertiesComponent}.
     *
     * This value must be unique amongst PropertyValue entries
     *
     * IMPORTANT: these should probably never change so that we don't lose a user's preferences between releases.
     * See also {@link com.dmarcotte.handlebars.config.Property#getStringName()}
     */
    public abstract String getStringValue();
}
