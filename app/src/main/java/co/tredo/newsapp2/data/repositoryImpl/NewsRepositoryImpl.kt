package co.tredo.newsapp2.data.repositoryImpl

import androidx.paging.PagingSource
import co.tredo.newsapp2.data.remote.NewsPagingSource
import co.tredo.newsapp2.domain.model.Article
import co.tredo.newsapp2.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val newsPagingSource: NewsPagingSource
//    private val newsDatabase: NewsDatabase
) : NewsRepository {

    override fun getHeadlines(): PagingSource<Int, Article> = newsPagingSource
}