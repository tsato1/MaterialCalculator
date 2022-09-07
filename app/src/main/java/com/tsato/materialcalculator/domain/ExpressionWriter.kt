package com.tsato.materialcalculator.domain

class ExpressionWriter {

    var expression = ""

    fun processAction(action: CalculatorAction) {
        when (action) {
            CalculatorAction.Calculate -> {
                val parser = ExpressionParser(parepareForCalculation())
                val evaluator = ExpressionEvaluator(parser.parse())
                expression = evaluator.evaluate().toString()
            }
            CalculatorAction.Clear -> {
                expression = ""
            }
            CalculatorAction.Decimal -> {
                if (canEnterDecimal()) {
                    expression += "."
                }
            }
            CalculatorAction.Delete -> {
                expression = expression.dropLast(1)
            }
            is CalculatorAction.Number -> {
                expression += action.number
            }
            is CalculatorAction.Op -> {
                if (canEnterOperation(action.operation)) {
                    expression += action.operation.symbol
                }
            }
            CalculatorAction.Parenthesis -> {
                processParentheses()
            }
        }
    }

    private fun parepareForCalculation(): String {
        val newExpression = expression.takeLastWhile {
            it in "$operationSymbols.("
        }

        return if (newExpression.isEmpty()) {
            "0"
        } else {
            newExpression
        }
    }

    private fun processParentheses() {
        val openingCount = expression.count { it == '(' }
        val closingCount = expression.count { it == ')' }
        expression += when {
            expression.isEmpty() || expression.last() in "$operationSymbols)" -> "("
            expression.last() in "1234567890)" && openingCount == closingCount -> return
            else -> ")"
        }
    }

    private fun canEnterDecimal(): Boolean {
        if (expression.isEmpty() || expression.last() in "$operationSymbols.()") {
            return false
        }

        // 4+1.23 -> prevent from entering another period
        // 4+123  -> allow to enter period
        return !expression.takeLastWhile {
            it in "1234567890."
        }.contains(".")
    }

    private fun canEnterOperation(operation: Operation): Boolean {
        if (operation in listOf(Operation.ADD, Operation.SUBTRACT)) {
            return expression.isEmpty() || expression.last() in "$operationSymbols()1234567890"
        }

        return expression.isNotEmpty() || expression.last() in "1234567890)"
    }

}