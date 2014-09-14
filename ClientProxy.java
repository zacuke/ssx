package com.yhamp.ssz;


import java.lang.reflect.Field;
import java.util.List;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererLivingEntity;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.ObfuscationReflectionHelper;
import cpw.mods.fml.relauncher.Side;

public class ClientProxy extends CommonProxy
{
        public static final String[] mainModel  = new String[] { "i", "field_77045_g", "mainModel" }; //RendererLivingEntity
    
	@Override
	public void initMod()
	{
	        //i think this is where you register render
	        //for any new entity
	    
	        //RenderingRegistry.registerEntityRenderingHandler(EntityLivingBase.class, new RenderPig(new ModelPig(), new ModelPig(0.5F), 0.7F));
		//RenderingRegistry.registerEntityRenderingHandler(EntityLivingBase.class, Shifter.proxy.tickHandlerClient.renderShifterInstance);
		//RenderingRegistry.registerEntityRenderingHandler(EntityLivingBase.class, Shifter.proxy.tickHandlerClient.renderShifterInstance);
	}
	
	
	
	@Override
	public void initPostMod()
	{
		super.initPostMod();

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
				    renderMap.put(clzz, (RendererLivingEntity)render);
				}
			}
		}
		catch(Exception e)
		{		
                    e.printStackTrace();
		}

		for(int i = compatibleEntities.size() - 1; i >= 0; i--)
		{
			Render rend = RenderManager.instance.getEntityClassRenderObject(compatibleEntities.get(i));
			if(rend.getClass() == RenderEntity.class)
			{
				rend = renderMap.get(compatibleEntities.get(i));
			}
			if(!(rend instanceof RendererLivingEntity))
			{
				compatibleEntities.remove(i);
				continue;
			}
			if(!renderMap.containsKey(compatibleEntities.get(i)))
			{
			    renderMap.put(compatibleEntities.get(i), (RendererLivingEntity)rend);
			}
		}

		for(Class clz : compatibleEntities)
		{
			try
			{
				RendererLivingEntity rend = (RendererLivingEntity)renderMap.get(clz);
				modelMap.put(clz,  (ModelBase)ObfuscationReflectionHelper.getPrivateValue(RendererLivingEntity.class, rend, mainModel));
			}
			catch(Exception e)
			{			
				e.printStackTrace();
			}
		}
	}

	
}