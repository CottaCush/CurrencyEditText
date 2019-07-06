# CurrencyEditText 

A simple library for taken currency inputs. 

## Gradle Dependency

Add the dependency to your app's `build.gradle`:

```groovy
implementation 'com.cottacush:CurrencyEditText:0.0.1'
```

## Usage

Add the `CurrencyEditText` to your layout. 
```xml
   <com.cottacush.android.currencyedittext.CurrencyEditText
            app:currencySymbol="₦"
            app:useCurrencySymbolAsHint="true"
            app:localeTag="en-NG"
            android:layout_width="wrap_content"
            android:layout_height="60dp"
            android:ems="10"
            android:id="@+id/editText"/>
```
That's all for basic setup. Your editText should automatically format currency inputs.
 
 
## Customisation

#### Currency Symbol
You can You can specify the currency symbol using the  `currencySymbol` and `useCurrencySymbolAsHint` attributes in xml. 
The formatted currency value will be prepended with the `currencySymbol` value. The `currencySymbol` value can also 
be used as hint, as described by the `useCurrencySymbolAsHint` attribute.
 
```xml
   <com.cottacush.android.currencyedittext.CurrencyEditText
            app:currencySymbol="₦"
            app:useCurrencySymbolAsHint="true"/>
```
or programmatically:
```kotlin
   currencyEditText.setCurrencySymbol("₦", useCurrencySymbolAsHint = true)
```

#### Locale 
The `CurrencyEditText` uses the default `Locale` if no locale is specified. `Locale` can be specified programmatically via
```kotlin
   currencyEditText.setLocale(locale)
```
 Locales can be specified using locale-tags. The locale tag method requires API 21 and above. Instructions on how to construct
 valid `Locale` and locale-tags can be found [here](https://docs.oracle.com/javase/tutorial/i18n/locale/create.html#factory).
 With xml:  
 ```xml
    <com.cottacush.android.currencyedittext.CurrencyEditText
             app:currencySymbol="₦"/>
 ```
         