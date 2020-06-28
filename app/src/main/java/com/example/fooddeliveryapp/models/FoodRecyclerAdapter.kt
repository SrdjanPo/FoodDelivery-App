package com.example.fooddeliveryapp.models

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.FoodActivity
import com.example.fooddeliveryapp.R
import kotlinx.android.synthetic.main.layout_food_list_item.view.*



class FoodRecyclerAdapter (val foods : List<Food>, val mCallback : AddOrderListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private var indexOfColoredItem = -1

    //private lateinit var mCallback: AddOrderListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return  FoodViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.layout_food_list_item, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when(holder){

            is FoodViewHolder ->{
                holder.bind(foods.get(position), position)
                holder.food = foods.get(position)
                holder.itemView.setOnClickListener {

                    indeks = position
                    notifyDataSetChanged()
                    holder.internalCounter++
                    clickCounter++

                }

                holder.itemView.order_button.setOnClickListener {

                    orderList.add(foods.get(position))
                    mCallback.onOrderAdded(orderList)
                }

            }
        }

    }

    override fun getItemCount(): Int {
        return foods.count()
    }

    class FoodViewHolder(itemView: View, var food: Food? = null) : RecyclerView.ViewHolder(itemView) {

        // layouts
        val basicFoodLayout = itemView.basicFoodLayout
        val extendedFoodLayout = itemView.extendedFoodLayout

        // basicFoodLayout items
        val foodImage = itemView.food_image
        val foodName = itemView.food_name
        val foodContent = itemView.food_content
        val foodPrice = itemView.food_price

        // extendedFoodLayout items

        val extendedFoodImage = itemView.extendedFood_image
        val extendedFoodName = itemView.extendedFood_name
        val extendedFoodContent = itemView.extendedFood_about
        val extendedFoodPrice = itemView.extendedFood_price
        val orderButton = itemView.order_button


        var internalCounter = 1

        //var orderList = arrayListOf<Food>()

        fun bind (foodPost: Food, pos: Int){

            if(indeks == pos && internalCounter%2 == 0) {

                basicFoodLayout.animate().alpha(0.0f).setDuration(300)
                extendedFoodLayout.animate().alpha(1.0f).setDuration(500)

                basicFoodLayout.visibility = View.GONE
                extendedFoodLayout.visibility = View.VISIBLE



            } else {

                internalCounter = 1

                basicFoodLayout.animate().alpha(1.0f).setDuration(500)
                extendedFoodLayout.animate().alpha(0.0f).setDuration(500)

                basicFoodLayout.visibility = View.VISIBLE
                extendedFoodLayout.visibility = View.GONE

            }

            foodName.setText(foodPost.ime)
            extendedFoodName.setText(foodPost.ime)
            foodContent.setText(foodPost.sastav)
            extendedFoodContent.setText(foodPost.sastav)
            foodPrice.setText(foodPost.cijena.toString().plus(" дин"))
            extendedFoodPrice.setText(foodPost.cijena.toString().plus(" дин"))


            var foodId = foodPost.id.toString()

            //var image_url = "https://s3.eu-central-1.amazonaws.com/donesi.projekat/jelo/".plus(foodId).plus(".png")

            //https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80

            var image_url = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80"

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(image_url)
                .into(foodImage)

            Glide.with(itemView.context)
                .applyDefaultRequestOptions(requestOptions)
                .load(image_url)
                .into(extendedFoodImage)

            orderButton.setOnClickListener {

                orderList.add(foodPost)
                Log.d("BIND KORPA", orderList.toString())
            }

        }


        /*init {
            itemView.setOnClickListener {
                *//*val intent = Intent(itemView.context, FoodActivity::class.java)
                intent.putExtra(FOOD, food)
                itemView.context.startActivity(intent)*//*
            }
        }*/

    }

    companion object {
        const val FOOD = "food"
        var indeks: Int = -1
        var clickCounter: Int = 1
        var orderList = arrayListOf<Food>()
    }

    interface AddOrderListener {
        fun onOrderAdded(orders: ArrayList<Food>)
    }

}