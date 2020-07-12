package com.dscepointblank.pointblank.fragments.onbording

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.dscepointblank.pointblank.R
import kotlinx.android.synthetic.main.fragment_coding_contests.view.*


class CodingContests : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_coding_contests, container, false)
        view.codingFinish.setOnClickListener {
            onBoardingFinished()
            findNavController().navigate(R.id.action_viewPagerFragment_to_loginFragment)
        }

        return view
    }

    private fun onBoardingFinished() {
        val prefs = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val edit = prefs.edit()
        edit.putBoolean("finish", true)
        edit.apply()
    }
}