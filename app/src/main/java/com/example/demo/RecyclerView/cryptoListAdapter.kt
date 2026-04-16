package com.example.demo.RecyclerView

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.demo.Model.CryptoResponseItem
import com.example.demo.R

class cryptoListAdapter(private val onItemClick: (CryptoResponseItem) -> Unit)
    : RecyclerView.Adapter<cryptoListAdapter.listViewHolder>() {

    private val differCallBack = object : DiffUtil.ItemCallback<CryptoResponseItem>() {
        override fun areItemsTheSame(oldItem: CryptoResponseItem, newItem: CryptoResponseItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: CryptoResponseItem, newItem: CryptoResponseItem): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val item = LayoutInflater.from(parent.context).inflate(R.layout.itemlist, parent, false)
        return listViewHolder(item)
    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        val item = differ.currentList[position]

        holder.cryptoPrice.text = item.current_price.toString()
        holder.cryptoName.text = item.name
        holder.cryptoSymbol.text = item.symbol
        holder.cryptoChange.text = item.market_cap_change_percentage_24h.toString()
        holder.CryptoRank.text = item.market_cap_rank.toString()
        Glide.with(holder.itemView.context).load(item.image).into(holder.CryptoIcon)

        // 🔹 Click listener using lambda
        holder.itemView.setOnClickListener {
            onItemClick(item)
        }
    }

    override fun getItemCount(): Int = differ.currentList.size

    class listViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val CryptoRank = itemView.findViewById<TextView>(R.id.rankTv)
        val CryptoIcon = itemView.findViewById<ImageView>(R.id.cryptoIcon)
        val cryptoName = itemView.findViewById<TextView>(R.id.cryptoName)
        val cryptoSymbol = itemView.findViewById<TextView>(R.id.cryptoSymbol)
        val cryptoPrice = itemView.findViewById<TextView>(R.id.cryptoPrice)
        val cryptoChange = itemView.findViewById<TextView>(R.id.cryptoChange)
    }
}