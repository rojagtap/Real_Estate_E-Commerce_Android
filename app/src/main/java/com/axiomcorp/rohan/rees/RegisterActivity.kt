package com.axiomcorp.rohan.rees

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class RegisterActivity : AppCompatActivity() {

    private val TAG = "RegisterActivity"

    //Firebase references
    private var mDatabaseReference: DatabaseReference? = null
    private var mDatabase: FirebaseDatabase? = null
    private var mAuth: FirebaseAuth? = null

    private var usernameField: EditText? = null
    private var emailField: EditText? = null
    private var passwordField: EditText? = null
    private var firstNameField: EditText? = null
    private var lastNameField: EditText? = null
    private var contactField: EditText? = null
    private var userTypeField: Spinner? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        //initialize FireBase auth instance
        mDatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mDatabase!!.reference.child("Users")
        mAuth = FirebaseAuth.getInstance()

        //Getting instances for layout elements
        usernameField = findViewById(R.id.usernameField) as EditText
        emailField = findViewById(R.id.emailField) as EditText
        passwordField = findViewById(R.id.passwordField) as EditText
        firstNameField = findViewById(R.id.firstNameField) as EditText
        lastNameField = findViewById(R.id.lastNameField) as EditText
        contactField = findViewById(R.id.contactField) as EditText
        userTypeField = findViewById(R.id.userTypeField) as Spinner

    }


    fun register(view: View) {

        val email: String? = emailField?.text.toString()
        val username: String? = usernameField?.text.toString()
        val password: String? = passwordField?.text.toString()
        val firstName: String? = firstNameField?.text.toString()
        val lastName: String? = lastNameField?.text.toString()
        val contact: String? = contactField?.text.toString()
        val userType: String? = userTypeField?.selectedItem.toString()

        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(firstName) || TextUtils.isEmpty(lastName) ||
                TextUtils.isEmpty(contact) || TextUtils.isEmpty(userType) || TextUtils.isEmpty(password)) {

            Toast.makeText(this, "All Fields are Required", Toast.LENGTH_SHORT).show()

        } else {

            mAuth!!
                    .createUserWithEmailAndPassword(email!!, password!!)
                    .addOnCompleteListener(this) { task ->

                        if (task.isSuccessful) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success")
                            val userId = mAuth!!.currentUser!!.uid
                            //Verify Email
                            verifyEmail()
                            //update user profile information
                            val currentUserDb = mDatabaseReference!!.child(userId)
                            val user = User(userId, email, username, firstName, lastName, contact, userType)
                            currentUserDb.setValue(user)
                            updateUserInfoAndUI()
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.exception)
                            Toast.makeText(this@RegisterActivity, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show()
                        }
                    }

        }

    }

    private fun updateUserInfoAndUI() {
        //start next activity
        val intent = Intent(this@RegisterActivity, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun verifyEmail() {
        val mUser = mAuth!!.currentUser
        mUser!!.sendEmailVerification()
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this@RegisterActivity,
                                "Verification email sent to " + mUser.email,
                                Toast.LENGTH_SHORT).show()
                    } else {
                        Log.e(TAG, "sendEmailVerification", task.exception)
                        Toast.makeText(this@RegisterActivity,
                                "Failed to send verification email.",
                                Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun goToLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
    }


}