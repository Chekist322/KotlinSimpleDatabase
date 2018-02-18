package com.medicine.database.kotlinmedicine.database

import android.database.sqlite.SQLiteDatabase
import com.medicine.database.kotlinmedicine.App
import org.jetbrains.anko.db.*

/**
 * Created by tosya on 16.02.18.
 */

object PatientTable {
    const val NAME = "PatientTable"
    const val _ID = "_id"
    const val PATIENT_NAME = "name"
    const val PATIENT_SURNAME = "surname"
    const val PATIENT_FATHERS_NAME = "fathers_name"
    const val PATIENT_AGE = "age"
}

object IllnessTable {
    const val NAME = "IllnessTable"
    const val _ID = "_id"
    const val PATIENT_ID = "patient_id"
    const val ILLNESS_NAME = "illness_name"
    const val ILLNESS_AGE = "illness_age"
}

class DBHelper : ManagedSQLiteOpenHelper(App.instance, DB_NAME, null, DB_VERSION) {

    companion object {
        const val DB_NAME = "patients.db"
        val DB_VERSION = 1

        fun instance(): DBHelper {
            return DBHelper()
        }
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.createTable(PatientTable.NAME, true,
                PatientTable._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                PatientTable.PATIENT_NAME to TEXT,
                PatientTable.PATIENT_SURNAME to TEXT,
                PatientTable.PATIENT_FATHERS_NAME to TEXT,
                PatientTable.PATIENT_AGE to INTEGER)

        db.createTable(IllnessTable.NAME, true,
                IllnessTable._ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                IllnessTable.PATIENT_ID to INTEGER,
                IllnessTable.ILLNESS_NAME to TEXT,
                IllnessTable.ILLNESS_AGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.dropTable(PatientTable.NAME, true)
        db.dropTable(IllnessTable.NAME, true)
        onCreate(db)
    }
}