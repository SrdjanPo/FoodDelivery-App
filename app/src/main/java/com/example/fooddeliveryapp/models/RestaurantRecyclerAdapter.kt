package com.example.fooddeliveryapp.models

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.RestaurantActivity
import kotlinx.android.synthetic.main.layout_restaurant_list_item.view.*

class RestaurantRecyclerAdapter(val restaurants : List<Restaurant>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  RestaurantViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_restaurant_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is RestaurantViewHolder ->{
                holder.bind(restaurants.get(position))
                holder.restaurant = restaurants.get(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return restaurants.count()
    }

    /*fun submitList(restaurantList: List<Restaurant>, onRestaurantClickListener: OnRestaurantItemClickListener) {

        items = restaurantList
        onRestaurantItemClickListener = onRestaurantClickListener

        Log.d("RECYCLER", items.toString())
    }*/

    class RestaurantViewHolder( itemView: View, var restaurant: Restaurant? = null) : RecyclerView.ViewHolder(itemView) {

        val restaurantImage = itemView.restaurant_image
        val restaurantTitle = itemView.restaurant_name
        val restaurantName = itemView.about_restaurant

        fun bind (restaurantPost: Restaurant){


            restaurantTitle.setText(restaurantPost.ime)
            restaurantName.setText(restaurantPost.opis)

            var restaurantId = restaurantPost.id.toString()

            var image_url = "https://s3.eu-central-1.amazonaws.com/donesi.projekat/restorani/".plus(restaurantId).plus(".jpg")


            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(image_url)
                .into(restaurantImage)
        }

        init {
            itemView.setOnClickListener {
                val intent = Intent(itemView.context, RestaurantActivity::class.java)
                intent.putExtra(RESTAURANT, restaurant)
                itemView.context.startActivity(intent)
            }
        }

    }

    companion object {
        const val RESTAURANT = "restaurant"
        lateinit var restaurantActivity: RestaurantActivity

    }


    interface OnRestaurantItemClickListener {
        fun onItemClick(position: Int)
    }

}