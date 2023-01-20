package com.example.motus

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TableLayout
import android.widget.TableRow
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.word)

        createKeyboard()


    }


    private fun createKeyboard(){
        val tableLayout = findViewById<TableLayout>(R.id.clavier)

        val alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray()
        var index = 0

        for (i in 0 until 3) {
            val tableRow = TableRow(this)
            tableRow.layoutParams = TableLayout.LayoutParams(
                TableLayout.LayoutParams.MATCH_PARENT,
                TableLayout.LayoutParams.MATCH_PARENT
            )
            for (j in 0 until 10) {
                if (!(i == 2 && (j == 1 || j == 9))) {
                    val button = AppCompatButton(this)
                    if (i == 2 && j == 0) {
                        button.layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            0.5f
                        )
                        button.text = "❌"
                    }
                    else if (i == 2 && j == 8) {
                        button.layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            0.5f
                        )
                        button.text = "✔"
                    } else {
                        button.layoutParams = TableRow.LayoutParams(
                            TableRow.LayoutParams.WRAP_CONTENT,
                            TableRow.LayoutParams.MATCH_PARENT,
                            1f
                        )
                        button.text = alphabet[index].toString()
                        index++
                    }
                    tableRow.addView(button)
                }
            }
            tableLayout.addView(tableRow)
        }

    }
}