package com.example.motus.models

class Motus(private val words : MutableList<String>) {
    var end :Boolean = false
    private var step : Int = 0
    private var column : Int = 1
    private var word : String = words.random()
    private var grid : MutableList<MutableList<Pair<Char, Int>>> = MutableList(6) { MutableList(word.length) { ' ' to 0 } }

    init {
        grid[0][0] = Pair(word[0], 1)
        for (indice in 1 until word.length){
            grid[0][indice] = Pair('·', 0)
        }
    }

    fun getGrid() : MutableList<MutableList<Pair<Char, Int>>> { return grid }
    fun getWord(): String { return word }

    fun addLetter(letter : Char){
        if (column < word.length && !end){
            grid[step][column] = Pair(letter, 1)
            column++
        }
    }

    fun removeLetter(){
        if (column > 1 && !end){
            column--
            grid[step][column] = Pair(' ', 0)
        }
    }

    fun checkWord() {
        if(!end) {
            if (column == word.length) {
                if (words.contains(getCurrentWordInRow())) {
                    colorRightLetter()
                    if (step >= word.length || word == getCurrentWordInRow()) {
                        end = true
                    } else {
                        nextStep()
                    }
                }else{
                    removeWord()
                }
            }
        }
    }

    private fun nextStep(){
        step++
        column=1
        grid[step][0] = Pair(word[0], 1)
        for (indice in 1 until word.length){
            grid[step][indice] = Pair('·', 0)
        }
    }

    private fun getCurrentWordInRow(): String {
        var currentWord = ""
        grid[step].forEach {
            currentWord+=it.first
        }
        return currentWord
    }

    private fun colorRightLetter() {
        val occurrences = getLetterOccurrences().toMutableMap()
        getCurrentWordInRow().forEachIndexed { index, letter ->
            if (letter == word[index]) {
                grid[step][index] = Pair(letter, 3)
                occurrences[letter] = occurrences[letter]!! - 1
            }
        }

        getCurrentWordInRow().forEachIndexed { index, letter ->
            if (grid[step][index].second!=3){ // Vérifie que la lettre n'est pas déjà rouge
                if (occurrences.containsKey(letter)){ // Vérifie que la lettre est dans le mots
                    if (occurrences[letter]!! > 0){
                        grid[step][index] = Pair(letter, 2)
                        occurrences[letter] = occurrences[letter]!! - 1
                    }
                }
            }
        }
    }

    private fun getLetterOccurrences(): Map<Char, Int> {
        return word.groupBy { it }.mapValues { it.value.size }
    }

    private fun removeWord(){
        for ( i in 1 until word.length){
            grid[step][i] = Pair(' ', 0)
        }
        grid[step][0] = Pair(word[0], 1)
        column=1
    }

}