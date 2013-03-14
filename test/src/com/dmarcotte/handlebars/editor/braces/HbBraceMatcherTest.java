package com.dmarcotte.handlebars.editor.braces;

import com.dmarcotte.handlebars.file.HbFileType;
import com.intellij.codeInsight.highlighting.BraceMatchingUtil;
import com.intellij.testFramework.IdeaTestCase;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;

public class HbBraceMatcherTest extends LightPlatformCodeInsightFixtureTestCase {

    public HbBraceMatcherTest() {
        IdeaTestCase.initPlatformPrefix();
    }

    /**
     * Heavy lifter for our brace match test.  Expects "fileText" to have two special tokens:
     * <ul>
     *     <li>&lt;caret&gt; - representing the position of the caret in the text</li>
     *     <li>&lt;brace_match&gt; - just before the brace which should be highlighted</li>
     * </ul>
     *
     * NOTE: Your &lt;caret&gt; should be placed in or before the brace you want it to highlight; the actual highlighter
     *  compensates for this, but we don't need to mess with that in this test since we just want to verify the
     *  matches.  If you get a "java.lang.IndexOutOfBoundsException: Wrong line: -1" error, your caret is probably
     *  sitting in whitespace rather than a brace (i.e. you have "}}&lt;caret&gt;" rather than "}&lt;caret&gt;}"
     */
    private void doBraceTest(String fileText) {
        String braceMatchIndicator = "<brace_match>";
        String caretIndicator = "<caret>";

        // compute the expected position of the matched brace (which does not include the chars from the caretIndicator)
        int expectedMatchedBracePosition = fileText.replace(caretIndicator, "").indexOf(braceMatchIndicator);
        assertTrue("Input test string must contain \"" + braceMatchIndicator + "\"", expectedMatchedBracePosition > -1);

        // remove the braceMatchIndicator from the string to prepare for the brace matcher,
        // and capture the position of the caretIndicator
        String cleanedFileText = fileText.replace(braceMatchIndicator, "");
        int caretPosition = cleanedFileText.indexOf(caretIndicator);
        assertTrue("Input test string must contain \"" + caretIndicator + "\"", caretPosition > -1);

        myFixture.configureByText(HbFileType.INSTANCE, cleanedFileText);

        boolean caretFirst = expectedMatchedBracePosition > caretPosition;
        int actualBraceMatchPosition
                = BraceMatchingUtil.getMatchedBraceOffset(myFixture.getEditor(),
                                                          caretFirst,
                                                          myFixture.getFile());

        // we want to have an easy to read result, so we insert a <brace_match> where
        // BraceMatchingUtil.getMatchedBraceOffset told us it should go.
        String result = new StringBuilder(cleanedFileText)
                // note that we need to compensate for the length of the caretIndicator if it comes before the braceMatchIndicator
                .insert(actualBraceMatchPosition + (caretFirst ? caretIndicator.length() : 0), braceMatchIndicator)
                .toString();

        assertEquals(fileText, result);
    }

    /**
     * Convenience property for quickly setting up brace match tests.  Note that all the
     * mustache ids (foo, foo2, bar, etc) are unique so that they can be easily targetted
     * by string replace functions.
     */
    private static String testSource =
            "{{#foo}}\n" +
            "    {{bar}}\n" +
            "    {{#foo2}}\n" +
            "        <div>\n" +
            "            {{^foo3}}\n" +
            "                Content\n" +
            "            {{/foo3}}\n" +
            "        </div>\n" +
            "        {{{baz}}}\n" +
            "        {{bat}}\n" +
            "        {{>partial}}\n" +
            "    {{/foo2}}\n" +
            "{{/foo}}";

    public void testSimpleMustache() {
        doBraceTest(
                testSource.replace("{{bar}}", "<caret>{{bar}}")
                        .replace("{{bar}}", "{{bar<brace_match>}}")
        );

        doBraceTest(
                testSource.replace("{{bar}}", "<brace_match>{{bar}}")
                        .replace("{{bar}}", "{{bar}<caret>}")
        );
    }

    public void testUnEscapedMustache() {
        doBraceTest(
                testSource.replace("{{{baz}}}", "<caret>{{{baz}}}")
                        .replace("{{{baz}}}", "{{{baz<brace_match>}}}")
        );

        doBraceTest(
                testSource.replace("{{{baz}}}", "<brace_match>{{{baz}}}")
                        .replace("{{{baz}}}", "{{{baz}}<caret>}")
        );
    }

    public void testPartial() {
        doBraceTest(
                testSource.replace("{{>partial}}", "<caret>{{>partial}}")
                        .replace("{{>partial}}", "{{>partial<brace_match>}}")
        );

        doBraceTest(
                testSource.replace("{{>partial}}", "<brace_match>{{>partial}}")
                        .replace("{{>partial}}", "{{>partial}<caret>}")
        );
    }

    public void testBlockMustache() {
        doBraceTest(
                testSource.replace("{{#foo}}", "<caret>{{#foo}}")
                        .replace("{{/foo}}", "{{/foo<brace_match>}}")
        );

        doBraceTest(
                testSource.replace("{{#foo}}", "<brace_match>{{#foo}}")
                        .replace("{{/foo}}", "{{/foo}<caret>}")
        );
    }

    public void testNestedBlockStache() {
        doBraceTest(
                testSource.replace("{{#foo2}}", "<caret>{{#foo2}}")
                        .replace("{{/foo2}}", "{{/foo2<brace_match>}}")
        );

        doBraceTest(
                testSource.replace("{{#foo2}}", "<brace_match>{{#foo2}}")
                        .replace("{{/foo2}}", "{{/foo2}<caret>}")
        );
    }

    public void testInverseBlockStache() {
        doBraceTest(
                testSource.replace("{{^foo3}}", "<caret>{{^foo3}}")
                        .replace("{{/foo3}}", "{{/foo3<brace_match>}}")
        );

        doBraceTest(
                testSource.replace("{{^foo3}}", "<brace_match>{{^foo3}}")
                        .replace("{{/foo3}}", "{{/foo3}<caret>}")
        );
    }
}
