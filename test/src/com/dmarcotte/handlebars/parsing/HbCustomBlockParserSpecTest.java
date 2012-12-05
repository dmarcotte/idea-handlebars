package com.dmarcotte.handlebars.parsing;

import com.dmarcotte.handlebars.config.HbConfig;

public class HbCustomBlockParserSpecTest extends HbParserTest {

    public void testMustachesWithCustomBlockMarkerDisabled() {
        HbConfig.setCustomBlockEnabled(false);
        doTest(true);
    }

    public void testMustachesWithCustomBlockMarkerDisabledByDefault() {
        doTest(true);
    }

    public void testMustachesWithCustomBlockMarkerEnabled() {
        HbConfig.setCustomBlockEnabled(true);
        doTest(true);
    }

}
