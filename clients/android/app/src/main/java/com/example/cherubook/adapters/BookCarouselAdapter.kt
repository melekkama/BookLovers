package com.example.cherubook.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.transition.DrawableCrossFadeFactory
import com.example.cherubook.R
import com.example.cherubook.models.books.Book


class BookCarouselAdapter(
    private val books: Array<Book>,
    private val itemClick: (Book) -> Unit) :
    RecyclerView.Adapter<BookCarouselAdapter.CarouselItemViewHolder>() {
    class CarouselItemViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarouselItemViewHolder {
        val viewHolder = LayoutInflater.from(parent.context).inflate(R.layout.carousel_book_item, parent, false)
        return CarouselItemViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: CarouselItemViewHolder, position: Int) {
        val bookImage=holder.itemView.findViewById<ImageView>(R.id.img_book_item_bookimage)
        val bookName = holder.itemView.findViewById<TextView>(R.id.tv_book_item_book_name)

        val book = books[position]
        Glide.with(holder.itemView.context)
            .load(book.image)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_hide_image_24)
            .into(bookImage)

        bookName.text = book.name

        holder.itemView.setOnClickListener {
            itemClick(book)
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }
}