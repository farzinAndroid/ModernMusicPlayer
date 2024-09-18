package com.farzin.core_ui.utils

import android.content.Context
import android.widget.Toast


fun Context.showToast(text:String){
    Toast.makeText(this,text,Toast.LENGTH_LONG).show()
}