# Placed Affiliate SDK

## Introduction

The Placed SDK for Android is designed to help you add Placed location gathering to your app. It exposes simple public API calls that can be used to turn on and off location gathering.

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

#### Location Permission

As outlined in Section 5 of the [Placed Affiliate Agreement](https://affiliate.placed.com/placed-affiliate-agreement/), you must satisfy two requirements prior to registering a user with the Placed SDK:  
1. *Gather Express Consent for User Data Collection via Opt-in Dialog*  
In addition having a legally compliant privacy policy describing Placed’s collection of location and device information, you must include a discrete opt-in dialog which gathers express consent for data collection. This dialog appears prior to the fine location permission prompt, and includes:
    - The language: “*Aggregated device data, including location and apps, is measured for the purposes of market research by Placed, Inc.*”
    - Links to the Placed [Terms of Service](https://www.placed.com/terms-of-service) and [Privacy Policy](https://www.placed.com/privacy-policy)
    - Buttons to “Accept” or “Cancel”


2. *Prompt for Fine Location Permission*  
Eligible users must allow fine location permission, which subsequently triggers app registration as described in the [Registering a User](https://github.com/placed/android-placed-sdk#registering-a-user) section.

For an example of the opt-in dialog and location permission prompt, please refer to the [main activity of the sample app](./SampleApp/app/src/main/java/com/placed/android/sampleapp/MainActivity.java). We have also provided a [gallery for inspiration](./gallery) on how you can better integrate the opt-in experience into your app.

#### Register a user

Once you've prompted for location permission and gathered express consent to collect user data, you need to register your user with the Placed SDK.

The following code snippet registers the user:

```java
PlacedAgent.registerUser(this);
```

For an example, see the [main activity in the sample app](https://github.com/placed/android-placed-sdk/blob/master/SampleApp/app/src/main/java/com/placed/android/sampleapp/MainActivity.java).

## How to join
Please contact your Placed representative to find out how to register your account. If you do not have a representative yet, please email [affiliate@placed.com](mailto:affiliate@placed.com).

## Support
For further guidance, please contact [affiliate@placed.com](mailto:affliate@placed.com).
