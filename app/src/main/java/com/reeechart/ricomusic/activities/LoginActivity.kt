package com.reeechart.ricomusic.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.network.ricommender.RicommenderApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val ricommenderApiCaller by lazy {
        RicommenderApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    fun register(view: View) {
        val registerIntent = Intent(this, RegisterActivity::class.java)
        this.startActivity(registerIntent)
    }

    fun login(view: View) {
        val username = usernameField.text.toString()
        disposable = ricommenderApiCaller.login(username)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            setButtonStatusLoggingIn()
                            commitUsername(result.username)
                            startPickingLocation()
                        },
                        { error ->
                            Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                            setButtonStatusErrorLogin()
                        }
                )
    }

    private fun setButtonStatusLoggingIn() {
        loggingInProgressBar.visibility = View.VISIBLE
        loginButton.isEnabled = false
        registerButton.isEnabled = false
    }

    private fun setButtonStatusErrorLogin() {
        loggingInProgressBar.visibility = View.INVISIBLE
        loginButton.isEnabled = true
        registerButton.isEnabled = true
    }

    private fun commitUsername(username: String) {
        val preferences: SharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
        preferences.edit().apply {
            putString("username", username)
            commit()
        }
    }

    private fun startPickingLocation() {
        val pickLocationIntent = Intent(this, PickLocationActivity::class.java)
        this.startActivity(pickLocationIntent)
        this.finish()
    }
}
