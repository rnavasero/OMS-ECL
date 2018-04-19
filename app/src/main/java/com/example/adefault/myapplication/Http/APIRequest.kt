package com.example.adefault.myapplication.Http

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.nio.charset.Charset
import com.android.volley.AuthFailureError

@Suppress("DEPRECATION")
/**
 * Created by CDI on 2/27/1*
 */
object APIRequest {

    fun postWithToken(context: Context, url:String, token:MutableMap<String, String>, params:MutableMap<String, String>, urlCallback: URLCallback){

        val pDialog: ProgressDialog = ProgressDialog(context).apply {
            setMessage("Please wait...")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        pDialog.show()

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            pDialog.dismiss()
            urlCallback.didUrlResponse(response)
        }, Response.ErrorListener {error ->
            urlCallback.didUrlError(error)
            pDialog.dismiss()

            val err = String(error.networkResponse.data, Charset.forName("UTF-8"))
            Toast.makeText(context, err, Toast.LENGTH_SHORT).show()

        }){
            override fun getHeaders(): MutableMap<String, String> {
                return if (token.isEmpty()){
                    super.getHeaders()
                }else{
                    token
                }
            }

            override fun getParams(): MutableMap<String, String> {
                return params
            }
        }
        queue.add(request)
    }

    fun post(context: Context, url:String, barcode:String,urlCallback: URLCallback){
        val pDialog: ProgressDialog = ProgressDialog(context).apply {
            setMessage("Please wait...")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
        pDialog.show()

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            pDialog.dismiss()
            urlCallback.didUrlResponse(response)

        }, Response.ErrorListener {error ->
            urlCallback.didUrlError(error)
            pDialog.dismiss()

            if (error.networkResponse != null){
                val err = String(error.networkResponse.data, Charset.forName("UTF-8"))
                Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
            }
        }){

            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val pars = HashMap<String, String>()
                pars.put("Content-Type", "application/x-www-form-urlencoded")
                return pars
            }

            @Throws(AuthFailureError::class)
            override fun getParams(): Map<String, String> {
                val pars = HashMap<String, String>()
                pars.put("tracking_num", barcode)
                return pars
            }
        }
        queue.add(request)
    }

    fun postLog_in(context: Context, url:String, params:MutableMap<String, String>, urlCallback: URLCallback){
        val pDialog: ProgressDialog = ProgressDialog(context).apply {
            setMessage("Please wait...")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
        pDialog.show()

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Request.Method.POST, url, Response.Listener { response ->
            pDialog.dismiss()
            urlCallback.didUrlResponse(response)

        }, Response.ErrorListener {error ->
            urlCallback.didUrlError(error)
            pDialog.dismiss()

            if (error.networkResponse != null){
                val err = String(error.networkResponse.data, Charset.forName("UTF-8"))
                Toast.makeText(context, err, Toast.LENGTH_SHORT).show()
            }
        }){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val pars = HashMap<String, String>()
                pars.put("Content-Type", "application/x-www-form-urlencoded")
                return pars
            }

            override fun getParams(): MutableMap<String, String> {
                return params
            }
        }
        queue.add(request)
    }

    fun get(context: Context, url:String, urlCallback: URLCallback){
        val pDialog: ProgressDialog = ProgressDialog(context).apply {
            setMessage("Please wait...")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }
        pDialog.show()

        val queue = Volley.newRequestQueue(context)
        val request = object : StringRequest(Request.Method.GET, url, Response.Listener { response ->
            pDialog.dismiss()
            urlCallback.didUrlResponse(response)
        }, Response.ErrorListener {error ->
            pDialog.dismiss()
            urlCallback.didUrlError(error)
        }){
        }
        queue.add(request)
    }

    interface URLCallback{
        fun didUrlResponse(response: String)
        fun didUrlError(error: VolleyError)
    }
}