package amble.tron.core.config;

import amble.tron.Tron;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.Boolean;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import dev.isxander.yacl3.platform.YACLPlatform;

/**
 * Tron mod configuration using YACL v2
 */
public class TronConfig {

    public static final String CATEGORY = "lightcycle";

    public static final ConfigClassHandler<TronConfig> INSTANCE = ConfigClassHandler.createBuilder(TronConfig.class)
            .id(YACLPlatform.rl(Tron.MOD_ID, "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(YACLPlatform.getConfigDir().resolve("tron.json5"))
                    .setJson5(true)
                    .build())
            .build();

    @AutoGen(category = CATEGORY)
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry
    public boolean lightcycleLegacyMode = false;

    @AutoGen(category = CATEGORY)
    @Boolean(formatter = Boolean.Formatter.YES_NO, colored = true)
    @SerialEntry
    public boolean persistentTrails = true;

    public static boolean isLightcycleLegacyMode() {
        return INSTANCE.instance().lightcycleLegacyMode;
    }
}

