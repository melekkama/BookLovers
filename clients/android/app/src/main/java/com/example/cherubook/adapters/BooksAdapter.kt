package com.example.cherubook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cherubook.R
import com.example.cherubook.adapters.holders.BookHolder
import com.example.cherubook.adapters.holders.LoadingHolder
import com.example.cherubook.models.books.Book

class BooksAdapter(
    val books: ArrayList<Book>,
    private val itemClick: (Book) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1


    override fun getItemViewType(position: Int): Int {

        return if (books[position].id == "0") {
            VIEW_TYPE_LOADING
        } else {
            VIEW_TYPE_NORMAL
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        if (viewType == VIEW_TYPE_LOADING)
            return LoadingHolder(
                inflater.inflate(
                    R.layout.loading_item,
                    parent,
                    false
                )
            )
        return BookHolder(
            inflater.inflate(
                R.layout.book_item,
                parent,
                false
            )
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val book = books[position]
        if (holder is BookHolder) {
            holder.run { bindItem(book) }
            holder.itemView.setOnClickListener {
                itemClick(book)
            }
        }
    }

    override fun getItemCount(): Int {
        return books.size
    }


    fun addLoading() {
        val loading = Book.getDummyBook()
        books.add(loading)
        notifyItemChanged(books.size - 1)
    }

    fun removeLoading() {
        var position = books.size - 1
        if(position<0) return;
        val book=books[position]
        if(book.id!="0") return;
        books.removeAt(position)
        notifyDataSetChanged()
    }


    fun addBooks(items: ArrayList<Book>) {
        val old= books.size-1
        books.addAll(items)
        notifyItemChanged(old,items.size)
    }
}