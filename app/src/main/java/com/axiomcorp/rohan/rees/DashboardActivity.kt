package com.axiomcorp.rohan.rees

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dashboard.*

class DashboardActivity : AppCompatActivity() {

    private lateinit var nameField: TextView
    private lateinit var userIdField: TextView
    private lateinit var usernameField: TextView
    private lateinit var emailField: TextView
    private lateinit var userTypeField: TextView
    private lateinit var contactField: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        val firstName: String = intent.getStringExtra("firstName")
        val lastName: String = intent.getStringExtra("lastName")
        val id: String = intent.getStringExtra("id")
        val username: String = intent.getStringExtra("username")
        val email: String = intent.getStringExtra("email")
        val userType: String = intent.getStringExtra("userType")
        val contact: String = intent.getStringExtra("contact")


        //layout
        nameField = findViewById(R.id.nameField) as TextView
        userIdField = findViewById(R.id.userIdField) as TextView
        usernameField = findViewById(R.id.usernameField) as TextView
        emailField = findViewById(R.id.emailField) as TextView
        userTypeField = findViewById(R.id.userTypeField) as TextView
        contactField = findViewById(R.id.contactField) as TextView

        //set data
        val name:String = firstName + lastName
        nameField.text = name
        userIdField.text = id
        usernameField.text = username
        emailField.text = email
        userTypeField.text = userType
        contactField.text = contact


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

