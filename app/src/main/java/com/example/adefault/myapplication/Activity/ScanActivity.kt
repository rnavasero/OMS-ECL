package com.example.adefault.myapplication.Activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.google.zxing.Result
import com.google.zxing.client.android.BeepManager
import me.dm7.barcodescanner.zxing.ZXingScannerView

/**
 * Created by Default on 17/04/2018.
 */
open class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private var mScannerView: ZXingScannerView? = null

    public override fun onCreate(state: Bundle?) {
        super.onCreate(state)

        mScannerView = ZXingScannerView(this)   // Programmatically initialize the scanner view
        setContentView(mScannerView)                // Set the scanner view as the content view
    }

    public override fun onResume() {
        super.onResume()
        mScannerView!!.setResultHandler(this) // Register ourselves as a handler for scan results.
        mScannerView!!.startCamera()          // Start camera on resume
    }

    public override fun onPause() {
        // TODO Auto-generated method stub
        super.onPause()

        try {
            mScannerView!!.stopCamera() // Stop camera on pause
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }

        val resultintent = Intent()
        resultintent.putExtra("BarCode", "")
        setResult(2, resultintent)
        finish()
    }

    override fun onBackPressed() {

        try {
            mScannerView!!.stopCamera() // Stop camera on pause
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }

        val resultintent = Intent()
        resultintent.putExtra("BarCode", "")
        setResult(2, resultintent)
        finish()

    }

//    override fun handleResult(rawResult: Result) {
//
//        if(rawResult.text != null){
//            //val bm:BeepManager
//        }
//        // Do something with the result here
//        // Log.v("tag", rawResult.getText()); // Prints scan results
//        // Log.v("tag", rawResult.getBarcodeFormat().toString()); // Prints the scan format (qrcode, pdf417 etc.)
//
//        MainActivity.result = rawResult.text
//        //IncomingFragment.instance
//        onBackPressed()
//        // If you would like to resume scanning, call this method below:
//        //mScannerView!!.resumeCameraPreview(this);
//
//    }

    override fun handleResult(rawResult: Result) {
        // Do something with the result here
        var bm = BeepManager(this)

        Log.e("handler", rawResult.text) // Prints scan results
        Log.e("handler", rawResult.barcodeFormat.toString()) // Prints the scan format (qrcode)

        try {
            bm.playBeepSoundAndVibrate()
            mScannerView!!.stopCamera()
            val resultintent = Intent()
            resultintent.putExtra("BarCode", rawResult.text)
            setResult(2, resultintent)
            bm.playBeepSoundAndVibrate()
            finish()
            println("************** Stop Camera**********")
            // Stop camera on pause
        } catch (e: Exception) {
            Log.e("Error", e.message)
        }

    }

}