package com.afdhal_fa.treasure.view.choose_nominal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Nominal
import kotlinx.android.synthetic.main.item_nominal.view.*
import java.util.*

class NominalAdapter : RecyclerView.Adapter<NominalAdapter.VHolder>() {
    private var listData = ArrayList<Nominal>()

    fun setData(newListData: List<Nominal>?) {
        if (newListData != null) {
            listData.clear()
            listData.addAll(newListData)
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_nominal, parent, false)
    )

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(listData[position])
    }

    override fun getItemCount() = listData.size

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(nominal: Nominal) {
            with(itemView) {
                textNominal.text = String.format("%s", nominal.nominal)
                textTotalNominal.text = String.format("Rp. %s", nominal.totalNominal)
            }
        }
    }
}