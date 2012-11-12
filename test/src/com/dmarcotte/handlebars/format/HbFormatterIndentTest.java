package com.dmarcotte.handlebars.format;

public class HbFormatterIndentTest extends HbFormatterTest {

    public void testSimpleStache() {
        doStringBasedTest(

                "{{foo}}",

                "{{foo}}"
        );
    }

    public void testSimpleBlock() {
        // todo set the settings?
        doStringBasedTest(

              "{{#foo}}\n" +
              "{{bar}}\n" +
              "{{/foo}}\n",

              "{{#foo}}\n" +
              "    {{bar}}\n" +
              "{{/foo}}\n"
        );
    }

    public void testNestedBlocks() {
        doStringBasedTest(

                "{{#foo}}\n" +
                "{{#bar}}\n" +
                "{{#bat}}\n" +
                "{{baz}}\n" +
                "{{/bat}}\n" +
                "{{/bar}}\n" +
                "{{/foo}}",

                "{{#foo}}\n" +
                "    {{#bar}}\n" +
                "        {{#bat}}\n" +
                "            {{baz}}\n" +
                "        {{/bat}}\n" +
                "    {{/bar}}\n" +
                "{{/foo}}"
        );
    }

    public void testSimpleStacheInDiv() {
        doStringBasedTest(

                "<div>\n" +
                "{{foo}}\n" +
                "</div>",

                "<div>\n" +
                "    {{foo}}\n" +
                "</div>"
        );
    }

    public void testMarkupInBlockStache() {
        doStringBasedTest(

                "{{#foo}}\n" +
                "<span></span>\n" +
                "{{/foo}}",

                "{{#foo}}\n" +
                "    <span></span>\n" +
                "{{/foo}}"
        );
    }

    public void testSimpleBlockInDiv() {
        doStringBasedTest(

                "<div>\n" +
                "{{#foo}}\n" +
                "{{bar}}\n" +
                "{{/foo}}\n" +
                "</div>",

                "<div>\n" +
                "    {{#foo}}\n" +
                "        {{bar}}\n" +
                "    {{/foo}}\n" +
                "</div>"
        );
    }

    public void testAttributeStaches() {
        doStringBasedTest(

                "<div {{foo}}>\n" +
                        "<div class=\"{{bar}}\">\n" +
                        "sweeet\n" +
                        "</div>\n" +
                        "</div>",

                "<div {{foo}}>\n" +
                        "    <div class=\"{{bar}}\">\n" +
                        "        sweeet\n" +
                        "    </div>\n" +
                        "</div>"
        );
    }

    public void testMixedContentInDiv() {
        doStringBasedTest(

                "<div>\n" +
                "{{#foo}}\n" +
                "<span class=\"{{bat}}\">{{bar}}</span>\n" +
                "{{/foo}}\n" +
                "</div>",

                "<div>\n" +
                "    {{#foo}}\n" +
                "        <span class=\"{{bat}}\">{{bar}}</span>\n" +
                "    {{/foo}}\n" +
                "</div>"
        );
    }
}
