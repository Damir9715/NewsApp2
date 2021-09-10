package co.tredo.newsapp2.data.remote

import androidx.paging.PagingSource
import androidx.paging.PagingState
import co.tredo.newsapp2.data.remote.response.toModel
import co.tredo.newsapp2.domain.model.Article
import co.tredo.newsapp2.util.Hardcode.NEWS_CATEGORY
import co.tredo.newsapp2.util.Hardcode.NEWS_PAGE_SIZE
import retrofit2.HttpException

class NewsPagingSource(
    private val newsApi: NewsApi,
) : PagingSource<Int, Article>() {

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val anchorPage = state.closestPageToPosition(anchorPosition) ?: return null
        return anchorPage.prevKey?.plus(1) ?: anchorPage.nextKey?.minus(1)
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> = try {

        val pageNumber = params.key ?: 1
        val pageSize = params.loadSize.coerceAtMost(NEWS_PAGE_SIZE)
        val newsResponse = newsApi.getTopHeadlines(NEWS_CATEGORY, pageNumber, pageSize)

        if (newsResponse.isSuccessful) {
            newsResponse.body()?.let { response ->
                val articles = response.articles.map { it.toModel() }
                val nextPageNumber = if (articles.isEmpty()) null else pageNumber + 1
                val prevPageNumber = if (pageNumber > 1) pageNumber - 1 else null
                return LoadResult.Page(articles, prevPageNumber, nextPageNumber)
            } ?: LoadResult.Error(HttpException(newsResponse))
        } else {
            LoadResult.Error(HttpException(newsResponse))
        }
    } catch (exception: HttpException) {
        LoadResult.Error(exception)
    } catch (exception: Exception) {
        LoadResult.Error(exception)
    }
}