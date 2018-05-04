package com.plusmobileapps.clock.alarm.landing

import android.arch.lifecycle.Observer
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.plusmobileapps.clock.MyApplication
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.alarm.detail.AlarmDetailActivity
import com.plusmobileapps.clock.data.entities.Alarm
import kotlinx.android.synthetic.main.fragment_alarm.*
import javax.inject.Inject

const val EXTRA_ALARM_ID = "alarm_id"

class AlarmFragment : Fragment(){

    companion object {
        fun newInstance(): AlarmFragment {
            return AlarmFragment()
        }
    }

    @Inject
    lateinit var viewModel: AlarmsViewModel

    @Inject
    lateinit var timePicker: com.plusmobileapps.clock.alarm.landing.TimePicker

    private val itemListener = object : AlarmItemListener {
        override fun alarmItemClicked(alarm: Alarm) {
            alarm.id?.let {
                val intent = Intent(context, AlarmDetailActivity::class.java)
                intent.putExtra(EXTRA_ALARM_ID, it)
                startActivity(intent)
            }
        }

        override fun alarmTimeClicked(alarm: Alarm) {
            alarm.let {
                timePicker.pickTime(activity, alarm)
            }
        }

        override fun alarmSwitchToggled(alarm: Alarm, isEnabled: Boolean) {
            viewModel.updateAlarmToggle(isEnabled, alarm)
        }
    }

    private val alarmAdapter by lazy {
        AlarmAdapter(listOf(), itemListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApplication.appComponent.inject(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        add_alarm_button.setOnClickListener { timePicker.pickTime(activity, Alarm()) }
        recycler_view.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = alarmAdapter
        }
        subscribe()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_alarm, container, false)
    }

    private fun subscribe() {
        val alarmsObserver = Observer<List<Alarm>>() {
            if(it != null){
                alarmAdapter.apply {
                    alarms = it
                    notifyDataSetChanged()
                }
            }
        }
        viewModel.getAlarms().observe(this, alarmsObserver)
    }
    
}