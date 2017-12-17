package com.liuyi.web.repository

import com.liuyi.web.Model.Article
import com.liuyi.web.Model.ArticleComment
import com.liuyi.web.Model.Articles
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import javax.transaction.Transactional

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 05/12/2017
 * Time: 6:07 PM
 * Describe:
 */
interface ArticleRepository : JpaRepository<Article, Int> {

}

interface ArticlesRepository : JpaRepository<Articles, Int> {
    fun findByTitleLike(@Param("title") title: String, pageable: Pageable): Page<Articles>
    fun findByCategory(@Param("category") category: String, pageable: Pageable): Page<Articles>
    fun findByCategoryAndTitleLike(@Param("category") category: String, @Param("title") title: String, pageable: Pageable): Page<Articles>

    fun findTop3ByOrderByPublishTimeDesc(): List<Articles>

    @Transactional
    @Modifying
    @Query("update Articles p set p.likeNum = p.likeNum + 1 where p.id = ?1")
    fun likeItOnce(id: Int): Int

    @Transactional
    @Modifying
    @Query("update Articles p set p.commentNum = p.commentNum + 1 where p.id = ?1")
    fun commentItOnce(id: Int): Int

    @Transactional
    @Modifying
    @Query("update Articles p set p.viewNum = p.viewNum + 1 where p.id = ?1")
    fun viewItOnce(id: Int): Int
}

interface ArticleCommentRepository : JpaRepository<ArticleComment, Long> {

}
