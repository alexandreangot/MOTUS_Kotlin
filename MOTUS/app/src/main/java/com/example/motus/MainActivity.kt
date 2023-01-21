package com.example.motus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word)

        val keys = createKeyboard()
        val word = "MAISON"
        val case = createGrid(word)
        var step = 0
        var column = 1
        case[step][0].text = word[0].toString()
        for (key in keys){
            key.setOnClickListener {
                if (column<word.length){
                    case[step][column].text = key.contentDescription
                    column++
                }
                else {
                    step++
                    column=1
                    case[step][0].text = word[0].toString()
                }
            }
        }

    }

    private fun createGrid(word : String) : MutableList<MutableList<TextView>>{
        val list = mutableListOf<MutableList<TextView>>()
        val tableLayoutTriedWord = findViewById<TableLayout>(R.id.tableLayoutTriedWord)
        for (i in 0 until 6) {
            val row = mutableListOf<TextView>()
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
            tableRow.gravity = Gravity.CENTER
            for (j in word.indices) {
                val textView = TextView(this)
                textView.layoutParams = TableRow.LayoutParams(
                    TableRow.LayoutParams.WRAP_CONTENT,
                    TableRow.LayoutParams.WRAP_CONTENT,
                    1f
                )
                textView.text = ""
                textView.gravity = Gravity.CENTER
                textView.layoutParams.height = 100
                textView.layoutParams.width = 50
                textView.setBackgroundResource(R.drawable.textview_border)
                row.add(textView)
                tableRow.addView(textView)
            }
            list.add(row)
            tableLayoutTriedWord.addView(tableRow)
        }
        return list
    }

    private fun createKeyboard(): MutableList<Button> {
        val buttonList = mutableListOf<Button>()
        val tableLayout = findViewById<TableLayout>(R.id.tableLayoutKeyboard)

        val alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray()
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