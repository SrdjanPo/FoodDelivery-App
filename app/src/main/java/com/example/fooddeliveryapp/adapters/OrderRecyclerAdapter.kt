package com.example.fooddeliveryapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.models.Food
import kotlinx.android.synthetic.main.layout_order_list_item.view.*

class OrderRecyclerAdapter(val orders: List<Food>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return OrderViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_order_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is OrderViewHolder ->{
                holder.bind(orders.get(position))
                holder.order = orders.get(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return orders.count()
    }


    class OrderViewHolder( itemView: View, var order: Food? = null) : RecyclerView.ViewHolder(itemView) {

        val foodName = itemView.orderItem_name
        val foodAbout = itemView.orderItem_about
        val foodPrice = itemView.orderItem_price

        fun bind (orderPost: Food){


            foodName.setText(orderPost.ime)
            foodAbout.setText(orderPost.opis)
            foodPrice.setText(orderPost.cijena.toString().plus(" дин"))
        }

    }

}