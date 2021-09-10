package co.tredo.newsapp2.data.remote.response

import co.tredo.newsapp2.data.local.entity.Article as ArticleEntity
import co.tredo.newsapp2.domain.model.Article as ArticleModel

data class Article(
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)

fun Article.toEntity(): ArticleEntity {
    return ArticleEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.name,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}

fun Article.toModel(): ArticleModel {
    return ArticleModel(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = source?.name,
        title = title,
        url = url,
        urlToImage = urlToImage
    )
}