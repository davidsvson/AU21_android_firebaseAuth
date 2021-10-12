package com.example.au21firebaseauth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class AddItemActivity : AppCompatActivity() {

    lateinit var itemText: EditText
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_item)

        itemText = findViewById(R.id.itemNameText)
        db = Firebase.firestore
        auth = Firebase.auth

        val saveButton = findViewById<Button>(R.id.saveButton)
        saveButton.setOnClickListener {
            saveItem()
        }

//        var monday : Item? = null
//        db.collection("weekdays").document("monday").get()
//            .addOnSuccessListener { snapshot ->
//                monday = snapshot.toObject<Item>()
//            }

        val user = auth.currentUser
        if (user != null)
            db.collection("users").document(user.uid)
                .collection("items").addSnapshotListener { snapshot, e ->
                    if (snapshot != null) {
                        for (document in snapshot.documents) {
                            val item = document.toObject<Item>()
                            Log.d("!!!", "onCreate: ${item}")
                        }
                    }
                }

    }

    fun saveItem() {
        val item = Item(name = itemText.text.toString())

        val user = auth.currentUser
        if (user == null)
            return

        db.collection("users").document(user.uid)
            .collection("items").add(item)
            .addOnCompleteListener {
                Log.d("!!!", "saveItem: add: ${it.exception}")
            }

    }


}