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

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.text.ParseException
import java.util.IllformedLocaleException
import java.util.Locale

internal fun parseMoneyValue(
    value: String,
    groupingSeparator: String,
    currencySymbol: String
): String =
    value.replace(groupingSeparator, "").replace(currencySymbol, "")

internal fun parseMoneyValueWithLocale(
    locale: Locale,
    value: String,
    groupingSeparator: String,
    currencySymbol: String
): Number {
    val valueWithoutSeparator = parseMoneyValue(value, groupingSeparator, currencySymbol)
    return try {
        NumberFormat.getInstance(locale).parse(valueWithoutSeparator)!!
    } catch (exception: ParseException) {
        0
    }
}

@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
internal fun getLocaleFromTag(localeTag: String): Locale {
    return try {
        Locale.Builder().setLanguageTag(localeTag).build()
    } catch (e: IllformedLocaleException) {
        Locale.getDefault()
    }
}

internal fun isLollipopAndAbove(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
