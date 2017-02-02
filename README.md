# Placed Affiliate SDK (Android) v1.30
  
## Integrating your application with the Placed Monetization SDK
  
### Adding the dependency to your project

1. Add the following to your **root** `build.gradle` file:

    ```
    allprojects {
        repositories {
            ...

            maven { url "https://raw.githubusercontent.com/placed/android-placed-sdk/master/repository" }
        }
    }
    ```

2. Add the following to your **app** `build.gradle` file:

    ```
    dependencies {
        ...

        compile 'com.placed.client:android-persistent-sdk:1.30'
    }
    ```

3. You may encounter Lint error: 'InvalidPackage: Package not included in Android' related to Okio and Retrofit. (This is a known issue with Okio that you can read about [here](https://github.com/square/okio/issues/58).)

    If so, create a `lint.xml` with the following contents:
    ```
    <lint>
        <issue id="InvalidPackage">
            <ignore regexp=".*okio.*" />
            <ignore regexp=".*retrofit.*" />
        </issue>
    </lint>
    ```

    And reference it from your **app** `build.gradle` file:
    ```
    android {
        ...
        lintOptions {
            lintConfig file("lint.xml")
        }
    }
    ```
  
### Configuration
* Add the application key provided to you by Placed in the application tag of your **AndroidManifest.xml**.
        <meta-data android:name="placed_app_key" android:value="YOUR_APP_KEY" />  

* Request runtime location permission (Android 6.0 Marshmallow)

    If your app targets Android 6.0 Marshmallow (API level 23) or higher, your app will need prompt for `ACCESS_FINE_LOCATION` permission at runtime. You can read more information in Android's documentation for [Requesting Permissions at Run Time](http://developer.android.com/training/permissions/requesting.html).

    You'll want to add this permission prompt to the Activity that calls `PlacedAgent.registerApp()` or `PlacedAgent.registerAppWithDialog`. For example:


        //-------
        // Add a constant for the request code
        private static final int REQUEST_CODE_PERMISSION = <unique int for requesting location permission for the use of the sdk>;

        //--------
        // This if() block needs to happen before PlacedAgent.registerApp() or PlacedAgent.registerAppWithDialog() is called
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSION);
            return;
        }

        //---------
        // This code needs to go into the onRequestPermissionsResult() method in the same Activity that calls ActivityCompat.requestPermissions
        if(requestCode == REQUEST_CODE_PERMISSION) {
            for(int idx = 0; idx < permissions.length; ++idx) {
                if(permissions[idx].equals(Manifest.permission.ACCESS_FINE_LOCATION) && grantResults[idx] == PackageManager.PERMISSION_GRANTED) {
                    // call a method that register the app or call one of the PlacedAgent registerApp methods
                    break;
                }
            }
        }

### Integration

* Place the following code in your application's main activity `onCreate` method:
* Make sure to replace **YOUR\_APP\_KEY** with your application key

        /**
        * Call this method for default opt-in behavior, this will show a dialog
        * to your users asking them to opt-in to location tracking (recommended)
        * This dialog will only be shown once over the lifetime of the application
        */
        PlacedAgent.registerAppWithDialog(YourMainActivity.this, YOUR_APP_KEY);  
        
        /**
        * If you want a custom theme for the dialog, pass in the style/theme as a parameter
        */
        PlacedAgent.registerAppWithDialog(YourMainActivity.this, YOUR_APP_KEY, R.style.your_dialog_theme);

* To send additional information, such as demographics, to Placed, please include the following code in your application's main activity after calling `PlacedAgent.registerAppWithDialog`:

* Log a unique identifier

        /**  
        * This method logs a unique id  
        *  
        * @param context Your application's context  
        * @param id the unique id  
        */
        PlacedAgent.logUniqueId(Context context, String id)

* Sending demographic data

        /**
        * This method is used to log demographics
        *
        * @param jsonString A string representing a JSON blob of the raw data returned  
        * by the source API
        * @param source A string with the name of the demographics source (e.g. Facebook)  
        * @param version If the source API is versioned please include the version number.  
        */
        PlacedAgent.logDemographics(Context context, String jsonString, String source, String version)

### Register
Please contact your Placed representative to find out how to register your account. If you do not have a representative yet, please email [affiliate@placed.com](mailto:affiliate@placed.com)

## Restricting Device Id Collection
To limit device id collection to just the advertising id, add the following line of code before calling registerApp or registerAppWithDialog
        PlacedAgent.setRestrictDeviceIds(YourMainActivity.this, true)

### That's it!

### Support
For further guidance contact [affiliate@placed.com](mailto:affliate@placed.com)

## SDK Glossary

### Placed Agent Methods  
  
`static void registerAppWithDialog(final Activity activity, final String appKey)`  
`static void registerAppWithDialog(final Activity activity, final String appKey, Integer theme)`  
Show the user an opt-in dialog for Placed location measurement. If the user accepts the terms in the dialog, they will be opted-in to Placed location measurement. Pass in the optional theme parameter to use a custom dialog theme.

`static boolean shouldDisplayDialog(final Activity activity, final String appKey)`  
Returns true if the current device can have an opt-in dialog displayed, meaning that they are either in the US or US only has been disabled and they have not seen a dialog before. To be used when implementing a custom opt-in dialog.

`static void registerApp(Context context, String appKey)`  
Register this install of your app with Placed. This method automatically opts a user in to location measurement. Usually you would use this method if you are creating your own opt-in dialog, or want finer control of when a user is opted-in.  
  
`static void logUniqueId(Context context, String id)`  
Log an `id` that is unique to this install of your application. This method is used with custom integrations with Placed.
  
`static void setRestrictDeviceIds(Context context, boolean restrict)`
If you app falls under Google's policy rules that prohibit collection of various device identifiers, call this method with `restrict` set to true so that Placed will only collect identifiers that meet that policy.
  
`static void logFacebookLikes(Context context, String jsonString)`  
This method is used for custom integrations with Placed. If you have questions please inquire with your contact at Placed.  
  
`static void logDemographics(Context context, String jsonString, String source, String version)`  
This method is used for custom integrations with Placed. If you have questions please inquire with your contact at Placed.  
