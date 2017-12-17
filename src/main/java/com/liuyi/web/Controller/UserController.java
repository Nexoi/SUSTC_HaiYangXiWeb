package com.liuyi.web.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 05/12/2017
 * Time: 6:32 PM
 * Describe:
 * <p>
 * 用户登录模块
 *
 * 这是个网页
 */
@Controller
public class UserController {

    /**
     * 这个页面进行登录，必须使用 GET 请求，否则会与 POST 请求冲突
     * （【POST /login】 已经分配给 spring security 作为登录验证接口）
     *
     * @return
     */
    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error) {
        if (error != null)
            return "admin/loginFailure";
        return "admin/login";
    }
}
