package com.grigoryev.demons.util;

import com.grigoryev.demons.Demon;
import com.grigoryev.demons.DemonType;
import lombok.Getter;
import lombok.experimental.UtilityClass;

import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;

@UtilityClass
public class DemonGenerator {

    private final String[] DEMON_NAMES = {
            "Astaroth", "Bael", "Caim", "Dantalion", "Eligos", "Furfur", "Gremory", "Halphas", "Ipos", "Jegudiel"
    };
    private final DemonType[] DEMON_TYPES = {
            DemonType.FIEND, DemonType.SPECTER, DemonType.WRAITH, DemonType.PHANTOM, DemonType.DEVIL
    };
    private final String[] ABILITIES = new String[]{
            "Fire Breath", "Ice Blast", "Shadow Stealth", "Teleportation", "Mind Control",
            "Soul Drain", "Dark Whisper", "Chaos Bolt", "Nether Swap", "Inferno Fury"
    };
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
                .setName(DEMON_NAMES[RANDOM.nextInt(DEMON_NAMES.length)])
                .setType(DEMON_TYPES[RANDOM.nextInt(DEMON_TYPES.length)])
                .setLevel(RANDOM.nextInt(100) + 1)
                .setAbility(ABILITIES[RANDOM.nextInt(ABILITIES.length)])
                .setIsCaptured(RANDOM.nextBoolean())
                .build();
    }

}
