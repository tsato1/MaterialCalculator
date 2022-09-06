package com.tsato.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {

    lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {
        parser = ExpressionParser("3+5-2x6/3")

        val actual = parser.parse()

        val expected = listOf(
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

        assertThat(expected).isEqualTo(actual)
    }

    @Test
    fun `Expression with parentheses poperly parsed`() {
        parser = ExpressionParser("5x(9-3)")

        val actual = parser.parse()

        val expected = listOf(
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(9.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
        )

        assertThat(expected).isEqualTo(actual)
    }
}