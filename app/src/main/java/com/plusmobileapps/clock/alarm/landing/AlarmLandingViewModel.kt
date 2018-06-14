package com.plusmobileapps.clock.alarm.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plusmobileapps.clock.data.AlarmRepository
import com.plusmobileapps.clock.data.entities.Alarm
import javax.inject.Inject


class AlarmLandingViewModel @Inject constructor(private val alarmRepository: AlarmRepository): ViewModel() {

    private val alarms: LiveData<List<Alarm>> = alarmRepository.getAlarms()
    val showTimePickerToggle = MutableLiveData<Boolean>()

    init {
        showTimePickerToggle.value = false
    }

    fun getAlarms(): LiveData<List<Alarm>> = alarms

    fun getAlarm(position: Int): Alarm? {
        return alarms.value?.get(position)
    }

    fun getAlarmId(position: Int): Int? {
        return alarms.value?.get(position)?.id
    }

    fun getAlarmById(id: Int): LiveData<Alarm> = alarmRepository.getAlarm(id)

    fun addAlarm(hour: Int, min: Int) {
        val alarm = Alarm(hour = hour, min = min)
        alarmRepository.saveAlarm(alarm)
    }

    fun updateAlarm(alarm: Alarm) = alarmRepository.saveAlarm(alarm)


    fun updateAlarmToggle(enabled: Boolean, position: Int) {
        if (alarms.value != null) {
            val alarm = alarms.value!![position]
            alarm.enabled = enabled
            alarmRepository.saveAlarm(alarm)
        }
    }

    fun showTimePicker() {
        showTimePickerToggle.value = true
    }

    fun deleteAlarm(alarm: Alarm) {
        alarmRepository.deleteAlarm(alarm)
    }

}