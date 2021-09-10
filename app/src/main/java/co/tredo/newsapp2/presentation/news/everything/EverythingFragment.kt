package co.tredo.newsapp2.presentation.news.everything

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import co.tredo.newsapp2.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class EverythingFragment : Fragment(R.layout.fragment_everything) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    companion object {

        fun newInstance(param1: String, param2: String) =
            EverythingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}