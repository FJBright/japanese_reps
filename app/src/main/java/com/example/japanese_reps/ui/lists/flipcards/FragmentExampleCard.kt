package com.example.japanese_reps.ui.lists.flipcards

import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.example.japanese_reps.R
import com.example.japanese_reps.databinding.FragmentCardBinding
import com.example.japanese_reps.ui.lists.flashcards_list.flashCardList

private const val ARG_PARAM2 = "CARD_ID"

class ExampleCard : Fragment() {

    private lateinit var binding: FragmentCardBinding
    private var cardID: Int? = null

    lateinit var front_anim:AnimatorSet
    lateinit var back_anim:AnimatorSet
    var isFront = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        arguments?.let {
            cardID = it.getInt(ARG_PARAM2)
        }

        binding = FragmentCardBinding.inflate(layoutInflater)
        val view: View = binding.root

        val card_front: CardView = binding.cardFront
        val card_back: CardView = binding.cardBack
        card_front.cameraDistance = 10000 * 3F
        card_back.cameraDistance = 10000 * 3F

        front_anim = AnimatorInflater.loadAnimator(view.context, R.anim.card_flip_vertical_1) as AnimatorSet
        back_anim = AnimatorInflater.loadAnimator(view.context, R.anim.card_flip_vertical_2) as AnimatorSet

        val sharedPreference = this.context?.getSharedPreferences("card_ordering",
            Context.MODE_PRIVATE
        )
        val japaneseFirst = sharedPreference?.getBoolean("flashcardJapaneseFirst",true)
        val front = binding.textFront
        val back = binding.textBack
        if (japaneseFirst == true) {
            back.text = flashCardList[cardID!!].english
            front.text = flashCardList[cardID!!].japanese
        } else {
            front.text = flashCardList[cardID!!].english
            back.text = flashCardList[cardID!!].japanese
        }

        back.setOnClickListener{
            isFront = if (!isFront) {
                front_anim.setTarget(card_back)
                back_anim.setTarget(card_front)
                back_anim.start()
                front_anim.start()
                true
            } else {
                front_anim.setTarget(card_front)
                back_anim.setTarget(card_back)
                front_anim.start()
                back_anim.start()
                false
            }
        }

        // Previous button pressed
        if (cardID!! > 0) {
            binding.buttonPrev.alpha = 1F
            val nextFlipCard = bundleOf(Pair("CARD_ID", cardID?.minus(1) ?: 0))
            view.findViewById<Button>(R.id.buttonPrev)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.action_individualFlashcard_self2,
                    nextFlipCard
                )
            )
        } else {
            binding.buttonPrev.alpha = 0.3F
        }

        // Next button pressed
        if (cardID!! < (flashCardList.size - 1)) {
            binding.buttonNext.alpha = 1F
            val nextFlipCard = bundleOf(Pair("CARD_ID", cardID?.plus(1) ?: 0))
            view.findViewById<Button>(R.id.buttonNext)?.setOnClickListener(
                Navigation.createNavigateOnClickListener(
                    R.id.action_individualFlashcard_self1,
                    nextFlipCard
                )
            )
        } else {
            binding.buttonNext.alpha = 0.3F
            Toast.makeText(
                view.context, R.string.final_card,
                Toast.LENGTH_SHORT
            ).show()
        }

        return view
    }
}