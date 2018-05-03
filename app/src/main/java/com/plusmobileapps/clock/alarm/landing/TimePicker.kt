package com.plusmobileapps.clock.alarm.landing

import android.app.TimePickerDialog
import android.content.Context
import com.plusmobileapps.clock.data.entities.Alarm
import java.util.*

interface TimePicker {
    fun pickTime(context: Context, alarm: Alarm)
}

class TimePickerImpl(private val alarmsViewModel: AlarmsViewModel): TimePicker {

    override fun pickTime(context: Context, alarm: Alarm) {
        val c = Calendar.getInstance()
        val hour = alarm.hour ?: c.get(Calendar.HOUR_OF_DAY)
        val minute = alarm.min ?: c.get(Calendar.MINUTE)
        val listener = if (alarm.isNewInstance()) {
            addAlarmTimeListener
        } else {
            updateAlarmTimeListener(alarm)
        }
        val dialog = TimePickerDialog(context, listener, hour, minute,true)
        dialog.show()
    }

    private val addAlarmTimeListener =
            TimePickerDialog.OnTimeSetListener { _: android.widget.TimePicker, hour: Int, min: Int ->
        alarmsViewModel.addAlarm(hour, min)
    }

    private fun updateAlarmTimeListener(alarm: Alarm) =
            TimePickerDialog.OnTimeSetListener { _: android.widget.TimePicker, hour: Int, min: Int ->
                alarmsViewModel.updateAlarm(alarm.copy(hour = hour, min = min))
    }


}