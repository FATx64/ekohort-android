package com.example.ekohort_android.presentation.blog

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ekohort_android.Detail_Blog_Activity
import com.example.ekohort_android.R
import com.example.ekohort_android.domain.blog.model.BlogModel

class BlogAdapter (private val listBlog: ArrayList<BlogModel>): RecyclerView.Adapter<BlogAdapter.ListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_blog, parent, false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(listBlog[position])
    }

    override fun getItemCount(): Int = listBlog.size

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var imgPhoto: ImageView = itemView.findViewById(R.id.photoImageView)
        private var txTitle : TextView = itemView.findViewById(R.id.titleTextView)
        private var tvDescription: TextView = itemView.findViewById(R.id.descTextView)
       // private var tvDate: TextView = itemView.findViewById(R.id.date_TextView)

        fun bind(blogModel: BlogModel){
            Glide.with(itemView.context)
                .load(blogModel.photo)
                .circleCrop()
                .into(imgPhoto)
            txTitle.text = blogModel.title
            tvDescription.text = blogModel.description
           // tvDate.text = blogModel.date

            itemView.setOnClickListener{
                val intent = Intent(itemView.context, Detail_Blog_Activity::class.java)
                intent.putExtra("Blog", blogModel)
                itemView.context.startActivity(intent)
            }
        }
    }
}