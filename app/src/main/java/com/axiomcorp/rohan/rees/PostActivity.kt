package com.axiomcorp.rohan.rees

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PostActivity : AppCompatActivity() {

    private lateinit var propertyTitleField: EditText
    private lateinit var localityField: EditText
    private lateinit var propertyTypeField: Spinner
    private lateinit var priceField: EditText
    private lateinit var bhkField: Spinner
    private lateinit var construction_statusField: Spinner
    private lateinit var areaField:EditText
    private lateinit var addressField: EditText
    private lateinit var descriptionField: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post)

        //back button action bar
        supportActionBar!!.setDisplayShowHomeEnabled(true)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        propertyTitleField = findViewById(R.id.propertyTitleField) as EditText
        localityField = findViewById(R.id.localityField) as EditText
        propertyTypeField = findViewById(R.id.propertyTypeField) as Spinner
        priceField = findViewById(R.id.priceField) as EditText
        bhkField = findViewById(R.id.bhkField) as Spinner
        construction_statusField = findViewById(R.id.construction_statusField) as Spinner
        areaField = findViewById(R.id.areaField) as EditText
        addressField = findViewById(R.id.addressField) as EditText
        descriptionField = findViewById(R.id.descriptionField) as EditText

    }

    fun post(view: View){
        val propertyTitle: String? = propertyTitleField.text.toString()
        if (TextUtils.isEmpty(propertyTitle)){
            propertyTitleField.error = "Please enter a Title."
            return
        }
        val locality: String? = localityField.text.toString()
        if (TextUtils.isEmpty(locality)){
            localityField.error = "Please enter a Locality."
            return
        }
        val propertyType: String? = propertyTypeField.selectedItem.toString()
        val price: Int? = Integer.parseInt(priceField.text.toString())
        if (TextUtils.isEmpty(price.toString())){
            priceField.error = "Please enter a Price."
            return
        }
        val bhk: String? = bhkField.selectedItem.toString()
        val construction_status: String? = construction_statusField.selectedItem.toString()
        val area: Int? = Integer.parseInt(areaField.text.toString())
        if (TextUtils.isEmpty(area.toString())){
            areaField.error = "Please enter the Area."
            return
        }
        val address: String? = addressField.text.toString()
        if (TextUtils.isEmpty(address)){
            addressField.error = "Please enter the address."
            return
        }
        val description: String? = descriptionField.text?.toString()

        val mRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("Properties")
        val id: String? = mRef.push().key
        val property = Property(id, propertyTitle, locality, propertyType, price, bhk,
                construction_status, area, address, description, R.drawable.pexels_phot_106936)
        if (id != null) {
            mRef.child(id).setValue(property).addOnCompleteListener{
                Toast.makeText(this@PostActivity, "Successful", Toast.LENGTH_SHORT).show()
            }
        }

        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()

    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
        return true
    }
}
