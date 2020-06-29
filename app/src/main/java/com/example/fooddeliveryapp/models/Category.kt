package com.example.fooddeliveryapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Category(
    var id: Int,
    var ime: String,
    var opis: String
) : Parcelable