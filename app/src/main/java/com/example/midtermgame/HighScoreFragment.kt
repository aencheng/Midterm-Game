package com.example.midtermgame

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermgame.databinding.FragmentHighScoreBinding

class HighScoreFragment : Fragment() {
    private lateinit var binding: FragmentHighScoreBinding
    private lateinit var viewModel: ScoresViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHighScoreBinding.inflate(inflater, container, false)
        val view = binding.root
        val recyclerView = binding.itemRecyclerView
        val emptyStateLayout = binding.emptyStateLayout

        // Initialize ViewModel
        val application = requireNotNull(this.activity).application
        val dao = ScoresDatabase.getInstance(application).scoresDao
        val viewModelFactory = ScoresViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[ScoresViewModel::class.java]

        // Initialize RecyclerView and Adapter
        val adapter = ItemAdapter { userId ->
            val dialog = ScoreDeleteDialogFragment(userId)
            dialog.show(childFragmentManager, ScoreDeleteDialogFragment.TAG)
        }

        binding.itemRecyclerView.adapter = adapter
        binding.itemRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        // Observe changes in the list of scores and update the adapter
        viewModel.scores.observe(viewLifecycleOwner) { scores ->
            adapter.submitList(scores)
            if (adapter.itemCount == 0) {
                recyclerView.visibility = View.GONE
                emptyStateLayout.visibility = View.VISIBLE
            }
            else {
                recyclerView.visibility = View.VISIBLE
                emptyStateLayout.visibility = View.GONE
            }
        }

        binding.backButton.setOnClickListener {
            val action = HighScoreFragmentDirections.actionHighScoreFragmentToMainFragment(
                Name = "default_name",
                Score = -1
            )
            view.findNavController().navigate(action)
        }

        return view
    }
}