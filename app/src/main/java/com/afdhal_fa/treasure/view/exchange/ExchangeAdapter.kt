package com.afdhal_fa.treasure.view.exchange

import android.content.Intent
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.view.choose_nominal.ChoseeNominalActivity
import kotlinx.android.synthetic.main.item_exchange.view.*
import java.util.*

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.VHolder>() {
    private var listData = ArrayList<String>()

    fun setData(newListData: List<String>?) {
        if (newListData != null) {
            listData.clear()
            listData.addAll(newListData)
            notifyDataSetChanged()
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

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(data: String) {
            with(itemView) {
                textTitleExchangeType.text = data
                buttonExpandItem.setOnClickListener {
                    if (layoutFormExchange.visibility == View.GONE) {
                        TransitionManager.beginDelayedTransition(container, AutoTransition())
                        layoutFormExchange.visibility = View.VISIBLE
                        buttonExpandItem.setImageResource(R.drawable.ic_left_arrow_up)
                    } else {
                        layoutFormExchange.visibility = View.GONE
                        buttonExpandItem.setImageResource(R.drawable.ic_left_arrow_down)
                    }
                }

                textFieldNominal.setEndIconOnClickListener {
                    itemView.context.startActivity(
                        Intent(
                            itemView.context,
                            ChoseeNominalActivity::class.java
                        )
                    )
                }
            }
        }
    }
}