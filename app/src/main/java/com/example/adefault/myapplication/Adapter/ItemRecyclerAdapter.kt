package com.example.adefault.myapplication.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.adefault.myapplication.Activity.MainActivity
import com.example.adefault.myapplication.Model.ItemPackage
import com.example.adefault.myapplication.R
import com.example.adefault.myapplication.R.id.img_row
import com.example.adefault.myapplication.R.id.tvStatus
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_package.view.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Default on 17/04/2018.
 */
class ItemRecyclerAdapter(var mContext: Context, var pList:MutableList<ItemPackage>?): RecyclerView.Adapter<ItemRecyclerAdapter.ViewHolder>() {


    var mActivity: MainActivity? = null

    init {
        mActivity = mContext as MainActivity?
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.onbindPackageItem(position)
    }

    override fun getItemCount(): Int {
        return pList!!.size
        //return 10
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.row_package, parent, false)
        return ViewHolder(v)
    }

    open inner class ViewHolder(v: View): RecyclerView.ViewHolder(v) {
        var string =""


        fun onbindPackageItem(position: Int){
            val p = pList!![position]

            when {
                p.status == "RECEIVED" -> Picasso.with(mContext).load(R.drawable.ic_inbound).into(itemView.img_row)
                p.status == "SORTED" -> Picasso.with(mContext).load(R.drawable.ic_sort).into(itemView.img_row)
                p.status == "FOR_OUTBOUND" -> Picasso.with(mContext).load(R.drawable.ic_outbound).into(itemView.img_row)
                p.status == "FOR_LOADING" -> Picasso.with(mContext).load(R.drawable.ic_loading).into(itemView.img_row)
                p.status == "LOADED" -> Picasso.with(mContext).load(R.drawable.ic_loaded).into(itemView.img_row)
                else -> Picasso.with(mContext).load(R.drawable.ic_new).into(itemView.img_row)
            }

            itemView.tv_c_id.text = p.courier_id.toString()
            itemView.tv_packageName.text = p.item_name
            itemView.tvTnum.text = p.track_num
            var format = SimpleDateFormat(
                    "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.US)
            format.timeZone = TimeZone.getTimeZone("UTC")
            string = p.date_received
            itemView.tvDate.text = format.parse(string).toString()
            itemView.tvStatus.text = p.status
            itemView.setOnClickListener {

                Toast.makeText(mContext,"Press" + position, Toast.LENGTH_LONG).show()
            }
        }

    }
}