package co.tredo.newsapp2.domain.repository

import androidx.paging.PagingSource
import co.tredo.newsapp2.domain.model.Article


interface NewsRepository {

    fun getHeadlines(): PagingSource<Int, Article>
}