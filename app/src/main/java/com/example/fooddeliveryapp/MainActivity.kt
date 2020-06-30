package com.example.fooddeliveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.models.Restaurant
import com.example.fooddeliveryapp.adapters.RestaurantRecyclerAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), RestaurantRecyclerAdapter.OnRestaurantItemClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        val topSpacingDecoration = TopSpacingItemDecoration(40)
        recycler_view.addItemDecoration(topSpacingDecoration)

        fetchJson()

    }


    override fun onItemClick(position: Int) {
        /*var dataSet = fetchJson()

        val intent = Intent(this, RestaurantActivity::class.java)
        //intent.putExtra(RESTAURANT, dataSet[position])
        startActivity(intent)*/

        Log.d("TAG", "onItemClicked: CLICK")

    }

    fun fetchJson() {

        val url =  "http://ec2-52-59-235-7.eu-central-1.compute.amazonaws.com:8080/restoran"
        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {

            override fun onResponse(call: Call, response: Response) {


                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val collectionType = object : TypeToken<List<Restaurant>>() {}.type

                val restaurants = gson
                    .fromJson(body, collectionType) as List<Restaurant>

                runOnUiThread {
                    recycler_view.adapter =
                        RestaurantRecyclerAdapter(restaurants)
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                println("Failed to execute request restaurants")
            }

        })

    }

}