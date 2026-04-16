package com.example.demo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.demo.Model.CryptoResponseItem
import com.example.demo.R
import com.example.demo.RecyclerView.cryptoListAdapter
import com.example.demo.ViewModel.cryptoViewModel
import com.example.demo.databinding.FragmentHomeBinding


class HomeFragment : Fragment(R.layout.fragment_home) {

    lateinit var binding: FragmentHomeBinding
    val viewModel: cryptoViewModel by activityViewModels()
    lateinit var  adapter: cryptoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAdapter()
        observeVm()
        setUpMarketData()
        viewModel.getCryptoList("usd", "market_cap_desc", 5)
    }

    fun setUpAdapter(){
        adapter= cryptoListAdapter{ coin: CryptoResponseItem ->

            viewModel.selectCoin(coin)
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)

        }
        binding.homeRv.apply {
            adapter= this@HomeFragment.adapter
            layoutManager= LinearLayoutManager(requireContext())
        }
    }

    fun observeVm(){
        viewModel.cryptoList.observe(viewLifecycleOwner) {value->
            adapter.differ.submitList(value)
        }
        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun setUpMarketData(){

        binding.marketVolume.text= "MARKET VOLUME\n ${viewModel.marketData.value?.data?.total_volume?.usd.toString()}"
        binding.marketCap.text="MARKET CAP\n ${viewModel.marketData.value?.data?.total_market_cap?.usd}"
        binding.activeCrypto.text= "ACTIVE CRYPTO\n ${viewModel.marketData.value?.data?.active_cryptocurrencies}"
        binding.marketChange.text= "MARKET CHANGE\n ${viewModel.marketData.value?.data?.market_cap_percentage} "
    }
}