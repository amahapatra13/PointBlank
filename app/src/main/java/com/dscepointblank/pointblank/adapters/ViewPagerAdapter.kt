package com.dscepointblank.pointblank.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(list: List<Fragment>, fm: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fm, lifecycle) {

    private val fragList: List<Fragment> = list
    override fun getItemCount(): Int = fragList.size

    override fun createFragment(position: Int): Fragment = fragList[position]
}