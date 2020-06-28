package com.example.fooddeliveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.models.Restaurant
import com.example.fooddeliveryapp.models.RestaurantRecyclerAdapter
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity(), RestaurantRecyclerAdapter.OnRestaurantItemClickListener {


    private var dataSet: List<Restaurant> = emptyList()

    private lateinit var restaurantAdapter: RestaurantRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        val topSpacingDecoration = TopSpacingItemDecoration(40)
        recycler_view.addItemDecoration(topSpacingDecoration)

        fetchJson()

        //initRecyclerView()
        //addDataSet()

    }

    /*private fun addDataSet() {

        val data = fetchJson()
        restaurantAdapter.submitList(data, this)

        Log.d("MAIN", data.toString())

    }*/

    /*private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(40)
            addItemDecoration(topSpacingDecoration)
            //adapter = RestaurantRecyclerAdapter()
        }

    }*/

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


                dataSet = restaurants


                runOnUiThread {
                    recycler_view.adapter = RestaurantRecyclerAdapter(restaurants)
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                println("Failed to execute request restaurants")
            }

        })

    }

}