#!/bin/sh

####
# Run this script to regenerate _HbLexer.java from handlebars.flex
####

# thanks to http://code.google.com/p/google-closure-soy/source/browse/trunk/src/net/intellij/plugins/soy/lexer/build-lexer.cmd
# for help with the command line switches
/home/dmarcotte/workspace/code/intellij-community-11.x/tools/lexer/jflex-1.4/bin/jflex --charat --nobak --skel \
  /home/dmarcotte/workspace/code/intellij-community-11.x/tools/lexer/idea-flex.skeleton -d . --verbose handlebars.flex
