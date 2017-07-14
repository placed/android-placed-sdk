# Placed Affiliate SDK v4.0.0

## Introduction

The Placed SDK for Android is designed to help you add Placed location gathering to your app. It exposes simple public API calls that can be used to turn location gathering on.

The SDK has been designed for easy setup and integration with both new and existing mobile applications.

## Setup

Integrating the Placed SDK into your app involves a few steps as described below.

### First, add the dependency to your project

Before you can integrate the Placed SDK into your app, you need to add the relevant dependency to your app.

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
        // other dependencies go here...

        // Use the latest patch version of the Placed SDK
        // noinspection AndroidLintGradleDynamicVersion
        compile 'com.placed.client:android-persistent-sdk:4.0.+'
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

At this point your app should build, although the Placed SDK will not start yet.

### Second, integrate the Placed SDK

Once you've added the Placed SDK as an app dependency, adding the Placed SDK to your app involves several more steps:

1. Add the Placed provided app key to AndroidManifest.xml
2. Prompt for location permission
3. Register your app and prompt for user opt-in to Placed measurement

#### AndroidManifest.xml changes

Add the application key provided to you by Placed in the application tag of your **AndroidManifest.xml**.

```
        <meta-data android:name="placed_app_key" android:value="YOUR_APP_KEY" />
```

#### Prompting for location permission

If you don't already prompt for fine location permission, you should do so before registering your app with the Placed SDK.

For an example, see the [main activity in the sample app](https://github.com/placed/android-placed-sdk/blob/master/SampleApp/app/src/main/java/com/placed/android/sampleapp/MainActivity.java).

Notice in this case the fine location permission is requested before registering the app and acceptance triggers app registion as described below.

### Registering your app and prompting for user opt-in

Once you've prompted for fine location permission, and on every subsequent app creation, you need to register your app with the Placed SDK.

The following code snippet registers the app:

```java
PlacedAgent.registerAppWithDialog(this);
```

For an example, see the [main activity in the sample app](https://github.com/placed/android-placed-sdk/blob/master/SampleApp/app/src/main/java/com/placed/android/sampleapp/MainActivity.java).

In particular, notice how `registerAppWithDialog` is called in both the case where fine location is first requested and in all subsequent app launches.

## Registration
Please contact your Placed representative to find out how to register your account. If you do not have a representative yet, please email [affiliate@placed.com](mailto:affiliate@placed.com).

## Support
For further guidance contact [affiliate@placed.com](mailto:affliate@placed.com).

## Reference

`static void registerAppWithDialog(final Activity activity)`

This is the main method to register your app with the Placed SDK. It first
checks if the user has already opted in. If not it will show the user an opt-in
dialog for Placed location measurement. On accept, measurement begins.  If the
user declines they will be excluded from measurement and not prompted again in
the future.

This method must be called on every app start, but the opt-in dialog will only
be presented if the user is not opted in.

`static void registerAppWithDialog(final Activity activity, Integer theme)`

Similar to the standard `registerAppWithDialog` method, but pass in a theme
parameter to use a custom dialog theme for user opt-in. The `theme` parameter
should be the resource id of the theme you want to use. See
[Android Styles and Themes](https://developer.android.com/guide/topics/ui/themes.html)
for more details.
