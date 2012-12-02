package com.dmarcotte.handlebars.config;

import com.intellij.ide.util.PropertiesComponent;

/**
 * Class responsible for reads and writes of properties
 */
class PropertyAccessor {

    private final PropertiesComponent myPropertiesComponent;

    PropertyAccessor(PropertiesComponent myPropertiesComponent) {
        this.myPropertiesComponent = myPropertiesComponent;
    }

    PropertyValue getPropertyValue(Property property) {

        // We getOrInit to ensure that the default is written for this user the first time it is fetched
        // This will ensure that users preferences stay stable in the future, even if defaults change
        String propertyStringValue
                = myPropertiesComponent.getOrInit(property.getStringName(), property.getDefault().getStringValue());

        PropertyValue returnPropertyValue = null;
        for (PropertyValue propertyValue : property.getSupportedValues()) {
            if (propertyValue.getStringValue().equals(propertyStringValue)) {
                returnPropertyValue = propertyValue;
            }
        }

        if (returnPropertyValue == null) {
            throw new IllegalStateException("Retrieved property value \"" + propertyStringValue + "\" does not correspond to any supported PropertyValues for this Property");
        }

        return returnPropertyValue;
    }

    void setPropertyValue(Property property, PropertyValue propertyValue) {
        // sanity check that we're writing a supported value
        if (!property.getSupportedValues().contains(propertyValue)) {
            throw new IllegalStateException("This property does not support this value.");
        }

        myPropertiesComponent.setValue(property.getStringName(),
                                       propertyValue.getStringValue());
    }
}
