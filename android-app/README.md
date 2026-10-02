# Android APK

Standalone Android 8+ application. Bundles the React interface from ../web-app as static assets; no server or network permission is needed. LocalStorage stores course state; Android SharedPreferences mirrors it for native scheduled reminders and recovery.

Build with Node 22, JDK 17, Android SDK 35 and Gradle 8.11.1:

```
npm install
ln -s ../android-app/node_modules ../web-app/node_modules
npm run build:web
gradle :app:assembleRelease
```

APK: app/build/outputs/apk/release/app-release.apk. Release build is signed with the local Android development key for direct installation; use a protected release signing identity for Play distribution. Application ID: ru.peppercourse.tracker.

Daily reminders use Android AlarmManager, are cancelled on completion and restored after reboot/timezone changes. Android power-saving policies may delay delivery. Notification sound can also be controlled per channel in Android settings. Force-stopping the app blocks alarms until it is opened again.

Build workflow publishes the APK and checksum under release android-v1.1.0. Existing browser progress does not automatically transfer to the installed app.
