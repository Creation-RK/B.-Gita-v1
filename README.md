# Gita Alarm

A minimal Android morning alarm that begins the day with a Bhagavad Gita shloka.

## Product direction

**namaste**  
**Begin your day.**

The Bhagavad Gita is the first content collection. The architecture is intended to grow into a customizable daily wisdom / ritual alarm engine with additional collections and user-defined content.

## Current MVP scaffold

- Jetpack Compose Android UI
- Minimal, calm visual language
- Bhagavad Gita sample shloka
- Exact-alarm permission declaration
- Alarm receiver + high-priority notification
- Sequential-content direction documented in the product model

## Next build steps

1. Add a local Gita content database containing the complete licensed/verified text set.
2. Add audio assets and Media3 playback.
3. Persist alarm settings and shloka progress with Room/DataStore.
4. Build a real time picker and repeat-day selector.
5. Add the alarm/ringing screen and snooze/dismiss behaviour.
6. Add exact-alarm permission guidance for Android 12+.
7. Add categories and custom collections.
8. Add tests for progression and alarm scheduling.

## Run

Open the repository in Android Studio, allow Gradle sync, then run the `app` configuration on an Android device or emulator.
