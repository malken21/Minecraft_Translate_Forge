package marumasa.translate;

import marumasa.translate.keybind.ModKeyBind;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.*;
import net.minecraftforge.client.event.ClientChatEvent;
import net.minecraftforge.client.event.ClientChatReceivedEvent;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Events {

    private static final String cmd = Config.CMD.get();
    private static final Minecraft mc = Minecraft.getInstance();

    private static boolean isAuto = false;

    @SubscribeEvent
    public void onChat(ClientChatReceivedEvent event) {
        if (isAuto) {
            String message = event.getMessage().getString();

            message = message.replaceFirst(cmd, "");
            message = message.replaceAll("§([0-9a-f]|r|l|o|n|m|k)", "");

            new translate(message).start();
        } else {
            MutableComponent component = (MutableComponent) event.getMessage();

            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.RUN_COMMAND, cmd + component.getString());
            Component hoverText = new TranslatableComponent("chat.translate.hover");//ホバーした時に表示するテキスト
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText);

            Style style = Style.EMPTY;
            style = style.withClickEvent(clickEvent);
            style = style.withHoverEvent(hoverEvent);

            event.setMessage(component.append(new TranslatableComponent("chat.translate.button").setStyle(style)));
        }
    }

    @SubscribeEvent
    public void onClick(ClientChatEvent event) {
        String message = event.getMessage();
        if (message.startsWith(cmd)) {
            event.setCanceled(true);

            message = message.replaceFirst(cmd, "");
            message = message.replaceAll("§([0-9a-f]|r|l|o|n|m|k)", "");

            new translate(message).start();
        }
    }

    @SubscribeEvent
    public void KeyInputEvent(InputEvent.KeyInputEvent event) {
        if (ModKeyBind.ModKeys[0].isDown()) {
            isAuto = !isAuto;
            ChatComponent chat = mc.gui.getChat();
            if (isAuto) {
                chat.addMessage(new TranslatableComponent("toggle.translate.on"));
            } else {
                chat.addMessage(new TranslatableComponent("toggle.translate.off"));
            }
        }
    }
}
