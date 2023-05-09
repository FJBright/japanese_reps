package com.example.japanese_reps.ui.lists.flashcards_list

var flashCardList = mutableListOf<FlashCards>()

val FLASHCARD_ID_EXTRA = "flashCardExtra"

class FlashCards(
    var english: String,
    var japanese: String,
    var listCategory: Int,
    val id: Int? = flashCardList.size
) {
    operator fun get(position: Int): FlashCards {
        return flashCardList[position]
    }
}