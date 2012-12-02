package com.dmarcotte.handlebars.format;

public class HbFormatterSampleFileTest extends HbFormatterTest {

    public void testSampleFile1()
            throws Exception {
        doFileBasedTest("TodosSampleFile.hbs");
    }

    public void testSampleFile2()
            throws Exception {
        doFileBasedTest("ContactsSampleFile.hbs");
    }
}
