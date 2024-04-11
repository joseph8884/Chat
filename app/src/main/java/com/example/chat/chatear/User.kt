package com.example.chat.chatear

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class User  {
    var name: String? = null
    var email: String? = null
    var uid: String? = null
    var foto_perfil: String? = null

    constructor(){}

    constructor(name: String?, email: String?, uid: String?, foto_perfil: String?){
        this.name = name
        this.email = email
        this.foto_perfil = foto_perfil
        this.uid = uid
    }

}