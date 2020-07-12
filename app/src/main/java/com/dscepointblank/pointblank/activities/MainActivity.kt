package com.dscepointblank.pointblank.activities

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.dscepointblank.pointblank.R
import com.dscepointblank.pointblank.models.UpdateModel
import com.dscepointblank.pointblank.notifications.*
import com.dscepointblank.pointblank.utilityClasses.Constants
import com.dscepointblank.pointblank.utilityClasses.RetrofitInstance
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.lang.Exception

const val TOPIC = "/topics/MyTopic"

class MainActivity : BaseActivity() {


    lateinit var downloadController: DownloadController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC)

        button.setOnClickListener {
            if (notititle.text.toString().isNotEmpty() && notides.text.toString().isNotEmpty()) {

                PushNotification(
                    NotificationData(
                        notititle.text.toString(),
                        notides.text.toString()
                    ), TOPIC
                )
                    .also { sendNotification(it) }
            }
        }

        codeForces.setOnClickListener {
            if(codeIdTV.text.toString().isNotEmpty())
                getCodeForces(codeIdTV.text.toString())
        }

        update.setOnClickListener { checkForUpdates() }

    }


    private fun checkForUpdates() =
        try {
            downloadController = DownloadController(this@MainActivity, UpdateModel(1,"d"))
            downloadController.beginDownloadProcess()
        }
        catch (e :Exception)
        {
            Log.d("DDDD",e.localizedMessage!!)
        }


    private fun getCodeForces(userId: String) = GlobalScope.launch(Dispatchers.IO) {
        try {
            val userData = RetrofitInstance.codeForcesAPI.getUserDetails(userId)
            withContext(Dispatchers.Main)
            {
                Toast.makeText(this@MainActivity, userData.details[0].firstName, Toast.LENGTH_SHORT)
                    .show();
            }

        } catch (e: Exception) {
            Log.d("NNNN", "error" + e.localizedMessage!!)
        }
    }


    private fun sendNotification(notification: PushNotification) =
        GlobalScope.launch(Dispatchers.IO) {
            try {
                val response = RetrofitInstance.api.postNotification(notification)
                if (response.isSuccessful) {
                    Log.d("DDDD", "Response is ${Gson().toJson(response.toString())}")
                }

            } catch (e: Exception) {
                Log.d("DDDD", e.localizedMessage!!)
            }
        }
}

