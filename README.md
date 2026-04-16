# 🧬 DNAMobs – Adaptive Evolution System

**Author:** MathTeacher

---

## 🚀 Overview

**DNAMobs** is a next-generation Minecraft plugin that introduces a dynamic mob evolution system.

Mobs no longer stay predictable—they **adapt**, **evolve**, and **fight back smarter** based on how players interact with them.

**Every fight changes the future.**

---

## 🔥 Features

- 🧠 **Smart mob adaptation system**
- 🧬 **DNA-based evolution tracking**
- ⚔️ **Strategy-based resistance mechanics**
- 👑 **Elite mutated mobs**
- ⚙️ **Fully configurable**
- 💾 **Persistent data storage**
- 🚀 **Optimized for performance**
- 🎯 **Supports Spigot/Paper 1.17–1.21+**

---

## 🧪 How It Works

Every time a player kills or interacts with a mob, the plugin records:

- **Damage type** (melee, ranged, fire, etc.)
- **Combat behavior**
- **Environmental interactions**

Over time, mobs evolve to counter the most common strategies.

---

## 🧬 Evolution Tiers

| Tier | Description |
|------|-------------|
| **1** | Normal mob |
| **2** | Minor resistance |
| **3** | New abilities |
| **4** | Elite mutation |

### Tier Progression

- **Tier 1:** Standard vanilla mob
- **Tier 2:** Slight stat boosts and minor resistance
- **Tier 3:** Unlocks special abilities (block breaking, strafing, etc.)
- **Tier 4:** Elite variant with enhanced stats and unique behaviors

---

## 🛡️ Adaptive Mechanics

### Resistance Evolution

Mobs develop resistance based on how they're killed:

- **Frequent melee attacks** → Mobs gain melee resistance
- **Frequent bow/crossbow attacks** → Mobs gain speed or dodge ability
- **Fire/lava damage** → Mobs gain fire resistance
- **Explosions** → Mobs gain blast resistance

### Behavior Evolution

#### 🧟 Zombies
- Break blocks
- Avoid traps
- Increased speed and health

#### 🏹 Skeletons
- Improved strafing
- Better aim prediction
- Enhanced accuracy

#### 💥 Creepers
- Delayed explosions
- Fake detonation behavior
- Increased explosion radius

#### 🕷️ Spiders
- Web traps
- Poison attacks
- Enhanced climbing

#### 👁️ Endermen
- Increased teleport frequency
- Dodge projectiles
- Enhanced damage

---

## 👑 Elite Mobs

Rare evolved mobs with:

- ✨ **Unique names** (customizable)
- 🎆 **Particle effects**
- 💪 **Enhanced stats** (2x health, 1.5x damage)
- 🧪 **Special abilities** (regeneration, resistance)
- 💰 **Better rewards** (3x experience, rare drops)

Elite mobs have a configurable spawn chance and are visually distinct with custom names and particle effects.

---

## ⚙️ Commands

| Command | Description | Permission |
|---------|-------------|------------|
| `/dna reload` | Reload configuration | `dnamobs.reload` |
| `/dna stats` | View evolution statistics | `dnamobs.view` |
| `/dna reset` | Reset all DNA data | `dnamobs.reset` |
| `/dna toggle` | Enable/disable evolution system | `dnamobs.toggle` |

**Aliases:** `/dnamobs`, `/evolution`

---

## 🔐 Permissions

| Permission | Description | Default |
|------------|-------------|---------|
| `dnamobs.admin` | Full admin access | OP |
| `dnamobs.use` | Basic usage | All players |
| `dnamobs.view` | View statistics | All players |
| `dnamobs.reload` | Reload config | OP |
| `dnamobs.reset` | Reset DNA data | OP |
| `dnamobs.toggle` | Toggle system | OP |

---

## 📂 Installation

1. **Download** the plugin JAR file
2. **Place** it in your server's `/plugins` folder
3. **Restart** your server
4. **Configure** `config.yml` to your preferences
5. **Enjoy** adaptive mob evolution!

---

## 🔧 Configuration

The plugin is highly configurable through `config.yml`:

### General Settings
- Enable/disable the entire system
- Choose tracking mode (GLOBAL, WORLD, PLAYER)
- Adjust save intervals

### Evolution Settings
- Kills required per tier
- Evolution speed multiplier
- Maximum tier level
- Elite mutation chance

### Mob-Specific Settings
- Enable/disable specific mobs
- Configure tier bonuses
- Customize abilities per tier

### Elite Settings
- Custom name format
- Particle effects
- Stat multipliers
- Special abilities

### Performance Settings
- Async data saving
- Cache size
- Update intervals

---

## ⚡ Performance

DNAMobs is designed with performance in mind:

- ✅ **Async data handling** – No lag during saves
- ✅ **Optimized event listeners** – Minimal overhead
- ✅ **Efficient caching** – Fast data access
- ✅ **Configurable limits** – Control resource usage
- ✅ **Minimal server impact** – Tested on large servers

---

## 💾 Data Storage

### YAML (Default)
- Simple file-based storage
- Easy to read and edit
- No external dependencies

### Future Support
- MySQL
- SQLite

Data is automatically saved at configurable intervals and on server shutdown.

---

## 🎯 Why DNAMobs?

**Because survival shouldn't be predictable.**

This plugin forces players to:

- 🔄 **Adapt strategies** – What worked yesterday might not work today
- 🧠 **Think differently** – Mobs learn from your tactics
- 🤝 **Work together** – Coordinate to overcome evolved threats
- 🏆 **Face challenges** – Elite mobs provide exciting encounters

---

## 💡 Future Ideas

- 🖥️ GUI DNA Analyzer
- 🗺️ Region-based evolution
- 👹 Boss mutation system
- 👤 Player-specific difficulty scaling
- 📊 Advanced statistics tracking
- 🌐 Multi-world support enhancements

---

## 🛠️ Building from Source

### Requirements
- Java 16 or higher
- Maven

### Build Steps

```bash
git clone https://github.com/MathTeacher/DNAMobs.git
cd DNAMobs
mvn clean package
```

The compiled JAR will be in the `target/` directory.

---

## 📝 License

This plugin is provided as-is for use on Minecraft servers.

---

## 🐛 Bug Reports & Suggestions

Found a bug or have a suggestion? Please open an issue on GitHub!

---

## ❤️ Support

If you enjoy the plugin, consider:
- ⭐ Starring the repository
- 📢 Sharing with others
- 💬 Providing feedback

---

## 📞 Contact

**Author:** MathTeacher

---

## 🎮 Compatibility

- ✅ Spigot 1.17+
- ✅ Paper 1.17+
- ✅ Purpur 1.17+
- ✅ Tested up to 1.21

---

## 🔄 Changelog

### Version 1.0.0
- Initial release
- DNA tracking system
- 4-tier evolution system
- Elite mob mutations
- Adaptive resistance mechanics
- Mob-specific abilities
- YAML data storage
- Full configuration support

---

**Made with ❤️ by MathTeacher**

*Transform your Minecraft survival experience with intelligent, evolving mobs!*
