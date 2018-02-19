package com.medicine.database.kotlinmedicine.fragments

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.drawable.AnimatedVectorDrawable
import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.support.v4.content.LocalBroadcastManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import com.medicine.database.kotlinmedicine.*
import com.medicine.database.kotlinmedicine.activities.DetailsActivity
import com.medicine.database.kotlinmedicine.activities.MainActivity
import com.medicine.database.kotlinmedicine.models.Illness
import kotlinx.android.synthetic.main.activity_details.*
import kotlinx.android.synthetic.main.fragment_illnesses_list.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread

/**
 * Created by tosya on 17.02.18.
 */
class IllnessesListFragment : Fragment() {

    override fun onSaveInstanceState(outState: Bundle?) {

    }

    companion object {
        private var mIllnessesList: MutableList<Illness> = mutableListOf()
        var adapter = RecyclerCardViewAdapter(mIllnessesList)

        fun newInstance(): IllnessesListFragment {
            return IllnessesListFragment()
        }

        fun bottomFragmentHidden(activity: FragmentActivity) {
            var plusToCross: AnimatedVectorDrawable = activity.resources.getDrawable(R.drawable.avd_plus_to_cross) as AnimatedVectorDrawable

            activity.floatingActionButton.setImageDrawable(plusToCross)
            plusToCross.start()

            activity.floatingActionButton.setOnClickListener {
                DetailsActivity.bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
                activity.supportFragmentManager.fragments.clear()
                activity.supportFragmentManager.beginTransaction()
                        .replace(R.id.details_container_add_illness, AddIllnessFragment.newInstance())
                        .commit()
            }
        }
    }

    private val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            doAsync {
                Thread.sleep(500)
                uiThread {
                    context?.toast("Запись успешно создана!")
                    initList()
                }
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var root = inflater.inflate(R.layout.fragment_illnesses_list, container, false)
        return root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        DetailsActivity.bottomSheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_SETTLING -> {
                        bottomFragmentHidden(activity)
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                println("")
            }

        })

        bottomFragmentHidden(activity)

        LocalBroadcastManager.getInstance(activity)
                .registerReceiver(broadCastReceiver, IntentFilter(LOCAL_RECEIVER_ILLNESSES))
        illnesses_recycler_view.layoutManager = LinearLayoutManager(activity)
        illnesses_recycler_view.adapter = IllnessesListFragment.adapter

        val swipeHandler = object : SwipeToDeleteCallback(activity) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                doAsync {
                    MainActivity.mMedicineDB.deleteIllnessByID(mIllnessesList.get(viewHolder.adapterPosition).id)
                    uiThread {
                        adapter.clearList()
                        illnesses_recycler_view.swapAdapter(IllnessesListFragment.adapter, true)
                        initList()
                    }
                }
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(illnesses_recycler_view)

        initList()
        super.onActivityCreated(savedInstanceState)
    }

    fun initList() {
        doAsync {
            IllnessesListFragment.mIllnessesList = MainActivity.mMedicineDB.selectAllIllnessForPatientId(DetailsActivity.patientID)
            uiThread {
                if (mIllnessesList.isEmpty()) {
                    empty_view?.visibility = VISIBLE
                } else {
                    empty_view?.visibility = GONE
                }
                IllnessesListFragment.adapter.updateList(IllnessesListFragment.mIllnessesList)
            }
        }
    }

    class RecyclerCardViewAdapter(private var list: MutableList<Illness>) : RecyclerView.Adapter<RecyclerCardViewAdapter.CardViewHolder>() {

        init {
            setHasStableIds(true)
        }

        class CardViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {

            override fun onClick(v: View?) {
                DetailsActivity.change = true
                LocalBroadcastManager.getInstance(App.instance)
                        .sendBroadcast(Intent(LOCAL_RECEIVER_CHANGE_ILLNESS)
                                .putExtra(LOCAL_RECEIVER_EXTRAS_COMMANDS, mIllnessesList[itemId.toInt()]))
            }

            var illnessName: TextView? = null
            var date: TextView? = null

            init {
                view.setOnClickListener(this)
                this.illnessName = view.findViewById(R.id.illness_name_card_view)
                this.date = view.findViewById(R.id.illness_date_card_view)
            }
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
            val root = LayoutInflater.from(parent.context).inflate(R.layout.illness_card, parent, false)
            return CardViewHolder(root)
        }

        override fun getItemCount(): Int {
            return list.size
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun onBindViewHolder(holder: CardViewHolder?, position: Int) {
            val illness = list.get(position)
            holder?.illnessName?.text = illness.illnessName
            holder?.date?.text = illness.illnessStartDate
        }

        fun updateList(aIllnessList: MutableList<Illness>) {
            list = aIllnessList
            this.notifyDataSetChanged()
        }

        fun clearList() {
            list.clear()
        }
    }
}