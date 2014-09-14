package com.yhamp.ssz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;

public class CommonProxy 
{
	public void initMod(){}

	public void initPostMod()
	{
	        //ok so this loops through all entity
	        //and creates the array of entities that are
	        //compatible with EntityLivingBase
		Iterator ite = EntityList.classToStringMapping.entrySet().iterator();
		while(ite.hasNext())
		{
			Entry e = (Entry)ite.next();
			Class clz = (Class)e.getKey();
			if(EntityLivingBase.class.isAssignableFrom(clz) && !compatibleEntities.contains(clz))
			{
				compatibleEntities.add(clz);
			}
		}
		compatibleEntities.add(EntityPlayer.class);
	}

	//these hold the magic
	public ArrayList<Class> compatibleEntities = new ArrayList<Class>();
	public HashMap<Class, Render> renderMap = new HashMap<Class, Render>();
	public HashMap<Class, ModelBase> modelMap = new HashMap<Class, ModelBase>();
}