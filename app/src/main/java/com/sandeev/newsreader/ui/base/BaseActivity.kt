package com.sandeev.newsreader.ui.base

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancelChildren
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity : AppCompatActivity(), CoroutineScope {

    private var shortAnimationDuration: Int = 0

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + SupervisorJob()

    override fun onDestroy() {
        coroutineContext.cancelChildren()
        super.onDestroy()
    }

    fun showView(view: View?) {
        shortAnimationDuration =
            resources?.getInteger(android.R.integer.config_shortAnimTime) ?: 0

        view?.apply {
            // Set the content view to 0% opacity but visible, so that it is visible
            // (but fully transparent) during the animation.
            alpha = 0f
            visibility = View.VISIBLE

            // Animate the content view to 100% opacity, and clear any animation
            // listener set on the view.
            animate()
                .alpha(1f)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }

    fun hideView(view: View?) {
        shortAnimationDuration =
            resources?.getInteger(android.R.integer.config_shortAnimTime) ?: 0

        // Animate the showProgress view to 0% opacity. After the animation ends,
        // set its visibility to GONE as an optimization step (it won't
        // participate in layout passes, etc.)
        view?.animate()
            ?.alpha(0f)
            ?.setDuration(shortAnimationDuration.toLong())
            ?.setListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    view.visibility = View.GONE
                }
            })
    }
}