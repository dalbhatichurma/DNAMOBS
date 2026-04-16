# 🧬 DNAMobs - Complete Documentation Index

**Author:** MathTeacher  
**Version:** 1.0.0  
**Minecraft:** 1.17 - 1.21+  
**Server:** Spigot/Paper  

---

## 📚 Documentation Files

### 🚀 Getting Started
1. **[README.md](README.md)** - Complete plugin overview and features
2. **[QUICKSTART.md](QUICKSTART.md)** - 5-minute setup guide
3. **[INSTALLATION.md](INSTALLATION.md)** - Detailed installation and testing

### 🔧 Configuration
4. **[config.yml](src/main/resources/config.yml)** - Main configuration file
5. **[config-examples.yml](config-examples.yml)** - Configuration examples and scenarios

### 📖 Technical Documentation
6. **[PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)** - Project structure and build info
7. **[ARCHITECTURE.md](ARCHITECTURE.md)** - System architecture and diagrams
8. **[CHANGELOG.md](CHANGELOG.md)** - Version history and changes

### 🔨 Development
9. **[pom.xml](pom.xml)** - Maven build configuration
10. **[build.bat](build.bat)** - Windows build script
11. **[.gitignore](.gitignore)** - Git ignore rules

---

## 🎯 Quick Navigation

### I want to...

#### Install the Plugin
→ Read [INSTALLATION.md](INSTALLATION.md)  
→ Follow steps 1-4 in [QUICKSTART.md](QUICKSTART.md)

#### Configure the Plugin
→ Edit [config.yml](src/main/resources/config.yml)  
→ See examples in [config-examples.yml](config-examples.yml)  
→ Use `/dna reload` to apply changes

#### Understand How It Works
→ Read "How It Works" in [README.md](README.md)  
→ View diagrams in [ARCHITECTURE.md](ARCHITECTURE.md)  
→ Check [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)

#### Build from Source
→ Follow "Building" section in [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)  
→ Run [build.bat](build.bat) on Windows  
→ Or use `mvn clean package`

#### Troubleshoot Issues
→ Check "Troubleshooting" in [INSTALLATION.md](INSTALLATION.md)  
→ Enable debug mode in [config.yml](src/main/resources/config.yml)  
→ Review [CHANGELOG.md](CHANGELOG.md) for known issues

#### Learn About Features
→ Read "Features" in [README.md](README.md)  
→ Check "Core Features" in [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)  
→ See examples in [config-examples.yml](config-examples.yml)

#### Customize Mobs
→ Edit mob settings in [config.yml](src/main/resources/config.yml)  
→ See "Mob Specific Settings" section  
→ Check examples in [config-examples.yml](config-examples.yml)

#### Adjust Difficulty
→ Modify `evolution.kills-per-tier` in config  
→ Change `elite-mutation-chance`  
→ Adjust resistance percentages  
→ See scenarios in [config-examples.yml](config-examples.yml)

---

## 📂 Source Code Structure

### Main Classes
- **[DNAMobs.java](src/main/java/com/dnamobs/DNAMobs.java)** - Plugin entry point
- **[DNAManager.java](src/main/java/com/dnamobs/managers/DNAManager.java)** - DNA tracking
- **[EvolutionManager.java](src/main/java/com/dnamobs/managers/EvolutionManager.java)** - Evolution logic

### Listeners
- **[MobCombatListener.java](src/main/java/com/dnamobs/listeners/MobCombatListener.java)** - Combat events
- **[MobSpawnListener.java](src/main/java/com/dnamobs/listeners/MobSpawnListener.java)** - Spawn events

### Commands
- **[DNACommand.java](src/main/java/com/dnamobs/commands/DNACommand.java)** - Command handler

### Models
- **[DNAProfile.java](src/main/java/com/dnamobs/models/DNAProfile.java)** - DNA data
- **[EvolvedMob.java](src/main/java/com/dnamobs/models/EvolvedMob.java)** - Evolved mob data
- **[DamageType.java](src/main/java/com/dnamobs/models/DamageType.java)** - Damage types

### Storage
- **[DataStorage.java](src/main/java/com/dnamobs/storage/DataStorage.java)** - Storage interface
- **[YAMLStorage.java](src/main/java/com/dnamobs/storage/YAMLStorage.java)** - YAML implementation

### Utils
- **[ColorUtil.java](src/main/java/com/dnamobs/utils/ColorUtil.java)** - Color utilities

---

## 🎓 Learning Path

### For Server Administrators

1. **Start Here:** [README.md](README.md)
   - Understand what the plugin does
   - Learn about features

2. **Install:** [INSTALLATION.md](INSTALLATION.md)
   - Follow installation steps
   - Run basic tests

3. **Configure:** [QUICKSTART.md](QUICKSTART.md)
   - Adjust settings for your server
   - Test different configurations

4. **Optimize:** [config-examples.yml](config-examples.yml)
   - Find the right balance
   - Monitor performance

### For Developers

1. **Overview:** [PROJECT_SUMMARY.md](PROJECT_SUMMARY.md)
   - Understand project structure
   - Learn build process

2. **Architecture:** [ARCHITECTURE.md](ARCHITECTURE.md)
   - Study system design
   - Review data flow

3. **Source Code:** Browse `src/main/java/`
   - Read class documentation
   - Understand implementation

4. **Build:** [pom.xml](pom.xml)
   - Review dependencies
   - Compile the project

### For Players

1. **What is DNAMobs?** [README.md](README.md)
   - Learn about adaptive mobs
   - Understand evolution system

2. **How to Play:** [README.md](README.md) - "Why DNAMobs?" section
   - Adapt your strategies
   - Face evolved challenges

---

## 📊 File Statistics

### Documentation
- **Total Documentation Files:** 8
- **Total Lines:** ~2,500+
- **Languages:** Markdown, YAML

### Source Code
- **Java Files:** 11
- **Total Lines:** ~1,500+
- **Packages:** 6

### Configuration
- **Config Files:** 2
- **Options:** 100+
- **Mob Types:** 5

---

## 🔍 Search Guide

### Find Information About...

**Commands:**
- [README.md](README.md) - Commands section
- [plugin.yml](src/main/resources/plugin.yml) - Command definitions
- [DNACommand.java](src/main/java/com/dnamobs/commands/DNACommand.java) - Implementation

**Permissions:**
- [README.md](README.md) - Permissions section
- [plugin.yml](src/main/resources/plugin.yml) - Permission definitions

**Configuration:**
- [config.yml](src/main/resources/config.yml) - All settings
- [config-examples.yml](config-examples.yml) - Examples
- [QUICKSTART.md](QUICKSTART.md) - Common configs

**Evolution System:**
- [README.md](README.md) - Evolution tiers
- [ARCHITECTURE.md](ARCHITECTURE.md) - Evolution flow
- [EvolutionManager.java](src/main/java/com/dnamobs/managers/EvolutionManager.java) - Code

**DNA Tracking:**
- [README.md](README.md) - How it works
- [ARCHITECTURE.md](ARCHITECTURE.md) - Data flow
- [DNAManager.java](src/main/java/com/dnamobs/managers/DNAManager.java) - Code

**Elite Mobs:**
- [README.md](README.md) - Elite mobs section
- [config.yml](src/main/resources/config.yml) - Elite settings
- [EvolutionManager.java](src/main/java/com/dnamobs/managers/EvolutionManager.java) - Implementation

**Performance:**
- [README.md](README.md) - Performance section
- [config.yml](src/main/resources/config.yml) - Performance settings
- [INSTALLATION.md](INSTALLATION.md) - Optimization tips

---

## 🎯 Common Tasks

### Task: Change Evolution Speed
1. Open [config.yml](src/main/resources/config.yml)
2. Find `evolution.kills-per-tier`
3. Lower number = faster evolution
4. Run `/dna reload`

### Task: Disable Specific Mob
1. Open [config.yml](src/main/resources/config.yml)
2. Find `mobs.<mob-name>.enabled`
3. Set to `false`
4. Run `/dna reload`

### Task: Increase Elite Spawn Rate
1. Open [config.yml](src/main/resources/config.yml)
2. Find `elite.elite-mutation-chance`
3. Increase value (0.05 = 5%)
4. Run `/dna reload`

### Task: Reset All Data
1. Run `/dna reset` in-game
2. Or delete `plugins/DNAMobs/data.yml`
3. Restart server

### Task: View Statistics
1. Run `/dna stats` in-game
2. Or check `plugins/DNAMobs/data.yml`

---

## 📞 Support Resources

### Documentation
- All documentation files in this project
- Inline code comments
- Configuration comments

### Commands
- `/dna` - Show help
- `/dna stats` - View data
- Enable `debug: true` for detailed logs

### Files to Check
- `server.log` - Error messages
- `plugins/DNAMobs/config.yml` - Settings
- `plugins/DNAMobs/data.yml` - Stored data

---

## ✅ Checklist for New Users

- [ ] Read [README.md](README.md)
- [ ] Follow [INSTALLATION.md](INSTALLATION.md)
- [ ] Configure [config.yml](src/main/resources/config.yml)
- [ ] Test with `/dna stats`
- [ ] Kill some mobs
- [ ] Check evolution
- [ ] Spawn evolved mobs
- [ ] Test elite mobs
- [ ] Verify data saves
- [ ] Optimize performance

---

## 🎉 You're All Set!

This index should help you navigate all the documentation and find exactly what you need.

**Quick Links:**
- 🚀 [Get Started](QUICKSTART.md)
- 📖 [Full Documentation](README.md)
- 🔧 [Installation Guide](INSTALLATION.md)
- ⚙️ [Configuration](config.yml)

---

**Author:** MathTeacher  
**Plugin:** DNAMobs v1.0.0  
**Last Updated:** 2024

*Happy evolving! 🧬*
