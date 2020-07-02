package com.example.fooddeliveryapp.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.models.Food
import com.example.fooddeliveryapp.models.NarudzbaBean
import com.example.fooddeliveryapp.models.Order
import com.example.fooddeliveryapp.models.Stavka
import kotlinx.android.synthetic.main.layout_food_list_item.view.*



class FoodRecyclerAdapter (val foods : List<Food>, val mCallback : AddOrderListener) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    //private var indexOfColoredItem = -1

    //private lateinit var mCallback: AddOrderListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return FoodViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_food_list_item,
                parent,
                false
            )
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

                    Log.d("jelooo", foods.get(position).toString())

                }

                holder.itemView.order_button.setOnClickListener {

                    var stavka = Stavka(foods.get(position), NarudzbaBean(0),1)
                    orderListFull.add(stavka)
                    Log.d("BIND KORPA", orderListFull.toString())
                    mCallback.onOrderAdded(orderListFull)
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

            Log.d("indeks i internal", indeks.toString().plus( " : ").plus(internalCounter.toString()))
            //Log.d("indeks i pos", indeks.toString().plus( " : ").plus(pos.toString()))

            if(indeks == pos && internalCounter%2 == 0) {

                basicFoodLayout.animate().alpha(0.0f).setDuration(300)
                extendedFoodLayout.animate().alpha(1.0f).setDuration(500)

                basicFoodLayout.visibility = View.GONE
                extendedFoodLayout.visibility = View.VISIBLE

                Log.d("ordinary", "asd")


            } else {

                internalCounter = 1

                basicFoodLayout.animate().alpha(1.0f).setDuration(500)
                extendedFoodLayout.animate().alpha(0.0f).setDuration(500)

                basicFoodLayout.visibility = View.VISIBLE
                extendedFoodLayout.visibility = View.GONE

                Log.d("extended", "asd")


            }

            foodName.setText(foodPost.ime)
            extendedFoodName.setText(foodPost.ime)
            foodContent.setText(foodPost.sastav)
            extendedFoodContent.setText(foodPost.sastav)
            foodPrice.setText(foodPost.cijena.toString().plus(" дин"))
            extendedFoodPrice.setText(foodPost.cijena.toString().plus(" дин"))


            val foodId = foodPost.id.toString()

            val image_url = "http://s3.eu-central-1.amazonaws.com/donesi.projekat/jela/".plus(foodId).plus(".jpg")

            //var image_url = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2100&q=80"

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

        }

    }

    companion object {
        const val FOOD = "food"
        var indeks: Int = -1
        var clickCounter: Int = 1
        var orderList = arrayListOf<Food>()
        var orderListFull = arrayListOf<Stavka>()
    }

    interface AddOrderListener {
        fun onOrderAdded(orders: ArrayList<Stavka>)
    }

}