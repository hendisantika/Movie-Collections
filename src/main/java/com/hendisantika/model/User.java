package com.hendisantika.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/17/22
 * Time: 18:08
 * To change this template use File | Settings | File Templates.
 */
@Data
@AllArgsConstructor
@Builder
public class User {
    private String username;
    private String password;
    private boolean enabled;
    private String role;

    public User() {
        this.role = "ROLE_USER";
    }
}
