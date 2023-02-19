package com.example.motus.controllers

import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.motus.R
import com.example.motus.adapters.GridAdapter
import com.example.motus.models.Motus
import com.example.motus.models.Timer
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word)

        startGame()
    }

    private fun setRestartButton(){
        val imageButtonRestart = findViewById<ImageButton>(R.id.imageButtonRestart)
        imageButtonRestart.setOnClickListener{
            startGame()
        }
    }

    private fun startGame() {

        val keys = createKeyboard()
        val words = readDictionary()

        val timer = Timer()
        val textViewTimer = findViewById<TextView>(R.id.textViewTimer)
        timer.start(textViewTimer)

        val motus = Motus(words)

        val gridAdapter = setGridAdapter(motus)

        setGiveUpButton(motus, timer, keys)
        setkeys(motus, gridAdapter, keys, timer)
        setRestartButton()
        removeWord()
    }

    private fun setGiveUpButton(motus: Motus, timer: Timer, keys: MutableList<Button>) {
        val imageButtonHint = findViewById<ImageButton>(R.id.imageButtonGiveUp)
        imageButtonHint.callOnClick()
        imageButtonHint.setOnClickListener{
            endGame(timer, motus, keys)
        }
    }

    private fun setGridAdapter(motus : Motus): GridAdapter {
        val gridView = findViewById<GridView>(R.id.gridView)
        gridView.numColumns = motus.getWord().length
        val adapter = GridAdapter(this, motus.getGrid())
        gridView.adapter = adapter
        return adapter
    }

    private fun readDictionary(): MutableList<String> {
        val size = (5..10).random()
        val words = mutableListOf<String>()
        val csvFile = "dictionary.csv"
        val inputStream: InputStream = assets.open(csvFile)
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String?

        while (reader.readLine().also { line = it } != null) {
            val word = line?.split(",")?.toTypedArray()?.get(0)
            if (word != null) {
                if (word.length==size){
                    words.add(word)
                }
            }

        }
        reader.close()
        return words
    }

    private fun createKeyboard(): MutableList<Button> {
        val buttonList = mutableListOf<Button>()
        val tableLayout = findViewById<TableLayout>(R.id.tableLayoutKeyboard)
        tableLayout.removeAllViews()
        val alphabet = resources.getString(R.string.keyboard)
        var index = 0

        for (i in 0 until 3) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
            for (j in 0 until 10) {
                if (!(i == 2 && (j == 1 || j == 9))) {
                    var button :Button
                    if (i == 2 && j == 0) {
                        button = createKey("❌", 0.5f)
                    }
                    else if (i == 2 && j == 8) {
                        button = createKey("✔", 0.5f)
                    } else {
                        button = createKey(alphabet[index].toString(), 1f)
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

    private fun createKey(name:String, size: Float): Button {
        val button = Button(this)
        button.layoutParams = TableRow.LayoutParams(
            TableRow.LayoutParams.WRAP_CONTENT,
            TableRow.LayoutParams.MATCH_PARENT,
            size
        )
        button.setBackgroundResource(R.drawable.key_shape)
        button.contentDescription = name
        button.text = name

        val layoutParams = button.layoutParams as TableRow.LayoutParams
        layoutParams.setMargins(3, 3, 3, 3)
        button.layoutParams = layoutParams

        return button
    }

    private fun setkeys(motus: Motus, adapter: GridAdapter, keys: MutableList<Button>, timer: Timer){
        for (key in keys) {
            key.setOnClickListener {
                when (key.contentDescription) {
                    "❌" -> {
                        motus.removeLetter()
                    }
                    "✔" -> {
                        motus.checkWord()
                        if (motus.end) {
                            endGame(timer, motus, keys)
                        }
                    }
                    else -> {
                        motus.addLetter(key.contentDescription[0])
                    }
                }
                adapter.updateData(motus.getGrid())
            }
        }
    }

    private fun displayWord(motus: Motus) {
        val textViewWord = findViewById<TextView>(R.id.textViewWord)
        textViewWord.text = resources.getString(R.string.end_game, motus.getWord())
    }

    private fun removeWord() {
        val textViewWord = findViewById<TextView>(R.id.textViewWord)
        textViewWord.text = ""
    }

    private fun endGame(timer: Timer, motus: Motus, keys: MutableList<Button>) {
        motus.end = true
        timer.stop()
        displayWord(motus)
        for (key in keys) {
            key.isEnabled = false
        }
    }
}