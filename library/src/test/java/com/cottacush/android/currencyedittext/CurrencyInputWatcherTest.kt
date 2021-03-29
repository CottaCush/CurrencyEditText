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
    }

    @Test
    fun `Should_setTextTo$ 1,000_when_textIsSetTo1000`() {
        val editTextContent = "1000"
        val expectedText = "$ 1,000"

        `when`(editable.toString()).thenReturn(editTextContent)

        watcher.afterTextChanged(editable)

        // Verify that the EditText's text was set to the expected text
        verify(editText, times(1)).setText(expectedText)
    }

    @Test
    fun `Should_setTextTo$ 10,002_when_previousTextIs$ 1,000and2isClicked`() {
        val currentEditTextContent = "$ 1,000"
        val expectedText = "$ 10,002"

        val newEditable = mock(Editable::class.java)

        `when`(editable.append("2")).thenReturn(newEditable)
        `when`(editText.text).thenReturn(newEditable)
        `when`(editable.toString()).thenReturn(currentEditTextContent)
        `when`(newEditable.toString()).thenReturn(currentEditTextContent + "2")

        watcher.afterTextChanged(editable.append("2"))

        verify(editText, times(1)).setText(expectedText)
    }
}
