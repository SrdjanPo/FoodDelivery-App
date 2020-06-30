package com.example.fooddeliveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.adapters.OrderRecyclerAdapter
import com.example.fooddeliveryapp.models.Food
import kotlinx.android.synthetic.main.activity_food.*
import androidx.recyclerview.widget.DividerItemDecoration






class FoodActivity : AppCompatActivity() {

    private var foodItem = arrayListOf<Food>()
    private var deliveryPrice = 0
    private var totalFoodPrice = 0
    private var totalPrice = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//  set status text dark
        getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.white));// set status background white


        //setting toolbar
        setSupportActionBar(findViewById(R.id.orderToolbar))
        //home navigation
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Narudzbina"

        order_recycler_adapter.layoutManager = LinearLayoutManager(this)
        val itemDecorator = DividerItemDecoration(this, DividerItemDecoration.VERTICAL)
        itemDecorator.setDrawable(ContextCompat.getDrawable(this, R.drawable.order_rv_item_devider)!!)

        order_recycler_adapter.addItemDecoration(itemDecorator)
       /* val topSpacingDecoration = TopSpacingItemDecoration(40)
        order_recycler_adapter.addItemDecoration(topSpacingDecoration)*/

        intent.let {
            val food = intent.extras!!.getParcelableArrayList<Food>("order_list")

            foodItem.clear()

            food!!.toCollection(foodItem)

            runOnUiThread {
                order_recycler_adapter.adapter =
                    OrderRecyclerAdapter(foodItem)

            }
        }

        orderCount.text = foodItem.count().toString().plus("x")

        deliveryPrice = foodItem[0].restoranBean.cijenaDostave

        for (item in foodItem) {

            totalFoodPrice = totalFoodPrice + item.cijena
        }

        totalPrice = totalFoodPrice + deliveryPrice

        orderTotal.text = totalPrice.toString().plus(" дин")
        totalPriceView.text = "Ukupno: ".plus(totalPrice.toString()).plus(" дин")
        deliveryPriceView.text = deliveryPrice.toString().plus(" дин")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
