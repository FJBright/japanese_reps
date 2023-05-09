package com.example.japanese_reps.ui.settings

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.japanese_reps.R
import com.example.japanese_reps.databinding.FragmentSettingsBinding


class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val sharedPreference = this.context?.getSharedPreferences("card_ordering",
            Context.MODE_PRIVATE
        )

        val editor = sharedPreference?.edit()

        val textSetting: TextView = binding.textSettings2
        val japaneseFirst = sharedPreference?.getBoolean("flashcardJapaneseFirst",true)
        if (japaneseFirst == false) {
            binding.switchCompat.isChecked = false
            textSetting.text = resources.getString(R.string.english_first)
        } else {
            binding.switchCompat.isChecked = true
            textSetting.text = resources.getString(R.string.japanese_first)
        }

        // Switch used to determine which card is shown first
        binding.switchCompat.setOnCheckedChangeListener { _, isChecked ->
            if (editor != null) {
                editor.putBoolean("flashcardJapaneseFirst", isChecked)
                editor.apply();
                if (isChecked) {
                    textSetting.text = resources.getString(R.string.japanese_first)
                } else {
                    textSetting.text = resources.getString(R.string.english_first)
                }
                Toast.makeText(
                    view?.context, resources.getString(R.string.saved),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }



        when (sharedPreference?.getInt("flashcardOrdering",0)) {
            0 -> {
                binding.radioButton1.isChecked = true
                binding.radioButton2.isChecked = false
                binding.radioButton3.isChecked = false
            }
            1 -> {
                binding.radioButton1.isChecked = false
                binding.radioButton2.isChecked = true
                binding.radioButton3.isChecked = false
            }
            else -> {
                binding.radioButton1.isChecked = false
                binding.radioButton2.isChecked = false
                binding.radioButton3.isChecked = true

            }
        }

        binding.radioButton1.setOnClickListener {
            if (editor != null) {
                editor.putInt("flashcardOrdering", 0)
                editor.apply();
            }
            Toast.makeText(
                view?.context, resources.getString(R.string.saved),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.radioButton2.setOnClickListener {
            if (editor != null) {
                editor.putInt("flashcardOrdering", 1)
                editor.apply();
            }
            Toast.makeText(
                view?.context, resources.getString(R.string.saved),
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.radioButton3.setOnClickListener {
            if (editor != null) {
                editor.putInt("flashcardOrdering", 2)
                editor.apply();
            }
            Toast.makeText(
                view?.context, resources.getString(R.string.saved),
                Toast.LENGTH_SHORT
            ).show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}