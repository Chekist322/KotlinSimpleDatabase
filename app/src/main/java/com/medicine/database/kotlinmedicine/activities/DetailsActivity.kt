package com.medicine.database.kotlinmedicine.activities

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.medicine.database.kotlinmedicine.*
import com.medicine.database.kotlinmedicine.fragments.AddIllnessFragment
import com.medicine.database.kotlinmedicine.fragments.IllnessesListFragment
import com.medicine.database.kotlinmedicine.models.Illness
import com.medicine.database.kotlinmedicine.models.Patient
import kotlinx.android.synthetic.main.activity_details.*

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
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container_add_illness, AddIllnessFragment.newInstance())
                        .commitAllowingStateLoss()
            }
        }
    }

    companion object {
        var patient: Patient? = null
        var patientID: Long? = null
        lateinit var bottomSheetBehavior: BottomSheetBehavior<View>
        var change = false
        lateinit var illnessToChange: Illness
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        bottomSheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
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

//            floatingActionButton.setOnClickListener {
//                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//                supportFragmentManager.beginTransaction()
//                        .add(R.id.details_container_add_illness, AddIllnessFragment.newInstance())
//                        .commit()
//                bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
//                    override fun onSlide(bottomSheet: View, slideOffset: Float) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                    override fun onStateChanged(bottomSheet: View, newState: Int) {
//                        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//                    }
//
//                })
//
//            }

            supportFragmentManager.beginTransaction()
                    .add(R.id.details_container, IllnessesListFragment.newInstance())
                    .commit()

            LocalBroadcastManager.getInstance(App.instance)
                    .registerReceiver(broadCastReceiver, IntentFilter(LOCAL_RECEIVER_CHANGE_ILLNESS))
        }
    }
}