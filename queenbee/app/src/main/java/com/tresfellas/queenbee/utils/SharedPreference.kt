package com.tresfellas.queenbee.utils

import android.content.Context
import android.content.SharedPreferences
import com.tresfellas.queenbee.base.BaseSharedPreference
import org.json.JSONArray
import org.json.JSONException

class SharedPreference(val context: Context) :
    BaseSharedPreference {
    private val prefsname = "hitit_prefs"
    var sharedPref: SharedPreferences? = context.getSharedPreferences(prefsname, Context.MODE_PRIVATE)
    var editor: SharedPreferences.Editor? = sharedPref!!.edit()
    override fun saveValueString(key: String, value: String) {
        editor!!.putString(key, value)
        editor!!.commit()
    }

    override fun getValueString(key: String): String? {
        return sharedPref!!.getString(key, null)
    }

    override fun setBoolean(key: String, value: Boolean) {
        editor!!.putBoolean(key, value)
        editor!!.commit()
    }

    override fun getBoolean(key: String): Boolean {
        return sharedPref!!.getBoolean(key,true)
    }

    override fun removeValueString(key: String) {
        editor!!.remove(key).commit()
    }

    override fun clearAll() {
        editor!!.clear().apply()
    }

    override fun setStringArrayPref(key: String, values: ArrayList<String>) {
        val a = JSONArray()
        for (i in 0 until values.size) {
            a.put(values[i])
        }
        if (values.isNotEmpty()) {
            saveValueString(key, a.toString())
        } else {
            saveValueString(key, "")
        }
    }

    override fun setIntArrayPref(key: String, values: ArrayList<Int>) {
        val a = JSONArray()
        for (i in 0 until values.size) {
            a.put(values[i])
        }
        if (values.isNotEmpty()) {
            saveValueString(key, a.toString())
        } else {
            saveValueString(key, "")
        }
    }

    override fun setBooleanArrayPref(key: String, values: ArrayList<Boolean>) {
        val a = JSONArray()
        for (i in 0 until values.size) {
            a.put(values[i])
        }
        if (values.isNotEmpty()) {
            saveValueString(key, a.toString())
        } else {
            saveValueString(key, "")
        }
    }

    override fun getStringArrayPref(key: String): ArrayList<String> {
        val json = sharedPref!!.getString(key, null)
        val urls = ArrayList<String>()
        if (json != null) {
            try {
                val a = JSONArray(json)
                for (i in 0 until a.length()) {
                    val url = a.optString(i)
                    urls.add(url)
                }
            } catch (e: JSONException) {
                e.printStackTrace()
            }
        }
        return urls
    }
}