package com.example.stopwatch

import android.app.Dialog
import android.os.Bundle
import android.os.SystemClock
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Chronometer
import android.widget.Chronometer.OnChronometerTickListener
import android.widget.NumberPicker
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.stopwatch.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var isRunning = false
    private var minutes: String? = "00.00.00"
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)

        var lapsList = ArrayList<String>()
        var arrayAdater = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, lapsList)
        binding.listview.adapter = arrayAdater

        binding.lap.setOnClickListener {
            if (isRunning) {
                lapsList.add(binding.chronometer.text.toString())
                arrayAdater.notifyDataSetChanged()
            }
        }


        binding.clockimg.setOnClickListener {
            var dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog)
            var numberPicker = dialog.findViewById<NumberPicker>(R.id.numberPicker)
            numberPicker.minValue = 0
            numberPicker.maxValue = 5

            dialog.findViewById<Button>(R.id.setTime).setOnClickListener {
                minutes = numberPicker.value.toString()
                binding.clocktime.text =
                    dialog.findViewById<NumberPicker>(R.id.numberPicker).value.toString() + " mins"
                dialog.dismiss()
            }

            dialog.show()
        }


        binding.run.setOnClickListener {
            if (!isRunning) {

                if (!minutes.equals("00.00.00") && !minutes.equals("0")) {
                    var totalmin = minutes!!.toInt() * 60 * 1000L
                    var countDown = 1000L
                    binding.chronometer.base = SystemClock.elapsedRealtime() + totalmin

                    binding.chronometer.format = "%S %S"

                    binding.chronometer.setOnChronometerTickListener(Chronometer.OnChronometerTickListener {
                        binding.run.text = "STOP"
//                        var elapsedTime = SystemClock.elapsedRealtime() - binding.chronometer.base

                        if (binding.chronometer.getText().toString()
                                .equals("00:00", ignoreCase = true)
                        ) {
                            binding.chronometer.stop()
                            binding.run.text = "RUN"
                        }


                    })
                    binding.chronometer.start()

                } else {
                    isRunning = true
                    binding.chronometer.base = SystemClock.elapsedRealtime()
                    binding.run.text = "STOP"
                    binding.chronometer.start()
                }


            } else {
                binding.chronometer.stop()
                isRunning = false
                binding.run.text = "RUN"
            }


        }


    }
}