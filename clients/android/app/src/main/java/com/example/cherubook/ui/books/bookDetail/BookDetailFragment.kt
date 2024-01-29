package com.example.cherubook.ui.books.bookDetail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cherubook.R
import com.example.cherubook.databinding.FragmentBookDetailBinding
import com.example.cherubook.ui.main.MainActivity
import com.example.cherubook.utility.MainApplication
import java.text.SimpleDateFormat
import java.util.*


class BookDetailFragment : Fragment(R.layout.fragment_book_detail)   {

    private lateinit var viewModel: BookDetailViewModel
    private lateinit var binding : FragmentBookDetailBinding

    private val calendar = Calendar.getInstance()
    private var selectedDateTime:Date?=null
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[BookDetailViewModel::class.java]
        binding= FragmentBookDetailBinding.bind(view)
        MainActivity.setLoadingStatus(viewModel, viewLifecycleOwner)
        MainActivity.setErrorStatus(viewModel, viewLifecycleOwner)

        viewModel.fetchUserCollections()

        var bookData= BookDetailFragmentArgs.fromBundle(requireArguments()).bookData


        Glide.with(this)
            .load(bookData.image)
            .fitCenter()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .placeholder(R.drawable.baseline_image_24)
            .error(R.drawable.baseline_hide_image_24)
            .into(binding.bookDetailBookimage)

        if(bookData.author?.photo!=null)
            Glide.with(this)
                .load(bookData.author?.photo)
                .fitCenter()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.drawable.user_icon)
                .error(R.drawable.user_icon)
                .into(binding.bookDetailAuthorImage)

        binding.bookDetailName.text=bookData.name
        binding.bookDetailAuthorFullname.text=bookData.author?.fullName
        binding.bookDetailPage.text=bookData.page.toString()
        binding.bookDetailIsbn.text=bookData.isbn
        binding.bookDetailCategory.text=bookData.category?.name
        binding.bookDetailPublisher.text=bookData.publisher?.name
        binding.bookDetailRate.text=bookData.rate.toString()
        binding.bookDetailDescription.text=bookData.description



        binding.bookDetailAddReadCollaction.setOnClickListener {
            showDatePicker()
        }

        binding.bookDetailAddCollection.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Choose an collection")

            val collections = viewModel.userCollections.value?.map { it.name }?.toTypedArray()
            val checkedItem = 0
            builder.setSingleChoiceItems(collections, checkedItem) { dialog, which ->
            }


            builder.setPositiveButton("OK") { dialog, which ->
            }
            builder.setNegativeButton("Cancel", null)

            val dialog = builder.create()
            dialog.show()
        }
    }
    private fun showDatePicker() {
        val datePickerDialog = this.context?.let {
            DatePickerDialog(
                it, { _, year: Int, monthOfYear: Int, dayOfMonth: Int ->
                    var selectedDate = Calendar.getInstance()
                    selectedDate.set(year, monthOfYear, dayOfMonth)
                    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                    val formattedDate = dateFormat.format(selectedDate.time)
                    selectedDateTime=Date(formattedDate)
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
        }
        datePickerDialog?.show()
    }
}