package com.dscepointblank.pointblank.apis

import com.dscepointblank.pointblank.models.CodeForce_UserDetails
import retrofit2.http.GET
import retrofit2.http.Query

interface CodeForcesAPI
{

    @GET("api/user.info")
    suspend fun getUserDetails(@Query(value = "handles")codeForcesUserID:String) :CodeForce_UserDetails
}