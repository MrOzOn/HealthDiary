package com.mrozon.utils.base

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.mrozon.utils.R
import kotlinx.android.synthetic.main.fragment_my_info_dialog.*

class MyInfoDialog: DialogFragment() {

    companion object {

        private var action: () -> Unit = {}
        const val TAG = "MyInfoDialog"

        private const val KEY_TYPE = "KEY_TYPE"
        private const val KEY_MESSAGE = "KEY_MESSAGE"

        fun newInstance(type: MyInfoDialogType, message: String, action:()->Unit): MyInfoDialog {
            val args = Bundle()
            args.putInt(KEY_TYPE, type.ordinal)
            args.putString(KEY_MESSAGE, message)
            this.action = action
            val fragment = MyInfoDialog()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(type: MyInfoDialogType, message: String): MyInfoDialog {
            val args = Bundle()
            args.putInt(KEY_TYPE, type.ordinal)
            args.putString(KEY_MESSAGE, message)
            val fragment = MyInfoDialog()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_my_info_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        setupClickListeners()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog?.setCanceledOnTouchOutside(false)
        val decorView = dialog?.window?.decorView
        val scaleDown: ObjectAnimator = ObjectAnimator.ofPropertyValuesHolder(
            decorView,
            PropertyValuesHolder.ofFloat("scaleX", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("scaleY", 0.0f, 1.0f),
            PropertyValuesHolder.ofFloat("alpha", 0.0f, 1.0f)
        )
        scaleDown.duration = 400
        scaleDown.start()
    }

    private fun setupClickListeners() {
        buttonCloseDialog.setOnClickListener {
            dismiss()
            action()
        }
    }

    private fun setupView() {
        val type = MyInfoDialogType.values()[arguments?.getInt(KEY_TYPE,0)?:0]
        if(type==MyInfoDialogType.INFO){
            ivTypeIcon.setImageResource(R.drawable.ic_success)
        }
        else
        {
            ivTypeIcon.setImageResource(R.drawable.ic_error)
        }
        tvMessage.text = arguments?.getString(KEY_MESSAGE,"")
    }

}

enum class MyInfoDialogType {
    INFO,
    ERROR
}