# Contributing to IDEA-Handlebars

## Before Coding
**Before you do too much coding**, please make sure that you've either
* **Commented on the existing issue** you intend to try and tackle, or
* **Filed a new issue** proposing your idea

It would be a total shame to write a bunch of code only to realize someone else fixed the issue, or find out for whatever reason the changes you are proposing don't fit in the project.  Plus, this way you can get input on implementation ideas and any other support you might need.

## Coding Guidelines

The coding guidelines are pretty simple:

* Write tests surrounding your change
* Ensure that all existing tests pass with your change
    * **NOTE:** The tests in `com.dmarcotte.handlebars.editor.actions` and `com.dmarcotte.handlebars.parsing` must currently be **run separately** due to a bad dependency between the different IDEA test scaffolding these tests need.
* Generally keep it clean and follow the coding style of the existing code

## Pull Requests

* To submit a change, just follow the standard fork/code/pull-request Github workflow
* Feel free to submit pulls marked "WIP" (Work in progress) to get early feedback and help

