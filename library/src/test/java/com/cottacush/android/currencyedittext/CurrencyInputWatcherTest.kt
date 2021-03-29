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
    fun `Should_setTextTo$1,000_when_textIsSetTo1000`() {
        val editTextContent = "1000"
        val expectedFormat = "$ 1,000"

        `when`(editable.toString()).thenReturn(editTextContent)
        watcher.afterTextChanged(editable)

        verify(editText, times(1)).setText(expectedFormat)
    }

}