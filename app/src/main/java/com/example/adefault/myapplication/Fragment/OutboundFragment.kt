package com.example.adefault.myapplication.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.adefault.myapplication.Activity.MainActivity
import com.example.adefault.myapplication.Activity.ScanActivity
import com.example.adefault.myapplication.Http.API

import com.example.adefault.myapplication.R
import com.google.zxing.client.android.BeepManager
import kotlinx.android.synthetic.main.content_main.*


/**
 * A simple [Fragment] subclass.
 */
class OutboundFragment : Fragment() {
    private var mActivity: MainActivity? = null
    private val bm:BeepManager? = null

    companion object {
        var instance:OutboundFragment? = OutboundFragment()
        val TAG = OutboundFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater?.inflate(R.layout.fragment_outbound, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity?
        mActivity!!.setToolbar("Outbound")

        mActivity!!.getIncomingItems(API.SORTEDITEMS)

        btn_Scan.setOnClickListener {
            val intent = Intent(context, ScanActivity::class.java)
            startActivityForResult(intent,2)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        MainActivity.barcode = data.extras.getString("BarCode")

        if (MainActivity.barcode.equals("")) {
            Toast.makeText(context,"Bar code not found", Toast.LENGTH_LONG).show()


        } else {
            //bm!!.playBeepSoundAndVibrate()
            tvResult?.text = MainActivity.barcode
            mActivity!!.transactItems(MainActivity.barcode.toString(),API.OUTBOUND)

        }
    }
    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
