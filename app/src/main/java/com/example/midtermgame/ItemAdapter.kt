package com.example.midtermgame

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.midtermgame.databinding.ScoreItemBinding


class ScoreDiffItemCallback : DiffUtil.ItemCallback<Score>() {
    // This class extends DiffUtil.ItemCallback<Note>, which is used by the ListAdapter
    // to determine whether two items represent the same object and whether their contents are the same.

    // Checks if two items have the same ID. If they do, they are considered the same item.
    override fun areItemsTheSame(oldItem: Score, newItem: Score)
            = (oldItem.userId == newItem.userId)

    // Checks if the contents of two items are the same.
    // This comparison is used to check if the item's content has changed.
    override fun areContentsTheSame(oldItem: Score, newItem: Score) = (oldItem == newItem)
}

class ItemAdapter(val onDeleteClicked: (noteId: Long) -> Unit)
    : ListAdapter<Score, ItemAdapter.ScoreItemViewHolder>(ScoreDiffItemCallback()) {

    // Creates and returns a new NoteItemViewHolder for the RecyclerView.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)
            : ScoreItemViewHolder = ScoreItemViewHolder.inflateFrom(parent)

    // Called by RecyclerView to display the data at the specified position.
    override fun onBindViewHolder(holder: ScoreItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onDeleteClicked)
        // Binds the data to the ViewHolder.
    }

    // ViewHolder class for individual note items.
    class ScoreItemViewHolder(val binding: ScoreItemBinding)
        : RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun inflateFrom(parent: ViewGroup): ScoreItemViewHolder {
                // Inflates the layout and creates a ViewHolder from it.
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ScoreItemBinding.inflate(layoutInflater, parent, false)
                return ScoreItemViewHolder(binding)
            }
        }

        // Binds data to the ViewHolder.
        fun bind(item: Score, onDeleteClicked: (userId: Long) -> Unit) {
            binding.score = item
            // Sets an OnClickListener for the delete button.
            binding.deleteButton.setOnClickListener { onDeleteClicked(item.userId) }
        }
    }
}