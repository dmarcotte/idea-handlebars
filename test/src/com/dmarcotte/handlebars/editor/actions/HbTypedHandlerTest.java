package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.config.HbConfig;


/**
 * These tests are based on other children of {@link com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase},
 * in particular {@code com.intellij.application.options.codeInsight.editor.quotes.SelectionQuotingTypedHandlerTest}
 *
 * TODO this test cannot be run with our others due to some interdependency in the IDEA base tests.  Fix this or organize the code in such a way that it is clear these cannot be run together
 */
public class HbTypedHandlerTest extends HbActionHandlerTest {

    private boolean myPrevAutoCloseSetting;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        myPrevAutoCloseSetting = HbConfig.isAutoGenerateCloseTagEnabled();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
        HbConfig.setAutoGenerateCloseTagEnabled(myPrevAutoCloseSetting);
    }

    public void testOpenBlockStache() {
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doCharTest('}', "{{#foo}<caret>", "{{#foo}}<caret>{{/foo}}");
        doCharTest('}', "{{#foo bar baz}<caret>", "{{#foo bar baz}}<caret>{{/foo}}");
        doCharTest('}', "{{#foo bar baz bat=\"bam\"}<caret>", "{{#foo bar baz bat=\"bam\"}}<caret>{{/foo}}");

        // test when caret is not at file boundary
        doCharTest('}', "{{#foo}<caret>some\nother content", "{{#foo}}<caret>{{/foo}}some\nother content");
        doCharTest('}', "{{#foo bar baz}<caret>some\nother content", "{{#foo bar baz}}<caret>{{/foo}}some\nother content");
        doCharTest('}', "{{#foo bar baz bat=\"bam\"}<caret>some\nother content", "{{#foo bar baz bat=\"bam\"}}<caret>{{/foo}}some\nother content");

        HbConfig.setAutoGenerateCloseTagEnabled(false);
        doCharTest('}', "{{#foo}<caret>", "{{#foo}}<caret>");
        doCharTest('}', "{{#foo bar baz}<caret>", "{{#foo bar baz}}<caret>");
        doCharTest('}', "{{#foo bar baz bat=\"bam\"}<caret>", "{{#foo bar baz bat=\"bam\"}}<caret>");
    }

    public void testOpenInverseStache(){
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doCharTest('}', "{{^foo}<caret>", "{{^foo}}<caret>{{/foo}}");
        doCharTest('}', "{{^foo bar baz}<caret>", "{{^foo bar baz}}<caret>{{/foo}}");
        doCharTest('}', "{{^foo bar baz bat=\"bam\"}<caret>", "{{^foo bar baz bat=\"bam\"}}<caret>{{/foo}}");

        // test when caret is not at file boundary
        doCharTest('}', "{{^foo}<caret>some\nother content", "{{^foo}}<caret>{{/foo}}some\nother content");
        doCharTest('}', "{{^foo bar baz}<caret>some\nother content", "{{^foo bar baz}}<caret>{{/foo}}some\nother content");
        doCharTest('}', "{{^foo bar baz bat=\"bam\"}<caret>some\nother content", "{{^foo bar baz bat=\"bam\"}}<caret>{{/foo}}some\nother content");

        HbConfig.setAutoGenerateCloseTagEnabled(false);
        doCharTest('}', "{{^foo}<caret>", "{{^foo}}<caret>");
        doCharTest('}', "{{^foo bar baz}<caret>", "{{^foo bar baz}}<caret>");
        doCharTest('}', "{{^foo bar baz bat=\"bam\"}<caret>", "{{^foo bar baz bat=\"bam\"}}<caret>");
    }

    public void testRegularStache() {
        // ensure that nothing special happens for regular 'staches, whether autoGenerateCloseTag is enabled or not
        
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doCharTest('}', "{{foo}<caret>", "{{foo}}<caret>");
        doCharTest('}', "{{foo bar baz}<caret>", "{{foo bar baz}}<caret>");

        // test when caret is not at file boundary
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doCharTest('}', "{{foo}<caret>some\nother stuff", "{{foo}}<caret>some\nother stuff");
        doCharTest('}', "{{foo bar baz}<caret>some\nother stuff", "{{foo bar baz}}<caret>some\nother stuff");

        HbConfig.setAutoGenerateCloseTagEnabled(false);
        doCharTest('}', "{{foo}<caret>", "{{foo}}<caret>");
        doCharTest('}', "{{foo bar baz}<caret>", "{{foo bar baz}}<caret>");
    }
}
