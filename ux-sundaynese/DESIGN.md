---
name: "Sundaynese"
description: "Visual identity for a satirical 2D Greenfoot life-sim set in rural Sundanese West Java."
status: final
created: "2026-11-26"
updated: "2026-11-26"
colors:
  surface-rumah: "#D9BA8C"
  surface-empang: "#4F8F62"
  surface-road: "#6E6A63"
  surface-hud: "#1F1A17"
  surface-phone: "#ECE5DD"
  surface-bubble: "#FFFDF7"
  ink-primary: "#1F1A17"
  ink-muted: "#5E544D"
  ink-light: "#FFFDF7"
  accent-sarung: "#245C4B"
  accent-uang: "#1E8449"
  accent-judol: "#FF2E93"
  accent-warning: "#F4B400"
  accent-strike: "#E53935"
  border-wood: "#5A3E28"
typography:
  title:
    fontFamily: "SansSerif"
    fontSize: "32px"
    fontWeight: "700"
    lineHeight: "38px"
  hud-label:
    fontFamily: "SansSerif"
    fontSize: "13px"
    fontWeight: "700"
    lineHeight: "18px"
  hud-value:
    fontFamily: "Monospaced"
    fontSize: "16px"
    fontWeight: "700"
    lineHeight: "20px"
  dialogue:
    fontFamily: "SansSerif"
    fontSize: "14px"
    fontWeight: "400"
    lineHeight: "19px"
  prompt:
    fontFamily: "SansSerif"
    fontSize: "14px"
    fontWeight: "700"
    lineHeight: "18px"
  phone-msg:
    fontFamily: "SansSerif"
    fontSize: "13px"
    fontWeight: "400"
    lineHeight: "18px"
rounded:
  none: "0px"
  sm: "4px"
  md: "8px"
  lg: "14px"
  full: "9999px"
spacing:
  "1": "4px"
  "2": "8px"
  "3": "12px"
  "4": "16px"
  "5": "24px"
  "6": "32px"
  hud-pad: "12px"
  tile-size: "40px"
components:
  hud-bar:
    background: "{colors.surface-hud}"
    text: "{colors.ink-light}"
    moneyAccent: "{colors.accent-uang}"
    border: "{colors.border-wood}"
    padding: "{spacing.hud-pad}"
    radius: "{rounded.none}"
  prompt-pill:
    background: "{colors.surface-hud}"
    text: "{colors.ink-light}"
    keycapAccent: "{colors.accent-warning}"
    paddingX: "{spacing.3}"
    paddingY: "{spacing.2}"
    radius: "{rounded.full}"
  speech-bubble:
    background: "{colors.surface-bubble}"
    text: "{colors.ink-primary}"
    border: "{colors.ink-primary}"
    padding: "{spacing.2}"
    radius: "{rounded.lg}"
  timing-bar:
    track: "{colors.surface-hud}"
    targetZone: "{colors.accent-uang}"
    marker: "{colors.accent-warning}"
    border: "{colors.ink-light}"
    radius: "{rounded.sm}"
  phone-frame:
    bezel: "{colors.ink-primary}"
    chatSurface: "{colors.surface-phone}"
    judolAccent: "{colors.accent-judol}"
    radius: "{rounded.lg}"
  slot-reel:
    background: "{colors.surface-bubble}"
    border: "{colors.accent-judol}"
    highlight: "{colors.accent-warning}"
    radius: "{rounded.sm}"
---

# DESIGN.md — Sundaynese

## Brand & Style

**Sundaynese** pairs warm rural West Java (*kampung*) daylight with sharp satirical comedy in a **GBA / 16-Bit Pixel Art** aesthetic (inspired by *The Sims Bustin' Out*, *The Urbz*, and *Stardew Valley*)—featuring crisp nearest-neighbor pixel sprites, tiled rural Sundanese *bilik* and teakwood floors, animated/rippled pixel *empang* water, and chunky retro UI borders sized for an `800x600` Greenfoot desktop canvas. The lived-in Sundanese village setting—bamboo walls, teakwood floors, roadside potholes, and sunny green *empang* ponds—deliberately contrasts with the loud, tacky neon interface of the *judol* slot app.

Camera framing reinforces both gameplay and humor:
- **Top-Down Exploration Camera:** Used whenever the player walks freely around `Rumah` or `Empang` like classic console *Sims* spin-offs, showing room layouts, furniture collision, and objective hotspots at a glance.
- **Front / POV Close-Up Camera:** Used at scene intros (waking up in bed, arriving at the *empang*) and during mini-games (phone chat screen, *judol* slot machine, coffee-and-smoke bar, fishing bobber view) to put Mang Ujang's *sarung*-and-*singlet* expressions and props right in front of the player.

## Colors

| Token | Hex | Story & Usage |
|---|---|---|
| `{colors.surface-rumah}` | `#D9BA8C` | Warm woven-bamboo (*bilik*) and wooden floor base for `Rumah`. Never used as UI text background. |
| `{colors.surface-empang}` | `#4F8F62` | Lush Sundanese pond grass, banana trees, and murky green fishpond palette in `EmpangWorld`. |
| `{colors.surface-road}` | `#6E6A63` | Dusty village asphalt for the `RoadBikeWorld` obstacle-dodge sequence. |
| `{colors.surface-hud}` | `#1F1A17` | Dark kopi-tubruk espresso panel for the persistent top HUD bar and bottom interaction pills. |
| `{colors.surface-phone}` | `#ECE5DD` | Familiar chat-app wallpaper inside the phone UI when begging the TKW wife or reading friends' invites. |
| `{colors.surface-bubble}` | `#FFFDF7` | Clean off-white paper fill for NPC Sundanese dad speech bubbles and slot reels. |
| `{colors.ink-primary}` | `#1F1A17` | Primary dark ink for speech bubbles, phone chat text, and crisp sprite outlines. |
| `{colors.ink-muted}` | `#5E544D` | Secondary timestamps, chat delivery checkmarks, and subtle UI labels. |
| `{colors.ink-light}` | `#FFFDF7` | High-contrast white-cream text inside `{colors.surface-hud}` bars and prompt pills. |
| `{colors.accent-sarung}` | `#245C4B` | Signature plaid emerald *sarung* tone paired with bright white *singlet* so Mang Ujang stands out on any ground tile. |
| `{colors.accent-uang}` | `#1E8449` | Rupiah green for the `Uang` HUD value, incoming wife remittance flashes, and the safe target zone in `{components.timing-bar}`. |
| `{colors.accent-judol}` | `#FF2E93` | Gaudy neon magenta reserved strictly for the fake *judol* slot frame, SPIN button, and loss flashes—never used in village world scenes. |
| `{colors.accent-warning}` | `#F4B400` | Bouncing objective arrow, keycap badge (`[SPACE]`, `[E]`), and sliding marker on `{components.timing-bar}`. |
| `{colors.accent-strike}` | `#E53935` | Urgent `"STRIKE!"` fishing alert, bike crash flash, and *judol* cash deduction popups. |
| `{colors.border-wood}` | `#5A3E28` | Dark teak frame separating the top HUD bar from the playable world and outlining rural wooden furniture. |

## Typography

`[ASSUMPTION: Uses standard Java/Greenfoot bundled system fonts (SansSerif and Monospaced) so text renders identically across Windows/macOS Greenfoot installations without external font loaders.]`

- **`{typography.title}` (`SansSerif`, 32px, Bold):** Title screen, scene-intro POV captions, and end-of-day Win screen header.
- **`{typography.hud-label}` (`SansSerif`, 13px, Bold):** Active mission text in the top HUD bar (e.g., `Misi: Ngopi & Ngerokok`).
- **`{typography.hud-value}` (`Monospaced`, 16px, Bold):** Fixed-width `Rp` currency counter (`Uang`) and mini-game progress counters (`2/4 Sruput`, `1/3 Ikan`) so numbers never jitter when updating.
- **`{typography.dialogue}` (`SansSerif`, 14px, Regular):** Ambient Sundanese dad speech bubbles at the *empang* and short POV narrative captions.
- **`{typography.prompt}` (`SansSerif`, 14px, Bold):** Bottom interaction pill (`[E] Ngopi dulu, Mang`).
- **`{typography.phone-msg}` (`SansSerif`, 13px, Regular):** Chat bubbles inside the phone screen for wife excuses and friends' `"OTW"` invites.

## Layout & Spacing

`[ASSUMPTION: Desktop Greenfoot viewport is 800x600px with cell size 1px for smooth Sims-style character movement and collision.]`

- **Top Persistent HUD Zone (`0–52px`):** Full-width `{components.hud-bar}` pinned to the top edge with `{spacing.hud-pad}` (`12px`) inner padding; displays active mission on the left and `Uang: Rp ...` on the right, separated by a `3px` `{colors.border-wood}` bottom border.
- **Playable Top-Down Arena (`52–548px`):** `800x496px` walkable space aligned mentally to a `{spacing.tile-size}` (`40px`) furniture/collision grid. Walls and large props leave at least `48px` walking corridors so Mang Ujang never snags.
- **Bottom Prompt Strip (`548–600px`):** Reserved for centered `{components.prompt-pill}` interaction hints and mini-game key prompts without blocking Mang Ujang's feet.
- **Front / POV Overlay Framing:** Scene-start cut-ins and mini-games dim the world behind a centered `520x420px` focal card or smartphone frame (`{components.phone-frame}`) with `{spacing.5}` (`24px`) outer breathing room.

## Elevation & Depth

- **Top-Down Grounding:** Every standing actor (`BapakActor`, `NpcDadActor`, wooden table, motorcycle, bed) casts a flat `35%`-opacity dark oval shadow beneath its base to anchor it onto `{colors.surface-rumah}` or `{colors.surface-empang}`.
- **2px Crisp Dark Outlines:** Foreground sprites and UI cards use `2px` `{colors.ink-primary}` outlines so objects read cleanly without soft blurs or heavy drop shadows.
- **Layer Order (Greenfoot `setPaintOrder`):**
  1. `RetryBanner` / `"STRIKE!"` alerts
  2. `HudOverlay` & `PromptPill`
  3. `SpeechBubble` & `HotspotHighlight`
  4. `BapakActor` & `NpcDadActor`
  5. Interactive furniture & obstacles

## Shapes

- **HUD & Timing Bars (`{rounded.none}` / `{rounded.sm}`):** Crisp boxy corners reinforce the retro 2D game HUD and tacky *judol* slot reels (`{components.slot-reel}`).
- **Speech & Chat Bubbles (`{rounded.lg}`):** Soft `14px` corners with a directional pointer tail distinguish spoken Sundanese dad banter and WhatsApp-style messages from game system panels.
- **Interaction Prompts (`{rounded.full}`):** Pill-shaped badges draw immediate attention when Mang Ujang walks near an active hotspot.

## Components

- **`HudOverlay` (`{components.hud-bar}`):** Spans `800x52px` at top. Left side shows a checklist step badge (`1/8`) + active Indonesian mission label; right side shows `Uang` in `{colors.accent-uang}` with floating `+Rp` / `-Rp` delta text during transfers and *judol* spins.
- **`HotspotHighlight`:** A pulsing `{colors.accent-warning}` downward arrow (`24x24px`) hovering `12px` above only the currently active object so the player always knows where to walk next.
- **`PromptPill` (`{components.prompt-pill}`):** Dark pill anchored near bottom-center displaying a yellow keycap (`[E]` or `[SPACE]`) beside the action verb (`Seduh Kopi`, `Buka HP`, `Pakai Kaos`, `Naik Motor`, `Mancing`, `Tidur`).
- **`SpeechBubble` (`{components.speech-bubble}`):** Compact `180–240px` wide card with `{colors.ink-primary}` border placed above `NpcDadActor` at the *empang* without overlapping `HudOverlay`.
- **`TimingBar` (`{components.timing-bar}`):** `360x32px` horizontal track used by *Timed Sip & Puff* and *Bobber Timing*. Center-ish green safe zone (`{colors.accent-uang}`) contrasts sharply with the fast-gliding yellow indicator (`{colors.accent-warning}`).
- **`PhoneFrame` (`{components.phone-frame}`):** Centered `340x480px` handheld bezel switched between green-header chat mode (`{colors.surface-phone}`) for wife/friends and flashing magenta slot mode (`{colors.accent-judol}`) for the 3-reel *judol* mini-game.

## Do's and Don'ts

- **Do** keep Mang Ujang dressed in his plaid `{colors.accent-sarung}` and white *singlet* at home until he equips his legendary black *"BARAYA BAPAK AING / KANG DEDI MULYADI"* fishing t-shirt (`wardrobe_shirt`) with a golden highlight showcase before riding out to the *empang*.
- **Do** scale all pixel sprites with crisp nearest-neighbor scaling (no blurry anti-aliased stretching) so the 16-bit GBA aesthetic stays sharp.
- **Do** keep the *judol* neon magenta (`{colors.accent-judol}`) isolated to the *judol* app screen so rural *kampung* daylight stays visually warm and grounded.
- **Do** keep timing zones (`{colors.accent-uang}`) and key prompts (`{colors.accent-warning}`) bold and uncluttered so players read the input in under a second.
- **Don't** put multi-paragraph instructions or branching dialogue menus over the top-down room—let the bouncing hotspot arrow and one-line prompt pill guide movement.
- **Don't** include religious symbols, hate tropes, or real gambling branding; satirize the lazy *bapak-bapak* habits cleanly through props (kopi hitam, rokok, HP, *sarung*, motor klasik, *empang*).
