package com.example.motus

class Motus {
    private var end :Boolean = false
    private var step : Int = 0
    private var column : Int = 1
    private var word : String = "ABRICOT"
    private var grid : MutableList<MutableList<Pair<Char, Int>>> = MutableList(6) { MutableList(word.length) { " ".toCharArray()[0] to 0 } }

    init {
        grid[0][0] = Pair(word[0], 1)
    }
    fun getGrid() : MutableList<MutableList<Pair<Char, Int>>> { return grid }
    fun getWord(): String { return word }
    private fun isEnd(): Boolean {
        if (!end){
            for (i in word.indices){
                if(grid[step][i].first != word[i]){
                    return false
                }
            }
            end=true
        }
        return end
    }

    fun addLetter(letter : Char){
        if (column < word.length && !isEnd()){
            grid[step][column] = Pair(letter, 1)
            column++
        }
    }

    fun removeLetter(){
        if (column > 1 && !isEnd()){
            column--
            val letter = " ".toCharArray()[0]
            grid[step][column] = Pair(letter, 0)
        }
    }

    fun checkWord() {
        if (column == word.length){
            if (step<word.length-1){
                checkRightLetter()
                if (!isEnd()){
                    step++
                    column=1
                    grid[step][0] = Pair(word[0], 1)
                }
            }
            else{
                this.end = true
            }
        }
    }

    private fun checkRightLetter() {
        val occurrences = getLetterOccurrences()

        for (i in word.indices){
            if (grid[step][i].first == word[i]){ // letter in right position
                grid[step][i] = Pair(grid[step][i].first, 3)
                occurrences[grid[step][i].first] = occurrences[grid[step][i].first]!! - 1
            }
        }
        for( i in word.indices){
            if (occurrences.containsKey(grid[step][i].first)){
                if (occurrences[grid[step][i].first]!! > 0){
                    grid[step][i] = Pair(grid[step][i].first, 2)
                    occurrences[grid[step][i].first] = occurrences[grid[step][i].first]!! - 1
                }
            }
        }
    }


    private fun getLetterOccurrences(): MutableMap<Char, Int> {
        val occurrences: MutableMap<Char, Int> = mutableMapOf()
        for (letter in word) {
            if (occurrences.containsKey(letter)) {
                occurrences[letter] = occurrences[letter]!! + 1
            } else {
                occurrences[letter] = 1
            }
        }
        return occurrences
    }


    fun removeWord(){
        for (i in 1 until word.length){
            grid[step][i] = Pair(" ".toCharArray()[0], 0)
        }
    }
}