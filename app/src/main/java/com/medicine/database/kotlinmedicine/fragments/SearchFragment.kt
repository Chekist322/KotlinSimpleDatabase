package com.medicine.database.kotlinmedicine.fragments

import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleCursorAdapter
import com.medicine.database.kotlinmedicine.R
import com.medicine.database.kotlinmedicine.RecyclerViewAdapter
import com.medicine.database.kotlinmedicine.activities.MainActivity
import com.medicine.database.kotlinmedicine.database.IllnessTable
import com.medicine.database.kotlinmedicine.database.PatientTable
import com.medicine.database.kotlinmedicine.models.Patient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_search.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by tosya on 16.02.18.
 */
class SearchFragment : Fragment() {

    companion object {
        private var mPatientsList: List<Patient> = listOf()
        var adapter = RecyclerViewAdapter(mPatientsList)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        search_result_recycler_view.layoutManager = LinearLayoutManager(activity)
        search_result_recycler_view.adapter = adapter

        surname_search_edit_text.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(s: Editable?) {
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                doAsync {
                    mPatientsList = MainActivity.mMedicineDB.dynamicPatientsSelection(s.toString())
                    uiThread {
                        adapter.updateList(mPatientsList)
                    }
                }
            }

        })
        super.onActivityCreated(savedInstanceState)
    }
}