package com.afdhal_fa.treasure.view.scan.scanstatus

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.ActivityScanStatusBinding

class ScanStatusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityScanStatusBinding

    companion object {
        const val EXTRA_INTENT_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanStatusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.getStringExtra(EXTRA_INTENT_DATA)?.let {
            makeToast(it)
            binding.textContent.text = String.format("Trashbag ID %s", it)
        }

        binding.buttonClose.setOnClickListener {
            finish()
        }
    }
}