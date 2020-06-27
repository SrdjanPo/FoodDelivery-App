package com.example.fooddeliveryapp

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.fooddeliveryapp.models.Food
import com.example.fooddeliveryapp.models.FoodRecyclerAdapter
import kotlinx.android.synthetic.main.activity_food.*
import androidx.core.view.ViewCompat
import android.util.Log


class FoodActivity : AppCompatActivity() {

    private var foodItem: ArrayList<Food> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            val w = window
            w.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }

        /*ViewCompat.setOnApplyWindowInsetsListener(
            dodatni_komentar
        ) { view, insets ->
            ViewCompat.onApplyWindowInsets(
                dodatni_komentar,
                insets.replaceSystemWindowInsets(
                    insets.systemWindowInsetLeft, 0,
                    insets.systemWindowInsetRight, insets.systemWindowInsetBottom
                )
            )
        }*/

        intent.let {
            val food = intent.extras!!.getParcelable(FoodRecyclerAdapter.FOOD) as Food?

            foodItem.add(food!!)

            food_name.text = food!!.ime
            about_food.text = food.sastav
            food_price.text = food.cijena.toString().plus(" дин")

            //restaurantId = restaurant.id

            //var restaurantId = restaurant.id.toString()

            var image_url = "https://images.unsplash.com/photo-1574071318508-1cdbab80d002?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2250&q=80"

            val requestOptions = RequestOptions()
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_background)

            Glide.with(this)
                .applyDefaultRequestOptions(requestOptions)
                .load(image_url)
                .into(food_image)
        }

        dodajBtn.setOnClickListener {


//        val intentFromFood = Intent(this, RestaurantActivity::class.java)
//        intentFromFood.putExtra(FOOD_ITEM, foodItem)
//        startActivity(intentFromFood)
//
//            finish()
            Log.d("IZABRANO JELO", foodItem.toString())
        }

        backButton.setOnClickListener {

            onBackPressed()
        }

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
