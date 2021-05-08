package com.codestomp.task.data.network

import com.codestomp.task.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDataSource {

    fun <Api> buildApi(
        api: Class<Api>,
        userToken:String?=null
    ): Api {
        return Retrofit.Builder().baseUrl(BuildConfig.BASE_URL).client(
            OkHttpClient.Builder()
                .addInterceptor{ chain ->  
                    chain.proceed(chain.request().newBuilder().also {
                        it.addHeader("Authorization","Bearer $userToken").
                                addHeader("x-api-key","f6ecf7af26a643f08783b2da85f77436")
                    }.build())
                }
                .also { client ->
                if (BuildConfig.DEBUG) {
                    val loggingInterceptor = HttpLoggingInterceptor()
                    loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
                    client.addInterceptor(loggingInterceptor)

                }
            }.build()).addConverterFactory(GsonConverterFactory.create()).build()
            .create(api)
    }
}