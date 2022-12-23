package ie.setu.hitlist.ui.auth

import android.app.Application
import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.annotation.NonNull
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseUser
import ie.setu.hitlist.firebase.FirebaseAuthManager


class LoginRegisterViewModel (app: Application) : AndroidViewModel(app) {

    var firebaseAuthManager : FirebaseAuthManager = FirebaseAuthManager(app)
    var liveFirebaseUser : MutableLiveData<FirebaseUser> = firebaseAuthManager.liveFirebaseUser

    fun login(email: String?, password: String?) {
        firebaseAuthManager.login(email, password)
    }

    fun register(email: String?, password: String?) {
        firebaseAuthManager.register(email, password)
    }

    fun authWithGoogle(acct: GoogleSignInAccount) {
        firebaseAuthManager.firebaseAuthWithGoogle(acct)
    }
}