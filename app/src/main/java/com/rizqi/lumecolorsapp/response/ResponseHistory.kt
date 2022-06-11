package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MHistory
import java.io.Serializable

class ResponseHistory(

    val status : Int,
    val message : String,
    val data : ArrayList<MHistory>,

): Serializable


