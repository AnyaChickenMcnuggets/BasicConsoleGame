# Domain glossary

**Game**: the deep module owning the play session — act/place progression, encounter dispatch, battle resolution, main loop. Small public interface: `Game(Console console)`, `start()`. Everything else package-private, exercised directly by tests as an internal seam.

**Console**: the seam between game logic and the terminal. Interface: `heading`, `separator`, `print`, `clear`, `askInt`, `askLine`, `waitForContinue`. Two adapters: `TerminalConsole` (production, real `Scanner`/`System.out`, UTF-8) and `ScriptedConsole` (test-only, queued answers).

**Act**: immutable record of one story act's content — `place`, `enemyNames`, `encounterTable`. `Game` holds the active `Act` and swaps it wholesale on act transitions instead of mutating shared arrays in place. Act 4 (the final boss) is not modeled as an `Act` with an encounter table — `finalBattle()` handles it directly since it's a single fixed fight, not a random-encounter act.

**Encounter**: the type of random event rolled while traveling within an act — `BATTLE`, `REST`, `SHOP`.

**Player** / **Enemy**: the two `Character` subtypes. `Player` holds progression state (level, gold, potions, upgrade tracks) and needs a `Console` to run its own interactive trait-choice flow (`chooseTrait`, `lvlUP`). `Enemy` is pure data + combat math, no `Console` dependency.

**Story**: static print routines for narrative beats (act intros/outros). Takes `Console` as a parameter rather than depending on `Game`, so there's no call-back cycle between the two.
