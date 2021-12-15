/**
 * Copyright (c) 2019 Cotta & Cush Limited.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.cottacush.android.currencyedittext

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class CurrencyRounderTest(
    private val current: String,
    private val expected: String,
    private val decimalDigits: Int,
    private val decimalSeparator: Char
) {

    companion object {
        private const val POINT_DECIMAL_SEPARATOR = '.'
        private const val COMMA_DECIMAL_SEPARATOR = ','
        private const val TWO = 2
        private const val THREE = 3

        private val emptyStringTestCase = arrayOf("", "", TWO, POINT_DECIMAL_SEPARATOR)
        private val extraDecimalTestCase1 = arrayOf("223.55644234234", "223.55", TWO, POINT_DECIMAL_SEPARATOR)
        private val extraDecimalTestCaseThreeDpVersion = arrayOf("223.55644234234", "223.556", THREE, POINT_DECIMAL_SEPARATOR)
        private val extraDecimalTestCase2 = arrayOf("334,242,203.4234234", "334,242,203.423", THREE, POINT_DECIMAL_SEPARATOR)
        private val extraDecimalTestCaseWhiteSpaceGrouping = arrayOf("334 242 203.4234234", "334 242 203.423", THREE, POINT_DECIMAL_SEPARATOR)
        private val extraDecimalTestCaseCommaDecimal = arrayOf("334.242.203,4234234", "334.242.203,42", TWO, COMMA_DECIMAL_SEPARATOR)
        private val emptyWholeTestCase = arrayOf(".4234234", ".42", TWO, POINT_DECIMAL_SEPARATOR)
        private val emptyDecimalTestCase = arrayOf("4,234,234.", "4,234,234.", TWO, POINT_DECIMAL_SEPARATOR)
        private val shorterDecimalTestCase = arrayOf("23.45", "23.45", THREE, POINT_DECIMAL_SEPARATOR)
        private val shorterDecimalTestCase2 = arrayOf("23.4", "23.4", THREE, POINT_DECIMAL_SEPARATOR)
        private val exactDecimalTestCase = arrayOf("515.809", "515.809", THREE, POINT_DECIMAL_SEPARATOR)
        private val noDecimalTestCase = arrayOf("5 809", "5 809", THREE, POINT_DECIMAL_SEPARATOR)
        private val noDecimalTestCase2 = arrayOf("5", "5", THREE, POINT_DECIMAL_SEPARATOR)
        private val noDecimalTestCase3 = arrayOf("5,452,635,242,242,423,434,333", "5,452,635,242,242,423,434,333", THREE, POINT_DECIMAL_SEPARATOR)
        private val multipleDecimalTestCase = arrayOf("343,432,242,342", "343,432,242,342", TWO, COMMA_DECIMAL_SEPARATOR)

        @JvmStatic
        @Parameterized.Parameters
        fun data(): Iterable<Array<Any>> = listOf(
            emptyStringTestCase,
            extraDecimalTestCase1,
            extraDecimalTestCaseThreeDpVersion,
            extraDecimalTestCase2,
            extraDecimalTestCaseWhiteSpaceGrouping,
            extraDecimalTestCaseCommaDecimal,
            emptyWholeTestCase,
            emptyDecimalTestCase,
            shorterDecimalTestCase,
            shorterDecimalTestCase2,
            exactDecimalTestCase,
            noDecimalTestCase,
            noDecimalTestCase2,
            noDecimalTestCase3,
            multipleDecimalTestCase
        )
    }

    @Test
    fun `should return expected value for set of valid inputs`() {
        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, decimalDigits, decimalSeparator)
        )
    }

}
