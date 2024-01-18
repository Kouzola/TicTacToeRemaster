package com.example.tictactoeremaster.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    private val _isGameFinished = MutableLiveData<Boolean>(false)
    val isGameFinished: LiveData<Boolean> = _isGameFinished

    fun setGameStatus(statut: Boolean){
        _isGameFinished.value = statut
    }

}