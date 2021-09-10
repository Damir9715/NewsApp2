package co.tredo.newsapp2.data.remote.response

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)