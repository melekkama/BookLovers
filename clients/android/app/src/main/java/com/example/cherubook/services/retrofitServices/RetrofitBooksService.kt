package com.example.cherubook.services.retrofitServices

import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.api.PaginationModel
import com.example.cherubook.models.books.Book
import com.example.cherubook.models.userBookRates.UserBookRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface RetrofitBooksService {
    @GET("api/books")
    suspend fun getBooks(): Response<ApiResponse<Array<Book>>>

    @GET("api/books/search")
    suspend fun searchBooks(
        @Header("PageNumber") pageNumber:Number,
        @Header("PageSize") pageSize:Number,
        @Header("search") search:String?): Response<ApiResponse<PaginationModel<Book>>>

    @GET("api/books/{id}")
    suspend fun getBookById(@Path("id") id:String): Response<ApiResponse<Book?>>
}