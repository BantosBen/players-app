package com.nerdpros.newshome.ui.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nerdpros.newshome.R
import com.nerdpros.newshome.databinding.ActivityGenderBinding
import com.nerdpros.newshome.ui.fragment.FemaleFragment
import com.nerdpros.newshome.ui.fragment.MaleFragment
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems

class GenderActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGenderBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = FragmentPagerItemAdapter(
            supportFragmentManager, FragmentPagerItems.with(this)
                .add("Male Players", MaleFragment::class.java)
                .add("Female Players", FemaleFragment::class.java)
                .create()
        )
        binding.toolbar.setNavigationOnClickListener { onBackPressed() }
        val viewPager = binding.viewPager
        viewPager.adapter = adapter
        binding.smartTabLayout.setViewPager(viewPager)
    }
}