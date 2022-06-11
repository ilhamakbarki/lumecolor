package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MCetakQR
import java.io.Serializable

class ResponseCetakQR (
    val status : Int,
    val message : String,
    val data : ArrayList<MCetakQR>
        ) : Serializable