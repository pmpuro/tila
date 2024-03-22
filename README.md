# tila
`Tila` is a Finnish word meaning `state` (among other things).

## Purpose

Tila provides a straightforward way to handle a state in an application.
It enables to extract click handlers away from UI code making it simple.
Also, it makes Composable functions truly transform their state from data.

## Principles
- Not tied to Android/OS/ViewModel (Jetpack Compose's MutableState dependency exists)
- App data and ui state/data are separated
- Easy to inject a new observed state
- Extract click handlers from UI code
- Easy to preview composable functions with any app data
- Event handlers (click handlers) work with app data and their arguments and produce modified app data
- Click handlers can be tested separately (pure functions)
- Derivatives can be tested separately (pure functions)
- Testable
