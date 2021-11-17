package com.tresfellas.queenbee.base

interface BaseSharedPreference {
    fun saveValueString(key: String, value: String)

    fun getValueString(key: String): String?

    fun setBoolean(key:String,value:Boolean)

    fun getBoolean(key:String):Boolean

    fun removeValueString(key: String)

    fun clearAll()

    fun setStringArrayPref(key: String, values: ArrayList<String>)

    fun setIntArrayPref(key: String, values: ArrayList<Int>)

    fun setBooleanArrayPref(key: String, values: ArrayList<Boolean>)

    fun getStringArrayPref(key: String): ArrayList<String>
}