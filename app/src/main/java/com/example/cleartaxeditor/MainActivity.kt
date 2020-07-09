package com.example.cleartaxeditor

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.cleartaxeditor.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.editText.setOnFocusChangeListener { _, hasFocus ->
            Log.i("Focus", hasFocus.toString())
            if (hasFocus) {
                viewModel.focusGained(binding.editText.text.toString())
            } else {
                viewModel.focusLost(binding.editText.text.toString())
            }
        }
        binding.undoButton.setOnClickListener {
            val prev = viewModel.undo()
            Log.i("Undo", prev)
            binding.editText.setText(prev)
            binding.editText.clearFocus()
        }
        binding.clearFocus.setOnClickListener {
            binding.editText.clearFocus()
        }

    }
}
