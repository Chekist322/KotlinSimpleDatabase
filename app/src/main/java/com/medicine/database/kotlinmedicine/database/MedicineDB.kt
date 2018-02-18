package com.medicine.database.kotlinmedicine.database

import com.medicine.database.kotlinmedicine.MedicineParser
import com.medicine.database.kotlinmedicine.models.Illness
import com.medicine.database.kotlinmedicine.models.Patient
import org.jetbrains.anko.db.*

/**
 * Created by tosya on 16.02.18.
 */
class MedicineDB(private val patientsDB: DBHelper = DBHelper.instance()) {

    fun insertPatient(patient: Patient) {
        patientsDB.use {
            val ID = insert(PatientTable.NAME,
                    PatientTable.PATIENT_NAME to patient.name,
                    PatientTable.PATIENT_SURNAME to patient.surname,
                    PatientTable.PATIENT_FATHERS_NAME to patient.fathers_name,
                    PatientTable.PATIENT_AGE to patient.age)
        }
    }

    fun insertIllness(illness: Illness, patientID: Long) {
        patientsDB.use {
            insert(IllnessTable.NAME,
                    IllnessTable.ILLNESS_NAME to illness.illnessName,
                    IllnessTable.ILLNESS_AGE to illness.illnessAge,
                    IllnessTable.PATIENT_ID to patientID)
        }
    }

    fun selectAllPatients(): List<Patient>? {
        var list: List<Patient>? = null
        patientsDB.use {
            list = MedicineParser().parseAllPatients(select(PatientTable.NAME))
        }
        return list
    }

    fun selectDetailOnePatient(whereId: Long): Patient? {
        var patient: Patient? = null
        patientsDB.use {
            patient = MedicineParser().parseSinglePatient(select(PatientTable.NAME).whereArgs("_id = " + whereId))
        }
        return patient
    }

    fun dropAllTables() {
        patientsDB.use {
            dropTable(PatientTable.NAME, true)
            dropTable(IllnessTable.NAME, true)
            createTable(PatientTable.NAME, true,
                    PatientTable._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    PatientTable.PATIENT_NAME to TEXT,
                    PatientTable.PATIENT_SURNAME to TEXT,
                    PatientTable.PATIENT_FATHERS_NAME to TEXT,
                    PatientTable.PATIENT_AGE to INTEGER)

            createTable(IllnessTable.NAME, true,
                    IllnessTable._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                    IllnessTable.PATIENT_ID to INTEGER,
                    IllnessTable.ILLNESS_NAME to TEXT,
                    IllnessTable.ILLNESS_AGE to INTEGER)
        }
    }
}