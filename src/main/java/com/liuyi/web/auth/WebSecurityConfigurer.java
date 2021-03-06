package com.liuyi.web.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)  // TODO 方法注解开启（关闭时方便调试）
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

    @Autowired
    private SEEUAuthenticationProvider seeuAuthenticationProvider;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(authUserService()); // 只配置了一个数据源，建议用 provider 配置多个，如 CAS、QQ、Weibo 等
        auth.authenticationProvider(seeuAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 暂时关闭 csrf 认证
                .authorizeRequests()
//                .antMatchers("/").permitAll() // 放行
//                .anyRequest().authenticated() // 验证所有的路径（不需要）
                .anyRequest().permitAll()       // 全部放行，不做验证（调试环境适用）
//                .antMatchers("/api/v1/article/**","/api/v1/pure").authenticated()  // 文章类 API 都需要验证【发布功能、评论】

                .and()
                .formLogin()
                .usernameParameter("username") // 用 email 登录
                .loginPage("/login")
                //设置默认登录成功跳转页面
//                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .permitAll()

                .and()
                //开启cookie保存用户数据
                .rememberMe()
                //设置cookie有效期
                .tokenValiditySeconds(60 * 60 * 24 * 7)
                //设置cookie的私钥
                .key("seeucoco")

                .and()
                .logout()
                //默认注销行为为logout，可以通过下面的方式来修改
                .logoutUrl("/logout")
                //设置注销成功后跳转页面，默认是跳转到登录页面
                .logoutSuccessUrl("/")
                .permitAll()


                .and()
                .httpBasic();

//        http.csrf().disable() // oauth server 不需要 csrf 防护
//                .authorizeRequests()
//                .antMatchers("/**").permitAll() //其他页面都可以访问
//                .antMatchers("/data/**").authenticated();// 需要认证即可访问
//                .and()
//                .httpBasic().disable(); // 禁止 basic 认证

        // 302 转 401 完成 REST-ful 的权限限制时返回 302 的不合理信息
        http.exceptionHandling()
                //Actually Spring already configures default AuthenticationEntryPoint - LoginUrlAuthenticationEntryPoint
                //This one is REST-specific addition to default one, that is based on PathRequest
                .defaultAuthenticationEntryPointFor(
                        getRestAuthenticationEntryPoint(),
                        new AntPathRequestMatcher("/api/**"));

    }

    private AuthenticationEntryPoint getRestAuthenticationEntryPoint() {
        return new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED);
    }
}
