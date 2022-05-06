package com.example.loginactivity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import com.example.loginactivity.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileActivity : AppCompatActivity() {
    //view binding
    private lateinit var binding: ActivityProfileBinding
    //Action bar
    private lateinit var actionBar: ActionBar
    //firebaseAuth
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //configure action bar
        actionBar=supportActionBar!!
        actionBar.title="Profile"

        //init firebase
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()

        //handle click,logout
        binding.logoutBtn.setOnClickListener {
            firebaseAuth.signOut()
            checkUser()
        }
    }

    private fun checkUser() {
        //check user is logged in or not
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser != null){
            //user not null,user is logged in,get user info
            val email=firebaseUser.email
            //set to text view
            binding.emailTv.text=email

        }
        else{
            //user is not logged in,goto login activity
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }
    }
}