package com.hendisantika.controller;

import com.hendisantika.service.AppService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by IntelliJ IDEA.
 * Project : Movie-Collections
 * User: powercommerce
 * Email: hendisantika@gmail.com
 * Telegram : @hendisantika34
 * Date: 7/18/22
 * Time: 05:43
 * To change this template use File | Settings | File Templates.
 */
@RestController
@RequestMapping("/rest")
@RequiredArgsConstructor
public class AppRestController {

    private final AppService appService;
}
