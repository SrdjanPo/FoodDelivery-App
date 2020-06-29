package com.example.fooddeliveryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.fooddeliveryapp.R
import com.example.fooddeliveryapp.models.Food
import kotlinx.android.synthetic.main.layout_order_list_item.view.*

class OrderListAdapter(orderList: ArrayList<Food>, context: Context): BaseAdapter() {

    private val mContext: Context

    private val mOrderList = orderList

    init {
        mContext = context
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val layoutInflater = LayoutInflater.from(mContext)
        val orderRow = layoutInflater.inflate(R.layout.layout_order_list_item, parent, false)

        val orderName = orderRow.orderItem_name
        val orderAbout = orderRow.orderItem_about
        val orderPrice = orderRow.orderItem_price

        orderName.text = mOrderList[position].ime
        orderAbout.text = mOrderList[position].sastav
        orderPrice.text = mOrderList[position].cijena.toString().plus(" дин")

        return orderRow
    }

    override fun getItem(position: Int): Any {

        return "string"
    }

    override fun getItemId(position: Int): Long {

        return position.toLong()
    }

    override fun getCount(): Int {

        return mOrderList.size
    }
}