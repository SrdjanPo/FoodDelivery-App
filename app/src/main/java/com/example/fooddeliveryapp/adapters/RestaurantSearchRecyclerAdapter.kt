package com.example.fooddeliveryapp.adapters

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.RestaurantActivity
import com.example.fooddeliveryapp.models.Restaurant
import kotlinx.android.synthetic.main.layout_search_list_item.view.*

class RestaurantSearchRecyclerAdapter(val restaurants : ArrayList<Restaurant>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), Filterable {

    private  var restaurantListAll : ArrayList<Restaurant>
    private var restaurantList: ArrayList<Restaurant>?

    init {
        restaurantListAll = restaurants
        restaurantList = restaurants
    }

    private val filter = object : Filter() {
        override fun performFiltering(constraint: CharSequence): FilterResults? {

            val filteredList : ArrayList<Restaurant>? = null

            if(constraint.toString().isNullOrEmpty()) {

                filteredList!!.addAll(restaurantListAll)

            } else {

                for (restaurant in restaurantListAll!!) {

                    if(restaurant!!.ime.toLowerCase().contains(constraint.toString().toLowerCase())) {

                        filteredList!!.add(restaurant)
                    }
                }
            }

            var filterResults: FilterResults? = null
            filterResults!!.values = filteredList

            return filterResults
        }

        @Suppress("UNCHECKED_CAST")
        override fun publishResults(constraint: CharSequence, results: FilterResults?) {
            //restaurantList.clear()
            restaurantListAll = results?.values as ArrayList<Restaurant>
            notifyDataSetChanged()
        }
    }

    override fun getFilter(): Filter {
        return filter
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return RestaurantViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_search_list_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is RestaurantViewHolder ->{
                holder.bind(restaurants.get(position)!!)
                holder.restaurant = restaurants.get(position)
            }
        }

    }

    override fun getItemCount(): Int {
        return restaurants.count()
    }


    class RestaurantViewHolder( itemView: View, var restaurant: Restaurant? = null) : RecyclerView.ViewHolder(itemView) {

        val restaurantImage = itemView.search_restaurant_image
        val restaurantName = itemView.search_restaurant_name
        val restaurantAbout = itemView.search_restaurant_about
        val restaurantOrderPrice = itemView.search_restaurant_price

        fun bind (restaurantPost: Restaurant){


            restaurantName.setText(restaurantPost.ime)
            restaurantAbout.setText(restaurantPost.opis)
            restaurantOrderPrice.setText(restaurantPost.cijenaDostave.toString().plus(" дин • Delivery"))

            var restaurantId = restaurantPost.id.toString()

            var image_url = "http://s3.eu-central-1.amazonaws.com/donesi.projekat/restorani/".plus(restaurantId).plus(".jpg")


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