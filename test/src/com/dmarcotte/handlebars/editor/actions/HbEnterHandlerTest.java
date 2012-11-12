package com.dmarcotte.handlebars.editor.actions;

/**
 * todo: disable formatter for this test?
 */
public class HbEnterHandlerTest extends HbActionHandlerTest {

    /**
     * On Enter between matching open/close tags,
     * expect an extra newline to be inserted with the caret placed
     * between the tags
     * * todo adjust formatter settings?
     */
    public void testEnterBetweenMatchingHbTags() {
        doEnterTest(

                "{{#foo}}<caret>{{/foo}}",

                "{{#foo}}\n" +
                "    <caret>\n" +
                "{{/foo}}"
        );
    }

    /**
     * On Enter between MIS-matched open/close tags,
     * expect a standard newline
     */
    public void testEnterBetweenMismatchedHbTags() {
        doEnterTest(

                "{{#foo}}<caret>{{/bar}}" +
                "stuff",

                "{{#foo}}\n" +
                "<caret>{{/bar}}" +
                "stuff"
        );
    }

    /**
     * On Enter at an open tag with no close tag,
     * expect a standard newline
     * (Notice that we have "other stuff" our test string.  When the caret is at the file
     * boundary, it's actually a special case.  See {@link #testEnterAtOpenTagOnFileBoundary}
     */
    public void testEnterAtOpenTag() {
        doEnterTest(

                "{{#foo}}<caret>" +
                "other stuff",

                "{{#foo}}\n" +
                "    <caret>" +
                "other stuff"

        );
    }

    /**
     * On Enter at an open tag with no close tag,
     * expect a standard newline.
     *
     * Note: this used to result in an error.  The was a bug where we checked beyond the
     * end of the file for a close tag to go with this open tag.
     *
     * todo adjust formatter settings?
     */
    public void testEnterAtOpenTagOnFileBoundary() {
        doEnterTest(

                "{{#foo}}<caret>",

                "{{#foo}}\n" +
                "    <caret>"
        );
    }
}
