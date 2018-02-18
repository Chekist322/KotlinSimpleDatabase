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
class AddIllnessFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_add_illness, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity.floatingActionButton.setOnClickListener {
            activity.onBackPressed()
        }

        var crossToPlus: AnimatedVectorDrawable = activity.resources.getDrawable(R.drawable.avd_cross_to_plus) as AnimatedVectorDrawable

        activity.floatingActionButton.setImageDrawable(crossToPlus)
        crossToPlus.start()

        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() : AddIllnessFragment {
            return AddIllnessFragment()
        }
    }
}