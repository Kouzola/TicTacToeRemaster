package com.example.tictactoeremaster

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tictactoeremaster.databinding.FragmentGameStartBinding


class FragmentGameStart : Fragment() {

    private var _binding: FragmentGameStartBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameStartBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonPlay.setOnClickListener{
            val action = FragmentGameStartDirections.actionFragmentGameStartToFragmentGrilleView()
            this.findNavController().navigate(action)
        }
    }


}