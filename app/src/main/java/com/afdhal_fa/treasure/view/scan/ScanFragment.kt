package com.afdhal_fa.treasure.view.scan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.utils.BaseToolbarFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.FragmentScanBinding
import com.afdhal_fa.treasure.view.scan.scan.ScanActivity
import com.afdhal_fa.treasure.view.scan.scanstatus.ScanStatusActivity
import com.afdhal_fa.treasure.view.scan.scanstatus.ScanStatusActivity.Companion.EXTRA_INTENT_DATA
import com.afdhal_fa.treasure.view.scan.statustrashbag.StatusTrashbagActivity
import com.google.zxing.Result
import me.dm7.barcodescanner.core.IViewFinder
import me.dm7.barcodescanner.zxing.ZXingScannerView

class ScanFragment : BaseToolbarFragment<ScanViewModel>(), ZXingScannerView.ResultHandler {
    private var _binding: FragmentScanBinding? = null
    private val binding get() = _binding!!

    private lateinit var mScannerView: ZXingScannerView

    override fun onStart() {
        mScannerView.startCamera()
        doRequestPermission()
        super.onStart()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentScanBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initScannerView()

        if (activity != null) {
            binding.layoutCameraScan.setOnClickListener {
                startActivity(Intent(activity, ScanActivity::class.java))
            }
        }

        binding.buttonInput.setOnClickListener {
            val idTreash = binding.textFieldIDCode.editText?.text.toString().trim()
            if (idTreash.contains("TRASH")) {
                startActivity(
                    Intent(this.context, StatusTrashbagActivity::class.java).putExtra(
                        StatusTrashbagActivity.EXTRA_INTENT_DATA,
                        idTreash
                    )
                )
            } else {
                activity?.makeToast("Code Tidak Benar")
            }
        }

    }


    private fun initScannerView() {
        mScannerView = object : ZXingScannerView(context) {
            override fun createViewFinderView(context: Context?): IViewFinder {
                return CustomViewScanFinderView(context!!)
            }
        }
        mScannerView.setAutoFocus(true)
        mScannerView.setResultHandler(this)
        binding.frameCameraScan.addView(mScannerView)
    }

    override fun handleResult(p0: Result?) {
        if (p0 != null) {
            if (p0.text.contains("TRASH")) {
                startActivity(
                    Intent(this.context, ScanStatusActivity::class.java).putExtra(
                        EXTRA_INTENT_DATA,
                        p0.text
                    )
                )
            } else {
                activity?.makeToast("QR Code Tidak Benar")
            }
        }
    }

    private fun doRequestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (activity?.checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
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

    override fun setToolbar(): Toolbar {
        binding.includeAppbar.textTitleToolbar.text = getString(R.string.title_scan)
        return binding.includeAppbar.toolbar
    }

    override fun initViewModel(): ScanViewModel {
        return ViewModelProvider(this)[ScanViewModel::class.java]
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}