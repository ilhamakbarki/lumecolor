package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MApprove
import java.io.Serializable

class ResponseApprove(

    val status : Int,
    val message : String,
    val data : ArrayList<MApprove>,

    ): Serializable


