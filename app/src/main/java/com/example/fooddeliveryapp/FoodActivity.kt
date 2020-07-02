package com.example.fooddeliveryapp

import android.app.PendingIntent.getActivity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.fooddeliveryapp.adapters.OrderRecyclerAdapter
import com.example.fooddeliveryapp.models.Food
import com.example.fooddeliveryapp.models.Stavka
import kotlinx.android.synthetic.main.activity_food.*
import androidx.recyclerview.widget.DividerItemDecoration
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import okhttp3.*
import java.io.IOException
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.MediaType.Companion.toMediaType
import com.example.fooddeliveryapp.models.Order
import com.example.fooddeliveryapp.models.Restaurant
import com.google.gson.Gson
import android.content.Intent
import android.view.WindowManager


class FoodActivity : AppCompatActivity() {

    private var foodItem = arrayListOf<Stavka>()
    private lateinit var restoran: Restaurant
    private var deliveryPrice = 0
    private var totalFoodPrice = 0
    private var totalPrice = 0
    private var mRestaurantActivity = RestaurantActivity()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        this@FoodActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)


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
            val food = intent.extras!!.getParcelableArrayList<Stavka>("order_list")

            foodItem.clear()
            restoran = food!!.get(0).jeloBean.restoranBean

            food.toCollection(foodItem)

            runOnUiThread {
                order_recycler_adapter.adapter =
                    OrderRecyclerAdapter(foodItem)

            }
        }

        orderCount.text = foodItem.count().toString().plus("x")

        deliveryPrice = foodItem[0].jeloBean.restoranBean.cijenaDostave

        for (item in foodItem) {

            totalFoodPrice = totalFoodPrice + item.jeloBean.cijena
        }

        totalPrice = totalFoodPrice + deliveryPrice

        orderTotal.text = totalPrice.toString().plus(" дин")
        totalPriceView.text = "Ukupno: ".plus(totalPrice.toString()).plus(" дин")
        deliveryPriceView.text = deliveryPrice.toString().plus(" дин")

        cartLayout.setOnClickListener {

            postOrder()
            //successView.visibility = View.VISIBLE
        }

        orderFinished.setOnClickListener {
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }

    fun postOrder() {

        val url = "http://ec2-52-59-235-7.eu-central-1.compute.amazonaws.com:8080/narudzba"

        val mediaType = "application/json; charset=utf-8".toMediaType()

        val orderName = order_name.text.toString()
        val orderPhoneNumber = order_phoneNumber.text.toString()
        val orderAddress = order_address.text.toString()
        val orderEmail = order_email.text.toString()
        val orderComment = dodatni_komentar.text.toString()

        if (orderName.isNullOrEmpty()) {

            order_name.error = "Molimo vas unesite ime"

        } else if (orderPhoneNumber.isNullOrEmpty()) {

            order_phoneNumber.error = "Molimo vas unesite broj telefona"

        } else if (orderAddress.isNullOrEmpty()) {

            order_address.error = "Molimo vas unesite adresu"

        } else if (orderEmail.isNullOrEmpty()) {

            order_email.error = "Molimo vas unesite email"
        } else {

            //var payload = "string"

            val payload = Order(orderAddress, orderEmail,0,orderName, orderComment, orderPhoneNumber, restoran, foodItem)

            val jsonString = Gson().toJson(payload)


            val okHttpClient = OkHttpClient()
            val requestBody = jsonString.toRequestBody(mediaType)
            val request = Request.Builder()
                .method("POST", requestBody)
                .url(url)
                .build()


            okHttpClient.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    println("Failed to execute POST order request")
                }

                override fun onResponse(call: Call, response: Response) {

                    /*val body = requestBody.toString()

                    val gson = GsonBuilder().create()

                    val collectionType = object : TypeToken<List<Order>>() {}.type

                    val orders = gson
                        .fromJson(body, collectionType) as List<Order>*/

                    println("Successfully executed POST order request")
                    Log.d("porudzbina", jsonString)
                    Log.d("status", response.toString())

                    if (response.code == 200) {

                        runOnUiThread {
                            successView.visibility = View.VISIBLE
                        }

                    } else {

                        Log.d("Neuspijesno slanje", response.code.toString())
                    }

                    //Bearer eyJhbGciOiJIUzI1NiJ9.eyJqdGkiOiI1IiwidXNlcm5hbWUiOiJhZG1pbiIsInJlc3RvcmFuIjotMSwidHlwZSI6IkEifQ.Lxq0X02c2KIcd1G90mb-PHslra28N85p_MRCScQ-gSA
                }
            })
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
