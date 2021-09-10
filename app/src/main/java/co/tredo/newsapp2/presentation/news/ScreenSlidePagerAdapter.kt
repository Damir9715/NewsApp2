package co.tredo.newsapp2.presentation.news

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.tredo.newsapp2.presentation.news.everything.EverythingFragment
import co.tredo.newsapp2.presentation.news.headlines.HeadlinesFragment

class ScreenSlidePagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> HeadlinesFragment()
            else -> EverythingFragment()
        }
    }
}