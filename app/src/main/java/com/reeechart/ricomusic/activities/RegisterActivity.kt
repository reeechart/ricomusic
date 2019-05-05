package com.reeechart.ricomusic.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.reeechart.ricomusic.R
import com.reeechart.ricomusic.models.User
import com.reeechart.ricomusic.network.ricommender.RicommenderApiService
import com.reeechart.ricomusic.utils.StringChecker
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val ricommenderApiCaller by lazy {
        RicommenderApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    private fun setButtonStatusRegistering() {
        registerButton.isEnabled = false
        registeringContainer.visibility = View.VISIBLE
    }

    private fun setButtonStatusErrorRegister() {
        registerButton.isEnabled = true
        registeringContainer.visibility = View.INVISIBLE
    }

    fun register(view: View) {
        val username: String = usernameField.text.toString()
        val usernameValidity = StringChecker.isUsernameValid(username)
        when (usernameValidity) {
            StringChecker.USERNAME_EMPTY -> {
                Toast.makeText(this, "Username must not be empty", Toast.LENGTH_SHORT).show()
                setButtonStatusErrorRegister()
            }
            StringChecker.USERNAME_CONTAINS_INVALID_CHAR -> {
                Toast.makeText(this, "Username contains invalid characters", Toast.LENGTH_SHORT).show()
                setButtonStatusErrorRegister()
            }
            StringChecker.USERNAME_VALID -> {
                setButtonStatusRegistering()
                val user = User(username, null)
                disposable = ricommenderApiCaller.register(user)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result ->
                                    Toast.makeText(this, String.format("%s is registered successfully", result.username), Toast.LENGTH_SHORT).show()
                                    this.finish()
                                },
                                { error ->
                                    Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show()
                                    setButtonStatusErrorRegister()
                                }
                        )
            }
        }
        this.finish()
    }
}
