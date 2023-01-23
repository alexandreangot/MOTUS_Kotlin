package com.example.motus

import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word)

        val motus = Motus()
        val keys = createKeyboard()
        val boxes = createGrid(motus.getWord())

        boxes[motus.getStep()][0].text = motus.getFirstLetter()

        for (key in keys){
            key.setOnClickListener {
                if (key.contentDescription == "❌"){
                    motus.setBox(key.contentDescription as String)
                }

            }
        }
    }

    private fun displayResultRow(gridRow : MutableList<TextView>, motus: Motus){
        for(i in 0 until valuesRow.size){
            motus.getStateLetters()
        }
    }

    private fun colorBoxes(word : String, row : MutableList<TextView>){
        for (i in word.indices){
            if(word[i].toString() == row[i].text){
                row[i].setBackgroundColor(Color.RED)
            }
            else{
                for (j in word.indices){
                    if((word[j].toString() == row[i].text) && (word[i].toString() != word[j].toString())){
                        row[i].setBackgroundColor(Color.GREEN)
                    }
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