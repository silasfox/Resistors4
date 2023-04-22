package com.example.resistors4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.TextView

class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val rings = findViewById<Spinner>(R.id.nRings)
        rings.onItemSelectedListener = this
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        val selected = parent.getItemAtPosition(pos)
        val digitColor3 = findViewById<Spinner>(R.id.color3)
        val tempCoefficient = findViewById<Spinner>(R.id.color6)
        if (selected.toString().toInt() == 4) {
            digitColor3.visibility = View.GONE
            tempCoefficient.visibility = View.GONE
        } else if (selected.toString().toInt() == 5) {
            digitColor3.visibility = View.VISIBLE
            tempCoefficient.visibility = View.GONE
        } else {
            digitColor3.visibility = View.VISIBLE
            tempCoefficient.visibility = View.VISIBLE
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        // Do nothing
    }

    fun onCalcHandler(view : View?) {
        if (view?.id != R.id.button) return

        val digitColor1 = findViewById<Spinner>(R.id.color1).selectedItem.toString()
        val digitColor2 = findViewById<Spinner>(R.id.color2).selectedItem.toString()
        val digitColor3 = findViewById<Spinner>(R.id.color3).selectedItem.toString()
        val multiplicatorStr = findViewById<Spinner>(R.id.color4).selectedItem.toString()
        val toleranceStr = findViewById<Spinner>(R.id.color5).selectedItem.toString()
        val tempCoefficientStr = findViewById<Spinner>(R.id.color6).selectedItem.toString()
        val result = findViewById<TextView>(R.id.result)

        val digit1 = colorToDigit(digitColor1)
        val digit2 = colorToDigit(digitColor2)
        val digit3 = colorToDigit(digitColor3)
        val multiplicator = colorToMultiplicator(multiplicatorStr)

        val digits = List(3) { i ->
            when (i) {
                0 -> digit1
                1 -> digit2
                2 -> digit3
                else -> 0.0
            }
        }
        val number = digitsToNumber(digits) * multiplicator
        val tolerance = strToTolerance(toleranceStr)
        val tempCoefficient = strToTempCoefficient(tempCoefficientStr)
        result.text = numberToOhm(number) + " " + tolerance +
                if (findViewById<Spinner>(R.id.nRings).selectedItem.toString().toInt() == 6)
                "\nTemp. Koeffizient: " + tempCoefficient else ""
    }

    private fun numberToOhm(number : Double): String {
        return if (1_000_000_000 <= number) {
            (number / 1_000_000_000).toString() + "GΩ"
        } else if (1_000_000 <= number) {
            (number / 1_000_000).toString() + "MΩ"
        } else if (1_000 <= number) {
            (number / 1_000).toString() + "kΩ"
        } else {
            number.toString() + "Ω"
        }
    }

    private fun strToTempCoefficient(tempCoefficientStr: String): String {
        return when (tempCoefficientStr) {
            resources.getString(R.string.braun) -> "100"
            resources.getString(R.string.rot) -> "50"
            resources.getString(R.string.orange) -> "15"
            resources.getString(R.string.gelb) -> "25"
            resources.getString(R.string.blau) -> "10"
            resources.getString(R.string.violett) -> "5"
            resources.getString(R.string.weiß) -> "1"
            else -> ""
        }
    }

    private fun strToTolerance(toleranceStr: String): String {
        return when (toleranceStr) {
            resources.getString(R.string.silber) -> "±10,0%"
            resources.getString(R.string.gold) -> "±5,0%"
            resources.getString(R.string.braun) -> "±1,0%"
            resources.getString(R.string.rot) -> "±2,0%"
            resources.getString(R.string.grün) -> "±0,5%"
            resources.getString(R.string.blau) -> "±0,25%"
            resources.getString(R.string.violett) -> "±0,1%"
            resources.getString(R.string.grau) -> "±0,05%"
            else -> ""
        }
    }

    private fun digitsToNumber(d : List<Double>) : Double {
        var digits = d
        if (findViewById<Spinner>(R.id.nRings).selectedItem.toString().toInt() == 4) {
           digits = d.take(2)
        }
        return digits.reduce { acc, digit -> acc * 10 + digit }
    }

    private fun colorToMultiplicator(multiplicator: String): Double {
        return when (multiplicator) {
            resources.getString(R.string.silber) -> 0.01
            resources.getString(R.string.gold) -> 0.1
            resources.getString(R.string.schwarz) -> 1.0
            resources.getString(R.string.braun) -> 10.0
            resources.getString(R.string.rot) -> 100.0
            resources.getString(R.string.orange) -> 1000.0
            resources.getString(R.string.gelb) -> 10000.0
            resources.getString(R.string.grün) -> 100000.0
            resources.getString(R.string.blau) -> 1000000.0
            resources.getString(R.string.violett) -> 10000000.0
            resources.getString(R.string.grau) -> 100000000.0
            resources.getString(R.string.weiß) -> 1000000000.0
            else -> 0.0
        }
    }

    private fun colorToDigit(digitColor1: String) : Double {
        return when (digitColor1) {
            resources.getString(R.string.schwarz) -> 0.0
            resources.getString(R.string.braun) -> 1.0
            resources.getString(R.string.rot) -> 2.0
            resources.getString(R.string.orange) -> 3.0
            resources.getString(R.string.gelb) -> 4.0
            resources.getString(R.string.grün) -> 5.0
            resources.getString(R.string.blau) -> 6.0
            resources.getString(R.string.violett) -> 7.0
            resources.getString(R.string.grau) -> 8.0
            resources.getString(R.string.weiß) -> 9.0
            else -> 0.0
        }
    }
}