package ru.borisov.customkeyboardcalculator

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.forEach
import androidx.core.view.forEachIndexed
import ru.borisov.customkeyboardcalculator.Calculator.Companion.NUMBERS
import ru.borisov.customkeyboardcalculator.Calculator.Companion.OPERATORS
import ru.borisov.customkeyboardcalculator.Calculator.Companion.RESET
import ru.borisov.customkeyboardcalculator.Calculator.Companion.RESULT

class MainActivity : AppCompatActivity() {
    lateinit var toolbar: Toolbar
    lateinit var inputFieldET: EditText
    lateinit var resultTV: TextView
    lateinit var gridLayoutGL: GridLayout
    private var calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        toolbar = findViewById(R.id.toolbar)
        toolbar.title = resources.getString(R.string.text_title)
        inputFieldET = findViewById(R.id.inputFieldET)
        resultTV = findViewById(R.id.resultTV)
        gridLayoutGL = findViewById(R.id.gridlayoutGL)
        setSupportActionBar(toolbar)
        gridLayoutGL.forEach { view ->
            val button = view as Button
            val text = button.text.toString()
            when (text) {
                in NUMBERS -> button.setOnClickListener { clickOnNumber(text.toInt()) }
                in OPERATORS -> button.setOnClickListener { clickOnOperator(text) }
                RESULT -> button.setOnClickListener { clickOnResult() }
                RESET -> button.setOnClickListener { clickOnReset() }
            }
        }
    }

    private fun clickOnNumber(number: Int) {
        calculator.result ?: let {
            calculator.pairNumbers?.let { pair ->
                calculator.operator?.let {
                    pair.second?.let {
                        calculator.pairNumbers =
                            pair.copy(second = (pair.second.toString() + number.toString()).toInt())
                        inputFieldET.append(number.toString())
                    } ?: run {
                        inputFieldET.append(number.toString())
                        calculator.pairNumbers = pair.copy(second = number)
                    }
                } ?: let {
                    calculator.pairNumbers =
                        pair.copy(first = (pair.first.toString() + number.toString()).toInt())
                    inputFieldET.append(number.toString())
                }
            } ?: run {
                inputFieldET.append(number.toString())
                calculator.pairNumbers = Pair(number, null)
            }
        }
    }

    private fun clickOnOperator(text: String) {
        calculator.operator ?: run {
            inputFieldET.append(text)
            calculator.operator = text
        }
    }

    private fun clickOnResult() {
        calculator.getResult()
        calculator.result?.let {
            resultTV.text = it.toString()
        }
    }

    private fun clickOnReset() {
        calculator = Calculator()
        inputFieldET.text.clear()
        resultTV.text = resources.getString(R.string.text_result)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.exit_menu_item) finish()
        return super.onOptionsItemSelected(item)
    }
}