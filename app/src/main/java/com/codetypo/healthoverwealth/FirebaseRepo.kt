package com.codetypo.healthoverwealth;

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class FirebaseRepo {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val firebaseFirestore : FirebaseFirestore = FirebaseFirestore.getInstance()

    //Authentication
    fun getUser(): FirebaseUser?{
        return firebaseAuth.currentUser
    }

    fun createUser() : Task<AuthResult>{
        return firebaseAuth.signInAnonymously()
    }

    //firestore
    fun getPostlist(): Task<QuerySnapshot>{
        return firebaseFirestore.collection("Testing").get()
    }


}
