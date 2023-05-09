package com.example.japanese_reps.ui.home.random

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.databinding.ListCellBinding
import java.util.*

class ViewHolderRandom(
    private val listCellBinding: ListCellBinding,
    private val clickListener: ClickListenerRandom
) : RecyclerView.ViewHolder(listCellBinding.root)
{
    fun bindList(random: String)
    {
        listCellBinding.title.text = random

        listCellBinding.listView.setOnClickListener{
            clickListener.onClick(random)
        }
    }
}