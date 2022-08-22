package marumasa.translate;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.ChatComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;

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
        MutableComponent resultText = new TranslatableComponent("chat.translate.result");
        final String translate = getGAS(msg, mc.options.languageCode.substring(0, 2));
        Component translateText;
        if (translate != null) {
            translateText = new TextComponent(translate);
        } else {
            translateText = new TranslatableComponent("chat.translate.error");
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
