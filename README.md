# CurrencyEditText 

A library to dynamically format your `EditTexts` to take currency inputs.

[![ci](https://github.com/CottaCush/CurrencyEditText/actions/workflows/ci.yml/badge.svg)](https://github.com/CottaCush/CurrencyEditText/actions/workflows/ci.yml)
[![Maven Central](https://img.shields.io/maven-central/v/com.cottacush/CurrencyEditText.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:%22com.cottacush%22%20AND%20a:%22CurrencyEditText%22)

<img src="https://raw.githubusercontent.com/cottacush/currencyEditText/master/sample.gif" width="280" />


## Gradle Dependency

Add the dependency to your app's `build.gradle`:

```groovy
implementation 'com.cottacush:CurrencyEditText:<insert-latest-version-here>'
```
For versions, kindly head over to the [releases page](https://github.com/CottaCush/CurrencyEditText/releases)

## Usage

Add the `CurrencyEditText` to your layout. 
```xml
   <com.cottacush.android.currencyedittext.CurrencyEditText
            ...
            android:layout_width="wrap_content"
            android:layout_height="60dp"
            android:ems="10"
            android:id="@+id/editText"/>
```
That's all for basic setup. Your `editText` should automatically format currency inputs.
 
 
## Customisation

### Currency Symbol
You can specify the currency symbol using the  `currencySymbol` and `useCurrencySymbolAsHint` attributes in xml. 
The formatted currency value will be prepended with the `currencySymbol` value. The `currencySymbol` value can also 
be used as hint, as described by the `useCurrencySymbolAsHint` attribute.
 
```xml
   <com.cottacush.android.currencyedittext.CurrencyEditText
            ...
            app:currencySymbol="₦"
            app:useCurrencySymbolAsHint="true"/>
```
or programmatically:
```kotlin
   currencyEditText.setCurrencySymbol("₦", useCurrencySymbolAsHint = true)
```

### Locale 
The `CurrencyEditText` uses the default `Locale` if no locale is specified. `Locale` can be specified programmatically via
```kotlin
   currencyEditText.setLocale(locale)
```
 Locales can also be specified using locale-tags. The locale tag method requires API 21 and above. Instructions on how to construct
 valid `Locale` and locale-tags can be found [here](https://docs.oracle.com/javase/tutorial/i18n/locale/create.html#factory).
  
 ```xml
    <com.cottacush.android.currencyedittext.CurrencyEditText
             ...
             app:localeTag="en-NG"/>
 ```
 or programmatically via 
 
 ```kotlin
    currencyEditText.setLocale("en-NG") //Requires API level 21 and above.
 ```
 
### Decimal Places
The maximum number of decimal digits can be specified using the `maxNumberOfDecimalDigits` attributes in the xml. However, it is optional and a default value of 2 is used.

```xml
   <com.cottacush.android.currencyedittext.CurrencyEditText
            ...
            app:maxNumberOfDecimalDigits="3" />
```
or programmatically:
```kotlin
   currencyEditText.setMaxNumberOfDecimalDigits(3)
```
        
## Getting the input value

Numeric values for the editText can be gotten as shown below. 
 ```kotlin
    currencyEditText.getNumericValue()
 ```
If you need a `BigDecimal` to continue your monetary calculations right away, you can get it by  
 ```kotlin
    currencyEditText.getNumericValueBigDecimal()
 ```

## Using the formatter directly
If you'd like to use the library with any `EditText` widget, you can attach your `EditText` with the `CurrencyInputWatcher` class:
  
 ```kotlin
    editText.addTextChangedListener(CurrencyInputWatcher(editText,"₦", Locale.getDefault()))
 ```
 
##  License

    Copyright (c) 2019 Cotta & Cush Limited.

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
