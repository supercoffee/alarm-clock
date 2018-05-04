package com.plusmobileapps.clock.alarm.landing

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Switch
import android.widget.TextView
import com.plusmobileapps.clock.R
import com.plusmobileapps.clock.data.entities.Alarm
import kotlinx.android.synthetic.main.view_holder_alarm.view.*

interface AlarmItemListener {
    fun alarmItemClicked(alarm: Alarm)
    fun alarmTimeClicked(alarm: Alarm)
    fun alarmSwitchToggled(alarm: Alarm, isEnabled: Boolean)
}

class AlarmAdapter(var alarms: List<Alarm>, private val itemListener: AlarmItemListener) : RecyclerView.Adapter<AlarmAdapter.AlarmViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlarmViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.view_holder_alarm, parent, false)
        return AlarmViewHolder(view, itemListener)
    }

    override fun getItemCount(): Int = alarms.size

    override fun onBindViewHolder(holder: AlarmViewHolder, position: Int) {
        val alarm = alarms[position]
        holder.bind(alarm)
    }

    class AlarmViewHolder(itemView: View, private val itemListener: AlarmItemListener) : RecyclerView.ViewHolder(itemView) {
        private val alarmToggle: Switch = itemView.alarm_toggle
        private val alarmTime: TextView = itemView.edit_time_button

        fun bind(alarm: Alarm) {
            alarmToggle.isChecked = alarm.enabled
            alarmTime.text = alarm.printTime()

            alarmTime.setOnClickListener{
                itemListener.alarmTimeClicked(alarm)
            }
            itemView.setOnClickListener{
                itemListener.alarmItemClicked(alarm)
            }
            alarmToggle.setOnCheckedChangeListener{ _, isChecked ->
                itemListener.alarmSwitchToggled(alarm, isChecked)
            }
        }
    }

}