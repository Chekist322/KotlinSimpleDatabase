package com.medicine.database.kotlinmedicine

import com.medicine.database.kotlinmedicine.models.Illness
import com.medicine.database.kotlinmedicine.models.Patient
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.classParser

/**
 * Created by tosya on 16.02.18.
 */
class MedicineParser {

    private val rowPatientParser = classParser<Patient>()
    private val rowIllnessParser = classParser<Illness>()

    fun parseAllPatients(builder: SelectQueryBuilder): List<Patient> {
        return builder.parseList(rowPatientParser)
    }

    fun parseSinglePatient(builder: SelectQueryBuilder): Patient {
        return builder.parseSingle(rowPatientParser)
    }
}