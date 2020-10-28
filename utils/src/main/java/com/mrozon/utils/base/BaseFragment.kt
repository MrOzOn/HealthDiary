package com.mrozon.utils.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar
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
        MyInfoDialog.newInstance(MyInfoDialogType.ERROR,message, action)
            .show(requireActivity().supportFragmentManager, MyInfoDialog.TAG)
    }

    fun showError(message: String) {
        MyInfoDialog.newInstance(MyInfoDialogType.ERROR,message)
            .show(requireActivity().supportFragmentManager, MyInfoDialog.TAG)
    }

    fun showInfo(message: String, action:()->Unit) {
        MyInfoDialog.newInstance(MyInfoDialogType.INFO,message, action)
            .show(requireActivity().supportFragmentManager, MyInfoDialog.TAG)
    }

    fun show(message: String) {
        val snackbar = Snackbar.make(binding?.root!!,message,LENGTH_LONG)
        snackbar.show()
    }
}
