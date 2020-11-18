package com.afdhal_fa.treasure.view.exchange

import android.text.Editable
import android.text.TextWatcher
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.ExchangeMetode
import com.afdhal_fa.treasure.core.domain.model.Nominal
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.core.utils.toRupiahUnFormat
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.item_exchange.view.*
import java.util.*

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.VHolder>() {
    private var listData = ArrayList<ExchangeMetode>()

    private lateinit var mNominal: Nominal
    private lateinit var mExchange: Exchange

    init {
        mNominal = Nominal()
        mExchange = Exchange()
    }

    var onItemClick: ((ExchangeMetode) -> Unit)? = null
    var onExchangeClick: ((Exchange) -> Unit)? = null

    fun setData(newListData: List<ExchangeMetode>?) {
        if (newListData != null) {
            listData.clear()
            listData.addAll(newListData)
            notifyDataSetChanged()
        }
    }

    fun setNominal(nominal: Nominal, position: Int) {
        this.mNominal = nominal
        notifyItemChanged(position)
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
            var nominal: Int = 0
            with(itemView) {

                setNominalEditText(textFieldNominal, textPrice)

                layoutFormExchange.visibility = View.GONE
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
                        textFieldPhone.editText?.text = null
                        textFieldNominal.editText?.text = null
                        textPrice.text = null
                    }
                }

                mNominal.let {
                    if (it.nominal != 0) {
                        nominal = it.nominal

                        layoutFormExchange.visibility = View.VISIBLE
                        buttonExpandItem.setImageResource(R.drawable.ic_left_arrow_up)
                        textFieldNominal.editText?.setText(it.nominal.toRupiah())
                        textPrice.text = it.totalNominal.toRupiah()
                    }
                }


            }
        }

        init {
            with(itemView) {
                textFieldNominal.setEndIconOnClickListener {
                    onItemClick?.invoke(listData[adapterPosition])
                }

                buttonExchange.setOnClickListener {
                    onExchangeClick?.invoke(mExchange)
                }
            }
        }
    }

    private fun setNominalEditText(textFieldNominal: TextInputLayout, textPrice: TextView) {
        var current = ""
        textFieldNominal.editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString() != current) {
                    if (s.toString() != "") {
                        textFieldNominal.editText?.removeTextChangedListener(this)

                        val sNonFormat = s.toString().toRupiahUnFormat()
                        if (!sNonFormat.equals("")) {
                            val formatted = sNonFormat.toInt().toRupiah()
                            current = formatted
                            textFieldNominal.editText?.setText(formatted)
                            textFieldNominal.editText?.setSelection(formatted.length)

                            textFieldNominal.editText?.addTextChangedListener(this)
                        } else {
                            textFieldNominal.editText?.setText(sNonFormat)
                            textFieldNominal.editText?.addTextChangedListener(this)
                            textFieldNominal.isErrorEnabled = false
                            textFieldNominal.helperText = null
                        }
                    }
                }
            }

            override fun afterTextChanged(s: Editable?) {
                val sNonFormat = s.toString().toRupiahUnFormat()
                if (sNonFormat != "") {
                    if (sNonFormat.toInt() < 999) {
                        textFieldNominal.isErrorEnabled = true
                        textFieldNominal.helperText = "Minimal Rp1.000"
                        textPrice.text = ""
                    }

                    if (sNonFormat.toInt() % 1000 != 0) {
                        textFieldNominal.isErrorEnabled = true
                        textFieldNominal.helperText = "Nominal hanya kelipatan Rp1.000"
                        textPrice.text = ""
                    }

                    if (sNonFormat.toInt() > 999 && sNonFormat.toInt() % 1000 == 0) {
                        textFieldNominal.isErrorEnabled = false
                        textFieldNominal.helperText = null
                        textPrice.text = sNonFormat.toInt().plus(1000).toRupiah()
                    }
                }
            }
        })
    }
}