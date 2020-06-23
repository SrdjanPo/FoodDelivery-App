package com.example.fooddeliveryapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.models.Restaurant
import kotlinx.android.synthetic.main.activity_restaurant.*
import android.view.WindowManager
import android.os.Build



class RestaurantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_restaurant)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        intent.let {
            val restaurant = intent.extras!!.getParcelable(MainActivity.RESTAURANT) as Restaurant?
            restaurant_name.text = restaurant!!.name
            about_restaurant.text = restaurant.about

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(restaurant.image)
                .into(restaurant_image)
        }

        backButton.setOnClickListener {
            onBackPressed()
        }

    }

}
