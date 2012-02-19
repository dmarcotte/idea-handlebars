package com.dmarcotte.handlebars.parsing;

import com.dmarcotte.handlebars.psi.HbPsiElement;
import com.dmarcotte.handlebars.psi.HbPsiFile;
import com.intellij.lang.ASTNode;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import org.jetbrains.annotations.NotNull;

public class HbParseDefinition implements ParserDefinition {
    @NotNull
    public Lexer createLexer(Project project) {
        return new HbLexer();
    }

    public PsiParser createParser(Project project) {
        return new HbParser();
    }

    public IFileElementType getFileNodeType() {
        return HbTokenTypes.FILE;
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return HbTokenTypes.WHITESPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return HbTokenTypes.COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return HbTokenTypes.STRING_LITERALS;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return new HbPsiElement(node);
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new HbPsiFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }
}
