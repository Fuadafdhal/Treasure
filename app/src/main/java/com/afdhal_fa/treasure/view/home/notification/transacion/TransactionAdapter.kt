package com.afdhal_fa.treasure.view.home.notification.transacion

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.setPadding
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.databinding.ItemTransactionBinding
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class TransactionAdapter : BaseRecyclerViewAdapter<TransactionAdapter.VHolder, Exchange>() {
    private var mTreasureUser: TreasureUser = TreasureUser()

    fun setTreasureUser(treasureUser: TreasureUser) {
        mTreasureUser = treasureUser
    }

    override fun onBindViewHolder(holder: VHolder, item: Exchange, position: Int) {
        holder.onBind(item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        VHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_transaction, parent, false)
        )


    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemTransactionBinding.bind(itemView)
        fun onBind(mExchange: Exchange) {
            with(binding) {
                if (mExchange.read) {
                    imageStatus.visibility = View.GONE
                    textDate.visibility = View.VISIBLE
                }
                imageItemTransaction.load(
                    when (mExchange.type) {
                        "Pulsa" -> {
                            R.drawable.ic_ponsel
                        }

                        "Link Aja" -> {
                            R.drawable.logo_linkaja
                        }

                        "Dana" -> {
                            R.drawable.logo_dana
                        }
                        else -> {
                            R.drawable.ic_ponsel
                        }
                    }
                ) {
                    when (mExchange.type) {
                        "Link Aja", "Dana" -> {
                            binding.imageItemTransaction.background = null
                            binding.imageItemTransaction.setPadding(0)
                        }
                        else -> {
                            binding.imageItemTransaction.setColorFilter(
                                Color.argb(
                                    255,
                                    255,
                                    255,
                                    255
                                )
                            )
                        }
                    }
                }
                textTitleTransaction.text = String.format("Penukaran %s", mExchange.type)
                textSubTitleTransaction.text = when (mExchange.type) {
                    "Pulsa" -> {
                        String.format(
                            "Pulsa sebesar %s dengan nomor kartu %s %s berhasil ditukankan." +
                                    " Sisa saldo anda sebesar %s",
                            mExchange.nominal,
                            mExchange.serviceType,
                            mExchange.phoneNumber,
                            mTreasureUser.saldo.toInt().toRupiah()
                        )
                    }

                    "Link Aja", "Dana" -> {
                        String.format(
                            "Saldo %s sebesar %S berhasil ditukarkan." +
                                    " Sisa saldo anda sebesar %s",
                            mExchange.type,
                            mExchange.nominal,
                            mExchange.phoneNumber,
                            mTreasureUser.saldo.toInt().toRupiah()

                        )
                    }
                    else -> {
                        String.format(
                            "Pulsa sebesar %s dengan nomor kartu %s %s berhasil ditukankan." +
                                    " Sisa saldo anda sebesar %s",
                            mExchange.nominal,
                            mExchange.serviceType,
                            mExchange.phoneNumber,
                            mTreasureUser.saldo.toInt().toRupiah()
                        )
                    }
                }
            }
        }

        init {
            binding.root.setOnClickListener {
                onItemClick?.invoke(items[adapterPosition])
            }
        }
    }

}
