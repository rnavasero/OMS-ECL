package com.example.adefault.myapplication.Model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import org.json.JSONException
import org.json.JSONObject

/**
 * Created by Default on 13/04/2018.
 */

@Entity(tableName = "packageData")
class ItemPackage {



    @PrimaryKey(autoGenerate = true)
    var dbId: Int = 0

    @ColumnInfo(name = "productId")
    var id = 0


    var courier_id = 0

    @ColumnInfo(name = "productName")
    var item_name = ""


    var track_num = ""
    var date_received = ""
    var status = ""

    constructor(jsonObject: JSONObject){
        id = try {
            jsonObject.getInt("id")
        }catch (e: JSONException){
            0
        }

        courier_id = try {
            jsonObject.getInt("courier_id")
        }catch (e: JSONException){
            0
        }

        item_name = try {
            jsonObject.getString("item_name")
        }catch (e: JSONException){
            ""
        }

        track_num = try {
            jsonObject.getString("tracking_num")
        }catch (e: JSONException){
            ""
        }

        date_received = try {
            jsonObject.getString("date_received")
        }catch (e: JSONException){
            ""
        }

        status = try {
            jsonObject.getString("status")
        }catch (e: JSONException){
            ""
        }
    }

    constructor()
    constructor(dbId: Int, id: Int, courier_id: Int, item_name: String, track_num: String, date_received: String, status: String) {
        this.dbId = dbId
        this.id = id
        this.courier_id = courier_id
        this.item_name = item_name
        this.track_num = track_num
        this.date_received = date_received
        this.status = status
    }


}