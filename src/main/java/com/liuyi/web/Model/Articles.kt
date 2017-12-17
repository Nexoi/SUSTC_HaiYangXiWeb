package com.liuyi.web.Model


import org.hibernate.validator.constraints.Length
import java.util.*
import javax.persistence.*

/**
 * Created by neoxiaoyi.
 * User: neo
 * Date: 05/12/2017
 * Time: 6:05 PM
 * Describe:
 */

@Entity
@Table(name = "articles", indexes = arrayOf(
        Index(name = "ARTICLES_INDEX1", unique = false, columnList = "id"),
        Index(name = "ARTICLES_INDEX2", unique = false, columnList = "uid"),
        Index(name = "ARTICLES_INDEX3", unique = false, columnList = "category")
))
data class Articles(
        @Id
        var id: Int? = null,
        var uid: String? = null,
        @Length(min = 1)
        var name: String? = null, // 用户名
        @Column(length = 14)
        var category: String? = null,
        val publishTime: Date? = Date(), // current timestamp
        @Length(min = 1, max = 64, message = "Article title length cannot out bound of 64")
        var title: String? = null,
        @Length(min = 1, max = 255, message = "The length of article introduction describe text cannot out bound of 255")
        var sortDescribe: String? = null,
        val commentNum: Int = 0, // default comments count is zero
        val likeNum: Int = 0, // default like count is zero
        val viewNum: Int = 0, // default like count is zero
        var imgUrl: String? = null      // img thumb url
)

@Entity
@Table(name = "article")
data class Article(
        @Id @GeneratedValue
        var id: Int? = null,
        @Column(length = Int.MAX_VALUE, nullable = false)
        var text: String? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "article_id") // 根据 article 的 id 去查询对应评论集合
        @OrderBy("createTime DESC")
        var comments: List<ArticleComment>? = ArrayList<ArticleComment>()
)

@Entity
@Table(name = "article_comment")
data class ArticleComment(
        @Id @GeneratedValue
        var id: Long? = null,
        @Column(name = "father_id")
        var fatherId: Long? = null, // 外键关联
        @Column(name = "article_id")
        var articleId: Int? = null, // 外键关联

        var uid: Long? = null,
        var name: String? = null,

        @OneToMany(cascade = arrayOf(CascadeType.ALL), fetch = FetchType.LAZY)
        @JoinColumn(name = "father_id") // 根据 comment 的 id 去查询对应子评论集合
        var childComments: List<ArticleComment>? = ArrayList<ArticleComment>(),
        val createTime: Date? = Date(),
        @Length(min = 1, max = 255, message = "The legth of comment text cannot out bound of 255")
        var text: String? = null
)