package com.king.morningfirebasedatabaseapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase

class MainActivity : AppCompatActivity() {
    var editTextName:EditText?=null
    var editTextEmail:EditText?=null
    var editTextIdNumber:EditText?=null
    var buttonSave:Button?=null
    var buttonView:Button?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        editTextName=findViewById(R.id.medtName)
        editTextEmail=findViewById(R.id.medtEmail)
        editTextIdNumber=findViewById(R.id.medtIDNumber)
        buttonSave=findViewById(R.id.mbtnSave)
        buttonView=findViewById(R.id.mbtnView)


        buttonSave!!.setOnClickListener {
        //receive the data
            val username=editTextName!!.text.toString().trim()
            val useremail=editTextEmail!!.text.toString().trim()
            val userIdNumber=editTextIdNumber!!.text.toString().trim()
            val id=System.currentTimeMillis().toString()

            //Check if user field is empty
            if (username.isEmpty()){
                editTextName!!.setError("Please fill this field!!")
                editTextName!!.requestFocus()
            }
            else if (useremail.isEmpty()){
                editTextEmail!!.setError("Please fill this field!!")
                editTextEmail!!.requestFocus()
            }
            else if (userIdNumber.isEmpty()){
                editTextIdNumber!!.setError("Please fill this field!!")
                editTextIdNumber!!.requestFocus()
            }else{
                //Save the data
                //Start by creating the user object
                val userData=User(username,useremail,userIdNumber,id)

                //Create a reference to the database to store data
                val reference=FirebaseDatabase.getInstance().
                            getReference().child("Users/$id")
                //Start saving userdata
                reference.setValue(userData).addOnCompleteListener{
                    task->
                    if (task.isSuccessful){
                        Toast.makeText(applicationContext,"Data saved Sucessfully",
                        Toast.LENGTH_LONG).show()
                    }
                }
            }

        }
        buttonView!!.setOnClickListener {
        val goToUser=Intent(applicationContext,UsersActivity::class.java)
            startActivity(goToUser)
        }
    }
}