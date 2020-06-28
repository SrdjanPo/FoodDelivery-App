package com.example.fooddeliveryapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Restaurant(
    var id: Int,
    var adresa: String,
    var email: String,
    var ime: String,
    var opis: String,
    var cijenaDostave: Int,
    var radnoVrijeme: String,
    var tel: String

) : Parcelable