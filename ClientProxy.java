package ssx;


import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelPig;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.common.registry.TickRegistry;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{

	@Override
	public void initMod()
	{
	//	RenderingRegistry.registerEntityRenderingHandler(EntityPlayer.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
	
		//RenderingRegistry.registerEntityRenderingHandler(EntityLivingBase.class, Shifter.proxy.tickHandlerClient.renderShifterInstance);
		//RenderingRegistry.registerEntityRenderingHandler(EntityLivingBase.class, Shifter.proxy.tickHandlerClient.renderShifterInstance);
	}
	
	
	
	@Override
	public void initPostMod()
	{
		super.initPostMod();

		HashMap<Class, RendererLivingEntity> renders = new HashMap<Class, RendererLivingEntity>();
		try
		{
			List entityRenderers = (List)ObfuscationReflectionHelper.getPrivateValue(RenderingRegistry.class, RenderingRegistry.instance(), "entityRenderers");

			for(Object obj : entityRenderers)
			{
				Field[] fields = obj.getClass().getDeclaredFields();
				Render render = null;
				Class clzz = null;
				for(Field f : fields)
				{
					f.setAccessible(true);
					if(f.getType() == Render.class)
					{
						render = (Render)f.get(obj);
					}
					else if(f.getType() == Class.class)
					{
						clzz = (Class)f.get(obj);
					}
				}
				if(render instanceof RendererLivingEntity && clzz != null)
				{
					renders.put(clzz, (RendererLivingEntity)render);
				}
			}
		}
		catch(Exception e)
		{
		}

		for(int i = compatibleEntities.size() - 1; i >= 0; i--)
		{
			Render rend = RenderManager.instance.getEntityClassRenderObject(compatibleEntities.get(i));
			if(rend.getClass() == RenderEntity.class)
			{
				rend = renders.get(compatibleEntities.get(i));
			}
			if(!(rend instanceof RendererLivingEntity))
			{
				compatibleEntities.remove(i);
				continue;
			}
			if(!renders.containsKey(compatibleEntities.get(i)))
			{
				renders.put(compatibleEntities.get(i), (RendererLivingEntity)rend);
			}
		}

		for(Class clz : compatibleEntities)
		{
			try
			{
				RendererLivingEntity rend = (RendererLivingEntity)renders.get(clz);
				ModelList.addModelInfo(clz, new ModelInfo(clz, rend, (ModelBase)ObfuscationReflectionHelper.getPrivateValue(RendererLivingEntity.class, rend, ObfHelper.mainModel)));
			}
			catch(Exception e)
			{
				ObfHelper.obfWarning();
				e.printStackTrace();
			}
		}
	}

	@Override
	public void initTickHandlers()
	{
		super.initTickHandlers();
		tickHandlerClient = new TickHandlerClient();
		TickRegistry.registerTickHandler(tickHandlerClient, Side.CLIENT);
	}

}