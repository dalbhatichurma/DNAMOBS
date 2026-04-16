# DNAMobs Quick Start Guide

## 🚀 Getting Started in 5 Minutes

### Step 1: Installation
1. Download `DNAMobs-1.0.0.jar`
2. Place in your server's `plugins/` folder
3. Restart your server

### Step 2: First Configuration
The plugin works out of the box, but you may want to adjust:

```yaml
# In config.yml
evolution:
  kills-per-tier: 50  # How many kills to evolve (lower = faster evolution)
  elite-mutation-chance: 0.05  # 5% chance for elite mobs
```

### Step 3: Test It Out
1. Kill some zombies with your sword (melee)
2. After 50 kills, zombies will evolve to Tier 2
3. They'll gain melee resistance and become harder to kill with swords
4. Switch to a bow and watch them adapt again!

### Step 4: Check Stats
Use `/dna stats` to see evolution progress:
```
ZOMBIE: Tier 2 (67 kills)
SKELETON: Tier 3 (152 kills)
CREEPER: Tier 1 (23 kills)
```

## 🎯 Recommended Settings

### For Casual Servers
```yaml
evolution:
  kills-per-tier: 100
  elite-mutation-chance: 0.03
  max-tier: 3
```

### For Hardcore Servers
```yaml
evolution:
  kills-per-tier: 30
  elite-mutation-chance: 0.10
  max-tier: 4
```

### For Testing
```yaml
evolution:
  kills-per-tier: 5  # Fast evolution for testing
  elite-mutation-chance: 0.50  # 50% elite chance
```

## 🔧 Common Configurations

### Disable Specific Mobs
```yaml
mobs:
  zombie:
    enabled: false  # Zombies won't evolve
```

### Adjust Resistance
```yaml
resistance:
  tier-2: 10  # Only 10% resistance at tier 2
  tier-3: 20
  tier-4: 35
```

### Change Elite Names
```yaml
elite:
  name-format: "&4&l⚡ ELITE %mob% ⚡"
```

## 📊 Understanding Evolution

### How Mobs Evolve
1. Players kill mobs using various methods
2. Plugin tracks damage types (melee, ranged, fire, etc.)
3. After X kills (default 50), mob evolves to next tier
4. Mob gains resistance to most common damage type
5. Process repeats up to Tier 4

### Damage Type Priority
- **Melee**: Sword, axe, hand attacks
- **Ranged**: Bow, crossbow, trident
- **Fire**: Fire, lava, fire aspect
- **Explosion**: TNT, creeper explosions
- **Environmental**: Fall damage, drowning, etc.

## 🎮 Player Strategy Tips

### Early Game
- Use varied attack methods
- Don't rely on one weapon type
- Prepare for mob adaptation

### Mid Game
- Check `/dna stats` regularly
- Adjust tactics based on evolution
- Farm different mob types

### Late Game
- Elite mobs become common
- Coordinate with team
- Use environmental kills

## ⚠️ Troubleshooting

### Mobs Not Evolving?
1. Check `/dna stats` - are kills being tracked?
2. Verify `general.enabled: true` in config
3. Check mob is enabled: `mobs.zombie.evolution-enabled: true`

### Too Many Elite Mobs?
Lower the chance:
```yaml
elite:
  elite-mutation-chance: 0.01  # 1% instead of 5%
```

### Performance Issues?
```yaml
performance:
  max-tracked-per-chunk: 25  # Lower from 50
  cache-size: 500  # Lower from 1000
```

### Reset Everything
```
/dna reset
```

## 🎯 Advanced Tips

### Region-Specific Evolution
Currently uses GLOBAL mode. Future updates will support:
- WORLD mode (per-world evolution)
- PLAYER mode (personal difficulty)

### Data Backup
Backup `plugins/DNAMobs/data.yml` regularly to preserve evolution data.

### Server Events
Use evolution resets for server events:
```
/dna reset  # Fresh start for new season
```

## 📞 Need Help?

- Check README.md for full documentation
- Review config.yml comments
- Enable debug mode: `general.debug: true`

---

**Author: MathTeacher**
**Version: 1.0.0**
