package com.afdhal_fa.treasure.view.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Deposit
import com.afdhal_fa.treasure.core.utils.covertToDate
import com.afdhal_fa.treasure.databinding.ItemDepositBinding
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class DepositAdapter : BaseRecyclerViewAdapter<DepositAdapter.VHolder, Deposit>() {
    class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemDepositBinding.bind(itemView)
        fun onBind(mDeposit: Deposit) {
            binding.textIdDeposit.text = mDeposit.TrashbagID
            binding.textDate.text = mDeposit.timestamp.covertToDate()
            if (mDeposit.code.equals("1")) {
                binding.textItemStatus.text = binding.root.context.getString(R.string.text_prosess)
            } else if (mDeposit.code.equals("2")) {
                binding.textItemStatus.text = binding.root.context.getString(R.string.text_success)
                binding.textItemStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        R.color.colorPrimary
                    )
                )

            } else {
                binding.textItemStatus.text = binding.root.context.getString(R.string.text_failed)
                binding.textItemStatus.setTextColor(
                    ContextCompat.getColor(
                        binding.root.context,
                        android.R.color.holo_red_light
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: VHolder, item: Deposit, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_deposit, parent, false)
    )

}
