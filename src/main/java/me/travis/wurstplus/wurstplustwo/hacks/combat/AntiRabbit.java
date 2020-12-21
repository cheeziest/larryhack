package me.travis.wurstplus.wurstplustwo.hacks.combat;


import me.travis.wurstplus.Wurstplus;
import me.travis.wurstplus.wurstplustwo.guiscreen.settings.WurstplusSetting;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusCategory;
import me.travis.wurstplus.wurstplustwo.hacks.WurstplusHack;
import me.travis.wurstplus.wurstplustwo.util.WurstplusFriendUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.entity.EntityRabbit

import java.util.stream.Collectors;

public class AntiRabbit extends WurstplusHack {

	public AntiRabbit() {
		super(WurstplusCategory.WURSTPLUS_COMBAT);

		this.name        = "antirabbit";
		this.tag         = "antirabbit";
		this.description = "death to rabbits";
	}
	
int frame = 0;
	@Override
	protected void enable() {
            frame = 0;
	}
	@Override
	public void update() {
            frame++;
		
            float longestdis = 5;
            Entity rabbit = null;

            //goes trough list of all entities
            for (Entity e : mc.world.loadedEntityList) {
                if (e instanceof EntityRabbit) {
                    if (mc.player.getDistanceToEntity(e) < longestdis) {
                        longestdis = mc.player.getDistanceToEntity(e);
                        rabbit = e;
                    }
                }
            }
            if (rabbit != null) {
                //if entity rabbit
                
                //looks at rabbit
                double xdiff = rabbit.posX - mc.player.posX;
                double zdiff = rabbit.posZ - mc.player.posZ;
                double ydiff = (rabbit.posY - 1.3) - (mc.player.posY + mc.player.getEyeHeight());
                double diff = MathHelper.sqrt(xdiff * xdiff + zdiff * zdiff);

                float yaw = (float) (Math.atan2(zdiff, xdiff) * 180 / Math.PI) - 90;
                float pitch = (float) -(Math.atan2(ydiff, diff) * 180 / Math.PI) - 20;
                
                ((EventMotion) event).setPitch(pitch);
                ((EventMotion) event).setYaw(yaw);

                if (delay > 12) {
                    //attack after 12 frame sword countdown thing
                    mc.player.swingArm(EnumHand.MAIN_HAND);
                    mc.playerController.attackEntity(mc.player, rabbit);
                    delay = 0;
                }
            }
