package com.example.tictactoeremaster


import android.content.Context
import android.widget.Toast

class GameLogic(private val grilleData: GrilleModel, private val grilleView: GrilleView, private val context: Context){

    interface onGameStatusListener{
        fun onGameFinished(isGameFinished: Boolean)
    }

    private lateinit var listener: onGameStatusListener

    var isGameFinished = false
        private set
    var isEvenMatch = false
        private set
    var turn = 0
    init {
        grilleView.setGameLogic(this)
    }


    fun tryPlaceMarker(element: Int, row: Int, col: Int): Boolean{
        return if (grilleData.setMarkerOnGrid(element, row, col)){
            turn = if(turn == 0) 1
            else 0
            checkGameStatus()
            true
        }else{
            checkGameStatus()
            false
        }
    }

    private fun checkGameStatus(){
            if(grilleData.isGridFull() && !grilleData.isWinner()){
                Toast.makeText(context,"Draw",Toast.LENGTH_SHORT).show()
                isGameFinished = true
                listener.onGameFinished(isGameFinished)
                isEvenMatch = true
            }
            if(grilleData.isWinner()){
                Toast.makeText(context,"Winner ${GrilleModel.markerName.values()[grilleData.winner]} , game finished",Toast.LENGTH_SHORT).show()
                isGameFinished = true
                listener.onGameFinished(isGameFinished)
                grilleView.setWinnerCoordinates(grilleData.winnerCoordinates)
            }
    }

    fun getGameBoard(): Array<IntArray>{
        return grilleData.gridData
    }

    fun resetGame(){
        grilleData.resetGridAndWinner()
        turn = 0
        isGameFinished = false
        isEvenMatch = false
    }

    fun setListener(listener: onGameStatusListener){
        this.listener = listener
    }


}