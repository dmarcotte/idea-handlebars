package com.dmarcotte.handlebars.parsing;

import com.intellij.openapi.fileTypes.LanguageFileType;
import com.intellij.openapi.fileTypes.StdFileTypes;
import com.intellij.testFramework.ParsingTestCase;

/**
 * Java representations of the validations in Handlesbars parser_spec.rb
 * (Precise revision: https://github.com/wycats/handlebars.js/blob/932e2970ad29b16d6d6874ad0bfb44b07b4cd765/spec/parser_spec.rb)
 *
 * The tests here should map pretty clearly by name to the `it "does something"` validations in parser_spec.rb.
 *
 * Note ParsingTestCase automatically constructs test cases from the files in test/data/parser,
 * so each .hbs has a corresponding .txt file which represents the expected Psi structure.
 */
public class HbParserSpecTest extends ParsingTestCase {

    @SuppressWarnings("UnusedDeclaration") // TODO odd... StdFileTypes is not initialized on time if it's not forward declared.  Figure out what's up and remove this hack.
    private static LanguageFileType html = StdFileTypes.HTML;

    public HbParserSpecTest() {
        super("parser", "hbs", new HbParseDefinition());
    }

    @Override
    protected String getTestDataPath() {
        return "test/data";
    }

    public void testSimpleMustaches() { doTest(true); }
    public void testMustachesWithPaths() { doTest(true); }
    // TODO testMustachesWithThisFoo is actually a bit odd... parser_spec.rb expects just an id of foo, but our parser gives id,sep,id for this,/,foo
    public void testMustachesWithThisFoo() { doTest(true); }
    public void testMustachesWithDashInPath() { doTest(true); }
    public void testMustachesWithParameters() { doTest(true); }
    public void testMustachesWithHashArguments() { doTest(true); }
    public void testMustachesWithStringParameters() { doTest(true); }
    public void testMustachesWithIntegerParameters() { doTest(true); }
    public void testMustachesWithBooleanParameters() { doTest(true); }
    public void testContentsFollowedByMustache() { doTest(true); }
    public void testPartial() { doTest(true); }
    public void testPartialWithContext() { doTest(true); }
    public void testComment() { doTest(true); }
    public void testMultiLineComment() { doTest(true); }
    public void testInverseSection() { doTest(true); }
    public void testStandaloneInverseSection() { doTest(true); }
}