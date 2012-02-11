/*
 * Copyright 2000-2009 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.dmarcotte.handlebars;

import com.dmarcotte.handlebars.parsing.HbLexer;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.openapi.util.Pair;
import com.intellij.psi.tree.IElementType;
import gnu.trove.THashMap;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * @author max
 */
public class HbHighlighter extends SyntaxHighlighterBase {
    private static final Map<IElementType, TextAttributesKey> keys1;
    private static final Map<IElementType, TextAttributesKey> keys2;

    @NotNull
    public Lexer getHighlightingLexer() {
//        return new HbHighlightingLexer(); dm todo create a highlighting lexer?
        return new HbLexer();
    }

    public static final TextAttributesKey BRACES = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.BRACES",
            SyntaxHighlighterColors.BRACES.getDefaultAttributes()
    );

    public static final TextAttributesKey TAG_EXPRESSION = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.TAG_EXPRESSION",
            SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()
    );

    public static final TextAttributesKey HTML_CONTENT = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.HTML_CONTENT",
            SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    public static final TextAttributesKey INVALID_CHARACTER = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.INVALID_CHARACTER",
            SyntaxHighlighterColors.JAVA_SEMICOLON.getDefaultAttributes() // dm todo semicolon is not a good default here
    );

    static {
        keys1 = new THashMap<IElementType, TextAttributesKey>();
        keys2 = new THashMap<IElementType, TextAttributesKey>();

        keys1.put(HbTokenTypes.OPEN, BRACES);
        keys1.put(HbTokenTypes.CLOSE, BRACES);
        keys1.put(HbTokenTypes.CONTENT, HTML_CONTENT);
        keys1.put(HbTokenTypes.REG_TAG_EXPRESSION, TAG_EXPRESSION);
        keys1.put(HbTokenTypes.INVALID, INVALID_CHARACTER);
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(keys1.get(tokenType), keys2.get(tokenType));
    }

    public static final Map<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES = new THashMap<TextAttributesKey, Pair<String, HighlightSeverity>>(6);
    static {
        DISPLAY_NAMES.put(BRACES, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.braces.key"),null));
        DISPLAY_NAMES.put(TAG_EXPRESSION, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.tag_expression.key"),null));
        DISPLAY_NAMES.put(HTML_CONTENT, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.html_content.key"),null));
    }
}
