package com.medicine.database.kotlinmedicine.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import com.medicine.database.kotlinmedicine.*
import com.medicine.database.kotlinmedicine.activities.DetailsActivity
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

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            when (intent?.extras?.getString(LOCAL_RECEIVER_EXTRAS_COMMANDS)) {
                UPDATE_LIST_ADD ->  doAsync {
                    Thread.sleep(500)
                    uiThread {
                        activity.toast("Запись успешно добавлена!")
                        initList()
                    }
                }
                UPDATE_LIST_CLEAR -> doAsync {
                    Thread.sleep(500)
                    uiThread {
                        activity.toast("База очищена...")
                        initList()
                    }
                }
            }
        }
    }


    private var mPatientsList: List<Patient>? = null
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mPatientsList = MainActivity.mMedicineDB.selectAllPatients()
        linearLayoutManager = LinearLayoutManager(activity.applicationContext)
        return inflater.inflate(R.layout.fragment_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        LocalBroadcastManager.getInstance(activity)
                .registerReceiver(broadCastReceiver, IntentFilter(LOCAL_RECEIVER))

        initList()
        super.onActivityCreated(savedInstanceState)
    }


    fun initList() {
        mPatientsList = MainActivity.mMedicineDB.selectAllPatients()
        var arrayList: ArrayList<HashMap<String, String>> = arrayListOf()
        mPatientsList?.forEach {
            var newMap: HashMap<String, String> = hashMapOf()
            newMap["Name"] = it.name + " " + it.surname + " " + it.fathers_name

            newMap["Age"] = it.age.toString()
            arrayList.add(arrayList.size, newMap)
        }

        val listView = list_view
        listView?.adapter = SimpleAdapter(activity, arrayList,
                android.R.layout.simple_list_item_2, arrayOf("Name","Age"),
                intArrayOf(android.R.id.text1, android.R.id.text2))

//        listView.setOnItemClickListener { parent, view, position, id ->
//            val intent = Intent(activity, DetailsActivity::class.java)
//            intent.putExtra(PATIENT_ID, id+1)
//            startActivity(intent)
//        }
    }


    class ReciclerViewAdapter: RecyclerView.Adapter<ReciclerViewAdapter.ViewHolder>() {

        private var patientsList: List<Patient>? = null

        class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
            lateinit var name: String
            lateinit var age: String


        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            var root = LayoutInflater.from(parent.context).inflate(android.R.layout.simple_list_item_2, parent, false)
            return ViewHolder(root)
        }

        override fun getItemCount(): Int {

        }

        override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        }

        fun updateList(aPatientList: List<Patient>) {
            patientsList = aPatientList
            notifyDataSetChanged()
        }
    }
}

