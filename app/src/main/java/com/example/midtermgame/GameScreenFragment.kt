package com.example.midtermgame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class GameScreenFragment : Fragment() {

    private lateinit var gameViewModel: GameViewModel
    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_screen, container, false)
        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        val ok = view.findViewById<Button>(R.id.OkButton)
        val nameEdit = view.findViewById<EditText>(R.id.NameEdit)
        val answerInput = view.findViewById<EditText>(R.id.AnswerInput)
        val randomNumber = (Math.random() * 5).toInt() + 1

        ok.setOnClickListener {
            if(answerInput.text.toString().toInt() != randomNumber){
                Toast.makeText(requireContext(), "Wrong", Toast.LENGTH_SHORT).show()
                gameViewModel.incrementAttemptCount()
            }
            else{
                gameViewModel.setName(nameEdit.text.toString())
                gameViewModel.incrementAttemptCount()

                val name = gameViewModel.nameLiveData.value
                val attempts = gameViewModel.attemptCount.value

                val action = GameFragmentDirections.actionGameFragmentToMainFragment2(name?: "John Doe",
                    attempts!!
                )
                view.findNavController().navigate(action)
                gameViewModel.setAttemptCountToZero()
            }
        }
        return view
    }
}