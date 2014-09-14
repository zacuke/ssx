package com.yhamp.ssz;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
//import cpw.mods.fml.common.network.FMLNetworkHandler;
//import cpw.mods.fml.common.network.NetworkMod;
//import cpw.mods.fml.common.network.NetworkModHandler;

@Mod(modid = ShapeShifterZ.modid, name = "Shape Shifter Z", version = "1.7.10.1")
public class ShapeShifterZ
{
    public static final String modid = "zacuke_shapeshifterz";

    // public static ItemStack tutorialBlock;
    public static final String version = "1.7.10.1";
    // The instance of your mod that Forge uses.
    @Instance("Shifter")
    public static ShapeShifterZ instance;
    private static Logger logger;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "com.yhamp.ssz.ClientProxy", serverSide = "com.yhamp.ssz.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
    	FMLCommonHandler.instance().bus().register(new com.yhamp.ssz.EventsFML());
    	MinecraftForge.EVENT_BUS.register(new com.yhamp.ssz.EventsMinecraftForge());
    	//FMLCommonHandler.instance().bus().register(new com.yhamp.ssz.EventHandler());
 }

    @EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {

    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {

    	proxy.initMod();

    }

    @EventHandler
    public void postLoad(FMLPostInitializationEvent event)
    {
        proxy.initPostMod();
    }

    @EventHandler
    public void serverStopped(FMLServerStoppedEvent event)
    {

    }

    public static void console(String s, boolean warning)
    {
        StringBuilder sb = new StringBuilder();
        logger.log(warning ? Level.WARNING : Level.INFO,
                sb.append("[").append(version).append("] ").append(s)
                        .toString());
    }

//    public static int getNetId()
//    {
//        return ((NetworkModHandler) FMLNetworkHandler.instance()
//                .findNetworkModHandler(ShapeShifterZ.instance)).getNetworkId();
//    }

}
