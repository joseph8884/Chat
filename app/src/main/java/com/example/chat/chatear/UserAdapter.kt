package com.example.chat.chatear


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.chat.R

class UserAdapter(val context: Context, val userList: ArrayList<User>):
    RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_layout, parent,false)
        return UserViewHolder(view)
    }
    override fun getItemCount(): Int {
        return userList.size
    }
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.textName.text = currentUser.name
        if (currentUser.foto_perfil!=""){
            Glide.with(context).load(currentUser.foto_perfil).into(holder.foto_perfil);
            //binding.textViewInformacion.text=arrayList[position].Correo
        }
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ChatActivity::class.java)
            intent.putExtra("name",currentUser.name)
            intent.putExtra("uid",currentUser.uid)
//intent.putExtra("uid",FirebaseAuth.getInstance().currentUser?.uid)
            context.startActivity(intent)
        }
    }
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textName = itemView.findViewById<TextView>(R.id.txt_name)
        val foto_perfil = itemView.findViewById<ImageView>(R.id.img_profile)
    }
}