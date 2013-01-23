package com.dmarcotte.handlebars.editor.actions;

import com.dmarcotte.handlebars.config.HbConfig;
import com.intellij.lang.Language;
import com.intellij.lang.html.HTMLLanguage;
import com.intellij.lang.java.JavaLanguage;

public class HbCommentActionTest extends HbActionHandlerTest {

    private Language myPrevCommenterLang;

    @Override
    protected void setUp()
            throws Exception {
        super.setUp();

        myPrevCommenterLang = HbConfig.getCommenterLanguage();
        HbConfig.setCommenterLanguage(HTMLLanguage.INSTANCE);
    }

    public void testInsertLineComment1() {
        doLineCommentTest(

                "{{#foo}}<caret>",

                "<!--{{#foo}}<caret>-->"
        );
    }

    public void testInsertLineComment2() {
        doLineCommentTest(

                "{{#foo}}" +
                "    {{ba<caret>r}}" +
                "{{/foo}",

                "{{#foo}}" +
                "    <!--{{ba<caret>r}}-->" +
                "{{/foo}"
        );
    }

    public void testInsertBlockComment1() {
        doBlockCommentTest(

                "{{#foo}}<caret>",

                "{{#foo}}<!--<caret>-->"
        );
    }

    public void testInsertBlockComment2() {
        doLineCommentTest(

                "{{#foo}}" +
                "    <caret>{{bar}}" +
                "{{/foo}",

                "{{#foo}}" +
                "    <!--caret-->{{bar}}" +
                "{{/foo}"
        );
    }

    public void testInsertBlockCommentWithSelection() {
        doBlockCommentTest(

                "<selection><caret>{{#foo}}" +
                "    {{bar}}</selection>" +
                "{{/foo}",

                "<selection><!--<caret>{{#foo}}" +
                "    {{bar}}--></selection>" +
                "{{/foo}"
        );
    }

    public void testInsertNonDefaultLineComment() {
        Language prevCommenterLanguage = HbConfig.getCommenterLanguage();
        HbConfig.setCommenterLanguage(JavaLanguage.INSTANCE);

        doLineCommentTest(

                "{{#foo}}<caret>",

                "//{{#foo}}<caret>"
        );

        HbConfig.setCommenterLanguage(prevCommenterLanguage);
    }

    public void testInsertNonDefaultBlockComment() {
        Language prevCommenterLanguage = HbConfig.getCommenterLanguage();
        HbConfig.setCommenterLanguage(JavaLanguage.INSTANCE);

        doBlockCommentTest(

                "{{#foo}}<caret>",

                "{{#foo}}/*<caret>*/"
        );

        HbConfig.setCommenterLanguage(prevCommenterLanguage);
    }

    @Override
    protected void tearDown()
            throws Exception {
        HbConfig.setCommenterLanguage(myPrevCommenterLang);
        super.tearDown();
    }
}
