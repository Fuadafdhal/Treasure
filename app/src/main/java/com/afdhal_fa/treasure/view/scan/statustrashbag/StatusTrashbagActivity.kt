package com.afdhal_fa.treasure.view.scan.statustrashbag

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.ActivityStatusTrashbagBinding
import com.afdhal_fa.treasure.view.scan.scanstatus.ScanStatusActivity

class StatusTrashbagActivity : AppCompatActivity() {
    private lateinit var binding: ActivityStatusTrashbagBinding

    companion object {
        const val EXTRA_INTENT_DATA = "extra_data"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusTrashbagBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent?.getStringExtra(ScanStatusActivity.EXTRA_INTENT_DATA)?.let {
            makeToast(it)
            binding.textContent.text = String.format("Trashbag dengan ID %s\nmasih tersambung", it)
        }
    }
}