//[^\x00]*?/("{{")                 {
//                                   if(yytext.slice(-1) !== "\\") this.begin("mu");
//                                   if(yytext.slice(-1) === "\\") yytext = yytext.substr(0,yyleng-1), this.begin("emu");
//                                   if(yytext) return 'CONTENT';
//                                 }
//
//[^\x00]+                         { return 'CONTENT'; }
//
//<emu>[^\x00]{2,}?/("{{")         { this.popState(); return 'CONTENT'; }
//
//<mu>"{{>"                        { return 'OPEN_PARTIAL'; }
//<mu>"{{#"                        { return 'OPEN_BLOCK'; }
//<mu>"{{/"                        { return 'OPEN_ENDBLOCK'; }
//<mu>"{{^"                        { return 'OPEN_INVERSE'; }
//<mu>"{{"\s*"else"                { return 'OPEN_INVERSE'; }
//<mu>"{{{"                        { return 'OPEN_UNESCAPED'; }
//<mu>"{{&"                        { return 'OPEN_UNESCAPED'; }
//<mu>"{{!"[\s\S]*?"}}"            { yytext = yytext.substr(3,yyleng-5); this.popState(); return 'COMMENT'; }
//<mu>"{{"                         { return 'OPEN'; }
//
//<mu>"="                          { return 'EQUALS'; }
//<mu>"."/[} ]                     { return 'ID'; }
//<mu>".."                         { return 'ID'; }
//<mu>[\/.]                        { return 'SEP'; }
//<mu>\s+                          { /*ignore whitespace*/ }
//<mu>"}}}"                        { this.popState(); return 'CLOSE'; }
//<mu>"}}"                         { this.popState(); return 'CLOSE'; }
//<mu>'"'("\\"["]|[^"])*'"'        { yytext = yytext.substr(1,yyleng-2).replace(/\\"/g,'"'); return 'STRING'; }
//<mu>"true"/[}\s]                 { return 'BOOLEAN'; }
//<mu>"false"/[}\s]                { return 'BOOLEAN'; }
//<mu>[0-9]+/[}\s]                 { return 'INTEGER'; }
//<mu>[a-zA-Z0-9_$-]+/[=}\s\/.]    { return 'ID'; }
//<mu>'['[^\]]*']'                 { yytext = yytext.substr(1, yyleng-2); return 'ID'; }
//<mu>.                            { return 'INVALID'; }
//
//<INITIAL,mu><<EOF>>              { return 'EOF'; }

package com.dmarcotte.handlebars.parsing;

import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import com.intellij.util.containers.Stack;
import com.dmarcotte.handlebars.parsing.HbTokenTypes;

%%

%class _HbLexer
%implements FlexLexer
%final
%unicode
%function advance
%type IElementType
%eof{ return;
%eof}

%{
    private Stack<Integer> stack = new Stack<Integer>();

    public void yypushState(int newState) {
      stack.push(yystate());
      yybegin(newState);
    }

    public void yypopState() {
      yybegin(stack.pop());
    }
%}

LineTerminator = \r|\n|\r\n
SlashSmallS = \t \n\x0B\f\r
IdFollower = [=}\t\n\f\r\/.]
WhiteSpace = {LineTerminator} | [ \t\f]
AtLeast2 = "{2,}"
OpenStache = "{{"
CloseStache = "}}"
AsciiZero = [^\x00]


%state mu
%state emu

%%

<YYINITIAL> {

  [AsciiZero*]?"{{" {
            yypushback(2);
            yypushState(mu); if (!yytext().toString().equals("")) return HbTokenTypes.CONTENT;
        }
//  [^\x00]*?"{{" {
//    if(!yytext().toString().substring(0, yylength() - 1).equals("\\")) yypushState(mu);
//    if(yytext().toString().substring(0, yylength() - 1).equals("\\")) zzBuffer = yytext().subSequence(0,yylength() - 1); yypushState(emu);
//    if(!yytext().toString().equals("")) return HbTokenTypes.CONTENT;
//  }

  [^\{\x00]+                         { return HbTokenTypes.CONTENT; }
}

//<emu> {
//
//  [^\x00]{2}?("{{")     { yypopState(); return HbTokenTypes.CONTENT; }
//
//} dm todo restore the logic for escaped mustaches

<mu> {

  "{{>" { return HbTokenTypes.OPEN_PARTIAL; }
  "{{#" { return HbTokenTypes.OPEN_BLOCK; }
  "{{/" { return HbTokenTypes.OPEN_ENDBLOCK; }
  "{{^" { return HbTokenTypes.OPEN_INVERSE; }
  "{{"[\t \n\x0B\f\r]*"else" { return HbTokenTypes.OPEN_INVERSE; }
  "{{{" { return HbTokenTypes.OPEN_UNESCAPED; }
  "{{&" { return HbTokenTypes.OPEN_UNESCAPED; }
  // dm todo why were we monkeying with the buffer here??? "{{!".*?"}}" { zzBuffer = yytext().subSequence(3,yylength() - 5); yypopState(); return HbTokenTypes.COMMENT; }
  "{{!".*?"}}" { yypopState(); return HbTokenTypes.COMMENT; }
  "{{" { return HbTokenTypes.OPEN; }
  "=" { return HbTokenTypes.EQUALS; }
  "."/[}\t \n\x0B\f\r] { return HbTokenTypes.ID; }
  ".." { return HbTokenTypes.ID; }
  [\/.] { return HbTokenTypes.SEP; }
  {WhiteSpace}+ { return HbTokenTypes.WHITE_SPACE; }
  "}}}" { yypopState(); return HbTokenTypes.CLOSE; }
  "}}" { yypopState(); return HbTokenTypes.CLOSE; }
  // dm todo what is up with this expression?
//  '"'("\\"["]\[^"])*'"' { zzBuffer = yytext().subSequence(1,yylength() - 2).toString().replaceAll("\\",'"'); return HbTokenTypes.STRING; }
  "true"/[}\t \n\x0B\f\r] { return HbTokenTypes.BOOLEAN; }
  "false"/[}\t \n\x0B\f\r] { return HbTokenTypes.BOOLEAN; }
  [0-9]+/[}\t \n\x0B\f\r]  { return HbTokenTypes.INTEGER; }
  [a-zA-Z0-9_$-]+/[=}\t \n\x0B\f\r\/.] { return HbTokenTypes.ID; }
  // dm todo deleted another setter of yytext here; this one seems to be trying to extract the inner value from the parens
  \[[^\]]*\] { return HbTokenTypes.ID; }
}

{WhiteSpace}+                      { return HbTokenTypes.WHITE_SPACE; }
.                                        { return HbTokenTypes.INVALID; }