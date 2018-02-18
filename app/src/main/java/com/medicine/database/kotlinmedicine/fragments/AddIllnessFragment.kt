package com.medicine.database.kotlinmedicine.fragments

import android.app.DatePickerDialog
import android.graphics.drawable.AnimatedVectorDrawable
import android.icu.util.Calendar
import android.os.Bundle
import android.support.v4.app.Fragment
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.medicine.database.kotlinmedicine.R
import com.medicine.database.kotlinmedicine.activities.DetailsActivity
import com.medicine.database.kotlinmedicine.activities.MainActivity
import com.medicine.database.kotlinmedicine.models.Illness
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_add_illness.*
import java.util.*


/**
 * Created by tosya on 17.02.18.
 */
class AddIllnessFragment : Fragment(), View.OnClickListener {

    override fun onSaveInstanceState(outState: Bundle?) {

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_add_illness, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val calendar = Calendar.getInstance(Locale.ENGLISH)
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        var dayStr = day.toString()
        if (day < 10) {
            dayStr = "0" + day.toString()
        }
        val monthStr = Months.values()[month]

        val yearStr = year.toString()
        val date = "$dayStr $monthStr $yearStr года"
        date_edit_text.setText(date)


        activity.floatingActionButton.setOnClickListener {
            var validated = true
            if (TextUtils.isEmpty(date_edit_text.text.toString())){
                illness_name_edit_text.error = "Введите Наименование!"
                validated = false
            }
            if (validated)
            if (!DetailsActivity.change) {
                val illness = Illness(0, DetailsActivity.patientID, illness_name_edit_text.text.toString(), date_edit_text.text.toString())
                MainActivity.mMedicineDB.insertIllness(illness)
                date_edit_text.text.clear()
                illness_name_edit_text.text.clear()
                activity.onBackPressed()
            } else {
                val illness = Illness(DetailsActivity.illnessToChange.id, DetailsActivity.patientID, illness_name_edit_text.text.toString(), date_edit_text.text.toString())
                MainActivity.mMedicineDB.updateIllness(illness)
                date_edit_text.text.clear()
                illness_name_edit_text.text.clear()
                DetailsActivity.change = false
                activity.onBackPressed()
            }
        }

        var crossToPlus: AnimatedVectorDrawable = activity.resources.getDrawable(R.drawable.avd_cross_to_plus, null) as AnimatedVectorDrawable

        activity.floatingActionButton.setImageDrawable(crossToPlus)
        crossToPlus.start()

        if (DetailsActivity.change) {
            adding_title.text = "Изменение Записи"
            date_edit_text.setText(DetailsActivity.illnessToChange.illnessStartDate)
            illness_name_edit_text.setText(DetailsActivity.illnessToChange.illnessName)
        }


        back_button.setOnClickListener(this)
        date_edit_text.setOnClickListener(this)
        super.onActivityCreated(savedInstanceState)
    }

    companion object {
        fun newInstance() : AddIllnessFragment {
            return AddIllnessFragment()
        }
    }

    override fun onDestroyView() {
        DetailsActivity.change = false
        super.onDestroyView()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.back_button -> {
                DetailsActivity.change = false
                activity.onBackPressed()
            }
            R.id.date_edit_text -> {
                val calendar = Calendar.getInstance(Locale.ENGLISH)
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val dpd = DatePickerDialog(activity, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    var dayStr = dayOfMonth.toString()
                    if (dayOfMonth < 10) {
                        dayStr = "0" + dayOfMonth.toString()
                    }
                    val monthStr = Months.values()[monthOfYear]

                    val yearStr = year.toString()
                    val date = "$dayStr $monthStr $yearStr года"
                    date_edit_text.setText(date)
                }, year, month, day)
                dpd.show()
            }
        }
    }

    enum class Months{
        января,
        февраля,
        марта,
        апреля,
        мая,
        июня,
        июля,
        августа,
        сентября,
        октября,
        ноября,
        декабря
    }
}