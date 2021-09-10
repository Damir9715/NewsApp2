package co.tredo.newsapp2.presentation.article

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import co.tredo.newsapp2.R

private const val KEY_URL = "KEY_URL"

class ArticleFragment : Fragment(R.layout.fragment_article) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    companion object {

        fun newInstance(url: String) = ArticleFragment().apply {
            arguments = Bundle().apply {
                putString(KEY_URL, url)
            }
        }
    }
}