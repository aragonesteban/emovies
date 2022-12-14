package com.movies.data.remote.api

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.movies.domain.MoviesResult
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

fun logging(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BASIC
}

@ExperimentalSerializationApi
fun buildRetrofit(baseUrl: String): Retrofit {
    val contentType = "application/json".toMediaType()
    val json = Json { ignoreUnknownKeys = true }
    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .client(provideHttpclient())
        .addConverterFactory(json.asConverterFactory(contentType))
        .build()
}

fun provideHttpclient(): OkHttpClient = OkHttpClient().newBuilder()
    .connectTimeout(1, TimeUnit.MINUTES)
    .readTimeout(1, TimeUnit.MINUTES)
    .writeTimeout(1, TimeUnit.MINUTES)
    .addInterceptor(logging())
    .build()

internal inline fun <T : Any> executeRetrofitRequest(block: () -> Response<T>): MoviesResult<T> {
    return try {
        val request = block.invoke()
        return if (request.isSuccessful && request.body() != null) {
            val body = request.body()
            if (body != null) {
                MoviesResult.Success(body)
            } else {
                MoviesResult.Error(request.message(), request.code())
            }
        } else {
            val errorBody = request.errorBody()
            val errorText = errorBody?.string() ?: "Error body null"
            MoviesResult.Error(errorText, request.code())
        }
    } catch (exception: UnknownHostException) {
        MoviesResult.Error(exception.toString())
    }
}

fun <Api : Any, Data : Any> handleResultRetrofit(
    result: MoviesResult<Api>,
    onSuccess: (Api) -> Data
): MoviesResult<Data> {
    return when (result) {
        is MoviesResult.Success -> MoviesResult.Success(onSuccess.invoke(result.data))
        is MoviesResult.Error -> result
    }
}