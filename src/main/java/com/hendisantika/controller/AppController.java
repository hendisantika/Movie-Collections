package com.hendisantika.controller;

import com.hendisantika.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/18/22
 * Time: 05:38
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequiredArgsConstructor
public class AppController {

    private final AppService appService;
}
