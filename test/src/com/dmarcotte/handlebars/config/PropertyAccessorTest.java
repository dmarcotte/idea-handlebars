package com.dmarcotte.handlebars.config;

import com.intellij.ide.util.PropertiesComponent;
import junit.framework.Assert;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class PropertyAccessorTest {

    // grab a Property to use in this test.  NOTE: the specific property is not significant.
    private final Property myTestProperty = Property.FORMATTER;

    @Test
    public void testGetPropertyValue() {
        PropertiesComponentStub propertiesComponent = new PropertiesComponentStub();
        PropertyValue originalValue = PropertyValue.DISABLED;

        // simulate an existing value by setting it directly on the propertiesComponent
        propertiesComponent.setValue(myTestProperty.getStringName(), originalValue.getStringValue());

        PropertyValue propertyValue = new PropertyAccessor(propertiesComponent).getPropertyValue(myTestProperty);

        Assert.assertEquals("Problem fetching existing property", originalValue, propertyValue);
    }

    @Test
    public void testGetPropertyValueDefaulting() {
        PropertiesComponentStub propertiesComponent = new PropertiesComponentStub();

        PropertyValue expectedValue = myTestProperty.getDefault();
        PropertyValue propertyValue = new PropertyAccessor(propertiesComponent).getPropertyValue(myTestProperty);

        Assert.assertEquals("Default value should have been returned", expectedValue, propertyValue);
    }

    @Test
    public void testSetPropertyValue() {
        PropertiesComponentStub propertiesComponent = new PropertiesComponentStub();

        PropertyValue testValue = PropertyValue.DISABLED;
        new PropertyAccessor(propertiesComponent).setPropertyValue(myTestProperty, PropertyValue.DISABLED);

        // fetch the value directly to ensure PropertyAccessor didn't mess it up
        String propertiesComponentValue = propertiesComponent.getValue(myTestProperty.getStringName());

        Assert.assertEquals("Value was not properly persisted", testValue.getStringValue(), propertiesComponentValue);
    }

    private class PropertiesComponentStub extends PropertiesComponent {
        private final Map<String, String> fakeStorage = new HashMap<String, String>();
        @Override
        public void unsetValue(String name) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean isValueSet(String name) {
            return fakeStorage.containsKey(name);
        }

        @Override
        public String getValue(@NonNls String name) {
            return fakeStorage.get(name);
        }

        @Override
        public void setValue(@NonNls String name, String value) {
            fakeStorage.put(name, value);
        }

        @NotNull
        @Override
        public String getValue(@NonNls String name, @NotNull String defaultValue) {
            throw new UnsupportedOperationException();
        }

        @SuppressWarnings ("EmptyMethod") // see comment in method for why this is cool
        @Override
        public String getOrInit(@NonNls String name, String defaultValue) {
            // parent is implemented using isValueSet and getValue, so use that to keep things
            // true to form.  There is a tiny chance that will change and this test will start behaving odd...
            // hopefully if that happens, this comment helps resolve the issue
            return super.getOrInit(name, defaultValue);
        }
    }
}
