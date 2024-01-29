package com.example.cherubook.adapters.holders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cherubook.R
import com.example.cherubook.models.books.Book

class BookHolder(view: View)  : RecyclerView.ViewHolder(view){
    private val context = view.context
    private val authorName=view.findViewById<TextView>(R.id.book_item_author_name)
    private val userRate=view.findViewById<RatingBar>(R.id.book_item_rating)
    private val categoryName = view.findViewById<TextView>(R.id.book_item_category_name)
    private val bookName = view.findViewById<TextView>(R.id.book_item_bookname)
    private val bookImage = view.findViewById<ImageView>(R.id.book_item_bookimage)
    private val content = view.findViewById<TextView>(R.id.book_item_content)
    private val isbn = view.findViewById<TextView>(R.id.book_item_isbn)
    private val publisherName = view.findViewById<TextView>(R.id.book_item_publisher)
    fun bindItem(book: Book){
        authorName.text=book.author?.fullName
        userRate.rating=book.rate
        categoryName.text=book.category?.name
        bookName.text=book.name
        content.text=book.description
        isbn.text=book.isbn
        publisherName.text=book.publisher?.name
        Glide.with(context)
            .load(book.image)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_hide_image_24)
            .into(bookImage)

    }
}