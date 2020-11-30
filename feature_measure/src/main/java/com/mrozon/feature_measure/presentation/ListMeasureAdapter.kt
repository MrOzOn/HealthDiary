package com.mrozon.feature_measure.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrozon.core_api.entity.Measure
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.core_api.entity.Person
import com.mrozon.feature_measure.databinding.ItemMeasureBinding

class ListMeasureAdapter(private val measureType: MeasureType, private val clickListener: ListMeasureClickListener): ListAdapter<Measure, ListMeasureAdapter.ViewHolder>(ListMeasureDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, measureType, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemMeasureBinding ): RecyclerView.ViewHolder(binding.root){
        fun bind(
            item: Measure,
            measureType: MeasureType,
            clickListener: ListMeasureClickListener) {
            binding.measure = item
            binding.measureType = measureType
            binding.layoutMeasure.setOnClickListener {
                clickListener.onClick(item)
            }
            binding.layoutMeasure.setOnLongClickListener{
                clickListener.onLongClick(item)
                true
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding =
                    ItemMeasureBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class ListMeasureDiffCallback : DiffUtil.ItemCallback<Measure>() {

        override fun areItemsTheSame(oldItem: Measure, newItem: Measure): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Measure, newItem: Measure): Boolean {
            return oldItem == newItem
        }
    }

    interface ListMeasureClickListener {
        fun onClick(measure: Measure)
        fun onLongClick(measure: Measure)
    }
}