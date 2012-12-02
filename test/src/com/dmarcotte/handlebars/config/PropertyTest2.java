package com.dmarcotte.handlebars.config;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RunWith (value = Parameterized.class)
public class PropertyTest2 {

    private final PropertyTestDefinition propertyTestDefinition;

    static final List<PropertyTestDefinition> PROPERTY_TEST_DEFINITIONS = new ArrayList<PropertyTestDefinition>();
    static {
        PROPERTY_TEST_DEFINITIONS.add(new PropertyTestDefinition(Property.AUTO_GENERATE_CLOSE_TAG,
                                                                 "HbDisableAutoGenerateCloseTag",
                                                                 valueSet(PropertyValue.ENABLED, PropertyValue.DISABLED)));

        PROPERTY_TEST_DEFINITIONS.add(new PropertyTestDefinition(Property.FORMATTER,
                                                                 "HbFormatter",
                                                                 valueSet(PropertyValue.ENABLED, PropertyValue.DISABLED)));
    }

    @Parameterized.Parameters
    public static List<Object[]> parameters() {
        List<Object[]> testParameters = new ArrayList<Object[]>();
        for (PropertyTestDefinition propertyTestDefinition : PROPERTY_TEST_DEFINITIONS) {
            testParameters.add(new Object[] { propertyTestDefinition });
        }

        return testParameters;
    }

    public PropertyTest2(PropertyTestDefinition propertyTestDefinition) {
        this.propertyTestDefinition = propertyTestDefinition;
    }

    @Test
    public void testPropertyNameBackwardsCompatibility() {

        Assert.assertEquals("Error in " + propertyTestDefinition.property.name() +
                                    ".\n\tPersisted property name changed.  This will mess up user preferences without some sort of migration strategy.\n\n",
                            propertyTestDefinition.expectedPropertyName,
                            propertyTestDefinition.property.getStringName());
    }

    @Test
    public void testPropertyValuesBackwardsCompatibility() {
        Assert.assertEquals("Error in " + propertyTestDefinition.property.name() +
                                    ".\n\tSupported " + PropertyValue.class.getSimpleName()  + " declaration has changed.  If a supported value has been removed, " +
                                    "his will mess up user preferences without some sort of migration strategy.\n" +
                                    "If this is an additive change, update this test.\n\n",
                            propertyTestDefinition.expectedSupportedValues,
                            propertyTestDefinition.property.getSupportedValues());
    }

    /**
     * Associates a {@link Property} with its expected attributes to ensure stability and backwards compatibility
     */
    static class PropertyTestDefinition {
        final Property property;
        final String expectedPropertyName;
        final Set<PropertyValue> expectedSupportedValues;

        PropertyTestDefinition(Property property, String expectedPropertyName, Set<PropertyValue> expectedSupportedValues) {
            this.property = property;
            this.expectedPropertyName = expectedPropertyName;
            this.expectedSupportedValues = expectedSupportedValues;
        }
    }

    private static Set<PropertyValue> valueSet(PropertyValue... expectedPropertyValues) {
        Set<PropertyValue> propertyValues = new HashSet<PropertyValue>();
        Collections.addAll(propertyValues, expectedPropertyValues);
        return propertyValues;
    }
}
