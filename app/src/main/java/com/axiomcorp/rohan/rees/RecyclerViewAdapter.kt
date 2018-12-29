package com.axiomcorp.rohan.rees

import android.content.Context
import android.content.Intent
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

class RecyclerViewAdapter(private var mContext: Context, private var mData: List<Property>) : RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        lateinit var view: View
        val mInflater: LayoutInflater = LayoutInflater.from(mContext)
        view = mInflater.inflate(R.layout.cardview_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mData.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.property_title.text = mData.get(position).title
        holder.property_image.setImageResource(mData.get(position).image!!)

        //action for card click
        holder.cardView.setOnClickListener {
            val intent = Intent(mContext, DetailActivity::class.java)
            intent.putExtra("title", mData.get(position).title)
            intent.putExtra("locality", mData.get(position).locality)
            intent.putExtra("propertyType", mData.get(position).propertyType)
            intent.putExtra("price", mData.get(position).price)
            intent.putExtra("BHK", mData.get(position).BHK)
            intent.putExtra("constructionStatus", mData.get(position).constructionStatus)
            intent.putExtra("area", mData.get(position).area)
            intent.putExtra("address", mData.get(position).address)
            intent.putExtra("description", mData.get(position).description)
            intent.putExtra("image", mData.get(position).image!!)

            mContext.startActivity(intent)
        }

    }

    public class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val property_title: TextView = itemView.findViewById(R.id.property_title) as TextView
        val property_image: ImageView = itemView.findViewById(R.id.property_image) as ImageView
        val cardView: CardView = itemView.findViewById(R.id.card_view)
    }
}