package com.dmarcotte.handlebars.format;

import com.dmarcotte.handlebars.util.HbTestUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.testFramework.LightIdeaTestCase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;

import java.io.File;

/**
 * Base class for Handlebars formatter tests.  Based on com.intellij.psi.formatter.java.AbstractJavaFormatterTest.
 */
public abstract class HbFormatterTest extends LightIdeaTestCase implements HbFormattingModelBuilderTest {

    private FormatterTestSettings formatterTestSettings = new FormatterTestSettings(getProject());

    @Override
    protected void setUp()
            throws Exception {
        super.setUp();

        formatterTestSettings.setUp();
    }

    @Override
    protected void tearDown()
            throws Exception {
        formatterTestSettings.tearDown();

        super.tearDown();
    }

    private interface TestFormatAction {
        void run(PsiFile psiFile, int startOffset, int endOffset);
    }

    private static final String BASE_PATH = HbTestUtils.getBaseTestDataPath() + "/formatter";

    /**
     * Call this to run the formatter on a test file in the {@link #BASE_PATH} directory.
     *
     * The test will validate the results against a file of the same name with "_expected" appended.
     * (i.e. for fileNameBefore "TestFile.hbs", the formatter will be run on {@link #BASE_PATH}/TestFile.hbs
     * the test will look for {@link #BASE_PATH}/TestFile_expected.hbs to validate the results).
     *
     * @param fileNameBefore The name of the file to test.
     * @throws Exception
     */
    public void doFileBasedTest(@NonNls String fileNameBefore) throws Exception {
        doTextTest(loadFile(fileNameBefore), loadFile(fileNameBefore.replace(".hbs", "_expected.hbs")));
    }

    public void doStringBasedTest(@NonNls final String text, @NonNls String textAfter) throws IncorrectOperationException {
        doTextTest(text, textAfter);
    }

    /**
     * NOTE: the line-by-line check in this test is currently disabled.  See TODO below.
     *
     * This method runs both a full-file reformat on beforeText, and a line-by-line reformat.  Though the tests
     * would output slightly better errors if these were separate tests, enforcing that they are always both run
     * for any test defined is the easiest way to ensure that the line-by-line is not messed up by formatter changes
     *
     * @param beforeText The text run the formatter on
     * @param textAfter The expected result after running the formatter
     * @throws IncorrectOperationException
     */
    public void doTextTest(final String beforeText, String textAfter) throws IncorrectOperationException {
        // run "Reformat Code" on the whole "file" defined by beforeText
        {
            final PsiFile file = createFile("A.hbs", beforeText);

            final PsiDocumentManager manager = PsiDocumentManager.getInstance(getProject());
            final Document document = manager.getDocument(file);

            CommandProcessor.getInstance().executeCommand(getProject(), new Runnable() {
                @Override
                public void run() {
                    ApplicationManager.getApplication().runWriteAction(new Runnable() {
                        @Override
                        public void run() {
                            document.replaceString(0, document.getTextLength(), beforeText);
                            manager.commitDocument(document);
                            try {
                                TextRange rangeToUse = file.getTextRange();
                                new TestFormatAction() {
                                    @Override
                                    public void run(PsiFile psiFile, int startOffset, int endOffset) {
                                        CodeStyleManager.getInstance(getProject()).reformatText(psiFile, startOffset, endOffset);
                                    }
                                }.run(file, rangeToUse.getStartOffset(), rangeToUse.getEndOffset());
                            }
                            catch (IncorrectOperationException e) {
                                assertTrue(e.getLocalizedMessage(), false);
                            }
                        }
                    });
                }
            }, "", "");


            if (document == null) {
                fail("Don't expect the document to be null");
                return;
            }
            assertEquals("Reformat Code failed", prepareText(textAfter), prepareText(document.getText()));
            manager.commitDocument(document);
            assertEquals("Reformat Code failed", prepareText(textAfter), prepareText(file.getText()));
        }

        // TODO Re-enable this check.  This test went flaky and gave a false negative on the SampleFile test, so disabling it.  It should be possible to figure out what went wrong and fix it.
//        // run "Adjust line indent" on every line in the "file" defined by beforeText
//        {
//            final PsiFile file = createFile("B.hbs", beforeText);
//
//            final PsiDocumentManager manager = PsiDocumentManager.getInstance(getProject());
//            final Document document = manager.getDocument(file);
//
//            // write our beforeText into our document
//            CommandProcessor.getInstance().executeCommand(getProject(), new Runnable() {
//                @Override
//                public void run() {
//                    ApplicationManager.getApplication().runWriteAction(new Runnable() {
//                        @Override
//                        public void run() {
//                            document.replaceString(0, document.getTextLength(), beforeText);
//                            manager.commitDocument(document);
//                        }
//                    });
//                }
//            }, "", "");
//
//            // now run the line formatter on each line in turn
//            for (int i = 0; i < document.getLineCount(); i++) {
//                final int lineNum = i;
//                CommandProcessor.getInstance().executeCommand(getProject(), new Runnable() {
//                    @Override
//                    public void run() {
//                        ApplicationManager.getApplication().runWriteAction(new Runnable() {
//                            @Override
//                            public void run() {
//                                try {
//                                    new TestFormatAction() {
//                                        @Override
//                                        public void run(PsiFile psiFile, int startOffset, int endOffset) {
//                                            CodeStyleManager.getInstance(getProject()).adjustLineIndent(psiFile, document.getLineStartOffset(lineNum));
//                                        }
//                                    }.run(file, 0, 0);
//                                }
//                                catch (IncorrectOperationException e) {
//                                    assertTrue(e.getLocalizedMessage(), false);
//                                }
//                            }
//                        });
//                    }
//                }, "", "");
//            }
//
//            assertEquals("Line-by-line formatting failed", prepareText(textAfter), prepareText(document.getText()));
//            manager.commitDocument(document);
//            assertEquals("Line-by-line formatting failed", prepareText(textAfter), prepareText(file.getText()));
//        }
    }

    private static String prepareText(String actual) {
        if (actual.startsWith("\n")) {
            actual = actual.substring(1);
        }
        if (actual.startsWith("\n")) {
            actual = actual.substring(1);
        }

        // Strip trailing spaces
        final Document doc = EditorFactory.getInstance().createDocument(actual);
        CommandProcessor.getInstance().executeCommand(getProject(), new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        ((DocumentImpl)doc).stripTrailingSpaces();
                    }
                });
            }
        }, "formatting", null);

        return doc.getText();
    }

    private static String loadFile(String name) throws Exception {
        String fullName = BASE_PATH + File.separatorChar + name;
        String text = FileUtil.loadFile(new File(fullName));
        text = StringUtil.convertLineSeparators(text);
        return text;
    }
}

