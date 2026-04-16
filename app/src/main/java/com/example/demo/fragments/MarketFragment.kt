package com.example.demo.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.navigation.fragment.findNavController
import com.example.demo.R
import com.example.demo.RecyclerView.cryptoListAdapter
import com.example.demo.ViewModel.cryptoViewModel
import com.example.demo.databinding.FragmentHomeBinding
import com.example.demo.Model.CryptoResponseItem
import com.example.demo.databinding.FragmentMarketBinding

class MarketFragment : Fragment(R.layout.fragment_market) {

    private lateinit var binding: FragmentMarketBinding
    private val viewModel: cryptoViewModel by activityViewModels()
    private lateinit var adapter: cryptoListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMarketBinding.bind(view)

        setUpAdapter()
        observeViewModel()
        viewModel.getCryptoList("usd", "market_cap_desc", 50)
        setUpChips()
        setupSearch()
    }

    private fun setUpAdapter() {

        adapter = cryptoListAdapter { coin: CryptoResponseItem ->

            viewModel.selectCoin(coin)
            findNavController().navigate(R.id.action_homeFragment_to_detailsFragment)
        }

        binding.marketRecycler.apply {
            adapter = this@MarketFragment.adapter
            layoutManager = LinearLayoutManager(requireContext())
        }
    }

    private fun observeViewModel() {
        viewModel.cryptoList.observe(viewLifecycleOwner) { results ->
            adapter.differ.submitList(results)
        }

        viewModel.error.observe(viewLifecycleOwner) { errorMessage ->
            if (!errorMessage.isNullOrEmpty()) {
                Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setUpChips(){
        binding.chipGroup.setOnCheckedChangeListener {group,checkedId->
            when (checkedId) {
                R.id.chipMarketCap -> viewModel.getCryptoList("usd", "market_cap_desc", 50)
                R.id.chipGainer -> viewModel.getCryptoList("usd", "gainers", 50)
                R.id.chiplosers -> viewModel.getCryptoList("usd", "losers", 50)
                R.id.chipPrice -> viewModel.getCryptoList("usd", "price_desc", 50)
                R.id.chipVolume -> viewModel.getCryptoList("usd", "volume_desc", 50)
                else -> viewModel.getCryptoList("usd", "market_cap_desc", 50)
            }
            binding.chipMarketCap.isChecked= true
        }
    }

    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    searchCrypto(it)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let {
                    searchCrypto(it)
                }
                return true
            }
        })
    }

    private fun searchCrypto(query: String) {
        val currentList = viewModel.cryptoList.value ?: emptyList()
        val filteredList = currentList.filter {
            it.name.contains(query, ignoreCase = true)
        }
        adapter.differ.submitList(filteredList)
    }
}