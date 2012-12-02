package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.file.HbFileType;
import com.intellij.openapi.actionSystem.IdeActions;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.actionSystem.EditorActionHandler;
import com.intellij.openapi.editor.actionSystem.EditorActionManager;
import com.intellij.openapi.editor.actionSystem.TypedAction;
import com.intellij.openapi.editor.ex.EditorEx;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.LightPlatformCodeInsightFixtureTestCase;
import org.jetbrains.annotations.NotNull;

/**
 * Base test for plugin action handlers
 */
public abstract class HbActionHandlerTest extends LightPlatformCodeInsightFixtureTestCase {
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
    }

    @Override
    protected void tearDown() throws Exception {
        if (myPrevPlatformPrefix == null) {
            System.setProperty("idea.platform.prefix", "");
        } else {
            System.setProperty("idea.platform.prefix", myPrevPlatformPrefix);
        }
        super.tearDown();
    }

    private void validateTestStrings(@NotNull String before, @NotNull String expected) {
        if (!before.contains("<caret>")
                || !expected.contains("<caret>")) {
            throw new IllegalArgumentException("Test strings must contain \"<caret>\" to indicate caret position");
        }
    }

    /**
     * Call this method to test behavior when the given char c is typed.  Use the String {@code "<caret>"} in your
     * 'before' and 'expected' arguments to indicate the position of the caret in those strings.
     *
     * @param c The character to type
     * @param before The text before the character is typed, with substring "<caret>" to indicate the position of the caret
     * @param expected The text expected after the character is typed, with substring "<caret>" to indicate the position of the caret
     */
    void doCharTest(final char c, @NotNull String before, @NotNull String expected) {
        validateTestStrings(before, expected);

        myFixture.configureByText(HbFileType.INSTANCE, before);
        final TypedAction typedAction = EditorActionManager.getInstance().getTypedAction();
        performWriteAction(myFixture.getProject(), new Runnable() {
            @Override
            public void run() {
                typedAction.actionPerformed(myFixture.getEditor(), c, ((EditorEx) myFixture.getEditor()).getDataContext());
            }
        });
        myFixture.checkResult(expected);
    }

    /**
     * Call this method to test behavior when Enter is typed.  Use the String {@code "<caret>"} in your
     * 'before' and 'expected' arguments to indicate the position of the caret in those strings.
     *
     * @param before The text before Enter typed, with substring "<caret>" to indicate the position of the caret
     * @param expected The text after Enter is typed, with substring "<caret>" to indicate the position of the caret
     */
    protected void doEnterTest(@NotNull String before, @NotNull String expected) {
        validateTestStrings(before, expected);

        myFixture.configureByText(HbFileType.INSTANCE, before);
        final EditorActionHandler enterActionHandler = EditorActionManager.getInstance().getActionHandler(IdeActions.ACTION_EDITOR_ENTER);
        performWriteAction(myFixture.getProject(), new Runnable() {
            @Override
            public void run() {
                enterActionHandler.execute(myFixture.getEditor(), ((EditorEx) myFixture.getEditor()).getDataContext());
            }
        });
        myFixture.checkResult(expected);
    }
}
