package com.example.fooddeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.models.Restaurant
import kotlinx.android.synthetic.main.activity_restaurant.*
import android.view.WindowManager
import android.os.Build
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.models.Food
import com.example.fooddeliveryapp.models.FoodRecyclerAdapter
import com.example.fooddeliveryapp.models.RestaurantRecyclerAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException


class RestaurantActivity : AppCompatActivity(), FoodRecyclerAdapter.AddOrderListener {


    private var restaurantId: Int = 0

    private var listItems = arrayListOf<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        cartLayout.setOnClickListener {

            val intent = Intent(this, FoodActivity::class.java)
            intent.putExtra("items", listItems)
            startActivity(intent)
        }

        food_recyclerview.layoutManager = LinearLayoutManager(this)
        val topSpacingDecoration = TopSpacingItemDecoration(40)
        food_recyclerview.addItemDecoration(topSpacingDecoration)

        intent.let {
            val restaurant = intent.extras!!.getParcelable(RestaurantRecyclerAdapter.RESTAURANT) as Restaurant?


            restaurant_name.text = restaurant!!.ime
            about_restaurant.text = restaurant.opis
            restaurant_address.text = restaurant.adresa
            working_hours.text = restaurant.radnoVrijeme

            restaurantId = restaurant.id

            var restaurantId = restaurant.id.toString()

            var image_url = "https://s3.eu-central-1.amazonaws.com/donesi.projekat/restorani/".plus(restaurantId).plus(".jpg")

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(image_url)
                .into(restaurant_image)

        }

        /*intent.let {

            val foodItem = intent.extras!!.getParcelable(FoodActivity.FOOD_ITEM) as Food?

            Log.d("JELO", foodItem.toString())
        }*/


        fetchFood()

        backButton.setOnClickListener {
            FoodRecyclerAdapter.orderList.clear()
            listItems.clear()

            FoodRecyclerAdapter.clickCounter = 1
            onBackPressed()
        }

    }

    fun fetchFood() {


        val url =  "http://ec2-52-59-235-7.eu-central-1.compute.amazonaws.com:8080/".plus(restaurantId).plus("/jela")

        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {


                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val collectionType = object : TypeToken<List<Food>>() {}.type

                val foods = gson
                    .fromJson(body, collectionType) as List<Food>


                runOnUiThread {
                    food_recyclerview.adapter = FoodRecyclerAdapter(foods, this@RestaurantActivity)
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                println("Failed to execute request")
            }

        })

    }

    override fun onOrderAdded(orders: ArrayList<Food>) {

        listItems.clear()

        listItems.addAll(orders)


        var sumPrice = 0

        for(item in listItems) {

            sumPrice = sumPrice + item.cijena
        }

        if (listItems.isEmpty()) {

            cartLayout.visibility = View.GONE

        } else {

            orderCount.text = listItems.count().toString().plus("x")
            orderTotal.text = sumPrice.toString().plus(" дин.")
            cartLayout.visibility = View.VISIBLE
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()

        FoodRecyclerAdapter.orderList.clear()

    }

}
