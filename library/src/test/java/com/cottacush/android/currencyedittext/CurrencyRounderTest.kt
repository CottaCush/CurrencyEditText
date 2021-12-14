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

class CurrencyRounderTest {

    companion object {
        const val POINT_DECIMAL_SEPARATOR = '.'
        const val COMMA_DECIMAL_SEPARATOR = ','
        const val TWO = 2
        const val THREE = 3
    }

    @Test
    fun `should return empty string if told to format empty string`() {
        var current = ""
        var expected = ""

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, TWO, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits away when given long digits`() {
        var current = "223.55644234234"
        var expected = "223.55"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, TWO, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits away when given long digits (three dp version)`() {
        var current = "223.55644234234"
        var expected = "223.556"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits away when given long digits (example 2)`() {
        var current = "334,242,203.4234234"
        var expected = "334,242,203.423"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits away when given long digits (whitespace decimal separator version)`() {
        var current = "334 242 203.4234234"
        var expected = "334 242 203.423"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits away when given long digits (comma decimal separator version)`() {
        var current = "334.242.203,4234234"
        var expected = "334.242.203,42"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, TWO, COMMA_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits when number with empty whole format is passed`() {
        var current = ".4234234"
        var expected = ".42"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, TWO, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should truncate extra decimal digits when number with empty decimal numbers is passed`() {
        var current = "4234234."
        var expected = "4234234."

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, TWO, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number with shorter decimal places is passed`() {
        var current = "23.45"
        var expected = "23.45"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number with shorter decimal places is passed (example 2)`() {
        var current = "23.4"
        var expected = "23.4"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number with shorter decimal places is passed (example 3)`() {
        var current = "515.809"
        var expected = "515.809"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number doesn't contain decimal separator`() {
        var current = "5 809"
        var expected = "5 809"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number doesn't contain decimal separator (example 2)`() {
        var current = "5"
        var expected = "5"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number doesn't contain decimal separator (example 3)`() {
        var current = "5,452,635,242,242,423,434,333"
        var expected = "5,452,635,242,242,423,434,333"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }

    @Test
    fun `should return original number when number contains more than one decimal separator`() {
        var current = "343,432,242,342"
        var expected = "343,432,242,342"

        Assert.assertEquals(
            expected,
            truncateNumberToMaxDecimalDigits(current, THREE, POINT_DECIMAL_SEPARATOR)
        )
    }
}
