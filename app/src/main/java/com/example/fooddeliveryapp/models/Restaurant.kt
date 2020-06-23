package com.example.fooddeliveryapp.models

import android.os.Parcel
import android.os.Parcelable

data class Food(
    var food_name: String,
    var food_about: String,
    var food_price: Int,
    var food_image: String
)

data class Restaurant(
    var name: String,
    var body: String,
    var image: String,
    var about: String

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: "",
        parcel.readString()?: ""
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(body)
        parcel.writeString(image)
        parcel.writeString(about)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Restaurant> {
        override fun createFromParcel(parcel: Parcel): Restaurant {
            return Restaurant(parcel)
        }

        override fun newArray(size: Int): Array<Restaurant?> {
            return arrayOfNulls(size)
        }
    }

}