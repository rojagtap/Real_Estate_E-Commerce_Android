package com.axiomcorp.rohan.rees

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView

class DetailActivity : AppCompatActivity() {
    private lateinit var titleField: TextView
    private lateinit var localityField: TextView
    private lateinit var propertyTypeField: TextView
    private lateinit var priceField: TextView
    private lateinit var bhkField: TextView
    private lateinit var construction_statusField: TextView
    private lateinit var areaField: TextView
    private lateinit var addressField: TextView
    private lateinit var descriptionField: TextView
    private lateinit var imageField: ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        //init view
        titleField = findViewById(R.id.titleField) as TextView
        localityField = findViewById(R.id.localityField) as TextView
        propertyTypeField = findViewById(R.id.propertyTypeField) as TextView
        priceField = findViewById(R.id.priceField) as TextView
        bhkField = findViewById(R.id.bhkField) as TextView
        construction_statusField = findViewById(R.id.construction_statusField) as TextView
        areaField = findViewById(R.id.areaField) as TextView
        addressField = findViewById(R.id.addressField) as TextView
        descriptionField = findViewById(R.id.descriptionField) as TextView
        imageField = findViewById(R.id.image) as ImageView

        //get data
        val title: String = intent.getStringExtra("title")
        val locality: String = intent.getStringExtra("locality")
        val propertyType: String = intent.getStringExtra("propertyType")
        val price: Int = intent.getIntExtra("price", 0)
        val BHK: String = intent.getStringExtra("BHK")
        val constructionStatus: String = intent.getStringExtra("constructionStatus")
        val area: Int = intent.getIntExtra("area", 0)
        val address: String = intent.getStringExtra("address")
        val description: String
        if (intent.getStringExtra("description") == "")
            description = "NA"
        else
            description= intent.getStringExtra("description")
        val image: Int = intent.getIntExtra("image", R.drawable.images)


        //set values
        titleField.text = title
        localityField.text = locality
        propertyTypeField.text = propertyType
        priceField.text = price.toString()
        bhkField.text = BHK
        construction_statusField.text = constructionStatus
        areaField.text = area.toString()
        addressField.text = address
        descriptionField.text = description
        imageField.setImageResource(image)


        //back on action bar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
        return true
    }
}
