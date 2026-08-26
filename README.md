# Gita Alarm

A minimal Android morning alarm that begins the day with a Bhagavad Gita shloka.

## Product direction

**namaste**  
**Begin your day.**

The Bhagavad Gita is the first content collection. The architecture is intended to grow into a customizable daily wisdom / ritual alarm engine with additional collections and user-defined content.

## Current MVP

- Jetpack Compose Android UI
- Minimal, calm visual language
- Four bundled, offline Bhagavad Gita recordings: BG1.1–BG1.4
- Local content repository with Sanskrit, transliteration, meaning, and audio mapping
- Persistent sequential progression using SharedPreferences
- Exact-alarm guidance, alarm receiver, high-priority/full-screen alarm notification
- Dedicated wake screen with snooze and “I'm awake” actions
- Progress advances only through “I'm awake”; snoozing or the alarm firing alone never advances it

## Next build steps

1. Add the complete licensed/verified text and audio set.
2. Replace the MVP time control with a time picker and repeat-day selector.
3. Move persisted settings to DataStore/Room as the feature set grows.
4. Add reboot/time-change rescheduling and automated tests.
5. Add categories and custom collections.

## Run

Open the repository in Android Studio, allow Gradle sync, then run the `app` configuration on an Android device or emulator.
