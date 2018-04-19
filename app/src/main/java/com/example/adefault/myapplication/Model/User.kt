package com.example.adefault.myapplication.Model

import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Default on 13/04/2018.
 */
class User {
    var id          = 0
    var firstName   = ""
    var lastName    = ""
    var username    = ""
    var token       = ""

    constructor()

    constructor(jsonObject: JSONObject){

        id = try {
            jsonObject.getInt("id")
        }catch (e: JSONException){
            0
        }

        firstName = try {
            jsonObject.getString("firstName")
        }catch (e: JSONException){
            ""
        }


        lastName = try {
            jsonObject.getString("lastName")
        }catch (e: JSONException){
            ""
        }

        username = try {
            jsonObject.getString("username")
        }catch (e: JSONException){
            ""
        }


        token = try {
            jsonObject.getString("token")
        }catch (e: JSONException){
            ""
        }
    }

    override fun toString(): String {
        val jsonObject = JSONObject()
        try {
            jsonObject.put("id", id)
            jsonObject.put("firstName", firstName)
            jsonObject.put("lastName", lastName)
            jsonObject.put("username", username)
            jsonObject.put("token", token)
        }catch (e: JSONException){
            e.printStackTrace()
        }

        return jsonObject.toString()
    }

}