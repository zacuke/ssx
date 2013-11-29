package ssx;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngameMenu;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderSpider;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet131MapData;
//import net.minecraft.src.EntityRendererProxy;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;
import net.minecraft.client.renderer.entity.RenderManager;

public class TickHandlerClient 
	implements ITickHandler
{
	//public RendererLivingEntity renderShifterInstance;


	public TickHandlerClient()
	{
	//	renderShifterInstance = new ShifterRendererLivingEntity(new ModelPig(), 0.7F);
	//	renderShifterInstance.setRenderManager(RenderManager.instance);
	}

//
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if (type.equals(EnumSet.of(TickType.CLIENT)))
		{
			if(Minecraft.getMinecraft().theWorld != null)
			{      		
				preWorldTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld);
			}
		}
		else if (type.equals(EnumSet.of(TickType.RENDER)))
		{
			if(Minecraft.getMinecraft().theWorld != null)
			{
				preRenderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, (Float)tickData[0]); //only ingame
			}
		}
	}
//
	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if (type.equals(EnumSet.of(TickType.CLIENT)))
		{
			if(Minecraft.getMinecraft().theWorld != null)
			{      		
				worldTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld);
			}
		}
		else if (type.equals(EnumSet.of(TickType.PLAYER)))
		{
			playerTick((World)((EntityPlayer)tickData[0]).worldObj, (EntityPlayer)tickData[0]);
		}
		else if (type.equals(EnumSet.of(TickType.RENDER)))
		{
			if(Minecraft.getMinecraft().theWorld != null)
			{
				renderTick(Minecraft.getMinecraft(), Minecraft.getMinecraft().theWorld, (Float)tickData[0]); //only ingame
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.CLIENT, TickType.PLAYER, TickType.RENDER);
	}

	@Override
	public String getLabel() 
	{
		return "TickHandlerClientShape";
	}

	public void preWorldTick(Minecraft mc, WorldClient world)
	{

	}

	public void worldTick(Minecraft mc, WorldClient world)
	{
	
	}

	public void playerTick(World world, EntityPlayer player)
	{
	
		EntityPlayer thePlayer;
		ExtendedPlayer props;

		

		thePlayer = Minecraft.getMinecraft().thePlayer;
		props = ExtendedPlayer.get(thePlayer);

		if (props.currentEntity == null)
		{	
			return;
		}
	
		
		EntityLivingBase renderView = player;

		props.entInstance.noClip = true;
		props.entInstance.onGround = player.onGround;
		props.entInstance.isAirBorne = player.isAirBorne;
		props.entInstance.motionX = player.motionX;
		props.entInstance.motionY = player.motionY;
		props.entInstance.motionZ = player.motionZ;
		
		props.entInstance.lastTickPosY -= player.yOffset;
		props.entInstance.prevPosY -= player.yOffset;
		props.entInstance.posY -= player.yOffset;
		props.entInstance.onUpdate();
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
		//nextState.entInstance.setSneaking(player.isSneaking());
		props.entInstance.setSprinting(player.isSprinting());
		props.entInstance.setInvisible(player.isInvisible());
		props.entInstance.setHealth(props.entInstance.getMaxHealth() * (player.getHealth() / player.getMaxHealth()));
		

		props.entInstance.yOffset = player.yOffset;
		
		
	}


	public void preRenderTick(Minecraft mc, World world, float renderTick)
	{
		this.renderTick = renderTick;
//
//		MorphInfoClient info1 = playerMorphInfo.get(mc.thePlayer.username);
//		if(info1 != null )
//		{
//			float prog = info1.morphProgress > 10 ? (((float)info1.morphProgress + renderTick) / 60F) : 0.0F;
//			if(prog > 1.0F)
//			{
//				prog = 1.0F;
//			}
//
//			prog = (float)Math.pow(prog, 2);
//
//			float prev = info1.prevState != null && !(info1.prevState.entInstance instanceof EntityPlayer) ? info1.prevState.entInstance.getEyeHeight() : mc.thePlayer.yOffset;
//			float next = info1.nextState != null && !(info1.nextState.entInstance instanceof EntityPlayer) ? info1.nextState.entInstance.getEyeHeight() : mc.thePlayer.yOffset;
//			ySize = mc.thePlayer.yOffset - (prev + (next - prev) * prog);
//			mc.thePlayer.lastTickPosY -= ySize;
//			mc.thePlayer.prevPosY -= ySize;
//			mc.thePlayer.posY -= ySize;
//
//		}
//
//		for(Entry<String, MorphInfoClient> e : playerMorphInfo.entrySet())
//		{
//			MorphInfoClient info = e.getValue();
//
//			if(info.player != null)
//			{
//				if(info.prevState.entInstance == null)
//				{
//					info.prevState.entInstance = info.player;
//				}
//				if(info.prevState.entInstance != null && info.nextState.entInstance != null)
//				{
//					info.player.ignoreFrustumCheck = true;
//
//					info.prevState.entInstance.prevRotationYawHead = info.nextState.entInstance.prevRotationYawHead = info.player.prevRotationYawHead;
//					info.prevState.entInstance.prevRotationYaw = info.nextState.entInstance.prevRotationYaw = info.player.prevRotationYaw;
//					info.prevState.entInstance.prevRotationPitch = info.nextState.entInstance.prevRotationPitch = info.player.prevRotationPitch;
//					info.prevState.entInstance.prevRenderYawOffset = info.nextState.entInstance.prevRenderYawOffset = info.player.prevRenderYawOffset;
//					info.prevState.entInstance.prevLimbYaw = info.nextState.entInstance.prevLimbYaw = info.player.prevLimbYaw;
//					info.prevState.entInstance.prevSwingProgress = info.nextState.entInstance.prevSwingProgress = info.player.prevSwingProgress;
//					info.prevState.entInstance.prevPosX = info.nextState.entInstance.prevPosX = info.player.prevPosX;
//					info.prevState.entInstance.prevPosY = info.nextState.entInstance.prevPosY = info.player.prevPosY;
//					info.prevState.entInstance.prevPosZ = info.nextState.entInstance.prevPosZ = info.player.prevPosZ;
//
//					info.prevState.entInstance.rotationYawHead = info.nextState.entInstance.rotationYawHead = info.player.rotationYawHead;
//					info.prevState.entInstance.rotationYaw = info.nextState.entInstance.rotationYaw = info.player.rotationYaw;
//					info.prevState.entInstance.rotationPitch = info.nextState.entInstance.rotationPitch = info.player.rotationPitch;
//					info.prevState.entInstance.renderYawOffset = info.nextState.entInstance.renderYawOffset = info.player.renderYawOffset;
//					info.prevState.entInstance.limbYaw = info.nextState.entInstance.limbYaw = info.player.limbYaw;
//					info.prevState.entInstance.swingProgress = info.nextState.entInstance.swingProgress = info.player.swingProgress;
//					info.prevState.entInstance.limbSwing = info.nextState.entInstance.limbSwing = info.player.limbSwing;
//					info.prevState.entInstance.posX = info.nextState.entInstance.posX = info.player.posX;
//					info.prevState.entInstance.posY = info.nextState.entInstance.posY = info.player.posY;
//					info.prevState.entInstance.posZ = info.nextState.entInstance.posZ = info.player.posZ;
//					info.prevState.entInstance.motionX = info.nextState.entInstance.motionX = info.player.motionX;
//					info.prevState.entInstance.motionY = info.nextState.entInstance.motionY = info.player.motionY;
//					info.prevState.entInstance.motionZ = info.nextState.entInstance.motionZ = info.player.motionZ;
//					info.prevState.entInstance.ticksExisted = info.nextState.entInstance.ticksExisted = info.player.ticksExisted;
//					info.prevState.entInstance.onGround = info.nextState.entInstance.onGround = info.player.onGround;
//					info.prevState.entInstance.isAirBorne = info.nextState.entInstance.isAirBorne = info.player.isAirBorne;
//					info.prevState.entInstance.moveStrafing = info.nextState.entInstance.moveStrafing = info.player.moveStrafing;
//					info.prevState.entInstance.moveForward = info.nextState.entInstance.moveForward = info.player.moveForward;
//					info.prevState.entInstance.dimension = info.nextState.entInstance.dimension = info.player.dimension;
//					info.prevState.entInstance.worldObj = info.nextState.entInstance.worldObj = info.player.worldObj;
//					info.prevState.entInstance.ridingEntity = info.nextState.entInstance.ridingEntity = info.player.ridingEntity;
//					info.prevState.entInstance.setSneaking(info.player.isSneaking());
//					info.nextState.entInstance.setSneaking(info.player.isSneaking());
//					info.prevState.entInstance.setSprinting(info.player.isSprinting());
//					info.nextState.entInstance.setSprinting(info.player.isSprinting());
//					info.prevState.entInstance.setInvisible(info.player.isInvisible());
//					info.nextState.entInstance.setInvisible(info.player.isInvisible());
//
//					for(int i = 0; i < 5; i++)
//					{
//						if(info.nextState.entInstance.getCurrentItemOrArmor(i) == null && info.player.getCurrentItemOrArmor(i) != null || 
//								info.nextState.entInstance.getCurrentItemOrArmor(i) != null && info.player.getCurrentItemOrArmor(i) == null || 
//										info.nextState.entInstance.getCurrentItemOrArmor(i) != null && info.player.getCurrentItemOrArmor(i) != null && 
//											!info.nextState.entInstance.getCurrentItemOrArmor(i).isItemEqual(info.player.getCurrentItemOrArmor(i)))
//						{
//							info.nextState.entInstance.setCurrentItemOrArmor(i, info.player.getCurrentItemOrArmor(i) != null ? info.player.getCurrentItemOrArmor(i).copy() : null);
//						}
//					}
//				}
//			}
//		}

	}
//
	public void renderTick(Minecraft mc, World world, float renderTick)
	{
	}
//	public HashMap<String, EntityLivingBase> playerShifterInfo = new HashMap<String, EntityLivingBase>();

	public float renderTick;
//	public boolean renderingshift;
//	public byte renderPass;

//	public boolean renderingSsx;
	
//	public EntityLivingBase entInstance;

	//public ModelInfo entModel;
	
}