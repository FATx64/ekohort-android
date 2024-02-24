package com.example.ekohort_android.presentation.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ekohort_android.R
import com.example.ekohort_android.domain.carousel.model.Carousel


class CarouselAdapter(private var carouselDataList: List<String>) : RecyclerView.Adapter<CarouselAdapter.CarouselItemViewHolder>() {

    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder =
            LayoutInflater.from(parent.context).inflate(R.layout.card_data_statistik, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val textView = holder.itemView.findViewById<TextView>(R.id.textview)
        textView.text = carouselDataList[position]
    }

    override fun getItemCount(): Int {
        return carouselDataList.size
    }

    fun submitData(data: Carousel) {
        this.carouselDataList = listOf(
            "Data ibu : ${data.ibu}",
            "Data anak : ${data.anak}",
            "Data nakes: ${data.nakes}"
        )
        notifyDataSetChanged()
    }
}