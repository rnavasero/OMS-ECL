package com.example.adefault.myapplication.Fragment


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.adefault.myapplication.Activity.MainActivity
import com.example.adefault.myapplication.Activity.ScanActivity
import com.example.adefault.myapplication.Http.API

import com.example.adefault.myapplication.R
import com.google.zxing.client.android.BeepManager
import kotlinx.android.synthetic.main.content_main.*


/**
 * A simple [Fragment] subclass.
 */
class SortFragment : Fragment() {
    private var mActivity: MainActivity? = null
    private val bm:BeepManager? = null

    companion object {
        var instance:SortFragment? = SortFragment()
        val TAG = SortFragment::class.java.simpleName
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        instance = this
        return inflater?.inflate(R.layout.fragment_sort, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mActivity = context as MainActivity?
        mActivity!!.setToolbar("Sorting")

        mActivity!!.getIncomingItems(API.RECEIVEDITEMS)

        btn_Scan.setOnClickListener {
            val intent = Intent(context, ScanActivity::class.java)
            startActivityForResult(intent,2)
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        instance = null
    }
}
