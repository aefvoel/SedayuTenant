package id.toriqwah.project.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import id.toriqwah.project.R
import id.toriqwah.project.helper.UtilityHelper
import id.toriqwah.project.model.List
import id.toriqwah.project.model.Menu
import id.toriqwah.project.model.Order
import id.toriqwah.project.model.Tenant

class TenantAdapter(context : Context, list: ArrayList<Order>, private val listener: Listener)
    : RecyclerView.Adapter<TenantAdapter.TenantViewHolder>() {

    private val contexts = context
    private val itemList = list

    interface Listener{
        fun onItemClicked(data: Order)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TenantViewHolder {
        val view = LayoutInflater.from(contexts).inflate(R.layout.item_tenant, parent,false)
        return TenantViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: TenantViewHolder, position: Int) {

        holder.id.text = "#${itemList[position].id}"
        holder.by.text = itemList[position].by
        holder.itemView.setOnClickListener {
            listener.onItemClicked(itemList[position])
        }
    }

    inner class TenantViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val id: TextView = itemView.findViewById(R.id.id)
        val by: TextView = itemView.findViewById(R.id.by)
    }

}