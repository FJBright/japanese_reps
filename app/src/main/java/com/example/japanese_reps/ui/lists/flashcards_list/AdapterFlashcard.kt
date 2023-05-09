package com.example.japanese_reps.ui.lists.flashcards_list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.databinding.FlashcardCellBinding

class AdapterFlashCard(
    private val flashcards: List<FlashCards>,
    private val clickListener: ClickListenerFlashCard
    )
    : RecyclerView.Adapter<ViewHolderFlashcard>()
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderFlashcard
    {
        val from = LayoutInflater.from(parent.context)
        val binding = FlashcardCellBinding.inflate(from, parent, false)
        return ViewHolderFlashcard(binding, clickListener)
    }

    override fun onBindViewHolder(holder: ViewHolderFlashcard, position: Int)
    {
        holder.bindFlashCard(flashcards[position])
    }

    override fun getItemCount(): Int = flashcards.size
}