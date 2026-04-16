# DNAMobs - Installation & Testing Guide

## 📋 Table of Contents
1. [Prerequisites](#prerequisites)
2. [Building the Plugin](#building-the-plugin)
3. [Installation](#installation)
4. [First-Time Setup](#first-time-setup)
5. [Testing the Plugin](#testing-the-plugin)
6. [Troubleshooting](#troubleshooting)
7. [Advanced Configuration](#advanced-configuration)

---

## 🔧 Prerequisites

### Required Software
- **Java Development Kit (JDK) 16 or higher**
  - Download: https://adoptium.net/
  - Verify: `java -version`

- **Apache Maven 3.6 or higher**
  - Download: https://maven.apache.org/download.cgi
  - Verify: `mvn -version`

- **Minecraft Server (Spigot/Paper 1.17+)**
  - Spigot: https://www.spigotmc.org/
  - Paper: https://papermc.io/

### Optional Software
- **Git** (for version control)
- **IntelliJ IDEA** or **Eclipse** (for development)

---

## 🔨 Building the Plugin

### Step 1: Navigate to Project Directory
```bash
cd C:\DNAMobs
```

### Step 2: Build with Maven

**Windows:**
```batch
build.bat
```

**Or manually:**
```batch
mvn clean package
```

**Linux/Mac:**
```bash
mvn clean package
```

### Step 3: Locate the JAR File
After successful build:
```
target/DNAMobs-1.0.0.jar
```

### Build Output Example
```
[INFO] Building jar: C:\DNAMobs\target\DNAMobs-1.0.0.jar
[INFO] BUILD SUCCESS
[INFO] Total time: 5.234 s
```

---

## 📦 Installation

### Step 1: Stop Your Server
```bash
# Stop the Minecraft server
stop
```

### Step 2: Copy Plugin JAR
```bash
# Copy to plugins folder
copy target\DNAMobs-1.0.0.jar <server-directory>\plugins\
```

### Step 3: Start Your Server
```bash
# Start the Minecraft server
java -Xmx2G -Xms2G -jar server.jar nogui
```

### Step 4: Verify Installation
Check server console for:
```
[DNAMobs] DNAMobs v1.0.0 has been enabled!
[DNAMobs] Author: MathTeacher
[DNAMobs] Evolution system: ACTIVE
```

---

## ⚙️ First-Time Setup

### Step 1: Check Generated Files
After first run, verify these files exist:
```
plugins/
├── DNAMobs-1.0.0.jar
└── DNAMobs/
    ├── config.yml
    └── data.yml (created after first mob kill)
```

### Step 2: Review Configuration
Open `plugins/DNAMobs/config.yml` and review settings:

**Key Settings to Check:**
```yaml
general:
  enabled: true  # System is active

evolution:
  kills-per-tier: 50  # Adjust for your server
  elite-mutation-chance: 0.05  # 5% elite chance

mobs:
  zombie:
    enabled: true  # Zombies will evolve
```

### Step 3: Reload Configuration
If you made changes:
```
/dna reload
```

---

## 🧪 Testing the Plugin

### Test 1: Basic Functionality

**1. Check Plugin Status**
```
/dna stats
```
Expected: Shows evolution statistics (empty at first)

**2. Kill Some Mobs**
- Kill 10 zombies with a sword
- Kill 10 skeletons with a bow
- Kill 5 creepers with fire

**3. Check Stats Again**
```
/dna stats
```
Expected: Shows kill counts for each mob type

### Test 2: Evolution System

**1. Fast Evolution Test**
Edit config.yml:
```yaml
evolution:
  kills-per-tier: 5  # Fast evolution for testing
```

**2. Reload Config**
```
/dna reload
```

**3. Kill 5 Zombies**
Use any method (sword, bow, etc.)

**4. Check Evolution**
```
/dna stats
```
Expected: `ZOMBIE: Tier 2 (5 kills)`

**5. Spawn New Zombie**
```
/summon zombie
```
Expected: Zombie has more health and speed

### Test 3: Resistance System

**1. Evolve Zombies with Melee**
- Kill 5 zombies with sword (melee)
- Check stats: `/dna stats`
- Zombies should be Tier 2

**2. Test Resistance**
- Spawn new zombie: `/summon zombie`
- Attack with sword
- Notice: Takes less damage (15% reduction)

**3. Switch Damage Type**
- Attack same zombie with bow
- Notice: Normal damage (no resistance to ranged)

### Test 4: Elite Mobs

**1. Increase Elite Chance**
Edit config.yml:
```yaml
elite:
  elite-mutation-chance: 0.5  # 50% for testing
```

**2. Reload and Spawn**
```
/dna reload
/summon zombie
```

**3. Look for Elite**
- Custom name: `[ELITE] ZOMBIE`
- Particle effects
- Higher health
- Glowing effect

### Test 5: Commands

**Test All Commands:**
```
/dna reload   # Should show success message
/dna stats    # Should show statistics
/dna toggle   # Should disable system
/dna toggle   # Should enable system
/dna reset    # Should reset all data
```

### Test 6: Permissions

**1. Test as Non-OP Player**
```
/dna reload
```
Expected: "You don't have permission"

**2. Grant Permission**
```
/lp user <player> permission set dnamobs.reload true
```

**3. Test Again**
```
/dna reload
```
Expected: Success message

### Test 7: Data Persistence

**1. Generate Some Data**
- Kill 20 zombies
- Kill 15 skeletons
- Check stats: `/dna stats`

**2. Restart Server**
```
stop
# Start server again
```

**3. Verify Data Loaded**
```
/dna stats
```
Expected: Same statistics as before restart

### Test 8: Performance

**1. Enable Debug Mode**
Edit config.yml:
```yaml
general:
  debug: true
```

**2. Monitor Console**
- Kill mobs
- Watch for debug messages
- Check for errors

**3. Check TPS**
```
/tps
```
Expected: No significant TPS drop

---

## 🐛 Troubleshooting

### Problem: Plugin Won't Load

**Symptoms:**
- Error in console on startup
- Plugin not in `/plugins` list

**Solutions:**
1. Check Java version: `java -version` (must be 16+)
2. Verify server version (must be 1.17+)
3. Check for error messages in console
4. Ensure JAR file is not corrupted

### Problem: Mobs Not Evolving

**Symptoms:**
- Kills tracked but tier stays at 1
- `/dna stats` shows kills but no evolution

**Solutions:**
1. Check `evolution.enabled: true` in config
2. Verify `kills-per-tier` setting
3. Check mob-specific `evolution-enabled: true`
4. Reload config: `/dna reload`

### Problem: No Elite Mobs Spawning

**Symptoms:**
- Never see elite mobs
- No custom names or particles

**Solutions:**
1. Check `elite.enabled: true`
2. Increase `elite-mutation-chance` for testing
3. Ensure mobs are evolved (Tier 2+)
4. Check console for errors

### Problem: Data Not Saving

**Symptoms:**
- Stats reset after restart
- data.yml empty or missing

**Solutions:**
1. Check file permissions on plugins folder
2. Verify `general.save-interval` in config
3. Check console for save errors
4. Manually trigger save: `/dna reload`

### Problem: Performance Issues

**Symptoms:**
- Server lag
- TPS drops
- High CPU usage

**Solutions:**
1. Reduce `max-tracked-per-chunk`
2. Increase `ability-update-interval`
3. Increase `save-interval`
4. Disable debug mode
5. Reduce `cache-size`

### Problem: Commands Not Working

**Symptoms:**
- "Unknown command" error
- No response from commands

**Solutions:**
1. Check plugin is loaded: `/plugins`
2. Verify permissions
3. Try aliases: `/dnamobs` or `/evolution`
4. Check for command conflicts

---

## 🔬 Advanced Configuration

### Scenario 1: Hardcore Server

**Goal:** Fast, difficult evolution

```yaml
evolution:
  kills-per-tier: 25
  elite-mutation-chance: 0.15
  max-tier: 4

resistance:
  tier-2: 20
  tier-3: 40
  tier-4: 60

elite:
  health-multiplier: 3.0
  damage-multiplier: 2.0
```

### Scenario 2: Casual Server

**Goal:** Slow, manageable evolution

```yaml
evolution:
  kills-per-tier: 150
  elite-mutation-chance: 0.02
  max-tier: 3

resistance:
  tier-2: 10
  tier-3: 20

elite:
  health-multiplier: 1.5
  damage-multiplier: 1.2
```

### Scenario 3: Testing Environment

**Goal:** Fast testing and debugging

```yaml
general:
  debug: true

evolution:
  kills-per-tier: 3
  elite-mutation-chance: 0.5

performance:
  ability-update-interval: 5
```

### Scenario 4: Performance Optimized

**Goal:** Minimal server impact

```yaml
performance:
  async-saving: true
  max-tracked-per-chunk: 25
  cache-size: 500
  ability-update-interval: 40

general:
  save-interval: 12000  # 10 minutes
```

---

## ✅ Testing Checklist

Before deploying to production:

- [ ] Plugin loads without errors
- [ ] All commands work correctly
- [ ] Mobs evolve after configured kills
- [ ] Resistance applies properly
- [ ] Elite mobs spawn with effects
- [ ] Data saves and loads correctly
- [ ] Config reload works
- [ ] Permissions function properly
- [ ] No console errors
- [ ] Performance is acceptable
- [ ] Data persists after restart
- [ ] Tab completion works
- [ ] Messages display correctly
- [ ] Particles render properly
- [ ] Stats command shows data

---

## 📊 Monitoring

### What to Monitor

**1. Console Messages**
- Look for errors or warnings
- Check debug messages if enabled
- Monitor save confirmations

**2. Performance**
```
/tps  # Check server TPS
/timings  # Detailed performance data
```

**3. Data Files**
- Check `data.yml` size
- Verify data structure
- Backup regularly

**4. Player Feedback**
- Are mobs too hard/easy?
- Are elites too common/rare?
- Is evolution too fast/slow?

---

## 🎯 Success Criteria

Your installation is successful if:

✅ Plugin loads without errors  
✅ Commands respond correctly  
✅ Mobs evolve as configured  
✅ Elite mobs spawn occasionally  
✅ Data persists across restarts  
✅ No performance degradation  
✅ Players notice mob adaptation  

---

## 📞 Getting Help

If you encounter issues:

1. **Check Documentation**
   - README.md
   - QUICKSTART.md
   - This guide

2. **Enable Debug Mode**
   ```yaml
   general:
     debug: true
   ```

3. **Check Console Logs**
   - Look for error messages
   - Note any stack traces

4. **Verify Configuration**
   - Review config.yml
   - Check for typos
   - Validate YAML syntax

5. **Test in Isolation**
   - Remove other plugins
   - Test on clean server
   - Verify Minecraft version

---

## 🎉 You're Ready!

If all tests pass, your DNAMobs installation is complete and ready for production use!

**Next Steps:**
- Fine-tune configuration for your server
- Monitor player feedback
- Adjust difficulty as needed
- Enjoy dynamic mob evolution!

---

**Author:** MathTeacher  
**Plugin:** DNAMobs v1.0.0  
**Last Updated:** 2024
