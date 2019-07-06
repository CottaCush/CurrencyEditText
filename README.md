# CurrencyEditText 

A library to dynamically format your `EditTexts` to take currency inputs.

[![Build Status](https://travis-ci.com/CottaCush/CurrencyEditText.svg?branch=master)](https://travis-ci.com/CottaCush/CurrencyEditText)
[ ![Download](https://api.bintray.com/packages/cottacush/maven/CurrencyEditText/images/download.svg) ](https://bintray.com/cottacush/maven/CurrencyEditText/_latestVersion)

<img src="https://raw.githubusercontent.com/cottacush/currencyEditText/master/sample.gif" width="280" />


## Gradle Dependency

Add the dependency to your app's `build.gradle`:

```groovy
implementation 'com.cottacush:CurrencyEditText:0.0.2'
```

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
 
## Getting the input value

Numeric values for the editText can be gotten as shown below. 
 ```kotlin
    currencyEditText.getNumericValue()
 ```
 
## Using the formatter directly
 If you'd like to use the library with any `EditText` widget, you can attach your `EditText` with the `CurrencyInputWatcher` class:
  
 ```kotlin
    editText.addTextChangedListener(CurrencyInputWatcher(editText,"₦", Locale.getDefault()))
 ```
 
