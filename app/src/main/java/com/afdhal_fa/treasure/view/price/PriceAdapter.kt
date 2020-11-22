package com.afdhal_fa.treasure.view.price

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Price
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.databinding.ItemPriceBinding
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class PriceAdapter : BaseRecyclerViewAdapter<PriceAdapter.VHolder, Price>() {
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemPriceBinding.bind(itemView)

        fun bind(mPrice: Price) {
            with(binding) {
                imageItemPrice.load(mPrice.image) {
                    crossfade(true)
                    crossfade(1000)
                    placeholder(R.drawable.image_placeholder)
                    error(R.drawable.image_broken)
                }

                textItemTitle.text = mPrice.title
                textItemPrice.text = mPrice.price.toInt().toRupiah()

                binding.root.setOnClickListener {
                    binding.root.context.makeToast(mPrice.title)
                }
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_price, parent, false))

    override fun onBindViewHolder(holder: VHolder, item: Price, position: Int) {
        holder.bind(item)
    }
}
