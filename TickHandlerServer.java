package ssx;



import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet131MapData;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;
import cpw.mods.fml.common.network.PacketDispatcher;

public class TickHandlerServer 
	implements ITickHandler
{
	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
//        if (type.equals(EnumSet.of(TickType.WORLD)))
//        {
//        	preWorldTick((WorldServer)tickData[0]);
//        }
//        else if (type.equals(EnumSet.of(TickType.PLAYER)))
//        {
//        	prePlayerTick((WorldServer)((EntityPlayerMP)tickData[0]).worldObj, (EntityPlayerMP)tickData[0]);
//        }
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
//        if (type.equals(EnumSet.of(TickType.WORLD)))
//        {
//        	worldTick((WorldServer)tickData[0]);
//        }
//        else if (type.equals(EnumSet.of(TickType.PLAYER)))
//        {
//        	playerTick((WorldServer)((EntityPlayerMP)tickData[0]).worldObj, (EntityPlayerMP)tickData[0]);
//        }
	}

	@Override
	public EnumSet<TickType> ticks() 
	{
		return EnumSet.of(TickType.WORLD, TickType.PLAYER);
	}

	@Override
	public String getLabel() 
	{
		return "TickHandlerServerShifter";
	}

	public void preWorldTick(WorldServer world)
	{
	}

}