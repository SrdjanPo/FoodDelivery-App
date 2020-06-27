package com.example.fooddeliveryapp

import android.util.Log
import com.example.fooddeliveryapp.models.Restaurant
import com.google.gson.GsonBuilder
import okhttp3.*
import java.io.IOException
import com.google.gson.reflect.TypeToken
import com.google.gson.Gson





class DataSource{

    companion object{

        fun fetchJson(): ArrayList<Restaurant> {

            val list = ArrayList<Restaurant>()

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

                    //val restaurant = gson.fromJson(body, Restaurant::class.java)

                    //list.add(restaurant)

                    list.addAll(restaurants)

                    Log.d("uspesno", list.toString())

                }

                override fun onFailure(call: Call, e: IOException) {

                    println("Failed to execute request")
                }

            })

            return list

        }

        fun createDataSet(): ArrayList<Restaurant>{
            val list = ArrayList<Restaurant>()

            val restaurantData = fetchJson()

            list.addAll(restaurantData)

            return list
        }
    }
}