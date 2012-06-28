# IDEA-Handlebars:  [Handlebars](http://handlebarsjs.com/) template plugin for Jetbrains IDEs

IDEA-Handlebars adds support for Handlebars templates to IDEs based on the Intellij IDEA platform (IntelliJ IDEA, IDEA Community Edition, RubyMine, PhpStorm, WebStorm, PyCharm, AppCode).

## _NEW:_ Syntax error highlighting beta
* Syntax error highlighting has been implemented!  Grab [the beta](https://github.com/downloads/dmarcotte/idea-handlebars/idea-handlebars-0.2.beta-1.jar) and update [the pull](https://github.com/dmarcotte/idea-handlebars/pull/5) with your thoughts, and, of course, file any bugs you encounter.
    * Intallating:
        * In Settings->Plugins, disable IDEA-Handlebars (the beta installs along side the released version, which is nice in case you need to toggle back to the release for any reason)
        * Download [the beta](https://github.com/downloads/dmarcotte/idea-handlebars/idea-handlebars-0.2.beta-1.jar) and in Settings->Plugins, choose "Install plugin from disk..." functionality
    * Uninstalling:
        * In Settings->Plugins, right-click Handlebars-Beta and choose "Uninstall"
    * Compatability: will work with build 110.000 and later.  (If you don't know how to check your IDE's build number, no worries.  If you try and install, your IDE will tell you if the plugin is not compatable.)

## Installing the stable version
* To install the latest release (and get automatic updates), install this plugin using your IDE's plugin manager:
  * In Settings->Plugins, choose "Browse repositories".  Find "Handlebars" on the list, right-click, and select "Download and Install"

## Features
* Syntax error highlighting (currently in [beta](https://github.com/dmarcotte/idea-handlebars/pull/5))
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