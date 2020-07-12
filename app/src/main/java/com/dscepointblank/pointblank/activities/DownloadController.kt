//This class is used for updating app procedure

package com.dscepointblank.pointblank.activities

import android.Manifest
import android.app.Activity
import android.app.DownloadManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.FileProvider
import com.dscepointblank.pointblank.BuildConfig
import com.dscepointblank.pointblank.R
import com.dscepointblank.pointblank.models.UpdateModel
import com.dscepointblank.pointblank.notifications.MyNotifications
import com.dscepointblank.pointblank.utilityClasses.Constants
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.io.File

class DownloadController(private val context: Context, private val updateDocument: UpdateModel) {
    val activity = context as Activity

    companion object {
        private const val FILE_NAME = "SampleDownloadApp.apk"
        private const val FILE_BASE_PATH = "file://"
        private const val MIME_TYPE = "application/vnd.android.package-archive"
        private const val PROVIDER_PATH = ".provider"
        private const val APP_INSTALL_PATH = "\"application/vnd.android.package-archive\""
        private const val PERMISSION_REQUEST_STORAGE = 0
    }

    private fun enqueueDownload(url: String) {
        var destination =
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS).toString() + "/"
        destination += FILE_NAME
        val uri = Uri.parse("$FILE_BASE_PATH$destination")
        val file = File(destination)
        if (file.exists()) file.delete()
        val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        val downloadUri = Uri.parse(url)
        val request = DownloadManager.Request(downloadUri)
        request.setMimeType(MIME_TYPE)
        request.setTitle(context.getString(R.string.title_file_download))
        request.setDescription(context.getString(R.string.downloading))
        // set destination
        request.setDestinationUri(uri)
        showInstallOption(destination, uri)
        // Enqueue a new download and same the referenceId
        downloadManager.enqueue(request)
        Toast.makeText(context, context.getString(R.string.downloading), Toast.LENGTH_LONG)
            .show()
    }

    private fun showInstallOption(
        destination: String,
        uri: Uri
    ) {
        // set BroadcastReceiver to install app when .apk is downloaded
        val onComplete = object : BroadcastReceiver() {
            override fun onReceive(
                context: Context,
                intent: Intent
            ) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    val contentUri = FileProvider.getUriForFile(
                        context,
                        BuildConfig.APPLICATION_ID + PROVIDER_PATH,
                        File(destination)
                    )
                    val install = Intent(Intent.ACTION_VIEW)
                    install.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    install.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    install.putExtra(Intent.EXTRA_NOT_UNKNOWN_SOURCE, true)
                    install.data = contentUri
                    val pendingIntent = PendingIntent.getActivity(context, 0, install, PendingIntent.FLAG_UPDATE_CURRENT)
                    val notifications = MyNotifications(context)
                    notifications.createNotification("PB apk Downloaded","Tap to install",pendingIntent)
                    context.startActivity(install)
                    context.unregisterReceiver(this)
                    // finish()
                } else {
                    val install = Intent(Intent.ACTION_VIEW)
                    install.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    install.setDataAndType(
                        uri,
                        APP_INSTALL_PATH
                    )
                    val notifications = MyNotifications(context)
                    val pendingIntent = PendingIntent.getActivity(context, 0, install, PendingIntent.FLAG_UPDATE_CURRENT)
                    notifications.createNotification("PB apk Downloaded","Tap to install",pendingIntent)
                    context.startActivity(install)
                    context.startActivity(install)
                    context.unregisterReceiver(this)
                    // finish()
                }
            }
        }
        context.registerReceiver(onComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
    }

    private fun permissionGiven() =
        ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED

    fun beginDownloadProcess() {
        when (permissionGiven()) {
            true -> checkNewVersionOnline()
            else -> showStoragePermissionRationale()
        }
    }


    private fun showStoragePermissionRationale() =
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            showSnackBar()
        else
            (activity as MainActivity).showErrorSnackMessage("Permission Denied,Please Go to Settings and enable the Permission")


    private fun showSnackBar() =
        Snackbar.make(activity.findViewById(android.R.id.content),
            "Storage Permission is Needed to Perform this Action",
            Snackbar.LENGTH_INDEFINITE
        ).setAction("Allow")
        {
            requestStoragePermission()
        }.show()

    private fun requestStoragePermission() =
        ActivityCompat.requestPermissions(
            activity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
            PERMISSION_REQUEST_STORAGE
        )

    fun onPermissionResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_STORAGE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                checkNewVersionOnline()
            } else {
                (context as MainActivity).showErrorSnackMessage("Storage permission request was denied")
            }
        }
    }

     private fun checkNewVersionOnline() = GlobalScope.launch(Dispatchers.IO) {
         withContext(Dispatchers.Main)
         {
             (activity as MainActivity).showProgressDialog("Checking ....")
         }

        val updateDocument = Firebase.firestore.collection(Constants.UPDATE_COLLECTION)
            .document(Constants.UPDATE_DOCUMENT).get().await().toObject(UpdateModel::class.java)

        withContext(Dispatchers.Main)
        {
            (activity as MainActivity).hideProgressBar()
            if(Constants.CURRENT_APK_VERSION<updateDocument!!.version)
                enqueueDownload(updateDocument.link)
            else
                Toast.makeText(context, "Already on New Version", Toast.LENGTH_SHORT).show();
        }
    }
}