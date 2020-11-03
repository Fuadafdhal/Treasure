package com.afdhal_fa.treasure.view.choose_nominal

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Nominal
import kotlinx.android.synthetic.main.activity_chosee_nominal.*
import java.util.*

class ChoseeNominalActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chosee_nominal)


        val nominalAdapter = NominalAdapter()

        val listData = ArrayList<Nominal>()
        listData.add(Nominal(1000, 1500))
        listData.add(Nominal(5000, 6000))
        listData.add(Nominal(10000, 11000))

        nominalAdapter.setData(listData)

        with(rv_nominal) {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = nominalAdapter
        }
    }
}