package com.example.snack.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import com.example.snack.R
import com.example.snack.dialog.NoticeDialog

class LoadingDialog
constructor(context: Context) : Dialog(context){
    lateinit var binding: NoticeDialog
    init {
        setCanceledOnTouchOutside(false)

        window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

        setContentView(R.layout.dialog_loading)
    }
}