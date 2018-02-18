package com.medicine.database.kotlinmedicine.fragments

import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicine.database.kotlinmedicine.R
import kotlinx.android.synthetic.main.activity_details.*

/**
 * Created by tosya on 17.02.18.
 */
class IllnessesListFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_illnesses_list, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity.floatingActionButton.setOnClickListener {
            activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container, AddIllnessFragment.newInstance())
                        .addToBackStack(activity.title as String?)
                        .commit()
        }

        var plusToCross: AnimatedVectorDrawable = activity.resources.getDrawable(R.drawable.avd_plus_to_cross) as AnimatedVectorDrawable

        activity.floatingActionButton.setImageDrawable(plusToCross)
        plusToCross.start()
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() : IllnessesListFragment {
            return IllnessesListFragment()
        }
    }

}