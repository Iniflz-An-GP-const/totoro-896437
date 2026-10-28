# Totoro

This project was generated from a donor template by TemplateForge.
Application type: **Advanced Sport Tracker** — a catalog of workouts
with guided exercises, history, statistics and a profile (Jetpack
Compose, Koin, Room).

- Package: `rubery.totoro.digital`
- Project name: `Totoro`

## Where the content lives

- `app/src/main/res/values/strings.xml` — English strings, the primary
  locale. `app/src/main/res/values-<lang>/strings.xml` — the same
  resources translated into de, es, fr, hi, id, pt and ar.
- The Kotlin data files sit in the `data` package of
  `rubery.totoro.digital`, under `app/src/main/java/`:
  - `data/repository/WorkoutRepository.kt` — the workout catalog. It
    ships empty. Each workout carries its own exercises, shaped by
    `ExerciseModel` in `data/model/ExerciseModel.kt`.
  - `data/repository/TipRepository.kt` — the tips shown on the home
    screen. Unlike the workouts, they ship written: six tips with their
    English text. A task adds their other languages and leaves the
    English entries as they are.
  - `data/model/WorkoutModel.kt` — `WorkoutModel`, the `WorkoutType`
    enum (STRENGTH, CORE, UPPER_BODY, HIIT, each with its icon) and the
    `Difficulty` enum (BEGINNER, INTERMEDIATE, ADVANCED).
  - `data/model/ExerciseModel.kt` — `ExerciseModel` and the `Intensity`
    enum (LOW, MODERATE, HIGH, MAXIMUM).
  - `data/model/Localized.kt` — `forCurrentLocale()`, the lookup every
    `texts` map is read through. Nothing to fill in here.

## Data invariants

- English is the primary language. `values/strings.xml` and the `"en"`
  entry of every `texts` map are in English.
- A `texts` map is keyed by two-letter language code. The `"en"` entry
  is mandatory — it is the fallback and the source of the workout name
  used to look up the photo; the other languages of this project follow
  it: "de", "es", "fr", "hi", "id", "pt", "ar". A workout and an exercise hold one name per
  language; a tip holds a `TipText` (title and message) per language.
- The three enums are fixed: the filters, the statistics and the
  history rely on them. Do not add, rename or remove a value.
- Every `WorkoutType` value needs its label resource —
  `workout_type_strength_title`, `workout_type_core_title`,
  `workout_type_upper_body_title` and `workout_type_hiit_title`. The
  project does not build without all four.
- The `difficulty_*`, `intensity_*` and `workout_type_*_title`
  resources are translated like any other resource, but they keep their
  original meaning (beginner/intermediate/advanced,
  low/moderate/high/maximum, strength/core/upper body/HIIT) in every
  language.
- `WorkoutModel.id` and `TipModel.id` are unique, numbered from 1
  upwards. The workout screen looks a workout up by its id.
- Every workout takes one `WorkoutType` and one `Difficulty` value. The
  name of a workout and its exercises fit its type: STRENGTH is
  resistance work, CORE is trunk and stability work, UPPER_BODY is
  chest, back, shoulders and arms, HIIT is short timed intervals.
- An `ExerciseModel` is either rep-based (`sets` and `repetitions`
  non-null, `duration` null) or duration-based (`duration` non-null,
  `sets` and `repetitions` null, running as one timed set). Never leave
  all three null and never write a zero `duration`: the screen counts
  down from it, and an exercise with neither time nor reps completes
  instantly.
- `duration` and `restDuration` are `Long` **milliseconds**, not
  seconds. Write them as a whole-second count times 1000 in the
  `N_NNNL` underscore form — `30_000L` for 30 seconds, `120_000L` for 2
  minutes — never a bare `30` or `120`, and never `0L`. The rest after
  a set runs for `restDuration`, so keep it positive.
- `intensity` is the effort of the exercise itself, not of the workout.
- `imageUrl` is loaded straight into an image view, so it has to be a
  direct image link of exactly one of these two shapes:
  `https://images.unsplash.com/photo-<id>` or
  `https://images.pexels.com/photos/<id>/<name>.jpeg`. A gallery page
  URL returns HTML and does not load; an unverified link is worse than
  none, so the field stays empty unless the URL was checked.
- A translation keeps the resource name and the placeholders of the
  English string (`%1$s`, `%2$d`, ...) in the same number. Their order
  changes only if the language needs it, and then in positional form
  (`%2$s` before `%1$s`).
- A right-to-left language (Arabic, Hebrew, Persian, Urdu) reorders a
  run of digits and separators sitting inside its own text: `%1$d/%2$d`
  can reach the screen as `8/3`, and `%1$s · %2$d/%3$d` with its parts
  swapped. Keep the placeholders in the order the English string gives
  them, and wrap each such run in left-to-right marks —
  `\u200E%1$d/%2$d\u200E` — which are invisible and change nothing else.
  It applies to every digit-and-symbol string: a counter, a percentage,
  a set or progress pair, a duration, anything joined by `/`, `×` or
  `·`.
- Resources marked `translatable="false"` — the app name, the version,
  the developer name, the contact link — stay exactly as they are and
  are not copied into `values-<lang>/`; neither are empty resources.
- In any `strings.xml`, escape an apostrophe as `\'` and an ampersand
  as `&amp;`. A bare apostrophe is still well-formed XML, so a plain
  XML check will not catch it — aapt2 will, by failing the build.

## Text has to fit the layout

- Rephrase a string resource when it reads as generic and text written
  for the training theme of this app would fit better. One that already
  reads naturally stays as it is — the goal is a cohesive result, not
  rewriting for its own sake. Pure interface chrome (Back, Next, Skip,
  Cancel, Done) has no theme to fit: leave it alone.
- A replacement keeps roughly the length of the text it replaces — the
  screens were built around those lengths, and longer text wraps badly
  or gets truncated on a phone. The same goes for a translation against
  its English original.
- Buttons, tabs, filter chips, labels and titles stay short, usually
  one to three words: do not turn a label into a sentence, and do not
  add marketing filler, adjective chains, or the company name where the
  original had none.

## Working in this repo

- Keep changes scoped to what the current task asks for — this is a
  freshly generated project, not a hand-maintained one, so do not
  "clean up" unrelated files.
- Preserve the existing package structure and naming conventions found
  in the generated code instead of introducing new patterns.
- Do not modify build/gradle configuration, project name, package name,
  or app id — those were set intentionally during generation.
- Screen variants live in `v_*` packages, and the theme and typography
  in `ui/theme`; they were selected during generation and are not
  content.
- Do not run Gradle builds, dependency downloads, Android lint, or
  emulator tests. The project is published as-is after your work.
  Validate only the modified data and resource files with lightweight
  local checks.
- Do not change business logic or UI code — limit changes strictly to
  the data described above (workouts, exercises, tips, string resources
  and their translations). Creating the `values-<lang>/strings.xml`
  files is part of that data.
- When filling in placeholder data, match the style and format already
  used elsewhere in the file.
