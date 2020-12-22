package me.travis.wurstplus.wurstplustwo.guiscreen.hud;

import me.travis.wurstplus.wurstplustwo.guiscreen.render.pinnables.WurstplusPinnable;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class RabbitDeathCounter extends WurstplusPinnable { 


    public RabbitDeathcounter() {
        super("Rabbit Death Counter", "RabbitCounter", 1, 0, 0);
    }

    @Override
	public void render() {

		  String line = "Rabbits Killed:";
    
    if 
