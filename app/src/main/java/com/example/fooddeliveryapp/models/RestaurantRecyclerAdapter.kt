package com.example.fooddeliveryapp.models

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import kotlinx.android.synthetic.main.layout_restaurant_list_item.view.*

class RestaurantRecyclerAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<Restaurant> = ArrayList()
    private lateinit var onRestaurantItemClickLestener: OnRestaurantItemClickLestener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  RestaurantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant_list_item, parent, false), onRestaurantItemClickLestener
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){

            is RestaurantViewHolder ->{
                holder.bind(items.get(position))
            }
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun submitList(restaurantList: List<Restaurant>, onRestaurantClickListener: OnRestaurantItemClickLestener) {

        items = restaurantList
        onRestaurantItemClickLestener = onRestaurantClickListener
    }

    class RestaurantViewHolder constructor(
        itemView: View,
        onRestaurantItemClickLestener: OnRestaurantItemClickLestener
    ):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        val restaurantImage = itemView.restaurant_image
        val restaurantTitle = itemView.restaurant_name
        val restaurantName = itemView.about_restaurant
        val onRestaurantItemClickLestener = onRestaurantItemClickLestener


        fun bind (restaurantPost: Restaurant){

            itemView.setOnClickListener(this)

            restaurantTitle.setText(restaurantPost.name)
            restaurantName.setText(restaurantPost.about)

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(restaurantPost.image)
                .into(restaurantImage)
        }

        override fun onClick(v: View?) {

            onRestaurantItemClickLestener.onItemClick(adapterPosition)
        }

    }

     interface OnRestaurantItemClickLestener {
        fun onItemClick(position: Int)
    }

}