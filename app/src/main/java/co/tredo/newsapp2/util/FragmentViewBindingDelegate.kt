package co.tredo.newsapp2.util

import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.coroutineScope
import androidx.viewbinding.ViewBinding
import kotlin.properties.ReadOnlyProperty
import kotlin.reflect.KProperty

class FragmentViewBindingDelegate<T : ViewBinding>(
    private val fragment: Fragment,
    val viewBindingFactory: (View) -> T
) : ReadOnlyProperty<Fragment, T> {

    private var binding: T? = null

    init {
        fragment.lifecycle.addObserver(OnCreateObserver {
            fragment.viewLifecycleOwnerLiveData.observe(fragment) { viewLifecycleOwner ->
                viewLifecycleOwner.lifecycle.addObserver(OnDestroyObserver {
                    binding = null
                })
            }
        })
    }

    override fun getValue(thisRef: Fragment, property: KProperty<*>): T {
        binding?.also { return it }

        val lifecycle = fragment.viewLifecycleOwner.lifecycle
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.INITIALIZED)) {
            return viewBindingFactory(thisRef.requireView()).also { this.binding = it }
        }

        throw IllegalAccessOnViewsDestroyedException
    }

    object IllegalAccessOnViewsDestroyedException : IllegalStateException(
        "Should not attempt to get bindings when Fragment views are destroyed."
    )
}

fun <T : ViewBinding> Fragment.viewBinding(viewBindingFactory: (View) -> T) =
    FragmentViewBindingDelegate(this, viewBindingFactory)


inline fun Lifecycle.doOnCreated(crossinline block: () -> Unit) {
    if (currentState.isAtLeast(Lifecycle.State.CREATED)) {
        block()
    } else {
        coroutineScope.launchWhenCreated { block() }
    }
}

fun interface OnCreateObserver : DefaultLifecycleObserver {

    fun doOnCreate()

    override fun onCreate(owner: LifecycleOwner) = doOnCreate()
}

fun interface OnDestroyObserver : DefaultLifecycleObserver {

    fun doOnDestroy()

    override fun onDestroy(owner: LifecycleOwner) = doOnDestroy()
}