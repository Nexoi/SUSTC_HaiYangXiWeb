package com.liuyi.web.Controller;

import com.liuyi.web.Model.Article;
import com.liuyi.web.Model.Articles;
import com.liuyi.web.Model.User;
import com.liuyi.web.repository.ArticleRepository;
import com.liuyi.web.repository.ArticlesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 05/12/2017
 * Time: 6:08 PM
 * Describe:
 * <p>
 * 提供基本的文章功能 【RESTful】
 * <p>
 * 1. 搜索／列出文章列表 【分页】【无权限验证】
 * [GET] /api/v1/articles
 * <p>
 * 2. 查看某一篇文章
 * [GET] /api/v1/articles/{id}  【无权限验证】
 * <p>
 * 3. 增加文章
 * [POST] /api/v1/articles 【管理员】
 * <p>
 * 4. 更新文章
 * [PUT] /api/v1/articles/{id} 【管理员】
 * <p>
 * 5. 删除文章
 * [DELETE] /api/v1/articles/{id} 【管理员】
 */
@RestController
@RequestMapping("/api/v1/articles")
public class ArticleController {

    @Resource
    ArticlesRepository articlesRepository; // 文章列表
    @Resource
    ArticleRepository articleRepository; // 文章详情

    /**
     * 搜索文章列表
     *
     * @param word     关键词（根据标题）
     * @param category 分类（字符串，如："新闻公告"）
     * @param page     默认值 0
     * @param size     默认值 10
     * @return
     */
    @GetMapping
    public ResponseEntity list(@RequestParam(defaultValue = "") String word,
                               @RequestParam(defaultValue = "") String category,  // category id
                               @RequestParam(defaultValue = "0") Integer page,
                               @RequestParam(defaultValue = "10") Integer size) {
        Page articlesPage = null;
        if ("".equals(category)) {
            // 查询所有信息
            articlesPage = articlesRepository.findByTitleLike("%" + word + "%", new PageRequest(page, size));
        } else {
            // 根据分类进行查询
            articlesPage = articlesRepository.findByCategoryAndTitleLike(category, "%" + word + "%", new PageRequest(page, size));
        }
        // return null?? 204:200
        return articlesPage == null ? ResponseEntity.noContent().build() : ResponseEntity.ok().body(articlesPage);
    }


    /**
     * 查看具体某一篇文章信息
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public ResponseEntity get(@PathVariable Integer id) {
        Articles article = articlesRepository.findOne(id);
        if (article == null)
            return ResponseEntity.noContent().build(); // 没有该文章，204
        Article articleDetail = articleRepository.findOne(id);
        Map result = new HashMap();
        result.put("info", article);
        result.put("detail", articleDetail);
        return ResponseEntity.ok().body(result);
    }

    /**
     * 新建一篇文章
     *
     * @param user     管理员信息，不需要上传，若已经登录系统会自动注入，否则为 null
     * @param articles 文章基本信息
     * @param text     文章详情内容
     * @return
     */
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity add(@AuthenticationPrincipal User user,
                              Articles articles,
                              String text) {
        if (user == null)
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("not authoritative");
        if (text == null || text.trim().length() == 0)
            return ResponseEntity.badRequest().body("please input text in this article");
        articles.setId(null);
        articles.setUid(user.getId());
        Articles afterSavedArticle = articlesRepository.save(articles);
        Article article = new Article(afterSavedArticle.getId(), text, null);
        articleRepository.save(article);
        return ResponseEntity.ok().body("post success");
    }

    /**
     * 更新文章
     * <p>
     * 注意！更新为非选择性更新，请务必传入每一个字段的准确内容，若为 null 则会覆盖曾经的数据
     *
     * @param id
     * @param user
     * @param articles
     * @param text
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity update(@PathVariable Integer id,
                                 @AuthenticationPrincipal User user,
                                 Articles articles,
                                 String text) {
        if (user == null)
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("not authoritative");
        Articles articlesExist = articlesRepository.findOne(id);
        if (articlesExist == null)
            return ResponseEntity.noContent().build(); // 无此文章
        articles.setUid(user.getId()); // 将发布者替换为更新者（可选，建议删除）
        articlesRepository.save(articles);
        if (text != null && text.trim().length() != 0) {
            // 否则只需要更新列表信息，详情内容不需要更新
            Article article = new Article(id, text, null);
            articleRepository.save(article);
        }
        return ResponseEntity.ok().body("update success");
    }

    /**
     * 删除文章详情及其列表内容
     *
     * @param id
     * @param user
     * @return
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity delete(@PathVariable Integer id,
                                 @AuthenticationPrincipal User user) {
        if (user == null)
            return ResponseEntity.status(HttpStatus.NON_AUTHORITATIVE_INFORMATION).body("not authoritative");
        articlesRepository.delete(id);
        articleRepository.delete(id);
        // 此处建议增加日志记录
        return ResponseEntity.ok().body("delete success");
    }

}
