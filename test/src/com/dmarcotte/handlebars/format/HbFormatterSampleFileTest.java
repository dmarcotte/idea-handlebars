package com.dmarcotte.handlebars.format;

public class HbFormatterSampleFileTest extends HbFormatterTest {

    public void testNonTrivialFile()
            throws Exception {
        doFileBasedTest("SampleFile.hbs");
    }
}
