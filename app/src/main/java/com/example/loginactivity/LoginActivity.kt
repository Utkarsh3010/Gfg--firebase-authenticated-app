package com.example.loginactivity

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.example.loginactivity.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var actionBar: ActionBar
    private lateinit var progressDialog: ProgressDialog
    private lateinit var firebaseAuth: FirebaseAuth
    private var email=""
    private var password=""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //config actionbar
        actionBar = supportActionBar!!
        actionBar.title="Login"
        //config progress dialog
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Please Wait")
        progressDialog.setMessage("Logging In....")
        progressDialog.setCanceledOnTouchOutside(false)
        //init firebase Auth
        firebaseAuth= FirebaseAuth.getInstance()
        checkUser()
        //handle click,open SignUp activity
        binding.noAccountTv.setOnClickListener {
            startActivity(Intent(this,SignUpActivity::class.java))
        }
        //handle click,begin login
        binding.loginBtn.setOnClickListener {
            //before login validate data
            validateData()
        }

    }

    private fun validateData() {
        //get data
        email=binding.emailEt.text.toString().trim()
        password=binding.passwordEt.text.toString().trim()
        //validate data
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            //invalid email format
            binding.emailEt.error = "Invalid email format"
        }
        else if(TextUtils.isEmpty(password)){
            //no password entered
            binding.passwordEt.error="Please enter password"
        }
        else{
            //data is validate begin login
            firebaseLogin()
        }
    }

    private fun firebaseLogin() {
        //show progress
        progressDialog.show()
        firebaseAuth.signInWithEmailAndPassword(email,password)
            .addOnSuccessListener {
                //login success
                progressDialog.dismiss()
                //get user info
                val firebaseUser=firebaseAuth.currentUser
                val email=firebaseUser!!.email
                Toast.makeText(this,"Logged In as $email", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this,ProfileActivity::class.java))
                finish()

            }
            .addOnFailureListener{ e->
                //login failed
                progressDialog.dismiss()
                Toast.makeText(this,"Login failed due to ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun checkUser() {
        //if user is already logged in go to profile activity
        //get current user
        val firebaseUser=firebaseAuth.currentUser
        if(firebaseUser!=null){
            //user is already logged in
            startActivity(Intent(this,ProfileActivity::class.java))
            finish()
        }
    }

}