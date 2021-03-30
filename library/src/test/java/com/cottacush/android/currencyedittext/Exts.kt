package com.cottacush.android.currencyedittext

import android.text.Editable
import android.text.TextWatcher

fun TextWatcher.runAllWatcherMethods(
        s: Editable,
        start: Int = 0,
        count: Int = 0,
        before: Int = 0,
        after: Int = 0
) {
    beforeTextChanged(s, start, count, after)
    onTextChanged(s, start, before, count)
    afterTextChanged(s)
}

fun String.removeCharAt(i: Int) = removeRange(i..i)

fun String.removeLastChar() = removeCharAt(length-1)

fun String.removeFirstChar() = removeCharAt(0)