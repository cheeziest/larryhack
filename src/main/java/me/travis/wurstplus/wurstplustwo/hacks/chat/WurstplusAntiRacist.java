package me.travis.wurstplus.wurstplustwo.hacks.chat;

import me.travis.wurstplus.wurstplustwo.event.events.WurstplusEventPacket;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.zero.alpine.fork.listener.EventHandler;
import me.zero.alpine.fork.listener.Listener;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.CPacketChatMessage;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WurstplusAntiRacist extends WurstplusHack {

    /*
     *    Updated by NathanW because we need to end racism on anarchy servers
     */

    public WurstplusAntiRacist() {
        super(WurstplusCategory.WURSTPLUS_CHAT);

        this.name = "Anti Racist but also random spam";
        this.tag = "AntiRacist but also random spam";
        this.description = "i love black squares (circles on the other hand...)";
    }

    WurstplusSetting delay = create("Delay", "AntiRacistDelay", 10, 0, 100);
    WurstplusSetting anti_nword = create("AntiNword", "AntiRacismAntiNword", true);
    WurstplusSetting chanter = create("Chanter", "AntiRacismChanter", false);

    List<String> chants = new ArrayList<>();

    Random r = new Random();
    int tick_delay;

    @Override
    protected void enable() {
        tick_delay = 0;

        chants.add("hello <player>!");
        chants.add("i am gaming!");
        chants.add("cheezhack?");
        chants.add("pog");
        chants.add("#what is cheezhack? sounds based");
        chants.add("where do i get cheezhack? it sounds super cool!");
        chants.add("this is a certified bruh moment");
        chants.add("Hey, cheeziest here. Currently I am coding this client during online school, and I'm very tired. Right now, I'm working on autotext!");
        chants.add("<player> is my best friend!");
        chants.add("get cheezhack for these cool funny messages!");
        chants.add("how do i commit code on github");
        chants.add("online school is boring so i am coding my client autotext");
    }

    String[] random_correction = {
            "Yuo jstu got nea nae'd by worst+2",
            "Wurst+2 just stopped me from saying something racially incorrect!",
            "<Insert nword word here>",
            "Im an edgy teenager and saying the nword makes me feel empowered on the internet.",
            "My mom calls me a late bloomer",
            "I really do think im funny",
            "Niger is a great county, I do say so myself",
            "Mommy and daddy are wrestling in the bedroom again so im going to play minecraft until their done",
            "How do you open the impact GUI?",
            "What time does FitMC do his basehunting livestreams?"
    };


    CharSequence nigger = "nigger";
    CharSequence nigga = "nigga";

    @Override
    public void update() {

        if(chanter.get_value(true)) {

            tick_delay++;

            if (tick_delay < delay.get_value(1) * 10) return;

            String s = chants.get(r.nextInt(chants.size()));
            String name = get_random_name();

            if (name.equals(mc.player.getName())) return;

            mc.player.sendChatMessage(s.replace("<player>", name));
            tick_delay = 0;

            }
        }

    public String get_random_name() {

            List<EntityPlayer> players = mc.world.playerEntities;
            return players.get(r.nextInt(players.size())).getName();
        }


    public String random_string(String[] list) {
        return list[r.nextInt(list.length)];
    }

    // Anti n-word

    @EventHandler
    private Listener<WurstplusEventPacket.SendPacket> listener = new Listener<>(event -> {

        if (!(event.get_packet() instanceof CPacketChatMessage)) {
            return;
        }

        if(anti_nword.get_value(true)) {

            String message = ((CPacketChatMessage) event.get_packet()).getMessage().toLowerCase();

            if (message.contains(nigger) || message.contains(nigga)) {

                String x = Integer.toString((int) (mc.player.posX));
                String z = Integer.toString((int) (mc.player.posZ));

                String coords = x + " " + z;

                message = (random_string(random_correction));
                mc.player.connection.sendPacket(new CPacketChatMessage("Hi, im at " + coords + ", come teach me a lesson about racism"));

            }

            ((CPacketChatMessage) event.get_packet()).message = message;
        }
    });


}
