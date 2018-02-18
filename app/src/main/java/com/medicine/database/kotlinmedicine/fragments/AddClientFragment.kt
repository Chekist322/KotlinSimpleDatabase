package com.medicine.database.kotlinmedicine.fragments

import android.content.Intent
import android.support.v4.app.Fragment
import android.os.Bundle
import android.support.v4.content.LocalBroadcastManager
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicine.database.kotlinmedicine.*
import com.medicine.database.kotlinmedicine.activities.MainActivity
import com.medicine.database.kotlinmedicine.models.Patient
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_client.*
import org.jetbrains.anko.toast

/**
 * Created by tosya on 16.02.18.
 */
class AddClientFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_add_client, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        clear_tables_button.setOnClickListener {
            MainActivity.mMedicineDB.dropAllTables()
            LocalBroadcastManager.getInstance(activity)
                    .sendBroadcast(Intent(LOCAL_RECEIVER)
                    .putExtra(LOCAL_RECEIVER_EXTRAS_COMMANDS, UPDATE_LIST_CLEAR))
            val tab = activity.tabs.getTabAt(1)
            tab?.select()
        }

        add_button.setOnClickListener{
            var valid = true

            val name = date_pick_view.text.toString()
            if (TextUtils.isEmpty(name)) {
                valid = false
                date_pick_view.error = "Введите имя!"
            }

            val surname = illness_edit_text.text.toString()
            if (TextUtils.isEmpty(surname)) {
                valid = false
                illness_edit_text.error = "Введите фамилию.!"
            }

            val fathersName = fathers_name_edit_text.text.toString()
            if (TextUtils.isEmpty(fathersName)) {
                valid = false
                fathers_name_edit_text.error = "Введите отчество!"
            }

            val age = age_edit_text.text.toString()
            if (TextUtils.isEmpty(age)) {
                valid = false
                age_edit_text.error = "Введите возраст!"
            }

            if (valid) {
                val patient = Patient(0, name, surname, fathersName, age.toLong())
                MainActivity.mMedicineDB.insertPatient(patient)
                val tab = activity.tabs.getTabAt(1)
                tab?.select()
                LocalBroadcastManager.getInstance(activity)
                        .sendBroadcast(Intent(LOCAL_RECEIVER)
                                .putExtra(LOCAL_RECEIVER_EXTRAS_COMMANDS, UPDATE_LIST_ADD))
            }
        }
        super.onActivityCreated(savedInstanceState)
    }
}