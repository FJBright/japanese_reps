package com.example.japanese_reps.ui.lists.flashcards_list

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.R
import com.example.japanese_reps.databinding.FragmentFlashcardsRecyclerBinding
import com.google.gson.Gson
import java.io.BufferedReader

private const val ARG_PARAM1 = "LIST_TITLE"
private const val ARG_PARAM2 = "LIST_ID"

class FragmentFlashCard : Fragment(), ClickListenerFlashCard {

    private lateinit var binding: FragmentFlashcardsRecyclerBinding
    private var listTitle: String? = null
    private var listID: Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            listTitle = it.getString(ARG_PARAM1)
            listID = it.getInt(ARG_PARAM2)
        }

        binding = FragmentFlashcardsRecyclerBinding.inflate(layoutInflater)
        val view: View = binding.root

        val text = binding.flashcardButton

        // Name of the list dictated by the recyclerview input
        text.text = resources.getString(R.string.start) + " \"" + listTitle + "\""

        flashCardList.clear()
        listID?.let { populateLists(it) }

        val layoutManager = GridLayoutManager(activity, 1)
        val flashcardAdapter = AdapterFlashCard(flashCardList, this)

        val recyclerViewFlashcards: RecyclerView = view.findViewById(R.id.recyclerViewFlashcards)
        recyclerViewFlashcards.adapter = flashcardAdapter
        recyclerViewFlashcards.layoutManager = layoutManager

        // BUTTON
        val args = bundleOf(Pair("LIST_TITLE", listTitle), Pair("CARD_ID", 0))

        if (flashCardList.isNotEmpty()) {
            view.findViewById<Button>(R.id.flashcardButton)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.action_fragment_flashcards_to_individualFlashcard,
                    args
                )
            )
        } else {
            val toast: Toast = Toast.makeText(context, R.string.no_content_available, Toast.LENGTH_LONG)
            val tView = toast.view

            tView!!.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
            val temp = tView.findViewById<TextView>(android.R.id.message)
            temp.setTextColor(Color.WHITE)

            toast.show()
        }




//        view.findViewById<Button>(R.id.flashcardButton)?.setOnClickListener{
//            if (flashCardList.isNotEmpty()) {
//                println("Should be working?")
//                Navigation.createNavigateOnClickListener(
//                    R.id.action_fragment_flashcards_to_individualFlashcard,
//                    args
//                )
//            } else {
//                val toast: Toast = Toast.makeText(context, R.string.no_content_available, Toast.LENGTH_LONG)
//                val tView = toast.view
//
//                tView!!.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
//                val temp = tView.findViewById<TextView>(android.R.id.message)
//                temp.setTextColor(Color.WHITE)
//
//                toast.show()
//            }
//        }

        return view
    }

    override fun onClick(flashCard: FlashCards)
    {
        val options = arrayOf("DeepL", "Google Translate", "Conjugations")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Translate How?")
        builder.setItems(options) { _, optionId ->
            dispatchAction(optionId, flashCard)
        }
        builder.show()
    }

    private fun dispatchAction(optionId: Int, flashCard: FlashCards) {
        when (optionId) {
            0 -> {
                val query = "https://www.deepl.com/translator#ja/en/" + flashCard.japanese
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
            }
            1 -> {
                val query = "https://translate.google.com/?sl=ja&tl=en&text=" + flashCard.japanese + "&op=translate"
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
            }
            2 -> {
                val query = "http://www.japaneseverbconjugator.com/SearchResultsBS.asp?q=" + flashCard.japanese
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
            }
        }
    }

    private fun populateLists(id: Int)
    {
        val content = this.resources.openRawResource(R.raw.all_data)

        val temporaryList: MutableList<FlashCards> =
            Gson().fromJson(content.reader(), Array<FlashCards>::class.java).toMutableList()

        val item1 = FlashCards(
            "English1",
            "たんご; 単語",
            0
        )

        val item2 = FlashCards(
            "English2",
            "たんご; 単語",
            0
        )

        val item3 = FlashCards(
            "English3",
            "たんご; 単語",
            0
        )

        val item4 = FlashCards(
            "English4",
            "たんご; 単語",
            3
        )

        val item5 = FlashCards(
            "English5",
            "たんご; 単語",
            4
        )

        val item6 = FlashCards(
            "English6",
            "たんご; 単語",
            5
        )

        val item7 = FlashCards(
            "English7",
            "たんご; 単語",
            6
        )


//        temporaryList.add(item1)
//        temporaryList.add(item2)
//        temporaryList.add(item3)
//        temporaryList.add(item4)
//        temporaryList.add(item5)
//        temporaryList.add(item6)
//        temporaryList.add(item7)
//
//        temporaryList.add(item1)
//        temporaryList.add(item2)
//        temporaryList.add(item3)
//        temporaryList.add(item4)
//        temporaryList.add(item5)
//        temporaryList.add(item6)
//        temporaryList.add(item7)

        for (i in temporaryList.indices) {
            if (temporaryList[i].listCategory == id || listID == -1) {
                flashCardList.add(temporaryList[i])
            }
        }

        val sharedPreference = this.context?.getSharedPreferences("card_ordering", MODE_PRIVATE)
        val test = sharedPreference?.getInt("flashcardOrdering",0)
        if (test == 1) {
            flashCardList.reverse()
            println("flashCardList reversed")
        } else if (test == 2) {
            flashCardList.shuffle()
            println("flashCardList shuffled")
        }
    }
}