package com.axiomcorp.rohan.rees

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.SearchView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*
import java.util.ArrayList


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private var typeStatus: Boolean = false

    private lateinit var propertyList: ArrayList<Property>

    private lateinit var mAuth: FirebaseAuth
    private lateinit var mFirebaseDatabase: FirebaseDatabase
    private var user: FirebaseUser? = null
    private lateinit var myRef: DatabaseReference

    lateinit var uInfo: User


    //Action bar elements
    private var drawerLayout: DrawerLayout? = null
    private var toggle: ActionBarDrawerToggle? = null
    private var navigationView: NavigationView? = null
    private var menuItems: Menu? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAuth = FirebaseAuth.getInstance()
        mFirebaseDatabase = FirebaseDatabase.getInstance()
        if (mAuth.currentUser != null) {
            myRef = mFirebaseDatabase.getReference("Users").child(mAuth.currentUser!!.uid)
            user = mAuth.currentUser
        }


        //Layout
        propertyList = ArrayList()
        propertyList.add(Property("1", "Test1", "Mumbai", "Flat",
                10000000, "3", "Ready to Move", 1000,
                "abc road, Mumbai", "abcd",
                R.drawable.download))
        propertyList.add(Property("2", "Test2", "Navi Mumbai", "Office Space",
                30000000, "4", "Under Construction", 2000,
                "pqrs road, Navi Mumbai", "pqrs",
                R.drawable.images))
        propertyList.add(Property("3", "Test3", "Mumbai", "Flat",
                10000000, "3", "Ready to Move", 1000,
                "xyz road, Mumbai", "xyz",
                R.drawable.maxresdefault))
        propertyList.add(Property("4", "Test4", "Navi Mumbai", "Flat",
                20000000, "3", "Ready to Move", 1000,
                "mno road, navi Mumbai", "mno",
                R.drawable.pexels_phot_106936))


        //Action bar
        drawerLayout = findViewById(R.id.drawerLayout) as DrawerLayout
        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
        drawerLayout!!.addDrawerListener(toggle!!)
        toggle!!.syncState()
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        //Navigation listener definition
        navigationView = findViewById(R.id.navigationView) as NavigationView
        navigationView!!.setNavigationItemSelectedListener(this)

        //User Characteristics
        if (mAuth.currentUser != null) {

            myRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    uInfo = dataSnapshot.getValue(User::class.java)!!
                    if (uInfo.userType.equals("Seller"))
                        typeStatus = true
                }

                override fun onCancelled(databaseError: DatabaseError) {

                }
            })
        }


        //property reference
        mFirebaseDatabase.getReference("Properties").addValueEventListener(object: ValueEventListener{
            override fun onCancelled(databaseError: DatabaseError) {
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (ds: DataSnapshot in dataSnapshot.children){
                    propertyList.add(ds.getValue(Property::class.java)!!)
                }
            }
        })

        //recycler layout elements
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view) as RecyclerView
        val recyclerViewAdapter = RecyclerViewAdapter(this, propertyList)
        recyclerView.layoutManager = GridLayoutManager(this, 2)
        recyclerView.adapter = recyclerViewAdapter

    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)

        //Login Logout nav item
        menuItems = navigationView!!.menu
        if (mAuth.currentUser != null) {
            if (typeStatus) {
                menuItems!!.add(Menu.NONE, 2, Menu.NONE, "Post")
                menuItems!!.getItem(menuItems!!.size() - 1)!!.setIcon(R.mipmap.baseline_library_add_white_18)

                menuItems!!.add(Menu.NONE, 3, Menu.NONE, "Logout")
                menuItems!!.getItem(menuItems!!.size() - 1)!!.setIcon(R.mipmap.baseline_power_settings_new_white_18)
            } else {
                menuItems!!.add(Menu.NONE, 2, Menu.NONE, "Logout")
                menuItems!!.getItem(menuItems!!.size() - 1)!!.setIcon(R.mipmap.baseline_power_settings_new_white_18)
            }


        } else {
            menuItems!!.getItem(0).isEnabled = false
            menuItems!!.add(Menu.NONE, 2, Menu.NONE, "Login")
            menuItems!!.getItem(menuItems!!.size() - 1).setIcon(R.mipmap.baseline_account_circle_white_18)
        }


        //SearchView setup
        val myActionMenuItem: MenuItem = menu.findItem(R.id.action_search)
        val searchView: SearchView = myActionMenuItem.actionView as SearchView

        return true
    }


    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (toggle!!.onOptionsItemSelected(item)) {
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.title) {

            "Dashboard" -> {
                val intent = Intent(this@MainActivity, DashboardActivity::class.java)
                intent.putExtra("firstName", uInfo.firstName)
                intent.putExtra("lastName", uInfo.lastName)
                intent.putExtra("id", uInfo.id)
                intent.putExtra("username", uInfo.username)
                intent.putExtra("email", uInfo.email)
                intent.putExtra("userType", uInfo.userType)
                intent.putExtra("contact", uInfo.contact)
                startActivity(intent)
            }

            "Settings" -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
            }

            "Post" -> {
                val intent = Intent(this@MainActivity, PostActivity::class.java)
                startActivity(intent)
            }

            "Logout" -> {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                mAuth.signOut()
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                startActivity(intent)
                finish()
            }

            "Login" -> {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
            }
        }
        return false
    }


    fun goToLogin(view: View) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

}
