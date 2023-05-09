package com.example.japanese_reps.ui.home

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
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import com.example.japanese_reps.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_home, container, false)

        val args = bundleOf(Pair("LIST_TITLE", resources.getString(R.string.all_flashcards)), Pair("LIST_ID", -1))
        view.findViewById<Button>(R.id.allFlashcardsButton)?.setOnClickListener (
            Navigation.createNavigateOnClickListener(R.id.action_navigation_home_to_fragment_flashcards, args)
        )

        // onClick listener for the button
        view.findViewById<Button>(R.id.translateButton)?.setOnClickListener {
            lifecycleScope.launch {
                // 5 minute break
                delay(60000L * 5)

                val toast: Toast = Toast.makeText(context, R.string.reminder, Toast.LENGTH_LONG)
                val tView = toast.view

                tView!!.background.setColorFilter(Color.RED, PorterDuff.Mode.SRC_IN)
                val temp = tView.findViewById<TextView>(android.R.id.message)
                temp.setTextColor(Color.WHITE)

                toast.show()
            }

            Toast.makeText(
                view.context, R.string.reminder_notif,
                Toast.LENGTH_SHORT
            ).show()
        }

        view.findViewById<Button>(R.id.randomWordsButton)?.setOnClickListener (
            Navigation.createNavigateOnClickListener(
                R.id.action_navigation_home_to_navigation_lists2,
                null
            )
        )

        return view
    }
}