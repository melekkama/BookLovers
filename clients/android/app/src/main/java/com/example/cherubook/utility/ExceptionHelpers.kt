package com.example.cherubook.utility

import android.app.AlertDialog
import android.widget.Toast
import com.example.cherubook.R
import com.example.cherubook.exceptions.OfflineException
import com.example.cherubook.models.api.ApiResponse
import com.example.cherubook.models.api.Error
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import retrofit2.Response

object ExceptionHelpers {
    val resources = MainApplication.applicationContext().resources

    inline fun <reified T> handleApiError(response: Response<ApiResponse<T>>): ApiResponse<T> {
        val apiResponse: ApiResponse<T>? = response.errorBody()?.string()?.let { errorBody ->
            val type = object : TypeToken<ApiResponse<T>>() {}.type
            Gson().fromJson(errorBody, type)
        }

        return apiResponse ?: ApiResponse(
            null,
            response.code(),
            false,
            Error(
                arrayListOf(resources.getString(R.string.ex_common_error)),
                true,
                "handleApiError"
            )
        )
    }

    fun <T> handleException(ex: Exception): ApiResponse<T> {
        val errorMessage: String = when (ex) {
            is OfflineException -> resources.getString(R.string.ex_offline_exception)
            else -> resources.getString(R.string.ex_common_error)
        }

        return ApiResponse(
            null,
            500,
            false,
            Error(
                arrayListOf(errorMessage),
                true,
                "handleException"
            )
        )
    }

    fun showErrorMessageByToast(error: Error?) {
            error?.let {
            val errorBuilder = StringBuilder()
            for (errorMsg in it.errors) {
                errorBuilder.append("$errorMsg\n")
            }

            if (errorBuilder.endsWith("\n")) {
                errorBuilder.delete(errorBuilder.length - 1, errorBuilder.length)
            }

            val messageToShow = if (errorBuilder.isNotEmpty()) {
                errorBuilder.toString()
            } else {
                resources.getString(R.string.ex_common_error)
            }

            Toast.makeText(
                MainApplication.applicationContext(),
                messageToShow,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun showErrorMessageByAlertDialog(error: Error?,ctx:android.content.Context) {
        error?.let {
            val errorBuilder = StringBuilder()
            for (errorMsg in it.errors) {
                errorBuilder.append("$errorMsg\n")
            }

            if (errorBuilder.endsWith("\n")) {
                errorBuilder.delete(errorBuilder.length - 1, errorBuilder.length)
            }

            val messageToShow = if (errorBuilder.isNotEmpty()) {
                errorBuilder.toString()
            } else {
                MainApplication.applicationContext().getString(R.string.ex_common_error)
            }

            val builder = AlertDialog.Builder(ctx)
            builder.setTitle(R.string.error_title)
            builder.setMessage(messageToShow)
            builder.setPositiveButton(android.R.string.ok) { dialog, _ ->
                dialog.dismiss()
            }

            val alertDialog = builder.create()
            alertDialog.show()
        }
    }
}