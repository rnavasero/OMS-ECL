package com.example.adefault.myapplication.Activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentManager
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.adefault.myapplication.Adapter.ItemRecyclerAdapter
import com.example.adefault.myapplication.Fragment.*
import com.example.adefault.myapplication.Http.API
import com.example.adefault.myapplication.Http.APIRequest
import com.example.adefault.myapplication.Model.ItemPackage
import com.example.adefault.myapplication.R
import com.example.adefault.myapplication.Session.Session
import com.google.zxing.client.android.BeepManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.json.JSONException
import org.json.JSONObject
import java.nio.charset.Charset
import java.util.HashMap

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var bm: BeepManager? = null
    private var adapter: ItemRecyclerAdapter? = ItemRecyclerAdapter(this, null)
    //private var fm:FragmentManager? = null
    var packageList:MutableList<ItemPackage> = mutableListOf()
    val TAG = "REQUEST"
    var session: Session?                    = null
    private var fm: FragmentManager?         = null

    companion object {

        var barcode:String ?= null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        setToolbar("OMS-ECL-Test")

        fm = supportFragmentManager
        bm = BeepManager(this@MainActivity)

        getIncomingItems(API.ALLITEMS)

        btn_Scan.setOnClickListener {
            tvResult.text = ""
            val intent = Intent(this@MainActivity,ScanActivity::class.java)
            startActivityForResult(intent,2)
        }

//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show()
//        }

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_home -> {

            }
            R.id.nav_receive -> {
                fm!!.beginTransaction().apply {
                   replace(R.id.main_frame, ReceiveFragment(), ReceiveFragment.TAG)
                    //addToBackStack(ReceiveFragment.TAG)
                }.commit()
            }
            R.id.nav_sort -> {
                fm!!.beginTransaction().apply {
                    replace(R.id.main_frame, SortFragment(), SortFragment.TAG)
                    //addToBackStack(SortFragment.TAG)
                }.commit()

            }
            R.id.nav_outbound -> {
                fm!!.beginTransaction().apply {
                    replace(R.id.main_frame, OutboundFragment(), OutboundFragment.TAG)
                    //addToBackStack(OutboundFragment.TAG)
                }.commit()

            }
            R.id.nav_loading -> {
                fm!!.beginTransaction().apply {
                    replace(R.id.main_frame, LoadingFragment(), LoadingFragment.TAG)
                    //addToBackStack(LoadingFragment.TAG)
                }.commit()

            }
            R.id.nav_loaded -> {
                fm!!.beginTransaction().apply {
                    replace(R.id.main_frame, LoadFragment(), LoadFragment.TAG)
                    //addToBackStack(LoadFragment.TAG)
                }.commit()

            }
            R.id.nav_logout -> {
                //logout()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    fun getIncomingItems(api:String){
        APIRequest.get(this, api, object : APIRequest.URLCallback{
            override fun didUrlResponse(response: String) {
                Log.i("MainActivity", "getAllProducts: $response")
                packageList = ArrayList()

                try {
                    val jsonArray = JSONObject(response).getJSONObject("data").getJSONArray("items")

                    for (i in 0 until jsonArray.length()){
                        val itempackage = ItemPackage(jsonArray.getJSONObject(i))
                        packageList.add(itempackage)
                    }
                    setRecyclerView()

                }catch (e: JSONException){
                    e.printStackTrace()
                }
            }

            override fun didUrlError(error: VolleyError) {
                showRequestError(error)
            }

        })
    }

    fun setToolbar(mTitle: String){
        title = mTitle
        //supportActionBar?.setDisplayHomeAsUpEnabled(!isMain)
//        when {isMain  -> {cartView?.visibility = View.VISIBLE}
//            else -> {cartView?.visibility = View.GONE}}
    }

    fun transactItems(barcode:String, api:String) {
//        val jsonObj     = JSONObject()
//        jsonObj.put("tracking_num", barcode)
//        Log.i(TAG,jsonObj.toString())


        APIRequest.post(this, api , barcode, object : APIRequest.URLCallback{
            override fun didUrlResponse(response: String) {
                Log.i("MainActivity", "receivedItems: $response")
                getIncomingItems(API.INCOMINGITEMS)
                Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()

            }
            override fun didUrlError(error: VolleyError) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun setRecyclerView(){
        rv_incoming.layoutManager  = LinearLayoutManager(this@MainActivity)
        adapter     = ItemRecyclerAdapter(this@MainActivity, packageList)
        rv_incoming.adapter        = adapter
    }

    fun showRequestError(error: VolleyError) {
        if (error.networkResponse != null) {
            val err = String(error.networkResponse.data, Charset.forName("UTF-8"))
            val errString = JSONObject(err).getJSONArray("errors").getJSONObject(0).getString("message")
            AlertDialog.Builder(this@MainActivity).apply {
                setTitle("Error")
                setMessage(errString)
                setPositiveButton("Ok", null)
            }.show()
            Log.e("MainActivity", "error: $err")
        }
    }

    fun logout(){


        val params: MutableMap<String,String> = HashMap()
        params.put("x-access-token", session?.user()!!.token)

        val pDialog: ProgressDialog = ProgressDialog(this).apply {
            setMessage("Please wait...")
            setCancelable(false)
            setCanceledOnTouchOutside(false)
        }

        pDialog.show()

        val queue = Volley.newRequestQueue(this)
        val request = object : StringRequest(Request.Method.POST, API.LOGOUT, Response.Listener { response ->
            pDialog.dismiss()

            session!!.deAuthourize()
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()

        }, Response.ErrorListener { error ->
            pDialog.dismiss()

            val err = String(error.networkResponse.data, Charset.forName("UTF-8"))
            Toast.makeText(this, err, Toast.LENGTH_SHORT).show()

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
}
