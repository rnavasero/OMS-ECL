package com.example.adefault.myapplication.Fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.android.volley.VolleyError
import com.example.adefault.myapplication.Activity.MainActivity
import com.example.adefault.myapplication.Activity.MainActivity.Companion.barcode
import com.example.adefault.myapplication.Activity.ScanActivity
import com.example.adefault.myapplication.Http.API
import com.example.adefault.myapplication.Http.APIRequest

import com.example.adefault.myapplication.R
import com.google.zxing.client.android.BeepManager
import kotlinx.android.synthetic.main.content_main.*

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [LoadFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 */
class LoadFragment : Fragment() {
    private var mActivity: MainActivity? = null
    private val bm: BeepManager? = null

    companion object {
        val TAG = LoadFragment::class.java.simpleName
        var instance:LoadFragment? = LoadFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater?.inflate(R.layout.fragment_load, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity?
        mActivity!!.setToolbar("Loaded")

        mActivity!!.getIncomingItems(API.LOADITEMS)

        btn_Scan.setOnClickListener {
            val intent = Intent(context, ScanActivity::class.java)
            startActivityForResult(intent,2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        barcode = data.extras.getString("BarCode")

        if (barcode.equals("")) {
            Toast.makeText(context,"Bar code not found", Toast.LENGTH_LONG).show()


        } else {
            bm!!.playBeepSoundAndVibrate()
            tvResult?.text = barcode
            mActivity!!.transactItems(barcode.toString(),API.LOADITEMS)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }

}
// Required empty public constructor
