package com.example.au21firebaseauth

import com.google.firebase.firestore.DocumentId

data class Item(@DocumentId var documentId: String? = null,
                var name: String? = null,
                var done: Boolean = false)