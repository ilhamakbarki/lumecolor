package com.rizqi.lumecolorsapp.response

import com.rizqi.lumecolorsapp.model.MUser
import java.io.Serializable

class ResponseLogin(

     val status : Int,
     val message : String,
     val data : ArrayList<MUser>,
     val periode : String,
     val level : String,
     val id_gudang : String,
     val nama_gudang : String,
//     val id_user: String,


): Serializable


