package com.dmarcotte.handlebars.inspections;

import com.dmarcotte.handlebars.util.HbTestUtils;
import com.intellij.codeInsight.daemon.quickFix.LightQuickFixTestCase;
import org.jetbrains.annotations.NotNull;

public class HbBlockMismatchFixTest extends LightQuickFixTestCase {

    public void test() throws Exception { doAllTests(); }

    @Override
    protected String getBasePath() {
        return "/inspections";
    }

    @NotNull
    @Override
    protected String getTestDataPath() {
        return HbTestUtils.BASE_TEST_DATA_PATH;
    }
}
