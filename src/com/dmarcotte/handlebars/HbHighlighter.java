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
//        return new HbHighlightingLexer(); dm todo create a highlighting-specific lexer?
        return new HbLexer();
    }

    public static final TextAttributesKey MUSTACHES = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.MUSTACHES",
            SyntaxHighlighterColors.BRACES.getDefaultAttributes()
    );

    public static final TextAttributesKey IDENTIFIERS = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.IDENTIFIERS",
            SyntaxHighlighterColors.KEYWORD.getDefaultAttributes()
    );

    public static final TextAttributesKey COMMENTS = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.COMMENTS",
            SyntaxHighlighterColors.DOC_COMMENT.getDefaultAttributes()
    );

    public static final TextAttributesKey OPERATORS = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.OPERATORS",
            SyntaxHighlighterColors.OPERATION_SIGN.getDefaultAttributes()
    );

    public static final TextAttributesKey VALUES = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.VALUES",
            SyntaxHighlighterColors.NUMBER.getDefaultAttributes()
    );

    static {
        keys1 = new THashMap<IElementType, TextAttributesKey>();
        keys2 = new THashMap<IElementType, TextAttributesKey>();

        keys1.put(HbTokenTypes.OPEN, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_BLOCK, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_ENDBLOCK, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_INVERSE, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_UNESCAPED, MUSTACHES);
        keys1.put(HbTokenTypes.CLOSE, MUSTACHES);
        keys1.put(HbTokenTypes.ID, IDENTIFIERS);
        keys1.put(HbTokenTypes.COMMENT, COMMENTS);
        keys1.put(HbTokenTypes.EQUALS, OPERATORS);
        keys1.put(HbTokenTypes.SEP, OPERATORS);
        keys1.put(HbTokenTypes.INTEGER, VALUES);
        keys1.put(HbTokenTypes.BOOLEAN, VALUES);

    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        return pack(keys1.get(tokenType), keys2.get(tokenType));
    }

    public static final Map<TextAttributesKey, Pair<String, HighlightSeverity>> DISPLAY_NAMES = new THashMap<TextAttributesKey, Pair<String, HighlightSeverity>>(6);
    static {
        DISPLAY_NAMES.put(MUSTACHES, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.mustaches.key"),null));
        DISPLAY_NAMES.put(IDENTIFIERS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.identifiers.key"),null));
        DISPLAY_NAMES.put(COMMENTS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.comments.key"),null));
        DISPLAY_NAMES.put(OPERATORS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.operators.key"),null));
        DISPLAY_NAMES.put(VALUES, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.values.key"),null));
    }
}
