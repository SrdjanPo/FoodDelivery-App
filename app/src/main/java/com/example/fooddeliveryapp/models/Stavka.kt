package com.example.fooddeliveryapp.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Stavka (
    var jeloBean: Food,
    var narudzbaBean: NarudzbaBean,
    var kolicina: Int
    ) : Parcelable