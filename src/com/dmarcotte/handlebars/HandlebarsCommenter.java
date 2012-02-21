package com.dmarcotte.handlebars;

import com.intellij.lang.Commenter;

public class HandlebarsCommenter implements Commenter {
    public String getLineCommentPrefix() {
        return null;
    }

    public String getBlockCommentPrefix() {
        return "<!--";
    }

    public String getBlockCommentSuffix() {
        return "-->";
    }

    public String getCommentedBlockCommentPrefix() {
        return "<!--";
    }

    public String getCommentedBlockCommentSuffix() {
        return "-->";
    }
}
