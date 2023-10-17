package com.example.midtermgame

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import kotlinx.coroutines.launch

class GameScreenFragment : Fragment() {
    private lateinit var dao: ScoresDao
    private lateinit var gameViewModel: GameViewModel
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game_screen, container, false)

        // Get the application context and initialize the database access object (DAO)
        val application = requireNotNull(this.activity).application
        dao = ScoresDatabase.getInstance(application).scoresDao

        gameViewModel = ViewModelProvider(requireActivity())[GameViewModel::class.java]
        val ok = view.findViewById<Button>(R.id.OkButton)
        val nameEdit = view.findViewById<EditText>(R.id.NameEdit)
        val answerInput = view.findViewById<EditText>(R.id.AnswerInput)
        val minus = view.findViewById<ImageButton>(R.id.minusButton)
        val plus = view.findViewById<ImageButton>(R.id.plusButton)
        val randomNumber = (Math.random() * 100).toInt() + 1

        plus.setOnClickListener {
            if (answerInput.text.toString().isNotEmpty()) {
                val newValue = answerInput.text.toString().toInt() + 1
                answerInput.setText(newValue.toString())
            }
        }

        minus.setOnClickListener {
            if (answerInput.text.toString().isNotEmpty()) {
                val newValue = answerInput.text.toString().toInt() - 1
                answerInput.setText(newValue.toString())
            }
        }

        ok.setOnClickListener {
            if(answerInput.text.isNotEmpty() && answerInput.text.toString().toInt() != randomNumber){
                val mediaPlayer = MediaPlayer.create(context, R.raw.buzz)
                if(answerInput.text.toString().toInt() < randomNumber){
                    Toast.makeText(requireContext(), "Wrong | The Value Is Higher Than Your Guess", Toast.LENGTH_SHORT).show()
                }
                else if(answerInput.text.toString().toInt() > randomNumber) {
                    Toast.makeText(requireContext(), "Wrong | The Value Is Less Than Your Guess", Toast.LENGTH_SHORT).show()
                }
                mediaPlayer.start()
                gameViewModel.incrementAttemptCount()
            }
            else if(answerInput.text.isNotEmpty() && answerInput.text.toString().toInt() == randomNumber){
                gameViewModel.setName(nameEdit.text.toString())
                gameViewModel.incrementAttemptCount()

                //val name = gameViewModel.nameLiveData.value
                val name = when(gameViewModel.nameLiveData.value){
                    "" -> "John Doe"
                    else -> gameViewModel.nameLiveData.value
                }
                val attempts = gameViewModel.attemptCount.value

                // Save score to database
                lifecycleScope.launch { // Launch a coroutine
                    saveScoreToDatabase(name.toString(), attempts!!)
                }

                val action = GameFragmentDirections.actionGameFragmentToMainFragment2(
                    name!!,
                    attempts!!
                )
                view.findNavController().navigate(action)
                gameViewModel.setAttemptCountToZero()
            }
        }
        return view
    }

    private suspend fun saveScoreToDatabase(userName: String, userScore: Int) {
        dao.insert(Score(userName = userName, userScore = userScore))
    }

}