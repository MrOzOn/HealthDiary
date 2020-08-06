package com.mrozon.core.base

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.InflateException
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection

abstract class BaseFragment<T : ViewDataBinding>: Fragment(),
    ViewTreeObserver.OnGlobalLayoutListener {

    var rootView: View? = null
    var binding: T? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    override fun onGlobalLayout() {
        rootView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
    }

//    override fun onCreate(savedInstanceState: Bundle?) {
//        performDI()
//        super.onCreate(savedInstanceState)
//    }

    override fun onAttach(context: Context) {
        performDI()
        super.onAttach(context)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
        if (rootView != null) {
            val parent = rootView!!.parent as ViewGroup
            parent.removeView(rootView)
        } else {
            try {
                binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
                rootView = if (binding != null) {
                    binding!!.root
                } else {
                    inflater.inflate(layoutId, container, false)
                }
                rootView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
                binding!!.executePendingBindings()

            } catch (e: InflateException) {
                e.printStackTrace()
            }
        }
        return rootView
    }

    private fun performDI() {
        AndroidSupportInjection.inject(this)
    }

}
