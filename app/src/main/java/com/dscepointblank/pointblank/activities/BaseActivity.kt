package com.dscepointblank.pointblank.activities

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.dscepointblank.pointblank.R

import kotlinx.android.synthetic.main.activity_base.*

open class BaseActivity : AppCompatActivity() {

    private lateinit var  progressbarDialog : ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        progressbarDialog = ProgressDialog(this)
    }

     fun showProgressDialog(message:String)
    {

        progressbarDialog.setMessage(message)
        progressbarDialog.setCanceledOnTouchOutside(false)
        progressbarDialog.setCancelable(false)
        progressbarDialog.show()
    }

    fun hideProgressBar()
    {
        progressbarDialog.dismiss()
    }

    fun closeKeyBoard(view : View?)
    {
        if(view!=null)
        {
            val imm : InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken,0)
        }

    }

    fun showErrorSnackMessage(message:String){
        val snackBar = Snackbar.make(findViewById(android.R.id.content),message,Snackbar.LENGTH_LONG)
        val snackBarView = snackBar.view

        snackBarView.setBackgroundColor(ContextCompat.getColor(this,R.color.redColor))
        snackBar.show()
    }
    fun showToast(message :String)
    {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

}
