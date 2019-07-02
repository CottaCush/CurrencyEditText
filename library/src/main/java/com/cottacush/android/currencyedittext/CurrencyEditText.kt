package com.cottacush.android.currencyedittext

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.text.InputType
import android.util.AttributeSet
import androidx.annotation.RequiresApi
import com.google.android.material.textfield.TextInputEditText
import java.util.*

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

    // check https://docs.microsoft.com/en-us/previous-versions/commerce-server/ee825488(v=cs.20)
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