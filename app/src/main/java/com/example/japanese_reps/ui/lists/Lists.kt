package com.example.japanese_reps.ui.lists

var listList = mutableListOf<ListNames>()

val LIST_ID_EXTRA = "listExtra"

class ListNames(
    var title: String,
    val id: Int? = listList.size
)