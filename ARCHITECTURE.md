# DNAMobs System Architecture

## 🏗️ Component Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                        DNAMobs Plugin                        │
│                     (Main Entry Point)                       │
└────────────────────────┬────────────────────────────────────┘
                         │
         ┌───────────────┼───────────────┐
         │               │               │
         ▼               ▼               ▼
┌────────────────┐ ┌──────────┐ ┌──────────────┐
│   DNAManager   │ │Evolution │ │ DataStorage  │
│                │ │ Manager  │ │              │
│ - Track kills  │ │- Apply   │ │- Save/Load   │
│ - Store DNA    │ │  evolution│ │- YAML format │
│ - Calculate    │ │- Abilities│ │- Async ops   │
│   tiers        │ │- Elites  │ │              │
└────────┬───────┘ └────┬─────┘ └──────┬───────┘
         │              │               │
         └──────────────┼───────────────┘
                        │
         ┌──────────────┴──────────────┐
         │                             │
         ▼                             ▼
┌─────────────────┐          ┌──────────────────┐
│   Listeners     │          │    Commands      │
│                 │          │                  │
│- MobCombat      │          │- /dna reload     │
│- MobSpawn       │          │- /dna stats      │
│                 │          │- /dna reset      │
│                 │          │- /dna toggle     │
└─────────────────┘          └──────────────────┘
```

## 🔄 Data Flow Diagram

```
Player Kills Mob
       │
       ▼
┌──────────────────┐
│ MobCombatListener│
│  (Death Event)   │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Determine       │
│  Damage Type     │
│  (Melee/Ranged/  │
│   Fire/etc.)     │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│   DNAManager     │
│  Record Kill     │
│  Update Profile  │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Check if        │
│  Should Evolve   │
│  (kills >= tier) │
└────────┬─────────┘
         │
    Yes  │  No
    ┌────┴────┐
    ▼         ▼
┌────────┐  ┌────────┐
│ Evolve │  │  End   │
│  Tier  │  └────────┘
└───┬────┘
    │
    ▼
┌──────────────────┐
│  DataStorage     │
│  Auto-Save       │
└──────────────────┘
```

## 🐛 Mob Spawn Flow

```
Mob Spawns Naturally
       │
       ▼
┌──────────────────┐
│ MobSpawnListener │
│  (Spawn Event)   │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Check if        │
│  Evolveable      │
└────────┬─────────┘
         │
    Yes  │  No
    ┌────┴────┐
    ▼         ▼
┌────────┐  ┌────────┐
│Continue│  │  End   │
└───┬────┘  └────────┘
    │
    ▼
┌──────────────────┐
│  Get DNA Profile │
│  for Mob Type    │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Get Current     │
│  Tier (1-4)      │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Roll for Elite  │
│  Mutation        │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│ EvolutionManager │
│  Apply Evolution │
└────────┬─────────┘
         │
         ├─────────────────┐
         │                 │
         ▼                 ▼
┌──────────────┐  ┌──────────────┐
│ Apply Stats  │  │Apply Abilities│
│ - Health     │  │- Break blocks│
│ - Speed      │  │- Strafe      │
│ - Damage     │  │- Dodge       │
└──────────────┘  └──────────────┘
         │                 │
         └────────┬────────┘
                  │
                  ▼
         ┌──────────────────┐
         │  If Elite:       │
         │  - Custom Name   │
         │  - Particles     │
         │  - Extra Stats   │
         └──────────────────┘
```

## ⚔️ Combat Flow

```
Player Attacks Evolved Mob
       │
       ▼
┌──────────────────┐
│ MobCombatListener│
│  (Damage Event)  │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Get EvolvedMob  │
│  Data            │
└────────┬─────────┘
         │
    Found│  Not Found
    ┌────┴────┐
    ▼         ▼
┌────────┐  ┌────────┐
│Continue│  │ Normal │
└───┬────┘  │ Damage │
    │       └────────┘
    ▼
┌──────────────────┐
│  Determine       │
│  Damage Type     │
└────────┬─────────┘
         │
         ▼
┌──────────────────┐
│  Check if Mob    │
│  Resistant to    │
│  This Type       │
└────────┬─────────┘
         │
    Yes  │  No
    ┌────┴────┐
    ▼         ▼
┌────────┐  ┌────────┐
│ Reduce │  │ Normal │
│ Damage │  │ Damage │
└───┬────┘  └───┬────┘
    │           │
    └─────┬─────┘
          │
          ▼
┌──────────────────┐
│  Apply Final     │
│  Damage          │
└──────────────────┘
```

## 💾 Data Storage Structure

```
data.yml
│
└── mobs:
    ├── ZOMBIE:
    │   ├── total-kills: 67
    │   ├── tier: 2
    │   └── damage:
    │       ├── MELEE: 45
    │       ├── RANGED: 12
    │       ├── FIRE: 8
    │       └── EXPLOSION: 2
    │
    ├── SKELETON:
    │   ├── total-kills: 152
    │   ├── tier: 3
    │   └── damage:
    │       ├── MELEE: 30
    │       ├── RANGED: 100
    │       ├── FIRE: 15
    │       └── EXPLOSION: 7
    │
    └── CREEPER:
        ├── total-kills: 23
        ├── tier: 1
        └── damage:
            ├── MELEE: 10
            ├── RANGED: 8
            └── FIRE: 5
```

## 🧬 Evolution Tier System

```
Tier 1 (Normal)
│
│ +50 kills
│
▼
Tier 2 (Enhanced)
│ - Minor stat boost
│ - 15% resistance
│
│ +50 kills
│
▼
Tier 3 (Advanced)
│ - Abilities unlock
│ - 30% resistance
│ - Behavior changes
│
│ +50 kills
│
▼
Tier 4 (Elite)
│ - Maximum stats
│ - 50% resistance
│ - All abilities
│ - Elite mutation chance
```

## 🎯 Resistance Calculation

```
Incoming Damage: 10 HP
Mob Tier: 3
Resistance: 30%
Damage Type: MELEE
Mob Resistant To: MELEE

Calculation:
10 HP × (1 - 0.30) = 7 HP

Final Damage: 7 HP
```

## 📊 DNA Profile Tracking

```
Player kills 100 zombies:
- 60 with sword (MELEE)
- 25 with bow (RANGED)
- 10 with lava (FIRE)
- 5 with TNT (EXPLOSION)

Dominant Type: MELEE (60%)

Result:
→ Zombies evolve with MELEE resistance
→ Sword attacks deal 30% less damage
→ Players must adapt strategy
```

## 🔄 System Lifecycle

```
Server Start
    │
    ▼
┌──────────────┐
│ Load Config  │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Load Data    │
│ (data.yml)   │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Initialize   │
│ Managers     │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Register     │
│ Listeners    │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ Start Auto-  │
│ Save Task    │
└──────┬───────┘
       │
       ▼
┌──────────────┐
│ System Ready │
└──────┬───────┘
       │
       │ (Runtime)
       │
       ▼
┌──────────────┐
│ Track Kills  │
│ Apply Evol.  │
│ Auto-Save    │
└──────┬───────┘
       │
       ▼
Server Stop
    │
    ▼
┌──────────────┐
│ Save Data    │
└──────────────┘
```

## 🎮 Player Experience Flow

```
Day 1: Normal mobs
    │
    ▼
Day 3: Tier 2 mobs appear
    │ (Players notice slight difficulty)
    ▼
Day 7: Tier 3 mobs common
    │ (Strategy adaptation required)
    ▼
Day 14: Tier 4 + Elites
    │ (Challenging encounters)
    ▼
Ongoing: Dynamic difficulty
```

---

**Author:** MathTeacher  
**Plugin:** DNAMobs v1.0.0
