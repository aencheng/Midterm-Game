package com.example.midtermgame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val play = view.findViewById<Button>(R.id.PlayButton)
        val highScore = view.findViewById<Button>(R.id.ViewHighScoreButton)

        play.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToGameFragment()
            findNavController().navigate(action)
        }

        highScore.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToHighScoreFragment()
            findNavController().navigate(action)
        }

        return view
    }
}