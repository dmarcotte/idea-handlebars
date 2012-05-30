package com.dmarcotte.handlebars.parsing;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

/**
 * The parser is based directly on Handlebars.yy
 * (taken from the following revision: https://github.com/wycats/handlebars.js/blob/2ea95ca08d47bb16ed79e8481c50a1c074dd676e/src/handlebars.yy)
 *
 * Methods mapping to expression in the grammar are commented with the part of the grammar they map to.
 *
 * Places where we've gone off book to make the live syntax detection a more pleasant experience are
 * marked HB_CUSTOMIZATION.  If we find bugs, or the grammar is ever updated, these are the first candidates to check.
 */
public class HbParser implements PsiParser {

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();

        builder.setDebugMode(true);  // dm todo delete

        new HbParsing(builder).parse();

        rootMarker.done(root);

        return builder.getTreeBuilt();
    }
}
