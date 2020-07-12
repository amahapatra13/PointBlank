package com.dscepointblank.pointblank.models

import com.google.gson.annotations.SerializedName

data class CodeForce_UserDetails(

    @SerializedName("status")
    val status:String,
    @SerializedName("result")
    val details : List<necessaryDetails>
)
{
    inner class necessaryDetails(

        @SerializedName("lastName")
        val lastName :String,

        @SerializedName("firstName")
        val firstName : String,

        @SerializedName("rating")
        val rating:Int,

        @SerializedName("rank")
        val rank:String
    )

}