package com.example.japanese_reps.ui.home.random

import android.app.AlertDialog
import android.app.SearchManager
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.japanese_reps.R
import com.example.japanese_reps.databinding.FragmentRandomWordRecyclerBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONException
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.URL
import java.nio.file.Paths


class FragmentFlashCard : Fragment(), ClickListenerRandom {

    private lateinit var binding: FragmentRandomWordRecyclerBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRandomWordRecyclerBinding.inflate(layoutInflater)
        val view: View = binding.root

        randomsList.clear()


        populateLists(this)

        val layoutManager = GridLayoutManager(activity, 1)
        val flashcardAdapter = AdapterRandom(randomsList, this)

        val recyclerViewFlashcards: RecyclerView = view.findViewById(R.id.randomRecyclerView)
        recyclerViewFlashcards.adapter = flashcardAdapter
        recyclerViewFlashcards.layoutManager = layoutManager

        return view
    }

    override fun onClick(random: String)
    {
        val options = arrayOf("DeepL", "Google Translate", "Conjugations")
        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Translate How?")
        builder.setItems(options) { _, optionId ->
            dispatchAction(optionId, random)
        }
        builder.show()
    }

    private fun dispatchAction(optionId: Int, random: String) {
        when (optionId) {
            0 -> {
                val query = "https://www.deepl.com/translator#ja/en/" + random
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
            }
            1 -> {
                val query = "https://translate.google.com/?sl=ja&tl=en&text=" + random + "&op=translate"
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
            }
            2 -> {
                val query = "http://www.japaneseverbconjugator.com/SearchResultsBS.asp?q=" + random
                val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                    putExtra(SearchManager.QUERY, query)
                }
                startActivity(intent)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun populateLists(fragmentFlashCard: FragmentFlashCard)
    {

        lifecycleScope.launch {
            var result = httpGet("https://random-word.ryanrk.com/api/jp/word/random/5")
            result = result.replace("[", "").replace("]", "").replace("\"", "")
            println(result)

            var temp = result.split(",").toTypedArray()

            randomsList.clear()
            randomsList.addAll(temp)

            delay(1000)
            binding.progressBar.isInvisible = true

            val layoutManager = LinearLayoutManager(activity)
            val flashcardAdapter = AdapterRandom(randomsList, fragmentFlashCard)
            val recyclerViewFlashcards: RecyclerView =
                view?.findViewById(R.id.randomRecyclerView) as RecyclerView
            recyclerViewFlashcards.adapter = flashcardAdapter
            recyclerViewFlashcards.layoutManager = layoutManager

            // My attempt at writing to the json file (read only file problem.) TODO
//            val json = JSONObject()
//            for (word in randomsList) {
//                try {
//                    json.put("english", "test")
//                    json.put("japanese", "word")
//                    json.put("listCategory", 9)
//                } catch (e: JSONException) {
//                    e.printStackTrace()
//                }
//
//                try {
//                    var pathToFile = Paths.get("all_data.json")
//                    println(pathToFile)
//
//                    println("===========================================")
//
//                    println(json.toString())
//                    PrintWriter(FileWriter(pathToFile.toFile(), true))
//                        .use { it.write(json.toString()) }
//                } catch (e: Exception) {
//                    e.printStackTrace()
//                }
//            }
        }
    }

    private suspend fun httpGet(myURL: String?): String {

        val result = withContext(Dispatchers.IO) {
            val inputStream: InputStream


            // create URL
            val url = URL(myURL)

            // create HttpURLConnection
            val conn: HttpURLConnection = url.openConnection() as HttpURLConnection

            // make GET request to the given URL
            conn.connect()

            // receive response as inputStream
            inputStream = conn.inputStream

            // convert inputstream to string
            if (inputStream != null)
                convertInputStreamToString(inputStream)
            else
                "Did not work!"


        }
        return result
    }

    private fun convertInputStreamToString(inputStream: InputStream): String {
        val bufferedReader = BufferedReader(InputStreamReader(inputStream))

        var line:String? = bufferedReader.readLine()
        var result = ""

        while (line != null) {
            result += line
            line = bufferedReader.readLine()
        }

        inputStream.close()
        return result
    }
}