package com.dmarcotte.handlebars;

import com.dmarcotte.handlebars.parsing.HbLexer;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;
import com.intellij.lang.annotation.HighlightSeverity;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.SyntaxHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
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
        return new HbLexer();
    }

    public static final TextAttributesKey MUSTACHES = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.MUSTACHES",
            new TextAttributes(null, null, null, null, 1)
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

    public static final TextAttributesKey STRINGS = TextAttributesKey.createTextAttributesKey(
            "HANDLEBARS.STRINGS",
            SyntaxHighlighterColors.STRING.getDefaultAttributes()
    );

    static {
        keys1 = new THashMap<IElementType, TextAttributesKey>();
        keys2 = new THashMap<IElementType, TextAttributesKey>();

        keys1.put(HbTokenTypes.OPEN, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_BLOCK, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_PARTIAL, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_ENDBLOCK, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_INVERSE, MUSTACHES);
        keys1.put(HbTokenTypes.OPEN_UNESCAPED, MUSTACHES);
        keys1.put(HbTokenTypes.CLOSE, MUSTACHES);
        keys1.put(HbTokenTypes.ID, IDENTIFIERS);
        keys1.put(HbTokenTypes.COMMENT, COMMENTS);
        keys1.put(HbTokenTypes.EQUALS, OPERATORS);
        keys1.put(HbTokenTypes.SEP, OPERATORS);
        keys1.put(HbTokenTypes.INTEGER, VALUES);
        keys1.put(HbTokenTypes.ELSE, IDENTIFIERS);
        keys1.put(HbTokenTypes.BOOLEAN, VALUES);
        keys1.put(HbTokenTypes.STRING, STRINGS);

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
        DISPLAY_NAMES.put(STRINGS, new Pair<String, HighlightSeverity>(HbBundle.message("hb.page.colors.descriptor.strings.key"),null));
    }
}
