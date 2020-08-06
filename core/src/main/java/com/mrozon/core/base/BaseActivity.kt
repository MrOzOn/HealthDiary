package com.mrozon.core.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject


abstract class BaseActivity<T : ViewDataBinding>: AppCompatActivity(), HasSupportFragmentInjector {

    lateinit var binding: T

    @LayoutRes
    protected abstract fun getLayoutId(): Int

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        performDI()
        super.onCreate(savedInstanceState)
        performDataBinding()
    }

    private fun performDataBinding() {
        binding = DataBindingUtil.setContentView(this, getLayoutId())
        binding.executePendingBindings()
    }

    private fun performDI() {
        AndroidInjection.inject(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }
}
