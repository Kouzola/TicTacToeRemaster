package com.example.tictactoeremaster

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentOnAttachListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import com.example.tictactoeremaster.databinding.ActivityMainBinding
import com.example.tictactoeremaster.model.GameViewModel


class MainActivity : AppCompatActivity(){

    private lateinit var navController: NavController
    private lateinit var navHostFragment: NavHostFragment
    private val sharedViewModel: GameViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        val buttonHome: Button = binding.buttonHome
        buttonHome.visibility = INVISIBLE
        buttonHome.setOnClickListener{
            val action = FragmentGrilleViewDirections.actionFragmentGrilleViewToFragmentGameStart()
            navController.navigate(action)
            buttonHome.visibility = INVISIBLE
        }
        sharedViewModel.isGameFinished.observe(this, Observer {
            if(it) buttonHome.visibility = VISIBLE
            else INVISIBLE
        })
    }


//TODO : Use viewModel but do tutorial before pour faire afficher bouton Home ou rejouer au dessous de l'écran dans la main activity
    //TODO : Idée mettre une variable en commun dans le view Model pour pouvoir afficher les bouton ici et une fois que t'appui sur les bouton ramener au home ou rejouer

}