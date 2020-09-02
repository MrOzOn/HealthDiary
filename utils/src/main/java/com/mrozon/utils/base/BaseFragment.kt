package com.mrozon.utils.base

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.InflateException
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat.getColor
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.snackbar.Snackbar
import com.mrozon.utils.R
import timber.log.Timber

abstract class BaseFragment<T : ViewDataBinding>: Fragment()//,
{
//    ViewTreeObserver.OnGlobalLayoutListener {

//    var rootView: View? = null
    var binding: T? = null

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun subscribeUi()

//    override fun onGlobalLayout() {
//        rootView!!.viewTreeObserver.removeOnGlobalLayoutListener(this)
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutId = getLayoutId()
//        if (rootView != null) {
//            val parent = rootView!!.parent as ViewGroup
//            parent.removeView(rootView)
//        } else {
//            try {
//                binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
//                rootView = if (binding != null) {
//                    binding!!.root
//                } else {
//                    inflater.inflate(layoutId, container, false)
//                }
//                rootView!!.viewTreeObserver.addOnGlobalLayoutListener(this)
//                binding!!.executePendingBindings()
//
//            } catch (e: InflateException) {
//                e.printStackTrace()
//            }
//        }
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        subscribeUi()
        return binding!!.root
    }

    fun showError(message: String, action:()->Unit) {
        val snackbar = Snackbar.make(binding?.root!!,message,Snackbar.LENGTH_INDEFINITE)
        snackbar.view.setBackgroundColor(getColor(requireContext(),R.color.color_snack_error))
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setAction(R.string.Ok) {
            snackbar.dismiss()
            action()
        }
        snackbar.show()
    }

    fun showInfo(message: String, action:()->Unit) {
        val snackbar = Snackbar.make(binding?.root!!,message,Snackbar.LENGTH_INDEFINITE)
        snackbar.view.setBackgroundColor(getColor(requireContext(),R.color.color_snack_info))
        snackbar.setActionTextColor(Color.WHITE)
        snackbar.setAction(R.string.Ok) {
            snackbar.dismiss()
            action()
        }
        snackbar.show()
    }

    fun show(message: String) {
        val snackbar = Snackbar.make(binding?.root!!,message,Snackbar.LENGTH_LONG)
        snackbar.setAction(R.string.Ok) {
            snackbar.dismiss()
        }
        snackbar.show()
    }
}
