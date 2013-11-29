package ssx;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStoppedEvent;
import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkModHandler;

@Mod(modid = ShapeShifterX.modid, name = "Shape Shifter X", version = "0.1")
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class ShapeShifterX
{
    public static final String modid = "zacuke_shapeshifterx";

    // public static ItemStack tutorialBlock;
    public static final String version = "0.1";
    // The instance of your mod that Forge uses.
    @Instance("Shifter")
    public static ShapeShifterX instance;
    private static Logger logger;

    // Says where the client and server 'proxy' code is loaded.
    @SidedProxy(clientSide = "ssx.ClientProxy", serverSide = "ssx.CommonProxy")
    public static CommonProxy proxy;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        // Stub Method
    }

    @EventHandler
    public void preLoad(FMLPreInitializationEvent event)
    {
        MinecraftForge.EVENT_BUS.register(new ssx.EventHandler());
    }

    @EventHandler
    public void load(FMLInitializationEvent event)
    {

        proxy.initTickHandlers();
        proxy.initMod();

        KeyBinding[] key =
        { new KeyBinding("Shifter", Keyboard.KEY_G) };
        boolean[] repeat =
        { false };
        KeyBindingRegistry.registerKeyBinding(new ShifterKeyPress(key, repeat));
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

    public static int getNetId()
    {
        return ((NetworkModHandler) FMLNetworkHandler.instance()
                .findNetworkModHandler(ShapeShifterX.instance)).getNetworkId();
    }

}
