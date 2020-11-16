package com.afdhal_fa.treasure.view.price

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Price
import com.afdhal_fa.treasure.core.utils.makeToast
import kotlinx.android.synthetic.main.item_price.view.*

class PriceAdapter(private val list: List<Price>) : RecyclerView.Adapter<PriceAdapter.VHolder>() {
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(mPrice: Price) {
            with(itemView) {
                imageItemPrice.load(mPrice.image) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(R.drawable.image_placeholder)
                    error(R.drawable.image_broken)
                }

                textItemTitle.text = mPrice.title
                //TODO : Change to price convert
                textItemPrice.text = mPrice.price.toString()

                setOnClickListener {
                    context.makeToast(mPrice.title)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_price, parent, false))


    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size
}
