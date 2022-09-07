package com.tsato.materialcalculator.domain

class ExpressionParser(
    private val calculation: String
) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()

        var i = 0
        while (i < calculation.length) {
            val currChar = calculation[i]

            when {
                currChar in operationSymbols -> {
                    result.add(ExpressionPart.Op(operationFromSymbol(currChar)))
                }
                currChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
                currChar in "()" -> {
                    parseParenthesis(currChar, result)
                }
            }
            i++
        }

        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var j = startingIndex

        val numberAsString = buildString {
            while (j < calculation.length && calculation[j] in "0123456789.") {
                append(calculation[j])
                j++
            }
        }

        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return j
    }

    private fun parseParenthesis(currChar: Char, result: MutableList<ExpressionPart>) {
        result.add(
            ExpressionPart.Parentheses(
                when (currChar) {
                    '(' -> ParenthesesType.Opening
                    ')' -> ParenthesesType.Closing
                    else -> throw IllegalArgumentException("Invalid input parenthesis")
                }
            )
        )
    }
}