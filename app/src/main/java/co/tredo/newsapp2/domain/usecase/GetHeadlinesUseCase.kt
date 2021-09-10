package co.tredo.newsapp2.domain.usecase

import androidx.paging.PagingSource
import co.tredo.newsapp2.domain.model.Article
import co.tredo.newsapp2.domain.repository.NewsRepository

interface GetHeadlinesUseCase {

    fun execute(): PagingSource<Int, Article>

    class Default(private val newsRepository: NewsRepository) : GetHeadlinesUseCase {

        override fun execute(): PagingSource<Int, Article> =
            newsRepository.getHeadlines()
    }
}