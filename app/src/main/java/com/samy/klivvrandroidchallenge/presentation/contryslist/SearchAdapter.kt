package com.samy.klivvrandroidchallenge.presentation.contryslist

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.samy.klivvrandroidchallenge.data.model.City
import com.samy.klivvrandroidchallenge.databinding.ItemCityBinding
import com.samy.mostafasamy.utils.Constants
import javax.inject.Inject

class SearchAdapter @Inject constructor() :
    ListAdapter<City, SearchAdapter.ViewHolder>(DiffCallback()) {
    class DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(
            oldItem: City, newItem: City,
        ): Boolean = newItem._id == oldItem._id

        override fun areContentsTheSame(
            oldItem: City, newItem: City,
        ): Boolean = newItem == oldItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, Type: Int): ViewHolder =
        ViewHolder(
            ItemCityBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: City) {

            binding.tile.text = "${data.name}, ${data._id}"
            binding.subtitle.text = "Lat: ${data.coord.lat}, Lon: ${data.coord.lon}"


            binding.root.setOnClickListener {
                onItemClickListener?.let { click ->
                    click(data)
                }
            }


        }

    }


    private var onItemClickListener: ((City) -> Unit)? = null

    fun setOnItemClickListener(listener: (City) -> Unit) {
        onItemClickListener = listener
    }

}
