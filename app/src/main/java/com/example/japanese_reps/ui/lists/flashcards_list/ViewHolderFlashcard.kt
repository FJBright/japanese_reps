package com.example.japanese_reps.ui.lists.flashcards_list

import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.databinding.FlashcardCellBinding

class ViewHolderFlashcard(
    private val flashcardCellBinding: FlashcardCellBinding,
    private val clickListener: ClickListenerFlashCard
) : RecyclerView.ViewHolder(flashcardCellBinding.root)
{
    fun bindFlashCard(flashcard: FlashCards)
    {
        flashcardCellBinding.english.text = flashcard.english
        flashcardCellBinding.japanese.text = flashcard.japanese

        flashcardCellBinding.flashCardView.setOnClickListener{
            clickListener.onClick(flashcard)
        }
    }
}