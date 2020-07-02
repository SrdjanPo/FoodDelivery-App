package com.example.fooddeliveryapp

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.adapters.RestaurantRecyclerAdapter
import com.example.fooddeliveryapp.adapters.RestaurantSearchRecyclerAdapter
import com.example.fooddeliveryapp.models.Restaurant
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_search.*
import okhttp3.*
import java.io.IOException
import androidx.core.view.MenuItemCompat.getActionView



class SearchActivity : AppCompatActivity() {

    private var fullRestaurantList = arrayListOf<Restaurant>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));// set status background white


        search_recyclerview.layoutManager = LinearLayoutManager(this)
        val topSpacingDecoration = TopSpacingItemDecoration(40)
        search_recyclerview.addItemDecoration(topSpacingDecoration)

        fetchJson()
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
                    .fromJson(body, collectionType) as ArrayList<Restaurant>

                fullRestaurantList.clear()
                fullRestaurantList.addAll(restaurants)

                runOnUiThread {
                    search_recyclerview.adapter =
                        RestaurantSearchRecyclerAdapter(restaurants)
                }

            }

            override fun onFailure(call: Call, e: IOException) {

                println("Failed to execute request restaurants")
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem = menu.findItem(R.id.action_search)
        if (searchItem != null) {

            val searchView = searchItem.actionView as SearchView

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    RestaurantSearchRecyclerAdapter(fullRestaurantList).filter.filter(newText)
                    return true
                }
            })
        }


        return super.onCreateOptionsMenu(menu)
    }

   /* override fun onCreateOptionsMenu(menu: Menu): Boolean {

        menuInflater.inflate(R.menu.main_menu, menu)
        val menuItem = menu.findItem(R.id.action_search)
        val searchView = menuItem.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                //recyclerAdapter.getFilter().filter(newText)
                return false
            }
        })


        return super.onCreateOptionsMenu(menu)
    }*/
}
