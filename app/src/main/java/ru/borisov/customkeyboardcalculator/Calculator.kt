package ru.borisov.customkeyboardcalculator

class Calculator {
    var pairNumbers: Pair<Int?, Int?>? = null
    var operator: String? = null
    var result: Double? = null

    fun getResult() {
        if (pairNumbers?.first != null &&
            pairNumbers?.second != null &&
            operator != null &&
            result == null
        ) {
            val first = pairNumbers?.first?.toDouble() ?: 0.0
            val second = pairNumbers?.second?.toDouble() ?: 0.0
            result = when (operator) {
                "+" -> first + second
                "-" -> first - second
                "*" -> first * second
                "/" -> first / second
                else -> 0.0
            }
        }
    }

    companion object {
        val NUMBERS = listOf("0", "1", "2", "3", "4", "5", "6", "7", "8", "9")
        val OPERATORS = listOf("+", "-", "*", "/")
        const val RESULT = "="
        const val RESET = "reset"
    }
}