package com.plusmobileapps.clock.di

import com.plusmobileapps.clock.alarm.landing.AlarmsViewModel
import com.plusmobileapps.clock.alarm.landing.TimePicker
import com.plusmobileapps.clock.alarm.landing.TimePickerImpl
import com.plusmobileapps.clock.data.AlarmRepository
import dagger.Module
import dagger.Provides

@Module
class TimersModule {

    @Provides
    fun provideAlarmViewModel(alarmRepository: AlarmRepository) = AlarmsViewModel(alarmRepository)


    @Provides
    fun provideTimePicker(viewModel: AlarmsViewModel): TimePicker = TimePickerImpl(viewModel)
}