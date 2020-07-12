package com.dscepointblank.pointblank.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController

import com.dscepointblank.pointblank.R




class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Handler().postDelayed( {
            if (!getOnBoardingValue())
            findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            else
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        },2000)

        return inflater.inflate(R.layout.fragment_splash, container, false)
    }

    private fun getOnBoardingValue():Boolean
    {
        val prefs = requireActivity().getSharedPreferences("onBoarding",Context.MODE_PRIVATE)
        return prefs.getBoolean("finish",false)
    }
}
