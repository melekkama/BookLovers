package com.example.cherubook.adapters.holders

import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cherubook.R
import com.example.cherubook.models.userBookRates.UserBookRate
import com.example.cherubook.models.userBookRates.UserBookRateLikeResponse
import com.example.cherubook.services.apiServices.UserBookRateService
import com.example.cherubook.utility.PrettyTimeHelper
import kotlinx.coroutines.launch


class UserRateHolder(view: View, viewLifecycleOwner: LifecycleOwner)  : RecyclerView.ViewHolder(view){
    private val context = view.context
    private val lifecycleOwner=viewLifecycleOwner
    private val userName=view.findViewById<TextView>(R.id.userbookrate_item_username)
    private val userRate=view.findViewById<RatingBar>(R.id.userbookrate_item_rating)
    private val createdTime = view.findViewById<TextView>(R.id.userbookrate_item_createdtime)
    private val bookName = view.findViewById<TextView>(R.id.userbookrate_item_bookname)
    private val bookImage = view.findViewById<ImageView>(R.id.userbookrate_item_bookimage)
    private val rateContent = view.findViewById<TextView>(R.id.userbookrate_item_content)
    private val likeButton=view.findViewById<ImageView>(R.id.userbookrate_item_btn_like)
    private val dislikeButton=view.findViewById<ImageView>(R.id.userbookrate_item_btn_dislike)
    private val likeCount=view.findViewById<TextView>(R.id.userbookrate_item_tv_like_count)
    private val dislikeCount=view.findViewById<TextView>(R.id.userbookrate_item_tv_dislike_count)

    private var isLiked:MutableLiveData<Boolean> = MutableLiveData(false)
    private var isDisliked:MutableLiveData<Boolean> = MutableLiveData(false)

    private var isLocked:Boolean=false
    fun bindItem(userBookRate:UserBookRate){
        userName.text=userBookRate.user?.userName
        userRate.rating=userBookRate.rate
        createdTime.text=PrettyTimeHelper.prettyTime.format(userBookRate.createdAt)
        bookName.text=userBookRate.book?.name
        rateContent.text=userBookRate.content
        likeCount.text=userBookRate.like.toString()
        dislikeCount.text=userBookRate.dislike.toString()
        if (userBookRate.book?.image!=null)
            Glide.with(context)
                .load(userBookRate.book?.image)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.baseline_image_24)
                .error(R.drawable.baseline_hide_image_24)
                .into(bookImage)

        if(userBookRate.isUserLiked==true)
            isLiked.value=true
        else if(userBookRate.isUserLiked==false)
            isDisliked.value=true

        isLiked.observe(lifecycleOwner){
            val image=if (it) R.drawable.icn_like_blue else R.drawable.icn_like_black
            likeButton.setImageResource(image)
        }

        isDisliked.observe(lifecycleOwner){
            val image=if (it) R.drawable.icn_dislike_red else R.drawable.icn_dislike_black
            dislikeButton.setImageResource(image)
        }

        likeButton.setOnClickListener{
            if(isLocked) return@setOnClickListener
            like(userBookRate)
        }

        dislikeButton.setOnClickListener{
            if(isLocked) return@setOnClickListener
           dislike(userBookRate)
        }
    }

    private fun like(userBookRate:UserBookRate){
        isLiked.value=!isLiked.value!!

        if(isLiked.value!! && isDisliked.value!!)
            isDisliked.value=false

        lifecycleOwner.lifecycleScope.launch{
            isLocked=true
            val response= UserBookRateService.likeUserBookRate(userBookRate.id,true)
            if(response.data!=null)
                likeCountRefresh(userBookRate,response.data!!)
            isLocked=false
        }
    }

    private fun dislike(userBookRate:UserBookRate){
        isDisliked.value=!isDisliked.value!!

        if(isLiked.value!! && isDisliked.value!!)
            isLiked.value=false

        lifecycleOwner.lifecycleScope.launch{
            isLocked=true
            val response= UserBookRateService.likeUserBookRate(userBookRate.id,false)
            if(response.data!=null)
                likeCountRefresh(userBookRate,response.data!!)
            isLocked=false
        }
    }
    /*
    isLike= true  isDislike= false isDeleted=false oldType=null  => likeCount+=  1  dislikeCount +=  0
    isLike= true  isDislike= false isDeleted=true  oldType=true  => likeCount+= -1  dislikeCount +=  0
    isLike= false isDislike= true  isDeleted=false oldType=null  => likeCount+=  0  dislikeCount +=  1
    isLike= false isDislike= true  isDeleted=true  oldType=false => likeCount+=  0  dislikeCount += -1
    isLike= false isDislike= true  isDeleted=false oldType=true  => likeCount+= -1  dislikeCount +=  1
    isLike= true  isDislike= false isDeleted=false oldType=false => likeCount+=  1  dislikeCount += -1
    * */
    private fun likeCountRefresh(userBookRate: UserBookRate,userBookRateLikeResponse: UserBookRateLikeResponse){
        when {
            userBookRateLikeResponse.isDeleted -> {
                if (userBookRateLikeResponse.oldType == true) userBookRate.like -= 1
                else if (userBookRateLikeResponse.oldType == false) userBookRate.dislike -= 1
            }
            userBookRateLikeResponse.oldType == null -> {
                if (userBookRateLikeResponse.isLike) userBookRate.like += 1
                else if (userBookRateLikeResponse.isDislike) userBookRate.dislike += 1
            }
            userBookRateLikeResponse.oldType == true && userBookRateLikeResponse.isDislike -> {
                userBookRate.like -= 1
                userBookRate.dislike += 1
            }
            userBookRateLikeResponse.oldType == false && userBookRateLikeResponse.isLike -> {
                userBookRate.like += 1
                userBookRate.dislike -= 1
            }
        }
        likeCount.text=userBookRate.like.toString()
        dislikeCount.text=userBookRate.dislike.toString()
    }

}