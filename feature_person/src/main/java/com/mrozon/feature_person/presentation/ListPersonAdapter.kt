package com.mrozon.feature_person.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrozon.core_api.entity.Person
import com.mrozon.feature_person.databinding.ItemPersonBinding

class ListPersonAdapter(private val clickListener: ListPersonClickListener): ListAdapter<Person, ListPersonAdapter.ViewHolder>(ListPersonDiffCallback()){

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder  = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ListPersonAdapter.ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemPersonBinding ): RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Person,
            clickListener: ListPersonClickListener) {
            binding.person = item
//            binding.listener = clickListener
            binding.cvPerson.setOnClickListener {
                clickListener.onClick(item)
            }
            binding.cvPerson.setOnLongClickListener{
                clickListener.onLongClick(item)
                true
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemPersonBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ListPersonDiffCallback : DiffUtil.ItemCallback<Person>() {

        override fun areItemsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Person, newItem: Person): Boolean {
            return oldItem == newItem
        }
    }

    interface ListPersonClickListener {
        fun onClick(person: Person)
        fun onLongClick(person: Person)
    }

}


