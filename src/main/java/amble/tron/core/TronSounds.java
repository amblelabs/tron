package amble.tron.core;

import amble.tron.Tron;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.List;


public class TronSounds {

    public static final SoundEvent LIGHTCYCLE_LOOP = register("lightcycle_loop");

    public static void init() {
    }

    private static SoundEvent register(String name) {
        return register(Tron.of(name));
    }

    private static SoundEvent register(Identifier id) {
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static List<SoundEvent> getSounds(String modid) {
        return Registries.SOUND_EVENT.stream().filter(sound -> sound.getId().getNamespace().equals(modid)).toList();
    }
}
