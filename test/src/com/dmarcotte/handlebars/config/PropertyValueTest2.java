package com.dmarcotte.handlebars.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.List;

@RunWith (value = Parameterized.class)
public class PropertyValueTest2 {

    private final PropertyValueTestDefinition propertyValueTestDefinition;

    static final List<PropertyValueTestDefinition> PROPERTY_VALUE_TEST_DEFINITIONS = new ArrayList<PropertyValueTestDefinition>();
    static {
        PROPERTY_VALUE_TEST_DEFINITIONS.add(new PropertyValueTestDefinition(PropertyValue.ENABLED, "enabled"));
        PROPERTY_VALUE_TEST_DEFINITIONS.add(new PropertyValueTestDefinition(PropertyValue.DISABLED, "disabled"));
    }

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        List<Object[]> testParameters = new ArrayList<Object[]>();
        for (PropertyValueTestDefinition propertyvalueTestDefinition : PROPERTY_VALUE_TEST_DEFINITIONS) {
            testParameters.add(new Object[] { propertyvalueTestDefinition });
        }

        return testParameters;
    }

    public PropertyValueTest2(PropertyValueTestDefinition propertyValueTestDefinition) {
        this.propertyValueTestDefinition = propertyValueTestDefinition;
    }

    @Test
    public void testPropertyValueStringBackwardsCompatibility() {
        Assert.assertEquals("Error in " + propertyValueTestDefinition.propertyValue.name() +
                                    ".\n\tPersisted property value string changed.  This will mess up user preferences without some sort of migration strategy.\n\n",
                            propertyValueTestDefinition.expectedStringValue,
                            propertyValueTestDefinition.propertyValue.getStringValue());
    }

    /**
     * Associates a {@link PropertyValue} with its expected attributes to ensure stability and backwards compatibility
     */
    static class PropertyValueTestDefinition {
        final PropertyValue propertyValue;
        final String expectedStringValue;

        PropertyValueTestDefinition(PropertyValue propertyValue, String expectedStringValue) {
            this.propertyValue = propertyValue;
            this.expectedStringValue = expectedStringValue;
        }
    }
}
