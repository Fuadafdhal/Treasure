package com.afdhal_fa.treasure.view.exchange

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afdhal_fa.treasure.R
import kotlinx.android.synthetic.main.item_exchange.view.*
import java.util.*

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.VHolder>() {
    private var listData = ArrayList<String>()
    var onItemClick: ((String) -> Unit)? = null

    fun setData(newListData: List<String>?) {
        if (newListData != null) {
            listData.clear()
            listData.addAll(newListData)
            notifyDataSetChanged()
        }

    }

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun onBind(data: String) {
            with(itemView) {
                textTitleExchangeType.text = data
            }
        }

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(listData[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_exchange, parent, false)
    )

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        val data = listData[position]
        holder.onBind(data)
    }

    override fun getItemCount() = listData.size
}