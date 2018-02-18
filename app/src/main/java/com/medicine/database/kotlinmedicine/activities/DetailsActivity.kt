package com.medicine.database.kotlinmedicine.activities

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AppCompatActivity
import com.medicine.database.kotlinmedicine.PATIENT_ID
import com.medicine.database.kotlinmedicine.R
import com.medicine.database.kotlinmedicine.R.id.floatingActionButton
import com.medicine.database.kotlinmedicine.fragments.AddIllnessFragment
import com.medicine.database.kotlinmedicine.fragments.IllnessesListFragment
import com.medicine.database.kotlinmedicine.models.Illness
import com.medicine.database.kotlinmedicine.models.Patient
import kotlinx.android.synthetic.main.activity_details.*

/**
 * Created by tosya on 17.02.18.
 */
class DetailsActivity: AppCompatActivity() {

    lateinit var illnesses: ArrayList<Illness>

    companion object {
        var patient: Patient? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        if (intent.extras != null) {
            var patientID = intent.extras.getLong(PATIENT_ID)
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
        }
    }
}