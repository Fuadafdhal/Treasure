package com.afdhal_fa.treasure.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Tips
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.databinding.ItemTipsBinding
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class TipsAdapter : BaseRecyclerViewAdapter<TipsAdapter.VHolder, Tips>() {
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTipsBinding.bind(itemView)
        fun onBind(mTips: Tips) {
            with(binding) {
                imageItem.load(mTips.image) {
                    crossfade(true)
                    crossfade(500)
                }
                textItemTitle.text = mTips.title
                binding.root.context.makeToast(itemCount.toString())
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }

    override fun onBindViewHolder(holder: VHolder, item: Tips, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_tips, parent, false))
}