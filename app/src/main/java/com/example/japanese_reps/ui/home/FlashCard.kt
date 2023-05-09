package com.example.japanese_reps.ui.home

var cardList = mutableListOf<NotFlashCard>()

val FLASHCARD_ID_EXTRA = "bookExtra"

class NotFlashCard(
    var english: String,
    var japanese: String,
    val id: Int? = cardList.size
)