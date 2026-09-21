# Domain glossary

**Game**: the deep module owning the play session — the main loop, wilderness encounters, the hub visit, save/load, climax gating. Small public interface: `Game(Console console)`, `start()`. Everything else package-private, exercised directly by tests as an internal seam.

**Console**: the seam between game logic and the terminal. Interface: `heading`, `separator`, `print`, `clear`, `askInt`, `askLine`, `waitForContinue`. Two adapters: `TerminalConsole` (production, real `Scanner`/`System.out`, UTF-8) and `ScriptedConsole` (test-only, queued answers).

**Dice** / **DiceExpr**: `Dice` wraps a `java.util.Random` (constructor-injected, not static `Math.random()`), so combat is seedable and testable. `DiceExpr` is a dice expression (count, sides, modifier), e.g. `2d6+3`.

**Combat**: pure attack resolution, no `Console` involved — `Combat.resolveAttack(attacker, defender, dice)` rolls a d20 against the defender's armor class, rolls damage dice on a hit, doubles the damage dice (not the modifier) on a natural 20. Returns an `AttackResult` (hit, critical, damage, naturalRoll).

**Character**: abstract base for anything that fights — `Player` and `Enemy`. Exposes `armorClass()`, `attackBonus()`, `damageDice()` for `Combat` to read; each subtype computes these differently (`Player` from equipment + skills, `Enemy` from its `EnemyTemplate`).

**SkillNode** / **SkillTree**: the branching talent tree. A `Player` commits to a `Branch` (`MIGHT` or `GUARD`) at tier 0, then unlocks one node per level: tier 1 is automatic (one node per branch), tier 2 is a real choice between two nodes, tier 3 (`Champion's Resolve`) is a capstone reachable from either branch. Each node carries stat deltas (attack bonus, armor class, max HP, bonus damage) applied to the `Player` when unlocked.

**Item** (sealed: `Weapon`, `Armor`, `Consumable`) / **ItemCatalog**: the static catalog of every item in this campaign, looked up by id (used by shops, quest rewards, and save/load). `Player` has one equipped `Weapon` and one equipped `Armor` slot plus a loose `inventory` list for everything else.

**EnemyTemplate** / **Bestiary**: fixed DnD-style stat blocks (hit dice, AC, attack bonus, damage dice, XP, gold dice) rather than stats scaled to the player's level. `Enemy` is built from a template plus a `Dice` roll for HP.

**Region** / **Hub**: `Region` replaces the old `Act` — it holds a wilderness enemy pool, an encounter table, and its `Hub` (the village). Only one `Region` exists so far, `Region.GREYWOOD_MARCHES`; more regions are just more `Region` data, not new code.

**Npc** / **Quest** / **QuestProgress**: `Npc`s live in a `Hub` and may carry a `Quest` (kill-count objective). `QuestProgress` (state + kill count) is tracked per player per quest id, so a quest's state persists independently of dialogue.

**Story**: narrative beats as data (`StoryBeat`: title + paragraphs) played through one `Story.play(Console, StoryBeat)` method, not nine near-duplicate print methods. Takes `Console` as a parameter rather than depending on `Game`, so there's no call-back cycle between the two.

**SaveManager**: serializes/deserializes a `Player` to a `Properties` file (`saves/save.properties`, gitignored). Reconstructs equipment and skills by id through `ItemCatalog`/`SkillTree` lookups.
