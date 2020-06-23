package com.example.fooddeliveryapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.models.RestaurantRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), RestaurantRecyclerAdapter.OnRestaurantItemClickLestener {


    private lateinit var restaurantAdapter: RestaurantRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()
        addDataSet()
    }

    private fun addDataSet() {

        val data = DataSource.createDataSet()
        restaurantAdapter.submitList(data, this)

    }

    private fun initRecyclerView() {
        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            val topSpacingDecoration = TopSpacingItemDecoration(40)
            addItemDecoration(topSpacingDecoration)
            restaurantAdapter = RestaurantRecyclerAdapter()
            adapter = restaurantAdapter
        }
    }
    override fun onItemClick(position: Int) {
        var dataSet = DataSource.createDataSet()

        Log.d("Item: ", dataSet[position].toString())

        val intent = Intent(this, RestaurantActivity::class.java)
        intent.putExtra(RESTAURANT, dataSet[position])
        startActivity(intent)

    }

    companion object {

        const val RESTAURANT = "restaurant"
    }

}
