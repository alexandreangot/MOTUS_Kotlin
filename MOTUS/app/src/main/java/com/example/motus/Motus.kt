package com.example.motus

class Motus {
    private var step : Int = 0
    private var column : Int = 1
    private var word : String = "MAISON"
    private lateinit var grid : MutableList<MutableList<String>>

    init {
        for (i in word.indices){
            for (j in word.indices){
                grid[i][j] = ""
            }
        }
        grid[0][0] = word[0].toString()
    }

    fun getStateLetters(){
        // return a list : 0  si la lettre n'est pas dans le mot
        // 1 si elle n'est pas a la bonne place
        // 2 si elle est bien placée
        var states = [2, 1].
        for (indice in word.indices){
            if (word[indice].toString() == grid[getStep()][indice]){
                states[indice] = 2
            }
        }
    }



    fun isEmpty(): Boolean {
        if (grid[step][column] == ""){
            return true
        }
        return false
    }

    fun getStep(): Int {
        return step
    }

    fun getColumn(): Int {
        return column
    }

    fun setBox(letter : String){
        if (column<word.length){
            grid[step][column] = letter
            column++
        }
    }

    fun removeBox(){
        if (column>0){
            column--
            grid[step][column] = ""
        }
    }

    fun nextStep(){
        if (column==word.length){
            if (step==5){

            }
            else{
                step++
            }
        }
    }

    fun getWord(): String {
        return word
    }

    fun getBox(step : Int, column : Int): String {
        return grid[step][column]
    }

    fun getFirstLetter(): String {
        return word[0].toString()
    }
}