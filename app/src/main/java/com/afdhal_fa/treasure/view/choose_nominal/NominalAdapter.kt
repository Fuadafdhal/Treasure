package com.afdhal_fa.treasure.view.choose_nominal

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Nominal
import kotlinx.android.synthetic.main.item_nominal.view.*
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class NominalAdapter : BaseRecyclerViewAdapter<NominalAdapter.VHolder, Nominal>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_nominal, parent, false)
    )

    override fun onBindViewHolder(holder: VHolder, item: Nominal, position: Int) {
        holder.onBind(item)
    }

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(nominal: Nominal) {
            with(itemView) {
                textNominal.text = String.format("%s", nominal.nominal)
                textTotalNominal.text = String.format("Rp. %s", nominal.totalNominal)
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }
}