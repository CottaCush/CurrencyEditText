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

/**
 * Helper method to truncate extra decimal digits from numbers.
 * Was created because the previously used approach, [java.math.RoundingMode.DOWN] approach
 * didn't work correctly for some devices.
 *
 * @param number the original number to format
 * @param maxDecimalDigits the maximum number of decimal digits permitted
 * @param decimalSeparator the decimal separator of the currently selected locale
 * @return a version of number that has a maximum of [maxDecimalDigits] decimal digits.
 * e.g.
 * - 14.333 with 2 max decimal digits return 14.33
 * - 19.2 with 2 max decimal digits return 19.2
 */
fun truncateNumberToMaxDecimalDigits(
    number: String,
    maxDecimalDigits: Int,
    decimalSeparator: Char
): String {
    // Split number into whole and decimal part
    var arr = number
        .split(decimalSeparator)
        .toMutableList()

    // We should have exactly 2 elements in our string;
    // the whole part and the decimal part
    if (arr.size != 2) {
        return number
    }

    // Take the first n (or shorter) from the decimal digits.
    arr[1] = arr[1].take(maxDecimalDigits)

    return arr.joinToString(separator = decimalSeparator.toString())
}
