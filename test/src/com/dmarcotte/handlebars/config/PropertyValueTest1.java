package com.dmarcotte.handlebars.config;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class PropertyValueTest1 {

    /**
     * This test will fail if properties are added/removed in {@link com.dmarcotte.handlebars.config.PropertyValue}
     *
     * When/if it fails:
     * - ensure the change is backwards compatible (i.e. when users upgrade, their properties will still be in the same state)
     * - update this test with the new number of properties to get it passing
     */
    @Test
    public void testPropertyValuesChange() {
        // expectedNumberOfPropertyValueFields represents the number of enum entries plus one for the $VALUES that every enum gets
        int expectedNumberOfPropertyValueFields = 3;

        org.junit.Assert.assertEquals("Declared property values in enum \"" + PropertyValue.class.getSimpleName() + "\" have changed!  Ensure that changes are backwards compatible.",
                                      expectedNumberOfPropertyValueFields,
                                      PropertyValue.class.getDeclaredFields().length);
    }

    @Test
    public void ensureAllPropertyValuesAreTested() {
        Set<PropertyValue> properties = new HashSet<PropertyValue>(Arrays.asList(PropertyValue.values()));

        for (PropertyValueTest2.PropertyValueTestDefinition propertyValueTestDefinition : PropertyValueTest2.PROPERTY_VALUE_TEST_DEFINITIONS) {
            properties.remove(propertyValueTestDefinition.propertyValue);
        }

        org.junit.Assert.assertTrue("The following " + PropertyValue.class.getSimpleName() + " entries do not have corresponding " +
                                            PropertyValueTest2.PropertyValueTestDefinition.class.getSimpleName() +
                                            " tests defined: " + properties.toString(),
                                    properties.isEmpty());
    }

    @Test
    public void testPropertyStringValueUniqueness() {
        Set<String> propertyValueStrings = new HashSet<String>();

        for (PropertyValue propertyValue : PropertyValue.values()) {
            String propertyValueString = propertyValue.getStringValue();
            Assert.assertFalse("String value \"" + propertyValueString + "\" is not unique in PropertyValue",
                               propertyValueStrings.contains(propertyValueString));
            propertyValueStrings.add(propertyValueString);
        }
    }
}
