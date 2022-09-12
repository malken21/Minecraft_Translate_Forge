package marumasa.translate;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod("translate")
public class minecraft {
    public static final String modid = "translate";

    public minecraft() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, Config.SPEC, "TranslateMod.toml");
        MinecraftForge.EVENT_BUS.register((new Events()));
    }
}
