package com.techfort.planetapi.ui.users

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.techfort.planetapi.R
import com.techfort.planetapi.remot.model.UserResponseItem

class UserAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    interface Listener{
        fun onClick(view:View, item : UserResponseItem)
    }
    val context: Context
    var userList: ArrayList<UserResponseItem>
    val clickListener : Listener
    constructor(context: Context, listener : Listener) {
        this.context = context
        userList = ArrayList()
        this.clickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.user_item, parent, false)
        return UserHolder(view)
    }

    fun addItem(list: List<UserResponseItem>) {
        userList.clear()
        userList.addAll(list)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val user = userList[position]
        val itemHolder = holder as UserHolder
        itemHolder.nameView.text = user.first_name + " (" + user.id.toString() + ")"
        itemHolder.nameView.setOnClickListener {
          clickListener.onClick(it, user)
        }

        itemHolder.delButton.setOnClickListener {
            clickListener.onClick(it, user)
        }
    }

    inner class UserHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameView: TextView
        val delButton : Button
        init {
            nameView = itemView.findViewById(R.id.tv_name)
            delButton = itemView.findViewById(R.id.btn_delete)
        }
    }
}