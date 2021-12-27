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

import android.text.Editable
import com.cottacush.android.currencyedittext.model.LocaleVars
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito.*
import java.lang.IllegalArgumentException

class CurrencyInputWatcherTest {

    // TODO Add more locale tests by their, tags, decimal separator and theur grouping separator
    private val locales = listOf(
            LocaleVars("en-NG", '.', ',', "$ "),
            LocaleVars("en-US", '.', ',', "$ "),
            LocaleVars("da-DK", ',', '.', "$ "),
            LocaleVars("fr-CA", ',', ' ', "$ ")
    )

    @Test
    fun `Should keep currency symbol as hint when enabled and move cursor to front when edit text is set to empty string`() {
        for (locale in locales) {
            val currentEditTextContent = ""
            val expectedText = locale.currencySymbol
            val expectedCursorPosition = expectedText.length

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
            verify(editText, times(1)).setSelection(expectedCursorPosition)
        }
    }

    @Test
    fun `Should set text To "$ 5" when text is set to "5"`() {
        for (locale in locales) {
            val currentEditTextContent = "5"
            val expectedText = "${locale.currencySymbol}5"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            // Verify that the EditText's text was set to the expected text
            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should set text to "$ 40" when text is set to "40"`() {
        for (locale in locales) {
            val currentEditTextContent = "40"
            val expectedText = "${locale.currencySymbol}40"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            // Verify that the EditText's text was set to the expected text
            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should set text to "$ 900" when text is set to "900"`() {
        for (locale in locales) {
            val currentEditTextContent = "900"
            val expectedText = "${locale.currencySymbol}900"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            // Verify that the EditText's text was set to the expected text
            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should set text to "$ 1,000" when text is set to "1000"`() {
        for (locale in locales) {
            val currentEditTextContent = "1000"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}000"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            // Verify that the EditText's text was set to the expected text
            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should set text to "$ 15,420point50" when text is set to "15420point50"`() {
        for (locale in locales) {
            val currentEditTextContent = "15420${locale.decimalSeparator}50"
            val expectedText = "${locale.currencySymbol}15${locale.groupingSeparator}420${locale.decimalSeparator}50"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            // Verify that the EditText's text was set to the expected text
            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep the decimal symbol when the edit text does not contain a decimal symbol before and it is clicked`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}000"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}000${locale.decimalSeparator}"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + locale.decimalSeparator)

            watcher.runAllWatcherMethods(editable)

            // Verify that the EditText's text was set to the expected text
            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should set text to "$ 10,002" when previous text is "$ 1,000" and "2" is clicked`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}000"
            val expectedText = "${locale.currencySymbol}10${locale.groupingSeparator}002"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + "2")

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should automatically append a zero to the decimal separator when the edit text is empty and the decimal operator is clicked`() {
        for (locale in locales) {
            val currentEditTextContent = locale.currencySymbol
            val expectedText = "${locale.currencySymbol}0${locale.decimalSeparator}"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + locale.decimalSeparator)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep the single decimal digit when there are no decimal digits and a digit is added after the decimal symbol`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}5"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + "5")

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep two decimal digits when there is one decimal digit and a digit is added after the decimal symbol`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}5"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + "0")

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep the current text as is when there are two decimal digits and a digit is added after the decimal symbol`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + "9")

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep the current text as is when there is two decimal digit and multiple digits are added after the decimal symbol`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + "92293948842")

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep only one decimal symbol when a decimal symbol is present and it is clicked again`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent + locale.decimalSeparator)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should not allow a delete when edit text is set to currency symbol and a delete is clicked`() {
        for (locale in locales) {
            val currentEditTextContent = locale.currencySymbol
            val expectedText = locale.currencySymbol

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent.removeLastChar())

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should not allow a delete when edit text is set to currency symbol and a delete is clicked at the zeroth index`() {
        for (locale in locales) {
            val currentEditTextContent = locale.currencySymbol
            val expectedText = locale.currencySymbol

            val (editText, editable, watcher) = setupTestVariables(locale)
            `when`(editable.toString()).thenReturn(currentEditTextContent.removeFirstChar())

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep a default of 2 decimal places when the max dp value isn't specified`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50992"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50"

            val (editText, editable, watcher) = setupTestVariables(locale)
            val watcherWithDefaultDP = CurrencyInputWatcher(editText, locale.currencySymbol, locale.tag.toLocale())
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcherWithDefaultDP.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should throw an Exception when maximum dp is set to zero`() {
        for (locale in locales) {
            try {
                val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 0)
                Assert.fail("Should have caught an illegalArgumentException at this point")
            } catch (e: IllegalArgumentException) { }
        }
    }

    @Test
    fun `Should keep only one decimal place when maximum dp is set to 1`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}50992"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}5"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 1)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep only two decimal places when maximum dp is set to 2`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}51992"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}51"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 2)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep only three decimal places when maximum dp is set to 3`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}51992"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}519"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 3)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep only seven decimal digits when maximum dp is set to 7`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}519923345634"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}5199233"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 7)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should keep up to ten decimal places when maximum dp is set to 10`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}519923345634"
            val expectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}5199233456"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 10)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    @Test
    fun `Should change maximum decimal digits to 3 if setMaxNumberOfDecimalDigits(3) is called after being init with decimal digits 2`() {
        for (locale in locales) {
            val currentEditTextContent = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}519923345634"
            val firstExpectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}51"
            val secondExpectedText = "${locale.currencySymbol}1${locale.groupingSeparator}320${locale.decimalSeparator}519"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 2)
            val secondWatcher = locale.toWatcher(editText, 3)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)
            secondWatcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(firstExpectedText)
            verify(editText, times(1)).setText(secondExpectedText)
        }
    }

    @Test
    fun `Should retain valid number if imputed`() {
        // This test tries to replicate issue #29 which fails on some devices and passes for some.
        // It however passes on my local. but might be helpful to have the test in here.
        for (locale in locales) {
            val currentEditTextContent = "515${locale.decimalSeparator}809"
            val expectedText = "${locale.currencySymbol}515${locale.decimalSeparator}809"

            val (editText, editable, watcher) = setupTestVariables(locale, decimalPlaces = 3)
            `when`(editable.toString()).thenReturn(currentEditTextContent)

            watcher.runAllWatcherMethods(editable)

            verify(editText, times(1)).setText(expectedText)
        }
    }

    private fun setupTestVariables(locale: LocaleVars, decimalPlaces: Int = 2): TestVars {
        val editText = mock(CurrencyEditText::class.java)
        val editable = mock(Editable::class.java)
        `when`(editText.text).thenReturn(editable)
        `when`(editable.append(isA(String::class.java))).thenReturn(editable)
        val watcher = locale.toWatcher(editText, decimalPlaces)
        return TestVars(editText, editable, watcher)
    }
}

data class TestVars(
    val editText: CurrencyEditText,
    val editable: Editable,
    val watcher: CurrencyInputWatcher
)
