package com.example.japanese_reps.ui.lists

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.databinding.ListCellBinding

class ViewHolderList(
    private val listCellBinding: ListCellBinding,
    private val clickListener: ClickListenerLists
) : RecyclerView.ViewHolder(listCellBinding.root)
{
    fun bindList(view: View, list: ListNames)
    {
        listCellBinding.title.text = list.title

        listCellBinding.listView.setOnClickListener{
            clickListener.onClick(view, list)
        }
    }
}