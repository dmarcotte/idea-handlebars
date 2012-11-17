package com.dmarcotte.handlebars.config;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Formalizes the properties which we will persist using {@link com.intellij.ide.util.PropertiesComponent}
 *
 * See also {@link PropertyValue}
 */
enum Property {
    AUTO_GENERATE_CLOSE_TAG {
        @Override
        public String getStringName() {
            // please excuse the "disabled" in this name.  This is an artifact from an earlier approach
            //      to properties, which we keep for backwards compatibility
            return "HbDisableAutoGenerateCloseTag";
        }

        @Override
        public PropertyValue getDefault() {
            return PropertyValue.ENABLED;
        }

        @Override
        public Set<PropertyValue> getSupportedValues() {
            Set<PropertyValue> supportedValues = new HashSet<PropertyValue>();
            supportedValues.add(PropertyValue.ENABLED);
            supportedValues.add(PropertyValue.DISABLED);
            return Collections.unmodifiableSet(supportedValues);
        }
    },

    FORMATTER {
        @Override
        public String getStringName() {
            return "HbFormatter";
        }

        @Override
        public PropertyValue getDefault() {
            return PropertyValue.ENABLED;
        }

        @Override
        public Set<PropertyValue> getSupportedValues() {
            Set<PropertyValue> supportedValues = new HashSet<PropertyValue>();
            supportedValues.add(PropertyValue.ENABLED);
            supportedValues.add(PropertyValue.DISABLED);
            return Collections.unmodifiableSet(supportedValues);
        }
    };

    /**
     * The String which will actually be persisted in a user's properties using {@link com.intellij.ide.util.PropertiesComponent}.
     *
     * This value must be unique amongst Property entries
     *
     * IMPORTANT: these should probably never change so that we don't lose a user's preferences between releases.
     * See also {@link com.dmarcotte.handlebars.config.PropertyValue#getStringValue()}
     */
    public abstract String getStringName();

    /**
     * The default/initial value for a user
     */
    public abstract PropertyValue getDefault();

    /**
     * The set of possible values this property supports.
     *
     * IMPORTANT: this set should never have elements removed.  As in {@link #getStringName},
     * we need to maintain a users preferences across releases.
     */
    public abstract Set<PropertyValue> getSupportedValues();
}
