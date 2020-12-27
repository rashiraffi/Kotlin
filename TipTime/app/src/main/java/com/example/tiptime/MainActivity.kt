package com.example.tiptime

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.tiptime.databinding.ActivityMainBinding
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {

    /*The lateinit keyword is something new. It's a promise that your code will initialize the variable before using it. If you don't, your app will crash.*/
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*This line initializes the binding object which you'll use to access Views in the activity_main.xml layout*/
        binding = ActivityMainBinding.inflate(layoutInflater)

        /*Set the content view of the activity.
        Instead of passing the resource ID of the layout, R.layout.activity_main,
        this specifies the root of the hierarchy of views in your app, binding.root.
        setContentView(R.layout.activity_main) */

        setContentView(binding.root)

        binding.calculateButton.setOnClickListener {
            calculateTip()
        }
    }

    private fun calculateTip(){
        /*Remember that you can access the UI element using the binding object, and that you can reference the UI element based on its resource ID name in camel case.*/
        val stringInTextField = binding.costOfServiceEditText.text.toString()
        val cost = stringInTextField.toDoubleOrNull()

        if(cost == null){
            displayTip(0.0)
            return
        }

        // tipOptions is the id of Radiogroup

        val tipPercentage = when(binding.tipOptions.checkedRadioButtonId){
            R.id.option_twenty_percent -> 0.20
            R.id.option_eighteen_percent -> 0.18
            else -> 0.15
        }

        var tip = tipPercentage * cost

        if(binding.roundUpSwitch.isChecked){
            tip=kotlin.math.ceil(tip)
        }

        displayTip(tip)
    }

    private fun displayTip(tip : Double) {
        val formattedTip = NumberFormat.getCurrencyInstance().format(tip)
        binding.tipResult.text = getString(R.string.tip_amount, formattedTip)
    }
}