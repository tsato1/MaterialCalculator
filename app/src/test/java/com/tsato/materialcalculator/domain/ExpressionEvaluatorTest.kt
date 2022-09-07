package com.tsato.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionEvaluatorTest {

    private lateinit var evaluator: ExpressionEvaluator

    @Test
    fun `Simple expression properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(3.0),
                ExpressionPart.Op(Operation.ADD),
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(2.0),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Number(6.0),
                ExpressionPart.Op(Operation.DIVIDE),
                ExpressionPart.Number(3.0)
            )
        )

        assertThat(evaluator.evaluate()).isEqualTo(4.0)
    }

    @Test
    fun `Expression with parentheses properly evaluated`() {
        evaluator = ExpressionEvaluator(
            listOf(
                ExpressionPart.Number(5.0),
                ExpressionPart.Op(Operation.MULTIPLY),
                ExpressionPart.Parentheses(ParenthesesType.Opening),
                ExpressionPart.Number(9.0),
                ExpressionPart.Op(Operation.SUBTRACT),
                ExpressionPart.Number(3.0),
                ExpressionPart.Parentheses(ParenthesesType.Closing),
            )
        )

        assertThat(evaluator.evaluate()).isEqualTo(30.0)
    }

}