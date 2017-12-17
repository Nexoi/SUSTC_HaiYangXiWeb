package com.liuyi.web.Controller;

import com.liuyi.web.Model.Article;
import com.liuyi.web.Model.Articles;
import com.liuyi.web.Utils.Crawler;
import com.liuyi.web.repository.ArticleRepository;
import com.liuyi.web.repository.ArticlesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.validation.OverridesAttribute;
import java.util.List;

@Controller
public class PageController {

    @Resource
    private ArticlesRepository articlesRepository;
    @Resource
    private ArticleRepository articleRepository;

    @RequestMapping("/")
    public String home(Model model) {
        List<String> imgPath = Crawler.GetImgPath();
        model.addAttribute("imgPath", imgPath);
        return "index";
    }

    // 查看某一类页面：http://xxx.com/2.html
    @GetMapping("/{id}.html")
    public String categoryPage(@PathVariable("id") Integer id) {
        // 跳转到那一类文章的第 id 篇即可
        return "redirect:/article/" + id;
    }

    // 查看某一篇文章：http://xxx.com/article/2
    @GetMapping("/article/{id}")
    public String articleById(@PathVariable("id") Integer id, ModelAndView modelAndView) {
        Articles articles = articlesRepository.findOne(id);
        if (articles == null)
            return "page404";

        Article article = articleRepository.findOne(id);
        // modelview
        modelAndView.addObject("info", articles);
        modelAndView.addObject("detail", article);
        String category = articles.getCategory();
        switch (category) {
            case "新闻":
                return "page1";
            case "人员构成":
                return "page2";
            //...
            default:
                return "pageN";
        }
    }
}
