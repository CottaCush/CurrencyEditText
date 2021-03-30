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
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class CurrencyInputWatcherTest {

    @Mock private lateinit var editText: CurrencyEditText
    @Mock private lateinit var editable: Editable

    private lateinit var watcher: CurrencyInputWatcher
    private val currencySymbol = "$ "
    private val locale = Locale("en-US")

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        watcher = CurrencyInputWatcher(editText, currencySymbol, locale)
        `when`(editText.text).thenReturn(editable)
        `when`(editable.append(isA(String::class.java))).thenReturn(editable)
    }

    @Test
    fun `Should_setTextTo "$ 5" _when_textIsSetTo "5"`() {
        val currentEditTextContent = "5"
        val expectedText = "$ 5"

        `when`(editable.toString()).thenReturn(currentEditTextContent)

        watcher.runAllWatcherMethods(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_setTextTo "$ 40" _when_textIsSetTo "40"`() {
        val currentEditTextContent = "40"
        val expectedText = "$ 40"

        `when`(editable.toString()).thenReturn(currentEditTextContent)

        watcher.runAllWatcherMethods(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_setTextTo "$ 900" _when_textIsSetTo "900"`() {
        val currentEditTextContent = "900"
        val expectedText = "$ 900"

        `when`(editable.toString()).thenReturn(currentEditTextContent)

        watcher.runAllWatcherMethods(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_setTextTo "$ 1,000" _when_textIsSetTo "1000"`() {
        val currentEditTextContent = "1000"
        val expectedText = "$ 1,000"

        `when`(editable.toString()).thenReturn(currentEditTextContent)

        watcher.runAllWatcherMethods(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_setTextTo "$ 15,420point50" _when_textIsSetTo "15420point50"`() {
        val currentEditTextContent = "15420.50"
        val expectedText = "$ 15,420.50"

        `when`(editable.toString()).thenReturn(currentEditTextContent)

        watcher.runAllWatcherMethods(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepTheDecimalSymbol_when_theEditTextDoesNotContainADecimalSymbolBeforeAndItIsClicked`() {
        val currentEditTextContent = "$ 1,000"
        val expectedText = "$ 1,000."

        `when`(editable.toString()).thenReturn(currentEditTextContent + ".")

        watcher.runAllWatcherMethods(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_setTextTo "$ 10,002" _when_previousTextIs "$ 1,000" and "2" isClicked`() {
        val currentEditTextContent = "$ 1,000"
        val expectedText = "$ 10,002"

        `when`(editable.toString()).thenReturn(currentEditTextContent + "2")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_automaticallyAppendAZeroToTheDecimalSeparator _when_theEditTextIsEmptyAndTheDecimalOperatorIsClicked`() {
        val currentEditTextContent = "$ "
        val expectedText = "$ 0."

        `when`(editable.toString()).thenReturn(currentEditTextContent + ".")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepTheSingleDecimalDigit_when_thereAreNoDecimalDigitsAndADigitIsAddedAfterTheDecimalSymbol`() {
        val currentEditTextContent = "$ 1,320."
        val expectedText = "$ 1,320.5"

        `when`(editable.toString()).thenReturn(currentEditTextContent + "5")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepTwoDecimalDigits_when_thereIsOneDecimalDigitAndADigitIsAddedAfterTheDecimalSymbol`() {
        val currentEditTextContent = "$ 1,320.5"
        val expectedText = "$ 1,320.50"

        `when`(editable.toString()).thenReturn(currentEditTextContent + "0")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepTheCurrentTextAsIs_when_thereIsTwoDecimalDigitAndADigitIsAddedAfterTheDecimalSymbol`() {
        val currentEditTextContent = "$ 1,320.50"
        val expectedText = "$ 1,320.50"

        `when`(editable.toString()).thenReturn(currentEditTextContent + "9")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepTheCurrentTextAsIs_when_thereIsTwoDecimalDigitAndMultipleDigitsAreAddedAfterTheDecimalSymbol`() {
        val currentEditTextContent = "$ 1,320.50"
        val expectedText = "$ 1,320.50"

        `when`(editable.toString()).thenReturn(currentEditTextContent + "92293948842")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepOnlyOneDecimalSymbol_when_aDecimalSymbolIsPresentAndItIsClickedAgain`() {
        val currentEditTextContent = "$ 1,320.50"
        val expectedText = "$ 1,320.50"

        `when`(editable.toString()).thenReturn(currentEditTextContent + ".")

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_keepCurrencySymbolAsHintWhenEnabledAndMoveCursorToFront_when_editTextIsSetToEmptyString`() {
        val currentEditTextContent = ""
        val expectedText = "$ "
        val expectedCursorPosition = expectedText.length

        `when`(editable.toString()).thenReturn(currentEditTextContent)

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
        verify(editText, times(1)).setSelection(expectedCursorPosition)
    }

    @Test
    fun `Should_notAllowADelete_when_editTextIsSetToCurrencySymbolAndADeleteIsClicked`() {
        val currentEditTextContent = "$ "
        val expectedText = "$ "

        `when`(editable.toString()).thenReturn(currentEditTextContent.removeLastChar())

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_notAllowADelete_when_editTextIsSetToCurrencySymbolAndADeleteIsClickedAtTheZerothIndex`() {
        val currentEditTextContent = "$ "
        val expectedText = "$ "

        `when`(editable.toString()).thenReturn(currentEditTextContent.removeFirstChar())

        watcher.runAllWatcherMethods(editable)

        verify(editText, times(1)).setText(expectedText)
    }

}
