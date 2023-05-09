package com.example.japanese_reps.ui.lists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.R
import com.example.japanese_reps.databinding.FragmentListsBinding


class FragmentLists : Fragment(), ClickListenerLists {

    private var _binding: FragmentListsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListsBinding.inflate(inflater, container, false)
        val view: View = binding.root

        listList.clear()
        populateLists()

        val layoutManager = GridLayoutManager(activity, 2)
        val cardAdapter = AdapterLists(listList, this)

        val listRecyclerView: RecyclerView = view.findViewById(com.example.japanese_reps.R.id.listRecyclerView)
        listRecyclerView.adapter = cardAdapter
        listRecyclerView.layoutManager = layoutManager

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClick(v: View, list: ListNames)
    {
        val args = bundleOf(Pair("LIST_TITLE", list.title), Pair("LIST_ID", list.id))
        v.findNavController().navigate(R.id.action_navigation_lists_to_fragment_flashcards, args)
    }

    private fun populateLists()
    {
        val listName1 = ListNames(
            "Kanji",
            0
        )

        val listName2 = ListNames(
            "る　Verbs",
            1
        )

        val listName3 = ListNames(
            "う　Verbs",
            2
        )

        val listName4 = ListNames(
            "い Adjectives",
            3
        )

        val listName5 = ListNames(
            "な Adjectives",
            4
        )

        val listName6 = ListNames(
            "Phrases",
            5
        )

        val listName7 = ListNames(
            "Mixed Vocab",
            6
        )

        val listName8 = ListNames(
            "Numbers",
            7
        )

        val listName9 = ListNames(
            "Irregulars",
            8
        )

        val listName10 = ListNames(
            "Empty",
            9
        )

        listList.add(listName1)
        listList.add(listName2)
        listList.add(listName3)
        listList.add(listName4)
        listList.add(listName5)
        listList.add(listName6)
        listList.add(listName7)
        listList.add(listName8)
        listList.add(listName9)
        listList.add(listName10)
    }
}