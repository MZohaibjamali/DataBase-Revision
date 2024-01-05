package com.example.databaserevision

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class recyclerViewAdapterClass(
    var arrEmp: List<Model_of_DataBase_class>,
    val context: Context,
    val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<recyclerViewAdapterClass.ViewHolder23>() {

    class ViewHolder23(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var id: TextView = itemView.findViewById(R.id.idTextView)
        var name: TextView = itemView.findViewById(R.id.nameTextView)
        var email: TextView = itemView.findViewById(R.id.emailTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder23 {
        val view1 =
            LayoutInflater.from(parent.context).inflate(R.layout.layout_recyclerview, parent, false)
        return ViewHolder23(view1)
    }

    override fun getItemCount(): Int {
        return arrEmp.size
    }


    override fun onBindViewHolder(holder: ViewHolder23, position: Int) {
        val currentEmployee = arrEmp[position]
        holder.id.text = "Id: ${currentEmployee.id}"
        holder.name.text = "Name: ${currentEmployee.name}"
        holder.email.text = "Email: ${currentEmployee.email}"

        holder.itemView.setOnClickListener {

            onItemClickListener?.onItemClick(position, currentEmployee)

        }

    }
}
