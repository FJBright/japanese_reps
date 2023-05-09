package com.example.japanese_reps.ui.home.random

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.databinding.ListCellBinding

class AdapterRandom(
    private val randomList: List<String>,
    private val clickListener: ClickListenerRandom
    )
    : RecyclerView.Adapter<ViewHolderRandom>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRandom
    {
        val from = LayoutInflater.from(parent.context)
        val binding = ListCellBinding.inflate(from, parent, false)
        return ViewHolderRandom(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderRandom, position: Int)
    {
        holder.bindList(randomList[position])
    }

    override fun getItemCount(): Int = randomList.size
}