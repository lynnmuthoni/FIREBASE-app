package com.king.morningfirebasedatabaseapp

import android.app.Dialog
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UsersActivity : AppCompatActivity() {
    var listUsers:ListView ?=null
    var adapter:CustomAdapter?=null
    var users:ArrayList<User>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)
        listUsers=findViewById(R.id.mListUsers)
        users = ArrayList()
        adapter= CustomAdapter(this,users!!)
        //Connect to the users table/child to fetch data
        val reference=FirebaseDatabase.getInstance().getReference().child("Users")

        //Start fetching data
        reference.addValueEventListener(object:ValueEventListener{
            //Override the data changed
            override fun onDataChange(snapshot: DataSnapshot) {
                users!!.clear()
                //Use for loop to add the users on the arraylist
                for (snap in snapshot.children){
                    var user=snap.getValue(User::class.java)
                    users!!.add(user!!)
                }
                adapter!!.notifyDataSetChanged()
            }

            //Override the data canceled
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext,"Please contact the admin",
                            Toast.LENGTH_LONG).show()
            }
        })

        listUsers!!.adapter=adapter!!

        //Set an on item click listener to the listview

        listUsers!!.setOnItemClickListener{ adapterView, view, i, l ->
            val userId=users!!.get(i).id
            val deletionReference= FirebaseDatabase.getInstance()
                .getReference().child("Users/$userId")
            //Set an alert when someone clicks
            val alertDialog=AlertDialog.Builder(this)
            alertDialog.setTitle("ALERT!!")
            alertDialog.setMessage("Select an option you want to perform")
            alertDialog.setNegativeButton("Update", DialogInterface.OnClickListener
            { dialogInterface, i ->
                //Dismiss the alert
            })

            alertDialog.setPositiveButton("Delete", DialogInterface.OnClickListener{
                dialogInterface, i ->  deletionReference.removeValue()
                Toast.makeText(applicationContext,"Delete Sucessfully",
                                                                Toast.LENGTH_LONG).show()
            })
            alertDialog.create().show()

        }
    }
}