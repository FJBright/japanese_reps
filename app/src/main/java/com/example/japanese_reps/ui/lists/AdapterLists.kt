package com.example.japanese_reps.ui.lists

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.databinding.ListCellBinding

class AdapterLists(
    private val lists: List<ListNames>,
    private val clickListener: ClickListenerLists
    )
    : RecyclerView.Adapter<ViewHolderList>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderList
    {
        val from = LayoutInflater.from(parent.context)
        val binding = ListCellBinding.inflate(from, parent, false)
        return ViewHolderList(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderList, position: Int)
    {
        holder.bindList(holder.itemView, lists[position])
    }

    override fun getItemCount(): Int = lists.size
}