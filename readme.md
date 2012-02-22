# IDEA-Handlebars:  An Intellij IDEA plug-in for [Handlebars](http://handlebarsjs.com/) templates

IDEA-Handlebars adds support for Handlebars templates (by default, files with the ".handlebars" extension) to IDEs based
on the Intellij IDEA platform (IDEA, RubyMine, PhpStorm, and others).

The key feature of this early version of the plugin is that it allows most of the power of the built-in IDEA HTML
editing utilities to be used with Handlebars syntax inserted in the markup.  Future versions will build out more
Handlebars-specific utilites.

## Features
* Configurable syntax highlighting for Handlebars code
* Matched mustache pair highlighting
* Full HTML highlighting, code-completion, inspections, formatting and commenting for the HTML content in your Handlebars templates
* Since Handlebars is a super-set of [Mustache](http://mustache.github.com/) syntax, this plugin should greatly improve IDEA's editing experience for Mustache templates too

## Not yet implemented
* Handlebars-specific syntax-error highlighting
* Find usages, Go-to declaration, etc. for references
* Support for Handlebars templates embedded in script tags in html files

## Compatibility
* Confirmed to work with IDEA 11, RubyMine 4 and PhpStorm/WebStorm 3
* IDEA 10.5 version can be found in branch idea-handlebars-10.5
* Should work with any other IDE based on the IDEA 11 platform, build 110.00 or higher (will confirm further compatability soon)

## Feedback
* Please feel free to call out bugs and feaure requests in the issue tracker
* Also, pull requests are absolutely welcome

## Special thanks
* Extra special thanks to the [intellij-latte](https://github.com/juzna/intellij-latte project) project
(for having a clean code-base to learn from, and taking the time to make posts like [this gem](http://devnet.jetbrains.net/message/5450284?tstart=0)
* Yehuda Katz for creating and maintaining [Handlebars](http://handlebarsjs.com/)
* And, of course, thanks to the Intellij team for making such a great extensible IDE