package com.hendisantika.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:30
 * To change this template use File | Settings | File Templates.
 */
public class TextForm {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Long> StrToIds() {
        List<Long> ids = new ArrayList<>();
        for (String elem : this.text.split(",")) {
            ids.add(Long.parseLong(elem));
        }

        return ids;
    }
}
