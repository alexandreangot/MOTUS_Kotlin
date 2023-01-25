package com.example.motus

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.FileReader
import java.io.InputStream
import java.io.InputStreamReader
import java.text.Normalizer
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word)




        val words = readDictionary((5..10).random())



        val motus = Motus(words)

        Log.d("MainActivity", "word: ${motus.getWord()}")

        val myGridView = findViewById<GridView>(R.id.gridView)
        myGridView.numColumns = motus.getWord().length
        val adapter = MyGridAdapter(this, motus.getGrid())
        myGridView.adapter = adapter

        val keys = createKeyboard()

        for (key in keys) {
                key.setOnClickListener {

                    when (key.contentDescription) {
                        "❌" -> {
                            motus.removeLetter()
                        }
                        "✔" -> {
                            motus.checkWord()
                        }
                        else -> {
                            motus.addLetter(key.contentDescription[0])
                        }
                    }
                    adapter.updateData(motus.getGrid())
                }
            }


    }

    private fun readDictionary(size : Int): MutableList<String> {
        val words = mutableListOf<String>()

        val csvFile = "dictionary.csv"
        val inputStream: InputStream = assets.open(csvFile)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            var word = line?.split(",")?.toTypedArray()?.get(0)

            word = Normalizer.normalize(word, Normalizer.Form.NFD)
            word = word.replace("[^\\p{ASCII}]".toRegex(), "")
            word  = word.uppercase()
            if (word.length==size){
                words.add(word)
                Log.d("MainActivity", "word: $word")
            }

        }
        reader.close()
        return words
    }

    private fun createKeyboard(): MutableList<Button> {
        val buttonList = mutableListOf<Button>()
        val tableLayout = findViewById<TableLayout>(R.id.tableLayoutKeyboard)

        val alphabet = "AZERTYUIOPQSDFGHJKLMWXCVBN".toCharArray()
        var index = 0

        for (i in 0 until 3) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
            for (j in 0 until 10) {
                if (!(i == 2 && (j == 1 || j == 9))) {
                    val button = Button(this)
                    if (i == 2 && j == 0) {
                        button.layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            0.5f
                        )
                        button.contentDescription = "❌"
                        button.text = "❌"
                    }
                    else if (i == 2 && j == 8) {
                        button.layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            0.5f
                        )
                        button.contentDescription = "✔"
                        button.text = "✔"
                    } else {
                        button.layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1f
                        )
                        button.contentDescription = alphabet[index].toString()
                        button.text = alphabet[index].toString()
                        index++
                    }
                    buttonList.add(button)
                    tableRow.addView(button)
                }
            }
            tableLayout.addView(tableRow)
        }
        return buttonList
    }
}