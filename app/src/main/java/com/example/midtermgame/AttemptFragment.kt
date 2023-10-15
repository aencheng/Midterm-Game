package com.example.midtermgame

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider

class AttemptFragment : Fragment() {
    private lateinit var gameViewModel: GameViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_attempt, container, false)
        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        val attemptCount = view.findViewById<TextView>(R.id.AttemptCount)

        gameViewModel.attemptCount.observe(viewLifecycleOwner
        ) { newCount ->
            Log.i("New Test", newCount.toString())
            attemptCount.text = "Number of attempts: $newCount"
        }

        return view
    }
}