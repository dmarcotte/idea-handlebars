package com.dmarcotte.handlebars.format;

import com.dmarcotte.handlebars.HbLanguage;
import com.dmarcotte.handlebars.util.HbTestUtils;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.command.CommandProcessor;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.EditorFactory;
import com.intellij.openapi.editor.impl.DocumentImpl;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.openapi.util.TextRange;
import com.intellij.openapi.util.io.FileUtil;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.codeStyle.CodeStyleSettings;
import com.intellij.psi.codeStyle.CodeStyleSettingsManager;
import com.intellij.psi.codeStyle.CommonCodeStyleSettings;
import com.intellij.testFramework.LightIdeaTestCase;
import com.intellij.util.IncorrectOperationException;
import org.jetbrains.annotations.NonNls;

import java.io.File;

/**
 * Base class for Handlebars formatter tests.  Based on com.intellij.psi.formatter.java.AbstractJavaFormatterTest.
 */
public abstract class HbFormatterTest extends LightIdeaTestCase
        implements HbFormattingModelBuilderTest {

    private interface TestFormatAction {
        void run(PsiFile psiFile, int startOffset, int endOffset);
    }

    private static final String BASE_PATH = HbTestUtils.getBaseTestDataPath() + "/formatter";

    public TextRange myTextRange;
    public TextRange myLineRange;

    public static CommonCodeStyleSettings getSettings() {
        CodeStyleSettings rootSettings = CodeStyleSettingsManager.getSettings(getProject());
        return rootSettings.getCommonSettings(HbLanguage.INSTANCE);
    }

    public static CommonCodeStyleSettings.IndentOptions getIndentOptions() {
        // todo do we want HB-specific indent settings?
        return getSettings().getRootSettings().getIndentOptions(StdFileTypes.HTML);
    }

    public void doTest() throws Exception {
        doFileBasedTest(getTestName(false) + ".hbs", getTestName(false) + "_after.hbs");
    }

    public void doFileBasedTest(@NonNls String fileNameBefore, @NonNls String fileNameAfter) throws Exception {
        doTextTest(loadFile(fileNameBefore), loadFile(fileNameAfter));
    }

    public void doStringBasedTest(@NonNls final String text, @NonNls String textAfter) throws IncorrectOperationException {
        doTextTest(text, textAfter);
    }

    public void doTextTest(final String text, String textAfter) throws IncorrectOperationException {
        final PsiFile file = createFile("A.hbs", text);

        // todo do we need this?
        if (myLineRange != null) {
            final DocumentImpl document = new DocumentImpl(text);
            myTextRange =
                    new TextRange(document.getLineStartOffset(myLineRange.getStartOffset()), document.getLineEndOffset(myLineRange.getEndOffset()));
        }

        final PsiDocumentManager manager = PsiDocumentManager.getInstance(getProject());
        final Document document = manager.getDocument(file);

        CommandProcessor.getInstance().executeCommand(getProject(), new Runnable() {
            @Override
            public void run() {
                ApplicationManager.getApplication().runWriteAction(new Runnable() {
                    @Override
                    public void run() {
                        document.replaceString(0, document.getTextLength(), text);
                        manager.commitDocument(document);
                        try {
                            TextRange rangeToUse = myTextRange;
                            if (rangeToUse == null) {
                                rangeToUse = file.getTextRange();
                            }
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
        assertEquals(prepareText(textAfter), prepareText(document.getText()));
        manager.commitDocument(document);
        assertEquals(prepareText(textAfter), prepareText(file.getText()));

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

