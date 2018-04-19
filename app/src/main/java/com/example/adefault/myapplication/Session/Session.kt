package com.example.adefault.myapplication.Session

import android.content.Context
import android.content.SharedPreferences
import com.example.adefault.myapplication.Model.User
import org.json.JSONObject

/**
 * Created by Default on 19/04/2018.
 */
class Session(context: Context) {
    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        sharedPreferences = context.getSharedPreferences(USER_PREFERENCE, Context.MODE_PRIVATE)
        editor            = sharedPreferences.edit()
    }

    fun authorize(raw: String){
        editor.putString(USER_OBJECT, raw).apply()
        setUserLogIn(true)
    }

    fun user(): User {
        val jsonObject = JSONObject(sharedPreferences.getString(USER_OBJECT, "")).getJSONObject("data").getJSONArray("items").getJSONObject(0)
        return User(jsonObject)
    }

    private fun setUserLogIn(isLogin: Boolean){
        editor.putBoolean(USER_LOGIN, isLogin).apply()
    }

    fun isUserLogin(): Boolean{
        return sharedPreferences.getBoolean(USER_LOGIN, false)
    }

    fun deAuthourize(){
        editor.clear().apply()
    }

    companion object {
        const val USER_PREFERENCE = "com.mycart.advance"
        const val USER_OBJECT     = "userObject"
        const val USER_LOGIN      = "userLogin"
    }
}