package co.tredo.newsapp2.presentation.news.headlines

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingSource
import co.tredo.newsapp2.domain.model.Article
import co.tredo.newsapp2.domain.usecase.GetHeadlinesUseCase
import co.tredo.newsapp2.util.Hardcode.NEWS_PAGE_SIZE
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

interface HeadlinesViewModel {

    val news: Flow<PagingData<Article>?>

    val error: Flow<String>

    val reload: FlowCollector<Unit>

    class Default(
        private val getHeadlinesUseCase: GetHeadlinesUseCase
    ) : ViewModel(), HeadlinesViewModel {

        override val news: MutableStateFlow<PagingData<Article>?> = MutableStateFlow(null)

        override val error = MutableSharedFlow<String>()

        override val reload = MutableSharedFlow<Unit>()

        private var newPagingSource: PagingSource<*, *>? = null

        init {
            viewModelScope.launch {
                getNews()
            }

            viewModelScope.launch {
                reload.collect {
                    getNews()
                }
            }
        }

        private suspend fun getNews() {
            Pager(PagingConfig(NEWS_PAGE_SIZE)) {
                newPagingSource?.invalidate()
                getHeadlinesUseCase.execute().also { newPagingSource = it }
            }.flow.collect {
                news.emit(it)
            }
        }
    }
}

@Suppress("UNCHECKED_CAST")
class HeadlinesViewModelFactory(
    private val getHeadlinesUseCase: GetHeadlinesUseCase
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T =
        HeadlinesViewModel.Default(getHeadlinesUseCase) as T
}