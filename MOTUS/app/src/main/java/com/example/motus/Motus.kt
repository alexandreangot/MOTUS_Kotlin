package com.example.motus

class Motus {
    private var step : Int = 0
    private var column : Int = 1
    private var word : String = "MAISON"
    private var grid : MutableList<MutableList<String>> = MutableList(6) { MutableList(word.length) { "" } }

    init {
        grid[0][0] = word[0].toString()
    }
    fun getGrid() : MutableList<MutableList<String>> { return grid }

    fun getWord(): String { return word }

    fun addLetter(letter : String){
        if (column < word.length){
            grid[step][column] = letter
            column++
        }
    }

    fun removeLetter(){
        if (column > 1){
            column--
            grid[step][column] = ""
        }
    }

    private fun isWordValid(): Boolean {
        return true
    }

    fun checkWord(){
        if (column == word.length){
            if(isWordValid()){

            }
        }
    }

    fun removeWord(){
        for (i in 1 until word.length){
            grid[step][i] = ""
        }
    }
}