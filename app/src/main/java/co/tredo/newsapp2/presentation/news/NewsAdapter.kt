package co.tredo.newsapp2.presentation.news

import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DimenRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import co.tredo.newsapp2.databinding.NewsCardBinding
import co.tredo.newsapp2.domain.model.Article
import com.bumptech.glide.Glide

class NewsAdapter : PagingDataAdapter<Article, NewsAdapter.ViewHolder>(NewsDiffUtil()) {

    class ViewHolder(private val binding: NewsCardBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Article?) {
            item ?: return
            with(binding) {
                //todo move transformations to viewModel or useCase
                sourceAndDateTextView.text =
                    "${item.source.orEmpty()} • ${item.publishedAt.orEmpty()}"
                titleTextView.text = item.title.orEmpty()
                descriptionTextView.text = item.description.orEmpty()
                item.urlToImage?.let {
                    Glide.with(binding.root.context).load(it).into(imageView)
                }
            }
        }

        companion object {

            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = NewsCardBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) =
        holder.bind(getItem(position))
}

class NewsDiffUtil : DiffUtil.ItemCallback<Article>() {

    override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem.url == newItem.url

    override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean =
        oldItem == newItem
}

class HorizontalMarginItemDecoration(
    context: Context,
    @DimenRes verticalMarginInDp: Int,
    @DimenRes horizontalMarginInDp: Int
) : RecyclerView.ItemDecoration() {

    private val verticalMarginInPx: Int =
        context.resources.getDimension(verticalMarginInDp).toInt()

    private val horizontalMarginInPx: Int =
        context.resources.getDimension(horizontalMarginInDp).toInt()

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        outRect.top = verticalMarginInPx
        outRect.bottom = verticalMarginInPx
        outRect.left = horizontalMarginInPx
        outRect.right = horizontalMarginInPx
    }
}