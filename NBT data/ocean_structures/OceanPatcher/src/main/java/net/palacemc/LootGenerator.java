package net.palacemc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.TagTypes;
import net.minecraft.util.Mth;

import static net.palacemc.LootGenerator.Item.*;
import static net.palacemc.LootGenerator.Effect.Type.*;
import static net.palacemc.LootGenerator.Enchant.Type.*;

public class LootGenerator {

    public static final Loot SHIPWRECK_MAP = new Loot(
            new Pool(1) {{
                addItem(MAP);
            }},
            new Pool(3) {{
                addItem(COMPASS);
                addItem(MAP);
                addItem(CLOCK);
                addItem(PAPER, 20, 1, 10);
                addItem(FEATHER, 10, 1, 5);
                addItem(BOOK, 5, 1, 5);
            }}
    );

    public static final Loot SHIPWRECK_TREASURE = new Loot(
            new Pool(3, 6) {{
                addItem(IRON_INGOT, 90, 1, 5);
                addItem(GOLD_INGOT, 10, 1, 5);
                addItem(EMERALD, 40, 1, 5);
                addItem(DIAMOND, 5);
                addItem(EXP_BOTTLE, 5);
            }},
            new Pool(2, 5) {{
                addItem(IRON_NUGGET, 50, 1, 10);
                addItem(GOLD_NUGGET, 10, 1, 10);
                addItem(LAPIS, 20, 1, 10);
            }}
    );

    public static final Loot SHIPWRECK_SUPPLY = new Loot(
            new Pool(3, 10) {{
                addItem(PAPER, 8, 1, 12);
                addItem(POTATO, 7, 2, 6);
                addItem(MOSS_BLOCK, 7, 1, 4);
                addItem(POISON_POTATO, 7, 2, 6);
                addItem(CARROT, 7, 4, 8);
                addItem(WHEAT, 7, 8, 21);
                addItem(SUS_STEW, 10);
                addItem(COAL, 6, 2, 8);
                addItem(ROTTEN_FLESH, 5, 5, 24);
                addItem(PUMPKIN, 2, 1, 3);
                addItem(BAMBOO, 2, 1, 3);
                addItem(GUNPOWDER, 3, 1, 5);
                addItem(TNT, 1, 1, 2);
                addItem(E_LEATHER_HELMET, 3);
                addItem(E_LEATHER_CHESTPLATE, 3);
                addItem(E_LEATHER_LEGGINGS, 3);
                addItem(E_LEATHER_BOOTS, 3);
            }}
    );

    public static final Loot RUIN_BIG = new Loot(
            new Pool(2, 8) {{
                addItem(COAL, 10, 1, 4);
                addItem(GOLD_NUGGET, 10, 1, 3);
                addItem(EMERALD);
                addItem(WHEAT, 10, 2, 3);
            }},
            new Pool(1) {{
                addItem(GOLDEN_APPLE);
                addItem(E_BOOK, 5);
                addItem(LEATHER_CHESTPLATE);
                addItem(GOLDEN_HELMET);
                addItem(E_FISHING_ROD, 5);
                addItem(MAP, 10);
            }}
    );

    public static final Loot RUIN_SMALL = new Loot(
            new Pool(2, 8) {{
                addItem(COAL, 10, 1, 4);
                addItem(STONE_AXE, 2);
                addItem(ROTTEN_FLESH, 5);
                addItem(EMERALD);
                addItem(WHEAT, 10, 2, 3);
            }},
            new Pool(1) {{
                addItem(LEATHER_CHESTPLATE);
                addItem(GOLDEN_HELMET);
                addItem(E_FISHING_ROD, 5);
                addItem(MAP, 5);
            }}
    );

    public record Enchant(Type type, int level) {

        public Type getType() {
            return type;
        }

        public int getLevel() {
            return level;
        }

        enum Type {
            AQUA_AFFINITY("minecraft:aqua_affinity"),
            BANE_OF_ART("minecraft:bane_of_arthropods"),
            BINDING("minecraft:binding_curse"),
            BLAST_PROT("minecraft:blast_protection"),
            CHANNELING("minecraft:channeling"),
            DEPTH_STRIDER("minecraft:depth_strider"),
            EFFICIENCY("minecraft:efficiency"),
            FEATHER_FALLING("minecraft:feather_falling"),
            FIRE_ASPECT("minecraft:fire_aspect"),
            FIRE_PROT("minecraft:fire_protection"),
            FLAME("minecraft:flame"),
            FORTUNE("minecraft:fortune"),
            FROST_WALKER("minecraft:frost_walker"),
            IMPALING("minecraft:impaling"),
            INFINITY("minecraft:infinity"),
            KNOCKBACK("minecraft:knockback"),
            LOOTING("minecraft:looting"),
            LOYALTY("minecraft:loyalty"),
            LOTS("minecraft:luck_of_the_sea"),
            LURE("minecraft:lure"),
            MENDING("minecraft:mending"),
            MULTISHOT("minecraft:multishot"),
            PIERCING("minecraft:piercing"),
            POWER("minecraft:power"),
            PROJECTILE_PROT("minecraft:projectile_protection"),
            PROTECTION("minecraft:protection"),
            PUNCH("minecraft:punch"),
            QUICK_CHARGE("minecraft:quick_charge"),
            RESPIRATION("minecraft:respiration"),
            RIPTIDE("minecraft:riptide"),
            SHARPNESS("minecraft:sharpness"),
            SILK_TOUCH("minecraft:silk_touch"),
            SMITE("minecraft:smite"),
            SOUL_SPEED("minecraft:soul_speed"),
            SWEEPING_EDGE("minecraft:sweeping"),
            THORNS("minecraft:thorns"),
            UNBREAKING("minecraft:unbreaking"),
            VANISHING("minecraft:vanishing_curse");

            String id;
            Type(String id) {
                this.id = id;
            }
            public String getId() {
                return id;
            }
        }

        private static final Type[] armor = new Type[]{
                BINDING,
                BLAST_PROT,
                FIRE_PROT,
                MENDING,
                PROJECTILE_PROT,
                PROTECTION,
                THORNS,
                UNBREAKING,
                VANISHING
        };

        private static final Type[] boots = new ArrayList<Type>() {{
            addAll(Arrays.asList(armor));
            add(DEPTH_STRIDER);
            add(FEATHER_FALLING);
            add(FROST_WALKER);
            add(SOUL_SPEED);
        }}.toArray(new Type[0]);

        private static final Type[] helmet = new ArrayList<Type>() {{
            addAll(Arrays.asList(armor));
            add(AQUA_AFFINITY);
            add(RESPIRATION);
        }}.toArray(new Type[0]);

        private static final Type[] fishing = new Type[]{
                LOTS,
                LURE,
                UNBREAKING,
                MENDING,
                VANISHING
        };

        public static Enchant randomAll(Random rng) {
            return random(rng, Type.values());
        }

        public static Enchant randomArmor(Random rng) {
            return random(rng, armor);
        }

        public static Enchant randomBoots(Random rng) {
            return random(rng, boots);
        }

        public static Enchant randomHelmet(Random rng) {
            return random(rng, helmet);
        }

        public static Enchant randomFishing(Random rng) {
            return random(rng, fishing);
        }

        private static int randomLevel(Random rng, Type type) {
            int max = switch (type) {
                case BANE_OF_ART, EFFICIENCY, IMPALING, POWER, SHARPNESS, SMITE -> 5;
                case BLAST_PROT, FEATHER_FALLING, FIRE_PROT, PIERCING, PROJECTILE_PROT, PROTECTION  -> 4;
                case DEPTH_STRIDER, FORTUNE, LOOTING, LOYALTY, LOTS, LURE, QUICK_CHARGE, RESPIRATION, RIPTIDE, SOUL_SPEED, SWEEPING_EDGE, THORNS, UNBREAKING -> 3;
                case FIRE_ASPECT, FROST_WALKER, KNOCKBACK, PUNCH -> 2;
                default -> 1;
            };
            return Mth.nextInt(rng, 1, max);
        }

        private static Enchant random(Random rng, Type[] types) {
            Type type = types[rng.nextInt(types.length)];
            return new Enchant(type, randomLevel(rng, type));
        }
    }

    public record Effect(Type type, int duration) {

        public Type getType() {
            return type;
        }

        public int getDuration() {
            return duration;
        }

        enum Type {
            JUMP_BOOST(8),
            BLINDNESS(15),
            NIGHT_VISION(16),
            WEAKNESS(18),
            POISON(19),
            SATURATION(23);

            int id;
            Type(int id) {
                this.id = id;
            }
            int getId() {
                return id;
            }
        }

        private static final Type[] stew = new Type[]{
                JUMP_BOOST,
                BLINDNESS,
                NIGHT_VISION,
                WEAKNESS,
                POISON,
                SATURATION
        };

        private static Effect randomStew(Random rng) {
            Type type = stew[rng.nextInt(stew.length)];
            return new Effect(type, switch (type) {
                case SATURATION, NIGHT_VISION, JUMP_BOOST -> Mth.nextInt(rng, 7, 10);
                case POISON -> Mth.nextInt(rng, 10, 20);
                case WEAKNESS -> Mth.nextInt(rng, 6, 8);
                case BLINDNESS -> Mth.nextInt(rng, 5, 7);
            });
        }

    }

    enum Item {
        MAP("minecraft:map"),
        COMPASS("minecraft:compass"),
        CLOCK("minecraft:clock"),
        PAPER("minecraft:paper"),
        FEATHER("minecraft:feather"),
        BOOK("minecraft:book"),
        IRON_INGOT("minecraft:iron_ingot"),
        IRON_NUGGET("minecraft:iron_nugget"),
        GOLD_INGOT("minecraft:gold_ingot"),
        GOLD_NUGGET("minecraft:gold_nugget"),
        EMERALD("minecraft:emerald"),
        DIAMOND("minecraft:diamond"),
        EXP_BOTTLE("minecraft:experience_bottle"),
        LAPIS("minecraft:lapis_lazuli"),
        POTATO("minecraft:potato"),
        MOSS_BLOCK("minecraft:moss_block"),
        POISON_POTATO("minecraft:poisonous_potato"),
        CARROT("minecraft:carrot"),
        WHEAT("minecraft:wheat"),
        SUS_STEW("minecraft:suspicious_stew"),
        COAL("minecraft:coal"),
        ROTTEN_FLESH("minecraft:rotten_flesh"),
        PUMPKIN("minecraft:pumpkin"),
        BAMBOO("minecraft:bamboo"),
        GUNPOWDER("minecraft:gunpowder"),
        TNT("minecraft:tnt"),
        E_LEATHER_HELMET("minecraft:leather_helmet"),
        LEATHER_CHESTPLATE("minecraft:leather_chestplate"),
        E_LEATHER_CHESTPLATE("minecraft:leather_chestplate"),
        E_LEATHER_LEGGINGS("minecraft:leather_leggings"),
        E_LEATHER_BOOTS("minecraft:leather_boots"),
        GOLDEN_APPLE("minecraft:golden_apple"),
        GOLDEN_HELMET("minecraft:golden_helmet"),
        E_BOOK("minecraft:book"),
        E_FISHING_ROD("minecraft:fishing_rod"),
        STONE_AXE("minecraft:stone_axe");

        String id;
        Item(String id) {
            this.id = id;
        }
        public String getId() {
            return id;
        }
    }

    public static class LootItem {

        private final Item type;
        private final int min, max;
        public LootItem(Item item, int min, int max) {
            this.type = item;
            this.min = min;
            this.max = max;
        }

        public ItemStack getRandom(Random rng) {
            return new ItemStack(type, Mth.nextInt(rng, min, max));
        }

    }

    public static class ItemStack {

        private final Item type;
        private CompoundTag tag = null;
        private int count;
        public ItemStack(Item item, int count) {
            this.type = item;
            this.count = count;
        }

        public Item getType() {
            return type;
        }

        public int getCount() {
            return count;
        }

        public ItemStack split(int splitSize) {
            splitSize = Math.min(splitSize, count);
            this.count -= splitSize;
            return new ItemStack(type, splitSize);
        }

        public void enchant(Enchant enchant) {
            if (tag == null)
                tag = new CompoundTag();

            String location;
            if (type == E_BOOK) location = "StoredEnchantments";
            else location = "Enchantments";

            if (!tag.contains(location, TagTypes.LIST))
                tag.put(location, new ListTag());
            ListTag enchantments = tag.getList(location, TagTypes.COMPOUND);

            CompoundTag enchantTag = new CompoundTag();
            enchantTag.putString("id", enchant.type().getId());
            enchantTag.putShort("lvl", (short) Mth.clamp(enchant.getLevel(), 1, 5));
            enchantments.add(enchantTag);
        }

        public void applyTag(CompoundTag tag) {
            this.tag = tag.copy();
        }

        public CompoundTag toNBT() {
            CompoundTag self = new CompoundTag();
            self.putString("id", type.getId());
            self.putByte("Count", (byte) count);
            if (tag != null)
                self.put("tag", tag.copy());
            return self;
        }
    }

    @SuppressWarnings("SameParameterValue")
    static class Pool {

        private final List<LootItem> items = new ArrayList<>();
        private final int min, max;
        private int length = 0;

        public Pool(int rolls) {
            this.min = rolls;
            this.max = rolls;
        }

        public Pool(int min, int max) {
            this.min = min;
            this.max = max;
        }

        void addItem(Item item) {
            items.add(new LootItem(item, 1, 1));
            length = items.size();
        }

        void addItem(Item item, int weight) {
            addItem(item, weight, 1, 1);
        }

        void addItem(Item item, int weight, int min, int max) {
            LootItem loot = new LootItem(item, min, max);
            for (int i = 0; i < weight; i++)
                items.add(loot);
            length = items.size();
        }

        List<LootItem> pickItems(Random rng) {
            List<LootItem> result = new ArrayList<>();
            for (int rolls = Mth.nextInt(rng, min, max); rolls > 0; rolls--)
                result.add(items.get(rng.nextInt(length)));
            return result;
        }

    }

    static class Loot {

        List<Pool> pools;

        private Loot(Pool... pools) {
            this.pools = Arrays.stream(pools).toList();
        }

        public List<ItemStack> generateLoot(Random rng) {
            List<ItemStack> result = new ArrayList<>();

            for (Pool pool : pools)
                for (LootItem item : pool.pickItems(rng))
                    result.add(item.getRandom(rng));

            return result;
        }

        public static ListTag toNBT(List<ItemStack> items, Random rng) {

            // Split items apart randomly
            List<ItemStack> shuffled = new ArrayList<>();
            while(items.size() + shuffled.size() < 27 && !items.isEmpty()) {
                ItemStack itemStackA = items.remove(Mth.nextInt(rng, 0, items.size() - 1));
                ItemStack itemStackB = itemStackA.split(Mth.nextInt(rng, 1, itemStackA.getCount() / 2));

                if (itemStackA.getCount() > 0) {
                    if (itemStackA.getCount() > 1 && rng.nextBoolean()) {
                        items.add(itemStackA); // split more
                    } else {
                        shuffled.add(itemStackA); // stop splitting
                    }
                }

                if (itemStackB.getCount() > 0) {
                    if (itemStackB.getCount() > 1 && rng.nextBoolean()) {
                        items.add(itemStackB); // split more
                    } else {
                        shuffled.add(itemStackB); // stop splitting
                    }
                }
            }

            shuffled.addAll(items); // add back remaining items

            // Fill list
            ListTag result = new ListTag();
            // shuffle slots
            List<Integer> slots = new ArrayList<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, 26));
            Collections.shuffle(slots, rng);
            for (ItemStack itemStack : shuffled) {
                if (slots.isEmpty()) // out of space
                    return result;

                // Apply enchant/ status effect
                switch (itemStack.getType()) {
                    case E_FISHING_ROD -> itemStack.enchant(Enchant.randomFishing(rng));
                    case E_LEATHER_HELMET -> itemStack.enchant(Enchant.randomHelmet(rng));
                    case E_LEATHER_BOOTS -> itemStack.enchant(Enchant.randomBoots(rng));
                    case E_LEATHER_CHESTPLATE, E_LEATHER_LEGGINGS -> itemStack.enchant(Enchant.randomArmor(rng));
                    case E_BOOK -> itemStack.enchant(Enchant.randomAll(rng));
                    case SUS_STEW -> {
                        Effect e = Effect.randomStew(rng);
                        CompoundTag tag = new CompoundTag();
                        ListTag effects = new ListTag();
                        CompoundTag effect = new CompoundTag();
                        effect.putByte("EffectId", (byte) Mth.clamp(e.type.getId(), 1, 29));
                        effect.putInt("EffectDuration", Mth.clamp(e.getDuration(), 1, 20) * 20); // must be in ticks
                        effects.add(effect);
                        tag.put("Effects", effects);
                        itemStack.applyTag(tag);
                    }
                }

                CompoundTag itemTag = itemStack.toNBT();
                itemTag.putByte("Slot", slots.remove(0).byteValue());
                result.add(itemTag);
            }

            return result;

        }

    }

}
