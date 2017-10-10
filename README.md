# Placed Affiliate SDK

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

        // NOTE: If you use any Google Play services APIs above version 
        // 10.0.1, add the following to avoid crashes caused by inconsistent
        // API versions.
        compile('com.google.android.gms:play-services-ads:<your-version>') {
            force = true
        }
        compile('com.google.android.gms:play-services-location:<your-version>') {
            force = true
        }
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
3. Register the user for location collection by the Placed SDK.

#### AndroidManifest.xml changes

Add the application key provided to you by Placed in the application tag of your **AndroidManifest.xml**.

```
<meta-data android:name="placed_app_key" android:value="YOUR_APP_KEY" />
```

#### Prompting for location permission

If you don't already prompt for fine location permission, you should do so before registering the user with the Placed SDK.

For an example, see the [main activity in the sample app](https://github.com/placed/android-placed-sdk/blob/master/SampleApp/app/src/main/java/com/placed/android/sampleapp/MainActivity.java).

Notice in this case the fine location permission is requested before registering the user and acceptance triggers app registion as described below.

### Registering a user

Once you've prompted for fine location permission you need to register your user with the Placed SDK.

The following code snippet registers the user:

```java
PlacedAgent.registerUser(this);
```

For an example, see the [main activity in the sample app](https://github.com/placed/android-placed-sdk/blob/master/SampleApp/app/src/main/java/com/placed/android/sampleapp/MainActivity.java).

## How to join
Please contact your Placed representative to find out how to register your account. If you do not have a representative yet, please email [affiliate@placed.com](mailto:affiliate@placed.com).

## Support
For further guidance contact [affiliate@placed.com](mailto:affliate@placed.com).

## Reference

`static void registerUser(final Context context)`

This is the main method to register the user with the Placed SDK and begin
location collection by the Placed SDK. If you have EULA or terms of service that the user is required to accept before tracking,
call this method after the user accepts those terms.
