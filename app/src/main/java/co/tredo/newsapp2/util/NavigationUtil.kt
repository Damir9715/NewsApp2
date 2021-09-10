package co.tredo.newsapp2.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import co.tredo.newsapp2.R

inline fun <reified T : Fragment> AppCompatActivity.replaceFragment(
    bundle: Bundle? = null,
    addToBackStack: Boolean = true
) {
    supportFragmentManager.commit {
        if (addToBackStack) addToBackStack(null)
        setReorderingAllowed(true)
        if (bundle == null) {
            replace<T>(R.id.fragmentContainerView)
        } else {
            replace(R.id.fragmentContainerView, T::class.java, bundle)
        }
    }
}