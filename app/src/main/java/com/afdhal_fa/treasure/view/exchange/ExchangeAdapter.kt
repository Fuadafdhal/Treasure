package com.afdhal_fa.treasure.view.exchange

import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.ExchangeMetode
import kotlinx.android.synthetic.main.item_exchange.view.*
import java.util.*

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.VHolder>() {
    private var listData = ArrayList<ExchangeMetode>()

    var onItemClick: ((ExchangeMetode) -> Unit)? = null

    fun setData(newListData: List<ExchangeMetode>?) {
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

        fun onBind(mExchangeMetode: ExchangeMetode) {
            with(itemView) {
                textTitleExchangeType.text = mExchangeMetode.title
                imageExchangeType.load(mExchangeMetode.icon) {
                    crossfade(true)
                    crossfade(500)
                }

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
            }
        }

        init {
            with(itemView) {
                textFieldNominal.setEndIconOnClickListener {
                    onItemClick?.invoke(listData[adapterPosition])
                }
            }
        }
    }
}