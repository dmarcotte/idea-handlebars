# IDEA-Handlebars:  [Handlebars](http://handlebarsjs.com/) template plugin for Jetbrains IDEs

IDEA-Handlebars adds support for Handlebars templates (by default, files with the ".handlebars" extension) to IDEs based on the Intellij IDEA platform (IDEA, RubyMine, PhpStorm, and others).

The key feature of this early version of the plugin is that it allows most of the power of the built-in IDEA HTML editing utilities to be used with Handlebars syntax inserted in the markup.  Future versions will build out more Handlebars-specific utilites.

## Features
* Configurable syntax highlighting for Handlebars code
* Matched mustache pair highlighting
* Full HTML highlighting, code completion, inspections, formatting and commenting for the HTML content in your Handlebars templates
* Since Handlebars is a super-set of [Mustache](http://mustache.github.com/) syntax, this plugin should greatly improve IDEA's editing experience for Mustache templates too

## Not yet implemented
* Handlebars-specific syntax-error highlighting
* Find usages, Go-to declaration, etc. for references
* Support for Handlebars templates embedded in script tags in html files

## Compatibility
* Confirmed to work with [IntelliJ IDEA 11](http://www.jetbrains.com/idea), [RubyMine 4](http://www.jetbrains.com/ruby), [PhpStorm 3](http://www.jetbrains.com/phpstorm), [WebStorm 3](http://www.jetbrains.com/webstorm)
* IDEA 10.5 version can be found in branch idea-handlebars-10.5
* Should work with any other IDE based on the IDEA 11 platform, build 110.00 or higher (will confirm further compatability soon)

## Feedback
* Please feel free to call out bugs and feaure requests in the [issue tracker](https://github.com/dmarcotte/idea-handlebars/issues)
* Also, [pull requests](https://github.com/dmarcotte/idea-handlebars/pulls) are absolutely welcome

## Special thanks
* [Bazaarvoice](http://www.bazaarvoice.com), my fantastic employer, for providing the time to work on this
* Yehuda Katz for creating and maintaining [Handlebars](http://handlebarsjs.com/)
* The Jetbrains team for making such a great, extensible IDE platform
* Extra special thanks to Jan Dolecek and the [intellij-latte](https://github.com/juzna/intellij-latte) project (for having a clean code-base to learn from, and taking the time to make posts like [this gem](http://devnet.jetbrains.net/message/5450284?tstart=0); these went a long way to making this plug-in possible