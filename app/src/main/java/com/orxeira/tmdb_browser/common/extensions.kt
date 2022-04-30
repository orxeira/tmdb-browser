package com.orxeira.tmdb_browser.common

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgs
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.paging.LoadStateAdapter
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

fun ImageView.loadUrl(url: String) {
    Glide.with(context).load(url).into(this)
}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = true): View =
    LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

fun Fragment.getNavController(id: Int) = requireActivity().findNavController(id)
fun NavController.popToNewDestinationBase(whereGo: Int, args: NavArgs? = null) {
    this.navigate(
        whereGo, null,
        NavOptions.Builder().setPopUpTo(this.graph.startDestinationId, true).build()
    )
}

fun <T : Any, V : RecyclerView.ViewHolder> PagingDataAdapter<T, V>.withLoadStateAdapters(
    header: LoadStateAdapter<*>,
    footer: LoadStateAdapter<*>
): ConcatAdapter {
    addLoadStateListener { loadStates ->
        header.loadState = loadStates.refresh
        footer.loadState = loadStates.append
    }

    return ConcatAdapter(header, this, footer)
}

//fun <T> LifecycleOwner.launchAndCollect(
//    flow: Flow<T>,
//    state: Lifecycle.State = Lifecycle.State.STARTED,
//    body: (T) -> Unit
//) {
//    lifecycleScope.launch {
//        this@launchAndCollect.repeatOnLifecycle(state) {
//            flow.collect(body)
//        }
//    }
//}