package com.example.tictactoeremaster

class GrilleModel {

    companion object {
        const val CROIX = 0
        const val ROND = 1
    }

    enum class markerName{
        CROIX,
        ROND
    }

    var winner = -1
        private set

    var winnerCoordinates = emptyList<Int>() //used to make the line when someone win(col1,row1,col2,row2)
        private set

    var gridData: Array<IntArray> = Array(3) {
        intArrayOf(-1, -1, -1)
    }
        private set


    //--------------Methods---------------------

    fun isWinner(): Boolean {
        winner = checkedGrid()
        return winner != -1
    }

    private fun checkedGrid(): Int {
        var tmp: Int
        for(i in 0..2){
            if (gridData[i][0] != -1 && gridData[i][0] == gridData[i][1] && gridData[i][1] == gridData[i][2]){ //Horizontal
                tmp = gridData[i][0]
                winnerCoordinates = listOf(0,i,2,i)
                return tmp
            }
            if (gridData[0][i] != -1 && gridData[0][i] == gridData[1][i] && gridData[1][i] == gridData[2][i]){  //Vertical
                tmp = gridData[0][i]
                winnerCoordinates = listOf(i,0,i,2)
                return tmp
            }
        }
        if (gridData[0][0] != -1 && gridData[0][0] == gridData[1][1] && gridData[1][1] == gridData[2][2]){   //Diagonal "\"
            tmp = gridData[0][0]
            winnerCoordinates = listOf(0,0,2,2)
            return tmp
        }
        if (gridData[0][2] != -1 && gridData[0][2] == gridData[1][1] && gridData[1][1] == gridData[2][0]){  //Diagonal "/"
            tmp = gridData[0][2]
            winnerCoordinates = listOf(0,2,2,0)
            return tmp
        }
        return -1
    }

    fun setMarkerOnGrid(marker: Int, row: Int, col: Int): Boolean {
        if(isCaseFree(row,col)) {
            gridData[row][col] = marker
            return true
        }
        return false
    }

    fun isGridFull(): Boolean{
        for(i in gridData.indices){
            if (gridData[i].contains(-1)) return false
        }
        return true
    }

    private fun isCaseFree(row: Int, col: Int): Boolean{
        return gridData[row][col] == -1
    }

    fun resetGridAndWinner(){
        gridData = Array(3) {
            intArrayOf(-1, -1, -1)
        }
        winner = -1
    }

}