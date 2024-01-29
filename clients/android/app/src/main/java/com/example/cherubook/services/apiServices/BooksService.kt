package com.example.cherubook.services.apiServices

import com.example.cherubook.constants.ApiConstants
import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.api.PaginationModel
import com.example.cherubook.models.books.Book
import com.example.cherubook.models.userBookRates.UserBookRate
import com.example.cherubook.services.retrofitServices.ApiClient
import com.example.cherubook.services.retrofitServices.RetrofitBooksService
import com.example.cherubook.utility.ExceptionHelpers

object BooksService {
    private var retrofitService =
        ApiClient.buildService(ApiConstants.webApiBaseUrl, RetrofitBooksService::class.java, true)

    suspend fun getBooks():ApiResponse<Array<Book>> {
        try {
            var response = retrofitService.getBooks();
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response);
            var booksResponse = response.body()!!;
            return booksResponse
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex);
        }
    }

    suspend fun searchBooks(pageNumber:Number, pageSize:Number,search: String?): ApiResponse<PaginationModel<Book>> {
        try {
            val response = retrofitService.searchBooks(pageNumber, pageSize, search)
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response)
            return response.body()!!
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex)
        }
    }


    suspend fun getBookById(id:String):ApiResponse<Book?> {
        try {
            var response = retrofitService.getBookById(id);
            if (!response.isSuccessful) return ExceptionHelpers.handleApiError(response);
            var bookResponse = response.body()!!;
            return bookResponse
        } catch (ex: Exception) {
            return ExceptionHelpers.handleException(ex);
        }
    }
}