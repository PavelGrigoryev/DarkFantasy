package com.grigoryev.demons.util;

import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonType;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

@UtilityClass
public class DemonGenerator {

    private final Queue<String> DEMON_NAMES = new ArrayDeque<>(Arrays.asList(
            "Astaroth", "Bael", "Caim", "Dantalion", "Eligos", "Furfur", "Gremory", "Halphas", "Ipos", "Jegudiel",
            "Kokabiel", "Leraje", "Marbas", "Naberius", "Orias", "Paimon", "Raum", "Seere", "Valefor", "Zagan"
    ));
    private final DemonType[] DEMON_TYPES = {
            DemonType.FIEND, DemonType.SPECTER, DemonType.WRAITH, DemonType.PHANTOM, DemonType.DEVIL
    };
    private final Queue<String> ABILITIES = new ArrayDeque<>(Arrays.asList(
            "Fire Breath", "Ice Blast", "Shadow Stealth", "Teleportation", "Mind Control",
            "Soul Drain", "Dark Whisper", "Chaos Bolt", "Nether Swap", "Inferno Fury",
            "Lightning Strike", "Earthquake", "Wind Fury", "Water Surge", "Nature's Grasp",
            "Stone Skin", "Solar Flare", "Lunar Blessing", "Starfall", "Void Rift"
    ));
    private final SecureRandom RANDOM = new SecureRandom();

    @Getter
    private final Map<Long, Demon> DEMONS;

    static {
        DEMONS = new HashMap<>();
        for (long i = 1; i < 21; i++) {
            DEMONS.put(i, generateRandomDemon(i));
        }
    }

    private Demon generateRandomDemon(Long id) {
        return Demon.newBuilder()
                .setId(id)
                .setName(DEMON_NAMES.poll())
                .setType(DEMON_TYPES[RANDOM.nextInt(DEMON_TYPES.length)])
                .setLevel(RANDOM.nextInt(100) + 1)
                .setAbility(ABILITIES.poll())
                .setIsCaptured(RANDOM.nextBoolean())
                .build();
    }

}
