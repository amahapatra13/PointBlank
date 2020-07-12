package com.dscepointblank.pointblank.fragments.onbording

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.dscepointblank.pointblank.R
import kotlinx.android.synthetic.main.fragment_write_up.view.*

class WriteUpFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =inflater.inflate(R.layout.fragment_write_up, container, false)

        val viewPager = requireActivity().findViewById<ViewPager2>(R.id.viewPager)

        view.writeUpNext .setOnClickListener { viewPager.currentItem=2 }

        return view
    }
}