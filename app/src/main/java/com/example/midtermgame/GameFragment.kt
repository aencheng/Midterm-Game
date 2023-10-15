package com.example.midtermgame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController

class GameFragment : Fragment() {
    private lateinit var gameViewModel: GameViewModel

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_game, container, false)
        gameViewModel = ViewModelProvider(this)[GameViewModel::class.java]
        val nameEdit = view.findViewById<EditText>(R.id.NameEdit)
        val ok = view.findViewById<Button>(R.id.OkButton)
        replaceFrag(GameScreenFragment())
        replaceFrag2(AttemptFragment())

        return view
    }

    private fun replaceFrag(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.childFragmentContainer, fragment)
        fragmentTransaction.commit()
    }
    private fun replaceFrag2(fragment: Fragment) {
        val fragmentTransaction = childFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.childFragmentContainer2, fragment)
        fragmentTransaction.commit()
    }
}