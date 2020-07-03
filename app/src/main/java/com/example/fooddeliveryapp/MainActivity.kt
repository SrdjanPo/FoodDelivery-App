package com.example.fooddeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
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

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));// set status background white

        recycler_view.layoutManager = LinearLayoutManager(this)
        val topSpacingDecoration = TopSpacingItemDecoration(40)
        recycler_view.addItemDecoration(topSpacingDecoration)

        fetchJson()

        mainSearch.setOnClickListener {

            var intent = Intent(this, SearchActivity::class.java)
            startActivity(intent)
        }

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

                    progress_loader.visibility = View.GONE
                    recycler_view.visibility = View.VISIBLE
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                println("Failed to execute request restaurants")
            }

        })

        progress_loader.visibility = View.GONE
        recycler_view.visibility = View.VISIBLE

    }

}