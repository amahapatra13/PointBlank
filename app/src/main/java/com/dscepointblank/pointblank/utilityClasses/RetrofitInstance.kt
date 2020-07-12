package com.dscepointblank.pointblank.utilityClasses

import com.dscepointblank.pointblank.apis.CodeForcesAPI
import com.dscepointblank.pointblank.notifications.NotificationAPI
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance
{
    companion object {
        lateinit var URL : String
        private val retrofit by lazy {
            Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }

        val api: NotificationAPI by lazy {
            URL = Constants.BASE_URL
            retrofit.create(
                NotificationAPI::class.java)
        }

        val codeForcesAPI: CodeForcesAPI by lazy {
            URL = Constants.CODE_FORCES_BASE_URL
            retrofit.create(CodeForcesAPI::class.java)
        }
    }
}