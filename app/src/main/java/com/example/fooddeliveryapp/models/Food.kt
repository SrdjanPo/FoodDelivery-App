package com.example.fooddeliveryapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Food(
    var id: Int,
    var cijena: Int,
    var ime: String,
    var opis: String,
    var sastav: String,
    var restoranBean: Restaurant
): Parcelable