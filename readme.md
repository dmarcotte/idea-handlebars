# IDEA-Handlebars:  [Handlebars](http://handlebarsjs.com/) template plugin for Jetbrains IDEs

IDEA-Handlebars adds support for Handlebars templates to IDEs based on the Intellij IDEA platform (IntelliJ IDEA, IDEA Community Edition, RubyMine, PhpStorm, WebStorm, PyCharm, AppCode).

## _NEW:_ Syntax error highlighting
* Syntax error highlighting has been released!
    * If you were running the beta, reenable the stable version and **uninstall** the beta as follows:
        * In Settings->Plugins, right-click Handlebars-Beta and choose "Uninstall"

## Installing
* To install the latest release (and get automatic updates), install this plugin using your IDE's plugin manager:
  * In Settings->Plugins, choose "Browse repositories".  Find "Handlebars" on the list, right-click, and select "Download and Install"

## Features
* Syntax error highlighting
* Configurable syntax highlighting
* Matched mustache pair highlighting
* Full HTML highlighting, code completion, inspections, formatting and commenting for the HTML content in your Handlebars templates
* Since Handlebars is a super-set of [Mustache](http://mustache.github.com/) syntax, this plugin should greatly improve IDEA's editing experience for Mustache templates too
* By default, files with the ".handlebars", ".hbs" or ".mustache" extensions are handled by this plugin

## Future directions
* See [the pulls](https://github.com/dmarcotte/idea-handlebars/pulls) for a preview of in-progress and planned features

## Feedback
* Please feel free to call out bugs and feaure requests in the [issue tracker](https://github.com/dmarcotte/idea-handlebars/issues)
* Also, [pull requests](https://github.com/dmarcotte/idea-handlebars/pulls) are absolutely welcome

## Special thanks
* [Bazaarvoice](http://www.bazaarvoice.com), my fantastic employer, for providing the time to work on this
* Yehuda Katz for creating and maintaining [Handlebars](http://handlebarsjs.com/)
* The Jetbrains team for making such a great, extensible IDE platform
* Extra special thanks to Jan Dolecek and the [intellij-latte](https://github.com/juzna/intellij-latte) project (for having a clean code-base to learn from, and taking the time to make posts like [this gem](http://devnet.jetbrains.net/message/5450284?tstart=0); these went a long way to making this plug-in possible)