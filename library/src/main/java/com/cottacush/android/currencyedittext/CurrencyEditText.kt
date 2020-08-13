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

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import java.util.Locale

class CurrencyEditText(context: Context, attrs: AttributeSet?) : TextInputEditText(context, attrs) {
    private var currencySymbolPrefix = ""
    private var textWatcher: CurrencyInputWatcher
    private var locale: Locale = Locale.getDefault()

    init {
        var useCurrencySymbolAsHint = false
        inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
        var localeTag: String?
        val prefix: String?
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CurrencyEditText,
            0, 0
        ).run {
            try {
                prefix = getString(R.styleable.CurrencyEditText_currencySymbol)
                localeTag = getString(R.styleable.CurrencyEditText_localeTag)
                useCurrencySymbolAsHint = getBoolean(R.styleable.CurrencyEditText_useCurrencySymbolAsHint, false)
            } finally {
                recycle()
            }
        }
        if (!prefix.isNullOrBlank()) currencySymbolPrefix = "$prefix "
        if (useCurrencySymbolAsHint) hint = currencySymbolPrefix
        if (isLollipopAndAbove() && !localeTag.isNullOrBlank()) locale = Locale.forLanguageTag(localeTag)
        textWatcher = CurrencyInputWatcher(this, currencySymbolPrefix, locale)
    }

    fun setLocale(locale: Locale) {
        this.locale = locale
        invalidateTextWatcher()
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    fun setLocale(localeTag: String) {
        locale = Locale.forLanguageTag(localeTag)
        invalidateTextWatcher()
    }

    fun setCurrencySymbol(currencySymbol: String, useCurrencySymbolAsHint: Boolean = false) {
        currencySymbolPrefix = "$currencySymbol "
        if (useCurrencySymbolAsHint) hint = currencySymbolPrefix
        invalidateTextWatcher()
    }

    private fun invalidateTextWatcher() {
        removeTextChangedListener(textWatcher)
        textWatcher = CurrencyInputWatcher(this, currencySymbolPrefix, locale)
        addTextChangedListener(textWatcher)
    }

    fun getNumericValue(): Double? {
        return parseMoneyValue(
            text.toString(),
            textWatcher.decimalFormatSymbols.groupingSeparator.toString(),
            currencySymbolPrefix
        ).toDoubleOrNull()
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        getText()?.length?.let { setSelection(it) }
    }

    override fun onFocusChanged(focused: Boolean, direction: Int, previouslyFocusedRect: Rect?) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect)
        if (focused) {
            removeTextChangedListener(textWatcher)
            addTextChangedListener(textWatcher)
            if (text.toString().isEmpty()) setText(currencySymbolPrefix)
        } else {
            removeTextChangedListener(textWatcher)
            if (text.toString() == currencySymbolPrefix) setText("")
        }
    }

    private fun isLollipopAndAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
}
