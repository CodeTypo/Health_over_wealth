package com.codetypo.healthoverwealth;

import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

/**
 * This class represents repository for Firebase.
 */
class FirebaseRepo {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    /**
     * This function returns the user who is currently logged in.
     */
    fun getUser(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    /**
     * This function creates user.
     */
    fun createUser(): Task<AuthResult> {
        return firebaseAuth.signInAnonymously()
    }
}
