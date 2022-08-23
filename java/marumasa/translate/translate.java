package marumasa.translate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.*;

import java.net.URLEncoder;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class translate extends Thread {

    private static final String GAS = Config.GAS_URL.get();
    private static final Minecraft mc = Minecraft.getInstance();

    //----------Thread----------//
    private final String msg;

    public translate(String message) {
        msg = message;
    }

    public void run() {
        ChatComponent chat = mc.gui.getChat();
        MutableComponent resultText = new TranslatableComponent("result.translate.text");
        final String translate = getGAS(msg, mc.options.languageCode.substring(0, 2));
        Component translateText;
        if (translate != null) {
            ClickEvent clickEvent = new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, translate);
            Component hoverText = new TranslatableComponent("result.translate.hover");//ホバーした時に表示するテキスト
            HoverEvent hoverEvent = new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText);

            Style style = Style.EMPTY;
            style = style.withClickEvent(clickEvent);
            style = style.withHoverEvent(hoverEvent);

            translateText = new TextComponent(translate).setStyle(style);
        } else {
            translateText = new TranslatableComponent("result.translate.error");
        }
        resultText.append(translateText);
        chat.addMessage(resultText);
    }
    //----------Thread----------//

    private static String getGAS(String message, String lang) {
        final String text = URLEncoder.encode(message, StandardCharsets.UTF_8);
        final HttpResponse<String> Response302 = request.get(GAS + "?text=" + text + "&lang=" + lang);
        if (Response302 == null) return null;
        if (Response302.statusCode() != 302) return null;
        final HttpResponse<String> Response200 = request.get(Response302.headers().map().get("location").get(0));
        if (Response200 == null) return null;
        if (Response200.statusCode() != 200) return null;
        return Response200.body();
    }
}
