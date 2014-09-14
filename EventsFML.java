package com.yhamp.ssz;

import java.util.EnumSet;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class EventsFML
{
	/** Storing an instance of Minecraft in a local variable saves having to get it every time */
	private final Minecraft mc;
	
	/** Key index for easy handling */
	public static final int CUSTOM_INV = 0;

	/** Key descriptions; use a language file to localize the description later */
	private static final String[] desc = {"key.debug.desc"};

	/** Default key values */
	private static final int[] keyValues = {Keyboard.KEY_G};

	/** Make this public or provide a getter if you'll need access to the key bindings from elsewhere */
	public static final KeyBinding[] keys = new KeyBinding[desc.length];

	public EventsFML() {
		mc = Minecraft.getMinecraft();
		for (int i = 0; i < desc.length; ++i) {
			keys[i] = new KeyBinding(desc[i], keyValues[i], StatCollector.translateToLocal("key.debug.label"));
			ClientRegistry.registerKeyBinding(keys[i]);
		}
	}

	/**
	 * KeyInputEvent is in the FML package, so we must register to the FML event bus
	 */
	@SubscribeEvent
	public void onKeyInput(KeyInputEvent event) {
		// checking inGameHasFocus prevents your keys from firing when the player is typing a chat message
		// NOTE that the KeyInputEvent will NOT be posted when a gui screen such as the inventory is open
		// so we cannot close an inventory screen from here; that should be done in the GUI itself
		if (mc.inGameHasFocus) {
			if (keys[CUSTOM_INV].getIsKeyPressed()) {
//				PacketDispatcher.sendToServer(new OpenGuiMessage(TutorialMain.GUI_CUSTOM_INV));
				ProcessGKey(event);
			}
		}
	}

	public void ProcessGKey(KeyInputEvent event) {


        EntityPlayer thePlayer;
        ExtendedPlayer props;
        Class lastOne;

        // what to do when key is pressed/down
        thePlayer = Minecraft.getMinecraft().thePlayer;
        props = ExtendedPlayer.get(thePlayer);
        lastOne =  ShapeShifterZ.proxy.compatibleEntities.get(ShapeShifterZ.proxy.compatibleEntities.size() - 1);

        boolean doNext = false;

        if ( props.currentEntity == "Player")
        {
            doNext = true;
        }
        

        for (Class clz : ShapeShifterZ.proxy.compatibleEntities)
        {
            if (doNext)
            {
                setPlayerShift(props, clz);
                break;
            }
            else
            {
                if ((String) EntityList.classToStringMapping.get(clz) == props.currentEntity)
                {
                    doNext = true;  
                    if (clz == lastOne)
                    {
                        props.currentEntity = "Player";
                    }
                }
              
            }
        }
	}      


    private void setPlayerShift(ExtendedPlayer props, Class clz)
    {
        String entityName;
        entityName = (String) EntityList.classToStringMapping.get(clz);
        props.currentEntity = entityName;
        
        
        //sprinkle magic dust
        props.entModel = ShapeShifterZ.proxy.modelMap.get(clz);
        props.entRender = ShapeShifterZ.proxy.renderMap.get(clz);
        
        //spin up a copy of the entity that is essentially treated like a puppet
        //during the onRenderTick event
        //it copies the state of the player onto this entity 
        props.entInstance = (EntityLivingBase) EntityList.createEntityByName(
                entityName, Minecraft.getMinecraft().theWorld);

    }
    

	// Called whenever the player is updated or ticked.
	@SubscribeEvent
	public void onPlayerTick(TickEvent.PlayerTickEvent event) {

	}

	// Called when the client ticks.
	@SubscribeEvent
	public void onClientTick(TickEvent.ClientTickEvent event) {

	}

	// Called when the server ticks. Usually 20 ticks a second.
	@SubscribeEvent
	public void onServerTick(TickEvent.ServerTickEvent event) {

	}

	// Called when a new frame is displayed (See fps)
	@SubscribeEvent
	public void onRenderTick(TickEvent.RenderTickEvent event) {

		Minecraft mc = Minecraft.getMinecraft();
		// float renderTick;

		if (mc.theWorld != null) {
			if (event.phase == TickEvent.Phase.START) {
				//renderTick = event.renderTickTime;

				EntityPlayer player;
				ExtendedPlayer props;
			

				player = Minecraft.getMinecraft().thePlayer;
				props = ExtendedPlayer.get(player);

				if (props.entInstance == null || props.currentEntity == null) {
					return;
				}

				//EntityLivingBase renderView = player;

				props.entInstance.noClip = true;

				// here is the "puppeteer" code
				props.entInstance.onGround = player.onGround;
				props.entInstance.isAirBorne = player.isAirBorne;
				props.entInstance.motionX = player.motionX;
				props.entInstance.motionY = player.motionY;
				props.entInstance.motionZ = player.motionZ;

				props.entInstance.lastTickPosY -= player.yOffset;
				props.entInstance.prevPosY -= player.yOffset;
				props.entInstance.posY -= player.yOffset;

				// this is critical to getting the legs/arms/wings to move
			//	props.entInstance.onUpdate();

				props.entInstance.lastTickPosY += player.yOffset;
				props.entInstance.prevPosY += player.yOffset;
				props.entInstance.posY += player.yOffset;

				props.entInstance.prevRotationYawHead = player.prevRotationYawHead;
				props.entInstance.prevRotationYaw = player.prevRotationYaw;
				props.entInstance.prevRotationPitch = player.prevRotationPitch;
				props.entInstance.prevRenderYawOffset = player.prevRenderYawOffset;
				props.entInstance.prevLimbSwingAmount = player.prevLimbSwingAmount;
				props.entInstance.prevSwingProgress = player.prevSwingProgress;
				props.entInstance.prevPosX = player.prevPosX;
				props.entInstance.prevPosY = player.prevPosY;
				props.entInstance.prevPosZ = player.prevPosZ;

				props.entInstance.rotationYawHead = player.rotationYawHead;
				props.entInstance.rotationYaw = player.rotationYaw;
				props.entInstance.rotationPitch = player.rotationPitch;
				props.entInstance.renderYawOffset = player.renderYawOffset;
				props.entInstance.limbSwingAmount = player.limbSwingAmount;
				props.entInstance.swingProgress = player.swingProgress;
				props.entInstance.limbSwing = player.limbSwing;
				props.entInstance.posX = player.posX;
				props.entInstance.posY = player.posY;
				props.entInstance.posZ = player.posZ;

				props.entInstance.ticksExisted = player.ticksExisted;

				props.entInstance.moveStrafing = player.moveStrafing;
				props.entInstance.moveForward = player.moveForward;
				props.entInstance.dimension = player.dimension;
				props.entInstance.worldObj = player.worldObj;
				props.entInstance.ridingEntity = player.ridingEntity;
				props.entInstance.hurtTime = player.hurtTime;
				props.entInstance.deathTime = player.deathTime;
				props.entInstance.isSwingInProgress = player.isSwingInProgress;

				props.entInstance.setSneaking(player.isSneaking());
				// nextState.entInstance.setSneaking(player.isSneaking());
				props.entInstance.setSprinting(player.isSprinting());
				props.entInstance.setInvisible(player.isInvisible());
				props.entInstance.setHealth(props.entInstance.getMaxHealth()
						* (player.getHealth() / player.getMaxHealth()));

				props.entInstance.yOffset = player.yOffset;
			}
		}

	}

	// Called when the world ticks
	@SubscribeEvent
	public void onWorldTick(TickEvent.WorldTickEvent event) {

	}

}