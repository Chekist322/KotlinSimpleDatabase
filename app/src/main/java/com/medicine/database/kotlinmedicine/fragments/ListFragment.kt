package com.medicine.database.kotlinmedicine.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicine.database.kotlinmedicine.*
import com.medicine.database.kotlinmedicine.activities.MainActivity
import com.medicine.database.kotlinmedicine.models.Patient
import kotlinx.android.synthetic.main.fragment_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * Created by tosya on 16.02.18.
 */
class ListFragment : Fragment() {

    companion object {
        lateinit var recyclerView: RecyclerView
        var mPatientsList: MutableList<Patient> = mutableListOf()
        var adapter = RecyclerViewAdapter(mPatientsList)
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.extras?.getString(LOCAL_RECEIVER_EXTRAS_COMMANDS)) {
                UPDATE_LIST_ADD -> doAsync {
                    Thread.sleep(500)
                    uiThread {
                        context?.toast("Запись успешно добавлена!")
                        initList()
                    }
                }
                UPDATE_LIST_CLEAR -> doAsync {
                    Thread.sleep(500)
                    uiThread {
                        context?.toast("База очищена...")
                        initList()
                    }
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        recyclerView = recycler_view
        LocalBroadcastManager.getInstance(activity)
                .registerReceiver(broadCastReceiver, IntentFilter(LOCAL_RECEIVER))
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        initList()
        super.onActivityCreated(savedInstanceState)
    }


    fun initList() {
        doAsync {
            mPatientsList = MainActivity.mMedicineDB.selectAllPatients()
            uiThread {
                if (mPatientsList.isEmpty()) {
                    empty_patient_view.visibility = View.VISIBLE
                } else {
                    empty_patient_view.visibility = View.GONE
                }
                adapter.updateList(mPatientsList)
            }
        }
    }
}

