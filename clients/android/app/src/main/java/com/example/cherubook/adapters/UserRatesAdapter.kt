package com.example.cherubook.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.cherubook.R
import com.example.cherubook.adapters.holders.LoadingHolder
import com.example.cherubook.adapters.holders.UserRateHolder
import com.example.cherubook.models.userBookRates.UserBookRate

class UserRatesAdapter(
    val userBookRates: ArrayList<UserBookRate>,
    private val viewLifecycleOwner: LifecycleOwner,
    private val itemClick: (UserBookRate) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val VIEW_TYPE_LOADING = 0
    private val VIEW_TYPE_NORMAL = 1


    override fun getItemViewType(position: Int): Int {

        return if (userBookRates[position].id == "0") {
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
        return UserRateHolder(
            inflater.inflate(
                R.layout.userbookrate_item,
                parent,
                false
            ),
            viewLifecycleOwner
        )

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val post = userBookRates[position]
        if (holder is UserRateHolder) {
            holder.run { bindItem(post) }
            holder.itemView.setOnClickListener {
                itemClick(post)
            }
        }
    }

    override fun getItemCount(): Int {
        return userBookRates.size
    }


    fun addLoading() {
        val loading = UserBookRate.getDummyUserBookRate()
        userBookRates.add(loading)
        notifyItemChanged(userBookRates.size - 1)
    }

    fun removeLoading() {
        var position = userBookRates.size - 1
        if(position<0) return;
        val userBookRate=userBookRates[position]
        if(userBookRate.id!="0") return;
        userBookRates.removeAt(position)
        notifyDataSetChanged()
    }


    fun addUserRates(items: ArrayList<UserBookRate>) {
        val old= userBookRates.size-1
        userBookRates.addAll(items)
        notifyItemChanged(old,items.size)
    }
}