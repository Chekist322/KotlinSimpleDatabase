package com.medicine.database.kotlinmedicine

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.medicine.database.kotlinmedicine.R.id.recycler_view
import com.medicine.database.kotlinmedicine.activities.DetailsActivity
import com.medicine.database.kotlinmedicine.activities.MainActivity
import com.medicine.database.kotlinmedicine.fragments.ListFragment
import com.medicine.database.kotlinmedicine.models.Patient
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

/**
 * Created by tosya on 18.02.18.
 */
class RecyclerViewAdapter(private var list: MutableList<Patient>) : RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {


        override fun onClick(v: View?) {
            val intent = Intent(v?.context, DetailsActivity::class.java)
            intent.putExtra(PATIENT_ID, itemId+1)
            v?.context?.startActivity(intent)
        }

        var name: TextView? = null
        var age: TextView? = null
        var id: Long? = null

        init {
            view.setOnClickListener(this)
            this.name = view.findViewById(R.id.patient_name_card_view)
            this.age = view.findViewById(R.id.patient_date_card_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val root = LayoutInflater.from(parent.context).inflate(R.layout.patient_card, parent, false)
        return ViewHolder(root)
    }

    override fun getItemCount(): Int {
        return list?.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    fun removeAt(position: Int) {
        MainActivity.mMedicineDB.deleteIllnessByID(position.toLong())
        list.removeAt(position)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        val patient = list?.get(position)
        val fullName = patient?.surname + " " + patient?.name + " " + patient?.fathers_name
        holder?.name?.text = fullName
        holder?.age?.text = patient?.age.toString()
        holder?.id = position.toLong()
    }

    fun updateList(aPatientList: MutableList<Patient>) {
        list = aPatientList
        notifyDataSetChanged()
    }

    fun clearList() {
        list.clear()
    }
}