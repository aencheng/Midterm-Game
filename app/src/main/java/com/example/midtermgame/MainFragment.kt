package com.example.midtermgame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

class MainFragment : Fragment() {
    private var param1: String? = null
    private var param2: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString("Name")
            param2 = it.getInt("Score")
        }
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_main, container, false)
        val text = view.findViewById<TextView>(R.id.HomeText)
        val play = view.findViewById<Button>(R.id.PlayButton)
        val highScore = view.findViewById<Button>(R.id.ViewHighScoreButton)

        if(param1 != null && param2 != null){
            text.text = "$param1 score: $param2\n\nPlay Another Game?"

        }

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