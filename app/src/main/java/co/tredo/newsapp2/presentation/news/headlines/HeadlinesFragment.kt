package co.tredo.newsapp2.presentation.news.headlines

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.paging.LoadState
import co.tredo.newsapp2.R
import co.tredo.newsapp2.data.remote.NewsPagingSource
import co.tredo.newsapp2.data.remote.RetrofitInstance
import co.tredo.newsapp2.data.repositoryImpl.NewsRepositoryImpl
import co.tredo.newsapp2.databinding.FragmentHeadlinesBinding
import co.tredo.newsapp2.domain.usecase.GetHeadlinesUseCase
import co.tredo.newsapp2.presentation.news.HorizontalMarginItemDecoration
import co.tredo.newsapp2.presentation.news.NewsAdapter
import co.tredo.newsapp2.util.alertPayload
import co.tredo.newsapp2.util.viewBinding
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filterNotNull

class HeadlinesFragment : Fragment(R.layout.fragment_headlines) {

    private val binding: FragmentHeadlinesBinding by viewBinding(FragmentHeadlinesBinding::bind)

    private val vm: HeadlinesViewModel by viewModels {
        HeadlinesViewModelFactory(
            GetHeadlinesUseCase.Default(
                NewsRepositoryImpl(
                    NewsPagingSource(RetrofitInstance.newsApi)
                )
            )
        )
    }

    private val adapter by lazy { NewsAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.setupSubscriptions()
    }

    private fun FragmentHeadlinesBinding.setupSubscriptions() {

        headlinesRecyclerView.adapter = adapter
        adapter.addLoadStateListener { state ->
            headlinesRecyclerView.isVisible = state.refresh != LoadState.Loading
            progressBar.isVisible = state.refresh == LoadState.Loading
        }

        val decoration = HorizontalMarginItemDecoration(
            requireContext(),
            R.dimen.recyclerViewVerticalMargin,
            R.dimen.recyclerViewHorizontalMargin,
        )
        headlinesRecyclerView.addItemDecoration(decoration)

        lifecycle.coroutineScope.launchWhenStarted {
            vm.news.filterNotNull().collect { news ->
                adapter.submitData(news)
            }
        }

        lifecycle.coroutineScope.launchWhenStarted {
            vm.error.collect {
                requireContext().alertPayload(message = it, negativeId = null)
            }
        }
    }
}