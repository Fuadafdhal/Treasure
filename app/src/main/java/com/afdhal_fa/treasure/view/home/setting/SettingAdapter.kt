package com.afdhal_fa.treasure.view.home.setting

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.databinding.ItemSettingBinding
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class SettingAdapter : BaseRecyclerViewAdapter<SettingAdapter.VHolder, Setting>() {
    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemSettingBinding.bind(itemView)

        fun onBind(mSetting: Setting) {
            with(binding) {
                imageItemSetting.load(mSetting.image)
                textTitleSetting.text = mSetting.title
                if (adapterPosition == items.lastIndex) {
                    line.background = null
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }

    }

    override fun onBindViewHolder(holder: VHolder, item: Setting, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_setting, parent, false))

}