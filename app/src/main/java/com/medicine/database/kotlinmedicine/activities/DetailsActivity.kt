package com.medicine.database.kotlinmedicine.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import com.medicine.database.kotlinmedicine.*
import com.medicine.database.kotlinmedicine.fragments.AddIllnessFragment
import com.medicine.database.kotlinmedicine.fragments.IllnessesListFragment
import com.medicine.database.kotlinmedicine.models.Illness
import com.medicine.database.kotlinmedicine.models.Patient
import kotlinx.android.synthetic.main.activity_details.*
import org.jetbrains.anko.act

/**
 * Created by tosya on 17.02.18.
 */
class DetailsActivity : AppCompatActivity() {


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            if (intent != null) {
                illnessToChange = intent.getParcelableExtra(LOCAL_RECEIVER_EXTRAS_COMMANDS)
                supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container, AddIllnessFragment.newInstance())
                        .addToBackStack(act.title as String?)
                        .commitAllowingStateLoss()
            }
        }
    }

    companion object {
        var patient: Patient? = null
        var patientID: Long? = null
        var change = false
        lateinit var illnessToChange: Illness
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        if (intent.extras != null) {
            patientID = intent.extras.getLong(PATIENT_ID)
            patient = MainActivity.mMedicineDB.selectDetailOnePatient(patientID)

            name_view.text = patient?.name
            surname_view.text = patient?.surname
            fathers_name_view.text = patient?.fathers_name
            age_view.text = patient?.age.toString()

            home_back_arrow.setOnClickListener {
                finish()
            }

            floatingActionButton.setOnClickListener {
                supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container, AddIllnessFragment.newInstance())
                        .addToBackStack(this.title as String?)
                        .commit()
            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.details_container, IllnessesListFragment.newInstance())
                    .commit()

            LocalBroadcastManager.getInstance(App.instance)
                    .registerReceiver(broadCastReceiver, IntentFilter(LOCAL_RECEIVER_CAHNGE_ILLNESS))
        }
    }
}