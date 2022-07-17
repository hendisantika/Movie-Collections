package com.hendisantika.util;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:29
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieFormWrapper {
    private String name;
    private Date date;
    private String category;
    private String description;
    private float rating;
    private String image;
    private String castRef;

    public MovieFormWrapper(String text) {
        this.castRef = text;
    }
}
