package com.example.fooddeliveryapp

import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
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
import androidx.core.widget.ImageViewCompat


class SearchActivity : AppCompatActivity() {

    private var mRestaurants = arrayListOf<Restaurant>()
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

                mRestaurants.addAll(restaurants)
                fullRestaurantList.clear()
                fullRestaurantList.addAll(restaurants)

                runOnUiThread {
                    search_recyclerview.adapter =
                        RestaurantSearchRecyclerAdapter(fullRestaurantList)
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
            val editText = searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)
            val closeBtn = searchView.findViewById<AppCompatImageView>(androidx.appcompat.R.id.search_close_btn)
            val backBtn = searchView.findViewById<AppCompatImageView>(androidx.appcompat.R.id.up)


            editText.hint = "Pretrazi restoran..."
            editText.setTextColor(Color.BLACK)
            ImageViewCompat.setImageTintList(closeBtn, ColorStateList.valueOf(Color.BLACK))

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if(newText!!.isNotEmpty()) {

                        fullRestaurantList.clear()

                        val search = newText.toLowerCase()

                        mRestaurants.forEach {
                            if(it.ime.toLowerCase().contains(search)) {

                                fullRestaurantList.add(it)
                            }
                        }
                        search_recyclerview.adapter!!.notifyDataSetChanged()

                    } else {
                        fullRestaurantList.clear()
                        fullRestaurantList.addAll(mRestaurants)
                        search_recyclerview.adapter!!.notifyDataSetChanged()
                    }
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
