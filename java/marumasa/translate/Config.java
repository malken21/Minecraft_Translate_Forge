package marumasa.translate;

import net.minecraftforge.common.ForgeConfigSpec;

public class Config {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.ConfigValue<String> GAS_URL;
    public static final ForgeConfigSpec.ConfigValue<String> CMD;

    static {
        BUILDER.push("TranslateMod");

        GAS_URL = BUILDER.comment("Google Apps Script URL")
                .define("GAS", "https://script.google.com/macros/s/AKfycbzlQDAjHBncfxZZo7rUCj1eb4rys8FZyr59ievvz6Y_1kryMBlwd7I8Js819m-iF4c6/exec");
        CMD = BUILDER.comment("Translate Command")
                .define("CMD", "/translate ");

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
