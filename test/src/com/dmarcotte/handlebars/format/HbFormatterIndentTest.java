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

    public void testMixedContentInDiv1() {
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

    public void testMixedContentInDiv2() {
        doStringBasedTest(

                "<div>\n" +
                "{{#foo}}\n" +
                "bar {{baz}}\n" +
                "{{/foo}}\n" +
                "</div>",

                "<div>\n" +
                "    {{#foo}}\n" +
                "        bar {{baz}}\n" +
                "    {{/foo}}\n" +
                "</div>"
        );
    }

    public void testSimpleStacheInNestedDiv() {
        doStringBasedTest(

                "{{#foo}}\n" +
                "    <div>\n" +
                "{{bar}}\n" +
                "    </div>\n" +
                "{{/foo}}",

                "{{#foo}}\n" +
                "    <div>\n" +
                "        {{bar}}\n" +
                "    </div>\n" +
                "{{/foo}}"
        );
    }

    public void testBlockStacheInNestedDiv() {
        doStringBasedTest(

                "{{#foo}}\n" +
                "<div>\n" +
                "{{#bar}}\n" +
                "stuff\n" +
                "{{/bar}}\n" +
                "</div>\n" +
                "{{/foo}}",

                "{{#foo}}\n" +
                "    <div>\n" +
                "        {{#bar}}\n" +
                "            stuff\n" +
                "        {{/bar}}\n" +
                "    </div>\n" +
                "{{/foo}}"
        );
    }

    public void testNestedDivsInBlock() {
        doStringBasedTest(

                "{{#foo}}\n" +
                "<div>\n" +
                "<div>\n" +
                "<div>\n" +
                "{{bar}}\n" +
                "{{#foo}}\n" +
                "{{/foo}}\n" +
                "</div>\n" +
                "</div>\n" +
                "</div>\n" +
                "{{/foo}}",

                "{{#foo}}\n" +
                "    <div>\n" +
                "        <div>\n" +
                "            <div>\n" +
                "                {{bar}}\n" +
                "                {{#foo}}\n" +
                "                {{/foo}}\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "{{/foo}}"
        );
    }

    // todo indent line behaves differently from reformat file.  Sigh.  retest!
}
