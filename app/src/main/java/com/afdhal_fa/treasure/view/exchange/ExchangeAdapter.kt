package com.afdhal_fa.treasure.view.exchange

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import coil.load
import com.afdhal_fa.treasure.R
import com.afdhal_fa.treasure.core.domain.model.Exchange
import com.afdhal_fa.treasure.core.domain.model.ExchangeMetode
import com.afdhal_fa.treasure.core.domain.model.Nominal
import com.afdhal_fa.treasure.core.domain.model.User
import com.afdhal_fa.treasure.core.utils.*
import com.afdhal_fa.treasure.databinding.ItemExchangeBinding
import org.difcool.aksirelawan.base.BaseRecyclerViewAdapter

class ExchangeAdapter : BaseRecyclerViewAdapter<ExchangeAdapter.ExchangeVHolder, ExchangeMetode>() {

    private var mNominal: Nominal
    private var mUser: User

    init {
        mNominal = Nominal()
        mUser = User()
    }

    var onExchangeClick: ((Exchange) -> Unit)? = null

    fun setNominal(nominal: Nominal, position: Int) {
        this.mNominal = nominal
        notifyItemChanged(position)
    }

    fun setUser(user: User) {
        this.mUser = user
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExchangeVHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_exchange, parent, false)
        )

    override fun onBindViewHolder(holder: ExchangeVHolder, item: ExchangeMetode, position: Int) {
        holder.onBind(item, mNominal, mUser)
        holder.onExchangeClik(item, mUser)
    }

    inner class ExchangeVHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemExchangeBinding.bind(itemView)

        fun onBind(mExchangeMetode: ExchangeMetode, mNominal: Nominal, mUser: User) {
            with(binding) {
                setPhoneNumberEditText()
                if (mNominal.nominal == 0) {
                    setNominalEditText()
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

                binding.root.setOnClickListener {
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


                if (mNominal.nominal != 0) {
                    layoutFormExchange.visibility = View.VISIBLE
                    buttonExpandItem.setImageResource(R.drawable.ic_left_arrow_up)

                    textFieldNominal.editText?.setText(mNominal.nominal.toRupiah())
                    textPrice.text = mNominal.totalNominal.toRupiah()
                }

                if (mUser.phoneNumber != "") {
                    textFieldPhone.editText?.setText(mUser.phoneNumber)
                }


            }
        }

        fun onExchangeClik(mExchangeMetode: ExchangeMetode, mUser: User) {
            with(binding) {
                buttonExchange.setOnClickListener {
                    val textPhoneNumber = textFieldPhone.editText?.text.toString().trim()
                    val textNominal = textFieldNominal.editText?.text.toString().trim()

                    if (validate(textPhoneNumber, textNominal)) {
                        val nominal = textNominal.toRupiahUnFormat().toLong()
                        val serviceType = textPhoneNumber.substring(0, 4).replace("0", "62")
                        val mExchange = Exchange(
                            type = mExchangeMetode.title,
                            serviceType = cekOperatorWithText(serviceType),
                            phoneNumber = textPhoneNumber,
                            nominal = nominal,
                            totalNominal = nominal.plus(1000),
                            timestamp = System.currentTimeMillis(),
                            read = false,
                            uid = mUser.uid
                        )
                        onExchangeClick?.invoke(mExchange)
                    } else {
                        binding.root.context?.makeToast("someating wrong")
                    }
                }
            }
        }

        init {
            with(binding) {
                textFieldNominal.setEndIconOnClickListener {
                    items[adapterPosition].position = adapterPosition
                    onItemClick?.invoke(items[adapterPosition])
                }
            }
        }

        private fun validate(phoneNumber: String, nominal: String): Boolean {
            if (phoneNumber.isEmpty()) {
                setErrorPhone("Nomor telepon tidak boleh Kosong")
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

            val serviceType = phoneNumber.substring(0, 4).replace("0", "62")


            if (phoneNumber.isEmpty() ||
                nominal.isEmpty() ||
                cekOperatorDrawrabel(serviceType) == 0 ||
                nominal.toRupiahUnFormat().toInt() < 999
            ) {
                return false
            }

            return true
        }


        private fun setPhoneNumberEditText() {
            with(binding) {
                textFieldPhone.editText?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
                        if (s.toString().length > 3) {
                            val textPhoneNumber = s.toString()
                                .trim().substring(0, 4).replace("0", "62")

                            if (textPhoneNumber.substring(0, 1) == "0") {
                                textFieldPhone.endIconDrawable = null
                                textFieldPhone.isErrorEnabled = true
                                textFieldPhone.helperText = "Gunakan format 08**********"
                            } else if (cekOperatorDrawrabel(textPhoneNumber) == 0) {
                                textFieldPhone.endIconDrawable = null
                                textFieldPhone.isErrorEnabled = true
                                textFieldPhone.helperText = "Nomor Telepon tidak ditemukan"
                            } else {
                                textFieldPhone.setEndIconDrawable(
                                    cekOperatorDrawrabel(
                                        textPhoneNumber
                                    )
                                )
                                textFieldPhone.isErrorEnabled = false
                                textFieldPhone.helperText = null
                            }

                        } else {
                            textFieldPhone.endIconDrawable = null
                            textFieldPhone.isErrorEnabled = false
                            textFieldPhone.helperText = null
                        }
                    }

                    override fun afterTextChanged(s: Editable?) {}
                })
            }
        }

        private fun setNominalEditText() {
            var current = ""
            with(binding) {
                textFieldNominal.editText?.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(
                        s: CharSequence?,
                        start: Int,
                        count: Int,
                        after: Int
                    ) {
                    }

                    override fun onTextChanged(
                        s: CharSequence?,
                        start: Int,
                        before: Int,
                        count: Int
                    ) {
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

        private fun setErrorPhone(err: String?) {
            with(binding) {
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
            with(binding) {
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
}