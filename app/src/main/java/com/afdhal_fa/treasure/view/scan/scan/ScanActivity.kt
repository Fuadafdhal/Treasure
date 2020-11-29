package com.afdhal_fa.treasure.view.scan.scan

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.ActivityScanBinding
import com.afdhal_fa.treasure.view.scan.CustomViewScanFinderView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.zxing.Result
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView


@Suppress("DEPRECATION")
class ScanActivity : AppCompatActivity(), ZXingScannerView.ResultHandler {
    private lateinit var binding: ActivityScanBinding

    private lateinit var mScannerView: ZXingScannerView

    private var mFlash = false

    override fun onStart() {
        mScannerView.flash = mFlash
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        changeBackgroundStatusBar()
        initMiscellneous()
        initScannerView()

        binding.imageNavigationBack.setOnClickListener {
            onBackPressed()
        }

        binding.imageNavigationGalery.setOnClickListener {
            makeToast("Coming Soon!!!")
        }

        binding.imageNavigationFlash.setOnClickListener {
            mScannerView.flash = mFlash
        }

    }

    private fun initScannerView() {
        mScannerView = object : ZXingScannerView(this) {
            override fun createViewFinderView(context: Context?): IViewFinder {
                return CustomViewScanFinderView(context!!)
            }
        }
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)

        binding.frameCameraScan.addView(mScannerView)
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            100 -> {
                initScannerView()
            }
            else -> {
                /* nothing to do in here */
            }
        }
    }

    override fun onPause() {
        mScannerView.stopCamera()
        super.onPause()
    }

    private fun initMiscellneous() {
        val layoutMiscelleneous = binding.includeMisccellaneous.layoutMiscellneous
        val bottomSheetBehavior = BottomSheetBehavior.from(layoutMiscelleneous)
        binding.includeMisccellaneous.groupLine.setOnClickListener {
            if (bottomSheetBehavior.state != BottomSheetBehavior.STATE_EXPANDED) {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED)
            } else {
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED)
            }
        }
    }


    private fun changeBackgroundStatusBar() {
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, android.R.color.background_dark)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    override fun handleResult(p0: Result?) {
        TODO("What do you do for handel result ")
    }
}