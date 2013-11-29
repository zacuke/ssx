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
//
//	public void worldTick(WorldServer world)
//	{
//		if(clock != world.getWorldTime())
//		{
//			clock = world.getWorldTime();
//
////			for(int i = 0 ; i < world.loadedEntityList.size(); i++)
////			{
////				if(world.loadedEntityList.get(i) instanceof EntityCow)
////				{
////					((EntityCow)world.loadedEntityList.get(i)).setDead();
////				}
////			}
//
//			if(world.provider.dimensionId == 0)
//			{
//				Iterator<Entry<String, MorphInfo>> ite = playerMorphInfo.entrySet().iterator();
//				while(ite.hasNext())
//				{
//					Entry<String, MorphInfo> e = ite.next();
//					MorphInfo info = e.getValue();
//
//					EntityPlayer player = world.getPlayerEntityByName(info.playerName);
//
//					if(info.getMorphing())
//					{
//						info.morphProgress++;
//						if(info.morphProgress > 80)
//						{
//							info.morphProgress = 80;
//							info.setMorphing(false);
//
//							if(player != null)
//							{
//								ObfHelper.forceSetSize(player, info.nextState.entInstance.width, info.nextState.entInstance.height);
//								player.setPosition(player.posX, player.posY, player.posZ);
//							}
//
//							if(info.nextState.playerMorph.equalsIgnoreCase(e.getKey()))
//							{
//								ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//								DataOutputStream stream = new DataOutputStream(bytes);
//								try
//								{
//									stream.writeUTF(e.getKey());
//
//									PacketDispatcher.sendPacketToAllPlayers(new Packet131MapData((short)Morph.getNetId(), (short)1, bytes.toByteArray()));
//								}
//								catch(IOException e1)
//								{
//
//								}
//								catch(Exception e1)
//								{
//									ObfHelper.obfWarning();
//									e1.printStackTrace();
//								}
//
//								ite.remove();
//							}
//						}
//					}
//				}
//
////				ArrayList<MorphState> states = getPlayerMorphs(world, "ohaiiChun");
////				for(MorphState state : states)
////				{
////					System.out.println(state.identifier);
////				}
//			}
//		}
//	}
//
//	public void prePlayerTick(WorldServer world, EntityPlayerMP player)
//	{
//		MorphInfo info = playerMorphInfo.get(player.username);
//		if(info != null)
//		{
//		}
//
//	}
//
//	public void playerTick(WorldServer world, EntityPlayerMP player)
//	{
//		MorphInfo info = playerMorphInfo.get(player.username);
//		if(info != null)
//		{
//			float prog = info.morphProgress > 10 ? (((float)info.morphProgress) / 60F) : 0.0F;
//			if(prog > 1.0F)
//			{
//				prog = 1.0F;
//			}
//
//			prog = (float)Math.pow(prog, 2);
//
//			float prev = info.prevState != null && !(info.prevState.entInstance instanceof EntityPlayer) ? info.prevState.entInstance.getEyeHeight() : player.yOffset;
//			float next = info.nextState != null && !(info.nextState.entInstance instanceof EntityPlayer) ? info.nextState.entInstance.getEyeHeight() : player.yOffset;
//			double ySize = player.yOffset - (prev + (next - prev) * prog);
//			player.lastTickPosY += ySize;
//			player.prevPosY += ySize;
//			player.posY += ySize;
//		}
//
//	}
//
//	public MorphState getSelfState(World world, String name)
//	{
//		ArrayList<MorphState> list = getPlayerMorphs(world, name);
//		for(MorphState state : list)
//		{
//			if(state.playerName.equalsIgnoreCase(state.playerMorph))
//			{
//				return state;
//			}
//		}
//		return new MorphState(world, name, name, null, world.isRemote);
//	}
//
//	public ArrayList<MorphState> getPlayerMorphs(World world, String name)
//	{
//		ArrayList<MorphState> list = playerMorphs.get(name);
//		if(list == null)
//		{
//			list = new ArrayList<MorphState>();
//			playerMorphs.put(name, list);
//			list.add(new MorphState(world, name, name, null, world.isRemote));
//		}
//		return list;
//	}
//
//	public boolean hasMorphState(EntityPlayer player, MorphState state)
//	{
//		ArrayList<MorphState> states = getPlayerMorphs(player.worldObj, player.username);
//		if(!state.playerMorph.equalsIgnoreCase(""))
//		{
//			for(MorphState mState : states)
//			{
//				if(mState.playerMorph.equalsIgnoreCase(state.playerMorph))
//				{
//					return true;
//				}
//			}
//		}
//		else
//		{
//			for(MorphState mState : states)
//			{
//				if(mState.identifier.equalsIgnoreCase(state.identifier))
//				{
//					return true;
//				}
//			}
//		}
//		return false;
//	}
//
//	public long clock;
//
//	public NBTTagCompound saveData;
//
//	public HashMap<String, MorphInfo> playerMorphInfo = new HashMap<String, MorphInfo>();
//	public HashMap<String, ArrayList<MorphState>> playerMorphs = new HashMap<String, ArrayList<MorphState>>();
}