package com.example.midtermgame

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _nameLiveData = MutableLiveData<String>()
    val nameLiveData: LiveData<String>
        get() = _nameLiveData

    private val _attemptCount = MutableLiveData(0)
    val attemptCount: LiveData<Int>
        get() = _attemptCount
    fun incrementAttemptCount() {
        _attemptCount.value = _attemptCount.value!!.plus(1)
        Log.i("TEST", attemptCount.value!!.toString())
    }

    fun setName(name: String) {
        _nameLiveData.value = name
    }

    fun setAttemptCountToZero() {
        _attemptCount.value = 0
    }
}
