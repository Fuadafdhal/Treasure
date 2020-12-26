package com.afdhal_fa.treasure.view.home.notification.transacion

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.afdhal_fa.treasure.LoginActivity
import com.afdhal_fa.treasure.core.domain.model.TreasureUser
import com.afdhal_fa.treasure.core.utils.BaseFragment
import com.afdhal_fa.treasure.core.utils.makeToast
import com.afdhal_fa.treasure.core.vo.Resource
import com.afdhal_fa.treasure.databinding.FragmentTransactionBinding
import com.afdhal_fa.treasure.view.home.notification.DetailNotificationActivity
import com.afdhal_fa.treasure.view.home.notification.DetailNotificationActivity.Companion.EXTRA_INTENT_TRANSACTION
import com.afdhal_fa.treasure.view.home.notification.DetailNotificationActivity.Companion.EXTRA_INTENT_TREASURE_USER

class TransactionFragment : BaseFragment<TransactionViewModel>() {
    private var _binding: FragmentTransactionBinding? = null
    private val binding get() = _binding!!
    private lateinit var mTreasureUser: TreasureUser
    private lateinit var transactionAdapter: TransactionAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        transactionAdapter = TransactionAdapter()
        checkIfUserIsAuthenticated()
        if (activity != null) {

            transactionAdapter.onItemClick = {
                startActivity(
                    Intent(activity, DetailNotificationActivity::class.java)
                        .putExtra(EXTRA_INTENT_TRANSACTION, it)
                        .putExtra(EXTRA_INTENT_TREASURE_USER, mTreasureUser)
                )
            }

            with(binding.rvTransaction) {
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(context)
                adapter = transactionAdapter
            }

        }
    }

    private fun getTransaction(uID: String) {
        viewmodel.getTransaction(uID).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        transactionAdapter.setItem(it.data)
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }

    private fun checkIfUserIsAuthenticated() {
        viewmodel.checkIfUserIsAuthenticated().observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data?.isAuthenticated!!) {
                        getTreasureUser(it.data.uid)
                        getTransaction(it.data.uid)
                    }
                }
                is Resource.Error -> {
                    context?.makeToast("Error")
                    if (!it.data?.isAuthenticated!!) {
                        updateUI(it.data.isAuthenticated)
                    }
                }
            }
        })
    }

    private fun getTreasureUser(uID: String) {
        viewmodel.getTreasureUser(uID).observe(viewLifecycleOwner, {
            when (it) {
                is Resource.Success -> {
                    if (it.data != null) {
                        mTreasureUser = it.data
                        transactionAdapter.setTreasureUser(mTreasureUser)
                    }
                }
                is Resource.Error -> {
                    context?.makeToast(it.message.toString())
                }
            }
        })
    }


    private fun updateUI(authenticated: Boolean) {
        if (authenticated) {
            startActivity(Intent(this.context, LoginActivity::class.java))
        }
    }

    override fun initViewModel() =
        ViewModelProvider(this)[TransactionViewModel::class.java]

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}