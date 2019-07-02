package com.cottacush.android.currencyedittext

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.text.DecimalFormat
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

class CurrencyInputWatcher(
    private val editText: EditText,
    private val currencySymbol: String,
    locale: Locale
) : TextWatcher {

    private var hasDecimalPoint = false
    private val wholeNumberDecimalFormat = (NumberFormat.getNumberInstance(locale) as DecimalFormat).apply {
        applyPattern("#,##0")
    }

    private val factionDecimalFormat = (NumberFormat.getNumberInstance(locale) as DecimalFormat).apply {
        applyPattern("#,##0.##")
    }

    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        factionDecimalFormat.isDecimalSeparatorAlwaysShown = true
    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        hasDecimalPoint = s.toString().contains(wholeNumberDecimalFormat.decimalFormatSymbols.decimalSeparator.toString())
    }

    @SuppressLint("SetTextI18n")
    override fun afterTextChanged(s: Editable) {
        val newInputString = s.toString()
        if (newInputString.length < currencySymbol.length) {
            editText.setText(currencySymbol)
            editText.setSelection(currencySymbol.length)
            return
        }

        if (newInputString == currencySymbol) {
            editText.setSelection(currencySymbol.length)
            return
        }

        editText.removeTextChangedListener(this)
        val startLength = editText.text.length
        try {
            val numberWithoutSeparator =
                newInputString.replace(wholeNumberDecimalFormat.decimalFormatSymbols.groupingSeparator.toString(), "")
                    .replace(currencySymbol, "")
            val parsedNumber = factionDecimalFormat.parse(numberWithoutSeparator)
            val selectionStartIndex = editText.selectionStart
            if (hasDecimalPoint) {
                editText.setText("$currencySymbol${factionDecimalFormat.format(parsedNumber)}")
            } else {
                editText.setText("$currencySymbol${wholeNumberDecimalFormat.format(parsedNumber)}")
            }
            val endLength = editText.text.length
            val selection = selectionStartIndex + (endLength - startLength)
            if (selection > 0 && selection <= editText.text.length) {
                editText.setSelection(selection)
            } else {
                editText.setSelection(editText.text.length - 1)
            }

        } catch (e: ParseException) {
            e.printStackTrace()
        }
        editText.addTextChangedListener(this)
    }
}