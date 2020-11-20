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
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.utils.toRupiah
import com.afdhal_fa.treasure.core.utils.toRupiahUnFormat
import com.google.android.material.textfield.TextInputLayout
import kotlinx.android.synthetic.main.item_exchange.view.*
import java.util.*

class ExchangeAdapter : RecyclerView.Adapter<ExchangeAdapter.VHolder>() {
    private var listData = ArrayList<ExchangeMetode>()

    private var mNominal: Nominal
    private var mUser: User

    init {
        mNominal = Nominal()
        mUser = User()
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

    fun setUser(user: User) {
        this.mUser = user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = VHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.item_exchange, parent, false)
    )

    override fun onBindViewHolder(holder: VHolder, position: Int) {
        holder.onBind(listData[position])
        holder.onExchangeClik(listData[position], mUser)
    }

    override fun getItemCount() = listData.size

    inner class VHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun onBind(mExchangeMetode: ExchangeMetode) {

            with(itemView) {

                if (mNominal.nominal == 0) {
                    setNominalEditText(textFieldNominal, textPrice)
                }

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
                        setErrorPhone(null)
                        setErrorNominal(null)
                    }
                }

                itemView.setOnClickListener {
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
                        setErrorPhone(null)
                        setErrorNominal(null)
                    }
                }

                mNominal.let {
                    if (it.nominal != 0) {
//                        nominal = it.nominal
                        layoutFormExchange.visibility = View.VISIBLE
                        buttonExpandItem.setImageResource(R.drawable.ic_left_arrow_up)
                        textFieldNominal.editText?.setText(
                            it.nominal.toRupiah()
                        )
                        textPrice.text = it.totalNominal.toRupiah()
                    }
                }
            }
        }

        fun onExchangeClik(mExchangeMetode: ExchangeMetode, mUser: User) {
            itemView.buttonExchange.setOnClickListener {
                val textPhoneNumber = itemView.textFieldPhone.editText?.text.toString().trim()
                val textNominal = itemView.textFieldNominal.editText?.text.toString().trim()
                if (validate(textPhoneNumber, textNominal)) {
                    val nominal = textNominal.toRupiahUnFormat().toInt()
                    val mExchange = Exchange(
                        "",
                        type = mExchangeMetode.title,
                        phoneNumber = textPhoneNumber,
                        nominal = nominal,
                        totalNominal = nominal.plus(1000),
                        exchangeUId = mUser.uid
                    )
                    onExchangeClick?.invoke(mExchange)
                }

            }
        }

        init {
            with(itemView) {
                textFieldNominal.setEndIconOnClickListener {
                    listData[adapterPosition].position = adapterPosition
                    onItemClick?.invoke(listData[adapterPosition])
                }
            }
        }

        private fun validate(phoneNumber: String, nominal: String): Boolean {
            if (phoneNumber.isEmpty()) {
                setErrorPhone("Nomor Telpon tidak boleh Kosong")
            } else {
                setErrorPhone(null)
            }


            if (nominal.isEmpty()) {
                setErrorNominal("Masukkan nominal")
            } else if (nominal.toRupiahUnFormat().toInt() < 999) {
                setErrorNominal("Minimal Rp1.000")
            } else {
                setErrorNominal(null)
            }




            if (phoneNumber.isEmpty() || nominal.isEmpty() ||
                nominal.toRupiahUnFormat().toInt() < 999
            ) {
                return false
            }

            return true
        }

        private fun setErrorPhone(err: String?) {
            with(itemView) {
                if (err != null) {
                    textFieldPhone.isErrorEnabled = true
                    textFieldPhone.helperText = err
                } else {
                    textFieldPhone.isErrorEnabled = false
                    textFieldPhone.helperText = null
                }
            }
        }

        private fun setErrorNominal(err: String?) {
            with(itemView) {
                if (err != null) {
                    textFieldNominal.isErrorEnabled = true
                    textFieldNominal.helperText = err
                } else {
                    textFieldNominal.isErrorEnabled = false
                    textFieldNominal.helperText = null
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

                        var sNonFormat = s.toString()
                        sNonFormat = sNonFormat.toRupiahUnFormat()
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