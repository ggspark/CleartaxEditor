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

    //Add to stack history and update state
    fun focusGained(value: String) {
        if (_undoHistory.empty() || value != _undoHistory.peek()) {
            _undoHistory.push(value)
        }
        updateStates(value)
    }

    //Update states
    fun focusLost(value: String) {
        updateStates(value)
    }

    //Perform Undo
    fun undo(): String {
        val prev = _undoHistory.pop()
        updateStates(prev)
        return prev
    }

    //Update undo and word count
    private fun updateStates(value: String) {
        if (value.trim().isBlank()) {
            _wordCount.value = 0
        } else {
            _wordCount.value = value.trim().split("\\s+".toRegex()).size
        }
        _undoEnabled.value = _undoHistory.isNotEmpty()
    }


}