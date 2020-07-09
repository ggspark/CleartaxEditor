package com.example.cleartaxeditor

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class MainViewModel : ViewModel() {

    //Keep track of current word count
    private val _wordCount = MutableLiveData<Int>()
    val wordCount: LiveData<Int>
        get() = _wordCount

    //Keep track of undo button enable disable
    private val _undoEnabled = MutableLiveData<Boolean>()
    val undoEnabled: LiveData<Boolean>
        get() = _undoEnabled

    //Keep track of undo stack history
    private val _undoHistory: Stack<String> = Stack()

    fun focusLost(value: String) {
        _undoHistory.push(value)
        _updateStates(value)
    }

    fun undo(): String {
        val prev = _undoHistory.pop()

        _updateStates(prev)
        return prev
    }


    private fun _updateStates(value: String) {
        _wordCount.value = value.trim().split("\\s+".toRegex()).size
        _undoEnabled.value = _undoHistory.isNotEmpty()
    }


}