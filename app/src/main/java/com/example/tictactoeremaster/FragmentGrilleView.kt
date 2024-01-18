package com.example.tictactoeremaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.fragment.app.activityViewModels
import com.example.tictactoeremaster.databinding.FragmentGrilleView2Binding
import com.example.tictactoeremaster.model.GameViewModel

class FragmentGrilleView : Fragment(), GameLogic.onGameStatusListener {

    private var _binding: FragmentGrilleView2Binding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGrilleView2Binding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val grilleModel = GrilleModel()
        val grilleView: GrilleView = binding.grilleView
        val gameLogic = GameLogic(grilleModel,grilleView,this.requireContext())
        gameLogic.setListener(this)

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun updateGameStatus(statut: Boolean){
        sharedViewModel.setGameStatus(statut)
    }

    override fun onGameFinished(isGameFinished: Boolean) {
        updateGameStatus(isGameFinished)
    }


}