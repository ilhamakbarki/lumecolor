package com.rizqi.lumecolorsapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesUtils(mContext : Context) {
    private var shared: SharedPreferences = mContext.getSharedPreferences("",0)
    private var edit: SharedPreferences.Editor = shared.edit()

    fun setSharedPreferences(key: String, value: String){
        edit.putString(key, value)
        edit.commit()
    }

    fun setSharedPreferences(key: String, value: Int){
        edit.putInt(key, value)
        edit.commit()
    }

    fun getStringSharedPreferences(key: String): String? {
        return shared.getString(key, "")
    }

    fun getIntSharedPreferences(key: String): Int {
        return shared.getInt(key, -1)
    }
}