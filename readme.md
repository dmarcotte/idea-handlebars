# [Handlebars](http://handlebarsjs.com/)/[Mustache](http://mustache.github.com/) template plugin for Jetbrains IDEs

IDEA-Handlebars adds support for [Handlebars](http://handlebarsjs.com/) and [Mustache](http://mustache.github.com/) templates to IDEs based on the Intellij IDEA platform (IntelliJ IDEA, IDEA Community Edition, RubyMine, PhpStorm, WebStorm, PyCharm, AppCode).

## _NEW:_ Auto-insert of close tags
The latest release adds [auto-inserting of closing tags](https://github.com/dmarcotte/idea-handlebars/pull/9) and improves the plugin's [charset defaulting](https://github.com/dmarcotte/idea-handlebars/pull/12)

## Installing
* To install the latest release (and get automatic updates), install this plugin using your IDE's plugin manager:
  * In Settings->Plugins, choose "Browse repositories".  Find "Handlebars/Mustache" on the list, right-click, and select "Download and Install"

## Features
* Syntax error highlighting
* Configurable syntax highlighting
* Auto-insert of closing tags
* Matched mustache pair highlighting
* Full HTML highlighting, code completion, inspections, formatting and commenting for the HTML content in your templates
* By default, files with the ".handlebars", ".hbs" or ".mustache" extensions are handled by this plugin

## Future directions
* See [the pulls](https://github.com/dmarcotte/idea-handlebars/pulls) and [issues](https://github.com/dmarcotte/idea-handlebars/issues) for a preview of in-progress and planned features

## Contributing
Contributions welcome!

There's a variety of ways you can help this project out:

* Contributing without coding
    * [File issues](https://github.com/dmarcotte/idea-handlebars/issues/new) for bugs, suggestions and requests.  This is a great and easy way to help out
    * Fluent in multiple languages?  [Provide a translation!](https://github.com/dmarcotte/idea-handlebars/issues/21)
* Contributing code
    * Have a gander at the [contributor guidelines](https://github.com/dmarcotte/idea-handlebars/blob/master/contributing.md)
    * The [developer setup instructions](https://github.com/dmarcotte/idea-handlebars/blob/master/developer_environment.md) should get you up and running in no time
    * Look at [contribs-welcome issues](https://github.com/dmarcotte/idea-handlebars/issues?direction=desc&labels=contrib-welcome&page=1&sort=created&state=open) for ideas, or [submit an idea of your own](https://github.com/dmarcotte/idea-handlebars/issues/new)

## Special thanks
* [Bazaarvoice](http://www.bazaarvoice.com), my fantastic employer, for providing the time to work on this
* Yehuda Katz for creating and maintaining [Handlebars](http://handlebarsjs.com/)
* The Jetbrains team for making such a great, extensible IDE platform
* Extra special thanks to Jan Dolecek and the [intellij-latte](https://github.com/juzna/intellij-latte) project (for having a clean code-base to learn from, and taking the time to make posts like [this gem](http://devnet.jetbrains.net/message/5450284?tstart=0); these went a long way to making this plug-in possible)