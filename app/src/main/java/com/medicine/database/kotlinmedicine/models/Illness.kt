package com.medicine.database.kotlinmedicine.models

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by tosya on 16.02.18.
 */
data class Illness(var id: Long, var patientID: Long?, var illnessName: String, var illnessStartDate: String): Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readLong(),
            parcel.readValue(Long::class.java.classLoader) as? Long,
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeLong(id)
        parcel.writeValue(patientID)
        parcel.writeString(illnessName)
        parcel.writeString(illnessStartDate)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Illness> {
        override fun createFromParcel(parcel: Parcel): Illness {
            return Illness(parcel)
        }

        override fun newArray(size: Int): Array<Illness?> {
            return arrayOfNulls(size)
        }
    }
}