package com.example.fooddeliveryapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Order (
    var adresa: String,
    var email: String,
    var id: Int,
    var ime: String,
    var napomena: String,
    var tel: String,
    var restoranBean: Restaurant,
    var stavke: ArrayList<Stavka>
) : Parcelable