---
title: "Sundaynese"
template: prd-light
version: 1.0.0
status: final
created: "2026-11-26"
updated: "2026-11-26"
---

# Sundaynese

## Table of Contents

- [Vision](#vision) — satirical 1-day Greenfoot life-sim concept and OOP UAS purpose
- [Users and journeys](#users-and-journeys) — player profile and wake-to-sleep daily loop
- [Features and requirements](#features-and-requirements) — Sims-style movement, HUD, and 8 sequential activities (`FR-1` to `FR-11`)
- [Non-goals](#non-goals) — cut features including branching dialogue, fail states, and multi-day progression
- [Technical approach](#technical-approach) — Greenfoot Java architecture, OOP classes, and project folder
- [Success](#success) — end-to-end playable grading criteria
- [Open questions](#open-questions) — pre-build Greenfoot version and sprite asset check

## Vision

**Sundaynese** is a satirical 2D life-simulation game built in Greenfoot (Java) for a 3rd-semester Object-Oriented Programming (OOP) final project (UAS). Inspired by the mission-driven structure of *The Sims Bustin' Out* and *The Urbz: Sims in the City*, the game puts the player in the sandals of a stereotypical lazy dad (*bapak-bapak*) wearing only a *sarung* and *singlet*, whose wife works abroad as a TKW and whose kids are away at *pesantren*. Instead of open sandbox needs management or branching social choices, the game delivers a tight, humorous 1-day loop where the player freely moves the character around 2D rooms and locations to trigger sequential daily activities and quick, retryable mini-games from wake-up until bedtime. To keep the project appropriate for school grading and avoid SARA friction, the satire targets the irresponsible-dad archetype (*"bapak judol & mancing"*) while keeping Sundanese setting, slang, and humor intact.

## Users and journeys

- **Player (Student, OOP Lecturer, and Classmates):** Wants a funny, intuitive 1-day demo with Sims-like exploration and quick mini-games that runs reliably in Greenfoot.
- **Core 1-Day Journey (Wake Up to Bedtime):**
  1. Player wakes up in the bedroom (`Rumah`) wearing only a *sarung* and *singlet*.
  2. Player walks to the table and completes the *Timed Sip & Puff* mini-game to drink black coffee while smoking.
  3. Player accesses the phone and completes the *Quick Chat Sequence* asking the TKW wife for money, increasing the cash balance.
  4. Player opens the *judol* platform and plays the *3-Reel Slot Spin* mini-game, which subtracts money from the cash balance.
  5. Friends message the player on the phone inviting him to fish at the *empang*; player sends `"OTW"`, walks to the clothes rack to equip his black *"BARAYA BAPAK AING / KANG DEDI MULYADI"* fishing shirt (triggering a dramatic highlight showcase), and walks to the front door / classic bike.
  6. Player rides his classic motorcycle (wearing the fishing shirt) to the *empang* via the *Obstacle Dodge* travel mini-game.
  7. At the *empang*, player walks to the fishing spot and completes the *Bobber Timing* mini-game while smoking and watching ambient Sundanese dad chit-chat with NPC dads.
  8. Player returns to `Rumah`, walks to the bed, and sleeps to win the day.

## Features and requirements

### Player Movement, World & HUD

- **FR-1:** Player can freely walk the Sundanese dad protagonist (*sarung* and *singlet* at home, switching to the black *"BARAYA BAPAK AING / KANG DEDI MULYADI"* fishing shirt before riding out) around playable 2D Greenfoot worlds (`Rumah` and `Empang`) using `WASD` or Arrow keys with wall and furniture collision, walk up to interactive objects/hotspots (coffee & cigarette table, phone, shirt rack/wardrobe, front door/classic bike, fishing spot, bed), and press `E` or `SPACE` when prompted to start the active objective or mini-game.
- **FR-2:** Game tracks progress through the ordered daily mission checklist (`Wake Up` → `Kopi & Rokok` → `Chat Istri` → `Judol` → `OTW Empang` → `Ganti Kaos Mancing` → `Naik Motor` → `Mancing & Ngobrol` → `Tidur`) and displays the current objective on the HUD at all times.
- **FR-3:** Game displays a persistent cash balance HUD (`Uang`) that updates immediately after receiving money from the wife and after spending money on *judol*.

### Sequential Daily Activities & Quick Mini-Games

- **FR-4:** Starting the game places the player in `Rumah` waking up beside the bed and unlocks the first objective (`Kopi & Rokok`) with a clear on-screen indicator at the coffee table.
- **FR-5:** Interacting with the coffee and cigarette table launches the *Timed Sip & Puff* mini-game: a marker slides back and forth across a horizontal bar with a green target zone, and pressing `SPACE` inside the zone 3–5 times completes the activity (missing simply lets the player try on the next swing), unlocking the phone chat objective.
- **FR-6:** Interacting with the phone launches the *Quick Chat Sequence* with the wife: player clicks Send or taps `SPACE` through 2–3 pre-written excuse messages begging for money with no branching choices, triggering a money transfer that adds a fixed remittance amount to the `Uang` HUD.
- **FR-7:** Opening the *judol* activity launches the *3-Reel Slot Spin* mini-game: player clicks **SPIN** or presses `SPACE` for 3–5 quick spins, with each spin subtracting cash from the `Uang` HUD (featuring comedic near-misses/losses) until the required spins finish.
- **FR-8:** Finishing *judol* triggers an incoming phone invitation from friends to fish at the *empang*; player sends `"OTW"`, unlocking the outfit-change objective in `Rumah`.
- **FR-8b:** Before leaving `Rumah` for the *empang*, player must walk to the clothes rack (`wardrobe_shirt`) and interact to put on his black *"BARAYA BAPAK AING / KANG DEDI MULYADI"* fishing t-shirt; equipping it triggers a dramatic highlight showcase displaying the front/back graphic design (including the orange & white tiger *"KANG DEDI MULYADI"* back print), swaps Mang Ujang's top-down and bike-rider sprites to the fishing shirt, and unlocks the front door / classic bike hotspot.
- **FR-9:** Leaving `Rumah` launches the *Obstacle Dodge* classic motorcycle mini-game: player uses Arrow keys on a short scrolling road to dodge obstacles (potholes, chickens, *angkot*) until the distance bar reaches 100% (`Empang`), with crashes restarting the short ride immediately.
- **FR-10:** Arriving at `EmpangWorld`, player walks to the fishing spot while NPC Sundanese dads display scripted ambient chit-chat speech bubbles, then completes the *Bobber Timing* mini-game while smoking: press `SPACE` to cast, wait for `"STRIKE!"`, and hit `SPACE` within the timing window to catch 2–3 fish (missed bites allow instant recast).
- **FR-11:** Failing any mini-game attempt never triggers a Game Over state and immediately lets the player retry that activity; once *empang* fishing finishes, returning to `Rumah`, walking to the bed, and pressing `E` or `SPACE` completes the day and shows the Win screen.

## Non-goals

- **Branching social choice conversations / dialogue trees:** Cut completely so the 1-day demo stays focused on physical movement and fast comedic activities.
- **Game Over / lose states:** Players cannot fail the day permanently; every failed mini-game attempt is infinitely retryable.
- **Multi-day calendar progression or save/load files:** Out of scope for a tight 1-day UAS demo.
- **Full Sims needs system:** No hunger, hygiene, bladder, energy bars, or house-building tools.
- **Real-money gambling, online APIs, or multiplayer:** *Judol* is strictly a satirical, offline mini-game with fake in-game currency.

## Technical approach

- **Project Location:** `D:\dandy_personal\Dandy_College\SEMESTER_3\oop\UAS`
- **Platform & Stack:** Greenfoot Desktop (Java) using core classes (`greenfoot.World`, `greenfoot.Actor`, `greenfoot.GreenfootImage`, `greenfoot.GreenfootSound`), with no special lecturer-mandated class counts, UML diagrams, or extra rubric constraints.
- **OOP Architecture:**
  - **Scene & Mini-Game Worlds (`World` subclasses):** Separate worlds for exploration and mini-games (`TitleWorld`, `HouseWorld`, `PhoneWorld`, `RoadBikeWorld`, `EmpangWorld`, `WinWorld`).
  - **Abstraction & Polymorphism:** An abstract `ActivityWorld` base class or `MiniGame` interface (`start()`, `isCompleted()`, `reset()`) so every activity shares a consistent lifecycle and instant-retry behavior.
  - **Shared State (`GameState`):** Central state class passed or accessed across worlds holding `currentStep`, `money`, and `completedActivities`.
  - **Entities (`Actor` subclasses):** `BapakActor` (2D Sims-style `WASD`/Arrow movement + wall/furniture collision, wearing *sarung* & *singlet* at home and swapping to the black graphic fishing shirt after equipping it), `InteractableObject` subclasses (coffee table, phone, shirt rack/wardrobe, bike/door, fishing spot, bed), `NpcDadActor` (fishing buddies with ambient speech bubbles), `HudOverlay` (objective + `Uang` display), and mini-game actors.
- **Assets:** 2D PNG sprites stored in Greenfoot's `images/` folder and optional SFX in `sounds/`.

## Success

A player or OOP lecturer can launch the Greenfoot scenario, walk the *sarung*-and-*singlet* dad freely around `Rumah` and `Empang`, complete all sequential activities (*Timed Sip & Puff*, *Quick Chat Sequence*, *3-Reel Slot Spin*, *Obstacle Dodge* bike ride, *Bobber Timing* fishing with ambient dad banter, and bedtime) with zero crashes or dead-ends, retry any failed mini-game smoothly, and reach the Win screen.

## Open questions

- **Greenfoot version & sprite assets (non-blocking pre-build check):** Confirm which Greenfoot version is installed locally and whether PNG sprites will be provided by the user or generated as starter assets — **User** (revisit when starting implementation).

## Assumptions

- `Technical approach`: Standard Greenfoot desktop project using the bundled Java/Greenfoot JAR with no external third-party libraries.
