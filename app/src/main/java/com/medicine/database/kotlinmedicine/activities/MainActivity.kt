package com.medicine.database.kotlinmedicine.activities

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import com.medicine.database.kotlinmedicine.R
import com.medicine.database.kotlinmedicine.database.MedicineDB
import com.medicine.database.kotlinmedicine.fragments.AddClientFragment
import com.medicine.database.kotlinmedicine.fragments.ListFragment
import com.medicine.database.kotlinmedicine.fragments.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
        val mMedicineDB: MedicineDB = MedicineDB()
        var mSectionsPagerAdapter: SectionsPagerAdapter? = null
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)
        container.adapter = mSectionsPagerAdapter
        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))
        val tab = tabs.getTabAt(1)
        tab?.select()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(R.anim.left_in, R.anim.left_out)
    }

    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            when (position) {
                0 -> return SearchFragment()
                1 -> return ListFragment()
                2 -> return AddClientFragment()
            }
            return Fragment()
        }

        override fun getCount(): Int {
            return 3
        }
    }
}
