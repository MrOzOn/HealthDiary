package com.mrozon.utils.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding

abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity(){

    lateinit var binding: T

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    abstract fun subscribeUi()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDataBinding()
        subscribeUi()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
//        binding.executePendingBindings()
    }

}
