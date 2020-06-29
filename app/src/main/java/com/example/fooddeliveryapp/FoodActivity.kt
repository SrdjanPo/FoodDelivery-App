package com.example.fooddeliveryapp

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.example.fooddeliveryapp.adapters.OrderListAdapter
import com.example.fooddeliveryapp.models.Food
import kotlinx.android.synthetic.main.activity_food.*


class FoodActivity : AppCompatActivity() {

    private var foodItem = arrayListOf<Food>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        //setting toolbar
        setSupportActionBar(findViewById(R.id.orderToolbar))
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "About"



        intent.let {
            val food = intent.extras!!.getParcelableArrayList<Food>("order_list")

            foodItem.clear()

            food!!.toCollection(foodItem)
            //foodItem.addAll(food)

        }

        order_listview.adapter = OrderListAdapter(foodItem,this)

    }

    companion object {
        const val FOOD_ITEM = "foodItem"
    }


    /*override fun onBackPressed() {
        super.onBackPressed()

        val view = currentFocus
        view?.clearFocus()

        Log.d("OCISCENO", "ASD")
    }*/
}
