package co.tredo.newsapp2.presentation.news

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import co.tredo.newsapp2.R
import co.tredo.newsapp2.databinding.FragmentNewsBinding
import co.tredo.newsapp2.util.viewBinding
import com.google.android.material.tabs.TabLayout

class NewsFragment : Fragment(R.layout.fragment_news) {

    private val binding: FragmentNewsBinding by viewBinding(FragmentNewsBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.initialize()
    }

    private fun FragmentNewsBinding.initialize() {
        viewPager.adapter = ScreenSlidePagerAdapter(parentFragmentManager, lifecycle)

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) =
                tabLayout.selectTab(tabLayout.getTabAt(position))
        })

        tabLayout.apply {
            addTab(newTab().setText("Headlines"))
            addTab(newTab().setText("Everything"))
        }

        tabLayout.addOnTabSelectedListener(OnTabSelected {
            viewPager.currentItem = it.position
        })
    }
}

private fun interface OnTabSelected : TabLayout.OnTabSelectedListener {

    fun doOnTabSelected(tab: TabLayout.Tab)

    override fun onTabSelected(tab: TabLayout.Tab?) {
        tab?.let { doOnTabSelected(tab) }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) = Unit

    override fun onTabReselected(tab: TabLayout.Tab?) = Unit
}