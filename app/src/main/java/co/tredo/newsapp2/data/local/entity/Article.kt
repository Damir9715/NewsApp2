package co.tredo.newsapp2.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import co.tredo.newsapp2.domain.model.Article as ArticleModel

@Entity(tableName = "article")
data class Article(
    @PrimaryKey(autoGenerate = true)
    val id: Long? = null,
    val author: String? = null,
    val content: String? = null,
    val description: String? = null,
    val publishedAt: String? = null,
    val source: String? = null,
    val title: String? = null,
    val url: String? = null,
    val urlToImage: String? = null
)

fun Article.toModel(): ArticleModel {
    return ArticleModel(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun ArticleModel.toEntity(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}