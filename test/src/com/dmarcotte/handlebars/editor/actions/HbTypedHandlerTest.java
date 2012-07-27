package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.config.HbConfig;
import com.dmarcotte.handlebars.file.HbFileType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;


/**
 * These tests are based on other children of {@link com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase},
 * in particular {@code com.intellij.application.options.codeInsight.editor.quotes.SelectionQuotingTypedHandlerTest}
 */
public class HbTypedHandlerTest extends LightPlatformCodeInsightFixtureTestCase {

    private boolean myPrevAutoCloseSetting;
    private String myPrevPlatformPrefix;

    private void performWriteAction(final Project project, final Runnable action) {
        ApplicationManager.getApplication().runWriteAction(new Runnable() {
            @Override
            public void run() {
                CommandProcessor.getInstance().executeCommand(project, action, "test command", null);
            }
        });
    }

    @Override
    protected void setUp() throws Exception {
        // this test's parent setup requires that this property be set
        myPrevPlatformPrefix = System.getProperty("idea.platform.prefix");
        System.setProperty("idea.platform.prefix", "Idea");

        super.setUp();
        myPrevAutoCloseSetting = HbConfig.isAutoGenerateCloseTagEnabled();
    }

    @Override
    protected void tearDown() throws Exception {
        HbConfig.setAutoGenerateCloseTagEnabled(myPrevAutoCloseSetting);
        if (myPrevPlatformPrefix == null) {
            System.setProperty("idea.platform.prefix", "");
        } else {
            System.setProperty("idea.platform.prefix", myPrevPlatformPrefix);
        }
        super.tearDown();
    }

    private void doTest(final char c, @NotNull String before, @NotNull String expected) {
        myFixture.configureByText(HbFileType.INSTANCE, before);
        myFixture.getEditor().getCaretModel().moveToOffset(before.length());
        final TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
        performWriteAction(myFixture.getProject(), new Runnable() {
            @Override
            public void run() {
                typedAction.actionPerformed(myFixture.getEditor(), c, ((EditorEx) myFixture.getEditor()).getDataContext());
            }
        });
        myFixture.checkResult(expected);
    }

    public void testOpenBlockStache() {
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doTest('}', "{{#foo}", "{{#foo}}{{/foo}}");
        doTest('}', "{{#foo bar baz}", "{{#foo bar baz}}{{/foo}}");
        doTest('}', "{{#foo bar baz bat=\"bam\"}", "{{#foo bar baz bat=\"bam\"}}{{/foo}}");

        HbConfig.setAutoGenerateCloseTagEnabled(false);
        doTest('}', "{{#foo}", "{{#foo}}");
        doTest('}', "{{#foo bar baz}", "{{#foo bar baz}}");
        doTest('}', "{{#foo bar baz bat=\"bam\"}", "{{#foo bar baz bat=\"bam\"}}");
    }

    public void testOpenInverseStache(){
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doTest('}', "{{^foo}", "{{^foo}}{{/foo}}");
        doTest('}', "{{^foo bar baz}", "{{^foo bar baz}}{{/foo}}");
        doTest('}', "{{^foo bar baz bat=\"bam\"}", "{{^foo bar baz bat=\"bam\"}}{{/foo}}");

        HbConfig.setAutoGenerateCloseTagEnabled(false);
        doTest('}', "{{^foo}", "{{^foo}}");
        doTest('}', "{{^foo bar baz}", "{{^foo bar baz}}");
        doTest('}', "{{^foo bar baz bat=\"bam\"}", "{{^foo bar baz bat=\"bam\"}}");
    }

    public void testRegularStache() {
        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doTest('}', "{{foo}", "{{foo}}");
        doTest('}', "{{foo bar baz}", "{{foo bar baz}}");

        HbConfig.setAutoGenerateCloseTagEnabled(true);
        doTest('}', "{{foo}", "{{foo}}");
        doTest('}', "{{foo bar baz}", "{{foo bar baz}}");
    }
}
