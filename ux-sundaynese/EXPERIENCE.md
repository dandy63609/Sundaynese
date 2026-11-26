---
title: "Sundaynese — Experience Specification"
status: final
created: "2026-11-26"
updated: "2026-11-26"
---

# EXPERIENCE.md — Sundaynese

## Foundation

- **Form Factor & Platform:** Desktop 2D game running inside Greenfoot (Java) at `800x600px` (`1px` cell resolution), built for keyboard-first play with optional mouse clicks on smartphone UI buttons.
- **Visual Identity Contract:** All colors, fonts, spacing, shapes, and component styles are defined in [DESIGN.md](./DESIGN.md) and referenced below via `{token}` paths.
- **Dual-Camera Model:**
  - **Scene Start & Mini-Games (Front / POV Camera):** Every major beat opens or plays in a close-up front/POV framing (waking up in bed, holding kopi & rokok, staring at the phone screen, riding down the road, casting a fishing line) to deliver punchy comedy and clear controls.
  - **Exploration (Top-Down Camera):** Between activities in `Rumah` and `Empang`, the camera pulls up to a Sims-style top-down view where the player freely walks Mang Ujang around rooms, navigates around furniture, and triggers hotspots.
- **Zero-Fail Loop Philosophy:** No timers can end the day in defeat and no mini-game fail state triggers Game Over; every missed attempt flashes brief comedic feedback and resets immediately for retry.

## Information Architecture

Every stated requirement in [prd-light-sundaynese.md](../prd-light-sundaynese.md) maps to a specific Greenfoot world or in-world overlay:

| Surface / World | Camera Angle | Entry Trigger | Exit Trigger | Mapped FRs |
|---|---|---|---|---|
| `TitleWorld` | Front / Title Key Art | Launch Greenfoot scenario | Press `SPACE` / Click **Mulai Hari** | `FR-4` |
| `HouseWorld` (`Rumah`) | **POV Intro** → **Top-Down** | Start game, finish phone, or return from *empang* | Interact with coffee table, phone, shirt rack, bike/door, or bed | `FR-1`, `FR-2`, `FR-3`, `FR-4`, `FR-5`, `FR-8`, `FR-8b`, `FR-11` |
| Coffee & Smoke Overlay (in `HouseWorld`) | **Front / POV Close-Up** | Press `E`/`SPACE` at coffee table (`STEP_1_COFFEE`) | Hit 4 green zones in *Timed Sip & Puff* | `FR-5`, `FR-11` |
| `PhoneWorld` (Chat Mode) | **POV Smartphone Screen** | Interact with phone (`STEP_2_CHAT_WIFE` or `STEP_4_FRIEND_INVITE`) | Finish wife transfer or reply `"OTW"` to friends | `FR-6`, `FR-8` |
| `PhoneWorld` (*Judol* Mode) | **POV Smartphone Screen** | Auto-switch or tap *judol* app after wife transfer (`STEP_3_JUDOL`) | Complete 4 *3-Reel Slot Spin* rounds | `FR-7`, `FR-11` |
| Fishing Shirt Highlight Overlay (in `HouseWorld`) | **POV Outfit Showcase** | Press `E`/`SPACE` at clothes rack (`STEP_5_CHANGE_SHIRT`) | Press `SPACE` / `E` after inspecting the `"BARAYA BAPAK AING / KANG DEDI MULYADI"` highlight → unlocks classic bike | `FR-8b` |
| `RoadBikeWorld` (`Jalan`) | **Front-Scroll / Top-Down Road** | Press `E`/`SPACE` at front door / classic bike (`STEP_6_BIKE_RIDE`) | Reach 100% distance bar to `Empang` | `FR-9`, `FR-11` |
| `EmpangWorld` (`Empang`) | **POV Arrival** → **Top-Down** → **POV Fishing** | Complete bike ride (`STEP_7_FISHING`) | Catch 3 fish in *Bobber Timing* → auto/prompt return home | `FR-1`, `FR-2`, `FR-3`, `FR-10`, `FR-11` |
| `WinWorld` | **Front / Bedtime Finale** | Press `E`/`SPACE` at bed after fishing (`STEP_8_SLEEP`) | Click **Main Lagi** → resets `GameState` to `TitleWorld` | `FR-2`, `FR-11` |

## Voice and Tone

- **System & HUD Language:** Crisp, deadpan **Bahasa Indonesia** for top HUD objectives, prompts, and win summary (`{typography.hud-label}`, `{typography.prompt}`).
- **Dialogue & Banter Language:** Casual **mixed Indonesian + Sundanese** (`{typography.dialogue}`, `{typography.phone-msg}`) reflecting rural West Java *bapak-bapak* slang (*"Mang"*, *"euy"*, *"heula"*, *"atuh"*, *"kumaha"*).
- **Sample Copy Across Beats:**
  - **HUD Objectives (`{components.hud-bar}`):**
    - `Misi 1/8: Ngopi hitam & ngerokok dulu`
    - `Misi 2/8: Chat istri minta kiriman uang`
    - `Misi 3/8: Cek nasib di aplikasi gacor`
    - `Misi 4/8: Balas ajakan mancing di HP`
    - `Misi 5/8: Pakai kaos andalan mancing`
    - `Misi 6/8: Gas motor klasik ke empang`
    - `Misi 7/8: Mancing sambil udud & ngobrol`
    - `Misi 8/8: Pulang & tidur (Selesai!)`
  - **Wife Chat (`FR-6`):**
    - Mang Ujang: *"Neng, punten transfer heula 500rb, buat bayar token listrik sama bensin..."*
    - Istri (TKW): *"Iya Kang, ini baru ditransfer. Jangan dipake aneh-aneh ya, inget anak di pesantren."*
  - **Friends' Invite (`FR-8`):**
    - Mang Asep: *"Mang Ujang! Empang Mang Jaja lagi rame nih, hayu merapat!"*
    - Mang Ujang: *"OTW euy! Tungguin, bawa kopi!"*
  - **Ambient *Empang* Dad Chit-Chat (`FR-10`):**
    - *"Kopi hideung mah wajib, lur!"*
    - *"Istri kerja di luar negeri, kita mah jaga empang wae."*
    - *"Eta pelampung gerak, Mang! Tarik atuh!"*
    - *"Aduh rungkad deui tadi, ayeuna mah fokus narik nila!"*
  - **Retry Feedback:** *"Melenceng! Coba deui, Mang!"* / *"Nabrak angkot! Ulangi dari awal jalan!"*

## Component Patterns

- **`HudOverlay` (`{components.hud-bar}`):** Always stays visible across `HouseWorld`, `PhoneWorld`, `RoadBikeWorld`, and `EmpangWorld`. Whenever `GameState.money` changes (wife transfer or *judol* bet), a brief floating delta badge (`+Rp 500.000` in `{colors.accent-uang}` or `-Rp 100.000` in `{colors.accent-strike}`) animates beside the balance for 1.5 seconds.
- **`HotspotHighlight` & `PromptPill` (`{components.prompt-pill}`):** Only the current objective's interactive prop pulses with the bouncing `{colors.accent-warning}` arrow. When `BapakActor`'s hitbox comes within `60px` of that active object, `PromptPill` appears at the bottom strip with the exact action label and `[E] / [SPACE]` badge. Interacting with out-of-order objects plays a short quip bubble (*"Ngopi dulu atuh, mata masih sepet."*).
- **`SpeechBubble` (`{components.speech-bubble}`):** At `EmpangWorld`, 2–3 `NpcDadActor` buddies sit by the water and cycle through mixed Sundanese-Indonesian lines every 3.5 seconds independently of the player's timing bar so the scene feels lively without interrupting input.
- **`RetryBanner`:** When a player misses a timing window or crashes on the road, a brief non-blocking flash (`1s`) shows the cheeky retry message and resets only that mini-game (never resetting `GameState.money` or prior completed steps).

## State Patterns

`GameState` persists linearly across Greenfoot `World` transitions:

```text
STEP_0_WAKEUP (POV Cut-In)
  └─> STEP_1_COFFEE (HouseWorld -> Timed Sip & Puff)
        └─> STEP_2_CHAT_WIFE (HouseWorld -> PhoneWorld Chat: +Rp 500.000)
              └─> STEP_3_JUDOL (PhoneWorld Slots: -Rp 400.000 across 4 spins)
                    └─> STEP_4_FRIEND_INVITE (PhoneWorld Chat: Reply "OTW")
                          └─> STEP_5_CHANGE_SHIRT (HouseWorld wardrobe -> POV Shirt Highlight)
                                └─> STEP_6_BIKE_RIDE (HouseWorld door -> RoadBikeWorld)
                                      └─> STEP_7_FISHING (EmpangWorld -> Bobber Timing)
                                            └─> STEP_8_SLEEP (HouseWorld bed -> WinWorld)
```

Every mini-game follows a uniform 4-state lifecycle (`MiniGame` interface / `ActivityWorld` base):
1. `INTRO_POV`: Brief `1–1.5s` (or press `SPACE` to skip) front/POV framing introducing the activity.
2. `ACTIVE`: Live player input loop (`TimingBar`, chat send, slot spins, road steering, or bobber watch).
3. `RETRY_FLASH`: Triggered on miss or crash; displays `"Coba deui, Mang!"` and immediately re-enters `ACTIVE` (or restarts `RoadBikeWorld` distance at `0%`).
4. `COMPLETE`: Advances `GameState.currentStep`, updates `Uang` if applicable, and transitions cleanly back to the top-down world or next scene.

## Interaction Primitives

| Context | Input Controls | Response & Feel |
|---|---|---|
| Top-Down Exploration (`HouseWorld`, `EmpangWorld`) | `W A S D` or `Arrow Keys` | Smooth 4-directional/diagonal walking (`3–4px/frame`) with AABB wall & furniture collision; Mang Ujang flips sprite facing left/right. |
| World Object Interaction | `E` or `SPACE` (when `PromptPill` visible) | Opens POV mini-game overlay or transitions to `PhoneWorld` / `RoadBikeWorld` / `WinWorld`. |
| *Timed Sip & Puff* (`FR-5`) | `SPACE` | Hits green target zone (`{colors.accent-uang}`) on oscillating `{components.timing-bar}`; 4 hits complete activity. |
| *Quick Chat Sequence* (`FR-6`, `FR-8`) | `SPACE` or Mouse Click on **Kirim / OTW** | Appends Mang Ujang's message, plays reply bubble after `0.6s`, then unlocks money transfer or `"OTW"` exit. |
| *3-Reel Slot Spin* (`FR-7`) | `SPACE` or Mouse Click on **SPIN** | Spins 3 reels for `~1.2s`, stops one reel at a time, subtracts `Rp 100.000` per spin (4 spins total). |
| *Obstacle Dodge* Bike Ride (`FR-9`) | `LEFT / RIGHT` (or `A / D`) | Shifts classic motorcycle across 3 rural road lanes dodging potholes, chickens, and *angkot* until distance hits `100%`. |
| *Bobber Timing* (`FR-10`) | `SPACE` | First press casts bobber; waits `2–4s` until `"STRIKE!"` flashes in `{colors.accent-strike}`; second press within `1.2s` reels in 1 fish (3 fish total). |

## Accessibility Floor

- **Explicit Control Hints:** Every scene and mini-game keeps the exact active keycap (`[WASD]`, `[E]`, `[SPACE]`) visible inside `{components.prompt-pill}`—players never have to guess controls.
- **Generous, Forgiving Timing:** Target zones on `{components.timing-bar}` cover at least `25%` of the bar width, and `"STRIKE!"` windows last `>= 1.2s`, ensuring anyone testing the UAS project can clear every beat smoothly.
- **Audio-Optional Feedback:** Sound effects (`GreenfootSound`) enhance immersion (coffee slurp, slot jingle, motor putt-putt, water splash) while every trigger also shows clear visual indicators (`+Rp`, `"STRIKE!"`, bobber splash sprite) so the game plays identically muted.

## Key Flows

**Protagonist:** **Mang Ujang** (42), wearing his signature emerald plaid *sarung*, white *singlet*, and rubber flip-flops (*sandal jepit*) in rural West Java.

1. **Wake Up (`INTRO_POV` → Top-Down):** Morning sunlight hits the bedroom. A quick front-view cut-in shows Mang Ujang yawning beside his wooden bed (*"Jam 9 pagi... saatnya beraktivitas."*), then shifts to top-down camera in `HouseWorld` with a bouncing arrow over the coffee table.
2. **Ngopi & Udud (`FR-5`):** Mang Ujang walks around the bedroom partition to the table, presses `E`, and switches to a front-view POV holding black coffee and a cigarette. He hits `SPACE` inside the green zone 4 times while smoke puffs upward.
3. **Chat Istri (`FR-6`):** Walking to his smartphone on the table opens `PhoneWorld`. Three taps of `SPACE` send polite Sundanese excuses to his wife abroad; she sends `Rp 500.000` (`Uang` jumps from `Rp 20.000` → `Rp 520.000`).
4. **Main Judol (`FR-7`):** Still on the phone, the flashy *judol* app opens (`{colors.accent-judol}`). Four spins promise a jackpot, tease near-misses, and drain `Rp 400.000` (`Uang` drops to `Rp 120.000`).
5. **Ajakan Mancing & Ganti Kaos Andalan (`FR-8`, `FR-8b`):** A chat notification pops in from Mang Asep (*"Hayu mancing!"*). Mang Ujang taps `"OTW euy!"`, exits `PhoneWorld` back into top-down `HouseWorld`, and walks to the clothes rack (`wardrobe_shirt.png`). Pressing `E` triggers a dramatic golden POV highlight showcase (`bapak_pov_shirt_highlight.png` + `shirt_design_closeup.png`) showing off the black **"BARAYA BAPAK AING"** front and **"KANG DEDI MULYADI"** dual-tiger back print (`"Aura Bapak-Bapak +999!"`), permanently swapping his sprite into the fishing t-shirt.
6. **OTW Motor Klasik (`FR-9`):** Wearing the black graphic shirt, Mang Ujang walks out the front door to his parked classic bike and dodges village chickens and *angkot* down `RoadBikeWorld` (`bike_rider_shirt.png`).
7. **Climax — Empang Hangout (`FR-10`):** A brief front-view cut-in welcomes Mang Ujang to the sunny *empang* where two Sundanese dads sit smoking. Top-down movement walks him (in his black fishing shirt + *sarung*) to the empty bamboo dock spot; pressing `E` launches the *Bobber Timing* POV view. Between ambient banter (*"Kopi hideung mah wajib, lur!"*), he catches 3 fish while smoking without losing a single beat.
8. **Pulang & Tidur (`FR-11`):** Prompted to head home (`Misi 8/8`), Mang Ujang walks back to the entrance, returns to `HouseWorld`, walks over to his bed, and hits `E`. `WinWorld` appears with a celebratory front-view sleeping card: *"Hari ini beres, Sundaynese sejati."*

## Sprite & Asset Manifest

Checklist of starter PNG assets to generate later under `images/` in the **GBA / 16-bit pixel art** style (sized cleanly for `800x600` Greenfoot):

| File Path (`images/`) | Used In | Visual Description |
|---|---|---|
| `bg_title.png` | `TitleWorld` | Front-view 16-bit GBA pixel-art scene of Mang Ujang relaxing with coffee, phone, and fishing rod (`800x600`). |
| `bg_rumah.png` | `HouseWorld` | Top-down rural Sundanese house interior: wood/tile floor, woven bamboo walls, bedroom + living corner (`800x600`). |
| `bg_empang.png` | `EmpangWorld` | Top-down sunny village fishpond with bamboo dock, grass bank, and banana trees (`800x600`). |
| `bg_road.png` | `RoadBikeWorld` | Vertical scrolling 3-lane village asphalt road flanked by rice fields (`800x600`). |
| `bapak_topdown.png` (+ walk/left/right) | `HouseWorld`, `EmpangWorld` | Top-down Mang Ujang in plaid *sarung*, white *singlet*, and sandals (`44x44`). |
| `bapak_shirt_topdown.png` (+ `walk1`, `walk2`) | `HouseWorld`, `EmpangWorld` | Top-down Mang Ujang equipped with the black *"BARAYA BAPAK AING / KANG DEDI MULYADI"* t-shirt + plaid *sarung* (`44x44`). |
| `bapak_pov_wakeup.png` / `bapak_pov_coffee.png` | POV Cut-Ins / Mini-Games | Front/POV upper-body Mang Ujang yawning, sipping black coffee, and smoking (`240x240`). |
| `bapak_pov_shirt_highlight.png` + `shirt_design_closeup.png` | Shirt Equip Showcase (`HouseWorld`) | Dramatic over-the-shoulder GBA highlight showcase (`600x450`) + pixel-snapped close-up of the *"KANG DEDI MULYADI"* tiger back design (`240x240`). |
| `table_coffee.png`, `bed.png`, `phone_prop.png`, `wardrobe_shirt.png`, `classic_bike.png`, `fishing_spot.png` | `HouseWorld`, `EmpangWorld` | Top-down interactive furniture and props with clear silhouettes (`36x36` to `80x56`). |
| `npc_dad_1.png`, `npc_dad_2.png` | `EmpangWorld` | Top-down seated Sundanese dads in *peci*/cap and *sarung* holding rods (`44x44`). |
| `bike_rider.png`, `bike_rider_shirt.png`, `obs_pothole.png`, `obs_chicken.png`, `obs_angkot.png` | `RoadBikeWorld` | Classic motorcycle rider (including black graphic fishing shirt variant) + 3 comedic village road obstacles (`48x72` / `40x40` to `64x96`). |
| `slot_icons/` (`7`, `kopi`, `ikan`, `zonk`) & `bobber.png` / `fish.png` | `PhoneWorld`, `EmpangWorld` | Bright *judol* reel symbols (`64x64`), pond bobber (`32x32`), and caught nila fish (`48x32`). |
| `bg_win.png` | `WinWorld` | Cozy front-view bedroom scene of Mang Ujang sleeping soundly in *sarung* & *singlet* (`800x600`). |
