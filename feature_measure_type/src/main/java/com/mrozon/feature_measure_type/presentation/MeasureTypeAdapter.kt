package com.mrozon.feature_measure_type.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mrozon.core_api.entity.MeasureType
import com.mrozon.feature_measure_type.databinding.ItemMeasureTypeBinding

class MeasureTypeAdapter(private val clickListener: TypeMeasureClickListener):
    ListAdapter<MeasureType, MeasureTypeAdapter.ViewHolder>(TypeMeasureDiffCallback()) {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder = ViewHolder.from(parent)

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, clickListener)
    }

    class ViewHolder private constructor(val binding: ItemMeasureTypeBinding): RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: MeasureType,
            clickListener: TypeMeasureClickListener) {
            binding.measureType = item
            binding.cvMeasureType.setOnClickListener {
                clickListener.onClick(item)
            }
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemMeasureTypeBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class TypeMeasureDiffCallback : DiffUtil.ItemCallback<MeasureType>() {
        override fun areItemsTheSame(oldItem: MeasureType, newItem: MeasureType): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MeasureType, newItem: MeasureType): Boolean {
            return oldItem == newItem
        }
    }

    interface TypeMeasureClickListener {
        fun onClick(measureType: MeasureType)
    }

}