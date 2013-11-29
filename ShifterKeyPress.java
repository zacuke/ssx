package ssx;

import java.util.EnumSet;
import java.util.Iterator;

import org.lwjgl.opengl.GL11;

import ssx.iChun.ModelList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

class ShifterKeyPress extends KeyHandler
{
    private EnumSet tickTypes = EnumSet.of(TickType.CLIENT);

    public ShifterKeyPress(KeyBinding[] keyBindings, boolean[] repeatings)
    {
        super(keyBindings, repeatings);
    }

    @Override
    public String getLabel()
    {
        return "ShifterKey";
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb,
            boolean tickEnd, boolean isRepeat)
    {

        EntityPlayer thePlayer;
        ExtendedPlayer props;
        Class lastOne;

        if (tickEnd == false)
        {
            // what to do when key is pressed/down
            thePlayer = Minecraft.getMinecraft().thePlayer;
            props = ExtendedPlayer.get(thePlayer);
            lastOne =  ShapeShifterX.proxy.compatibleEntities.get(ShapeShifterX.proxy.compatibleEntities.size() - 1);

            boolean doNext = false;

            if (props.currentEntity == null)
            {
                doNext = true;
            }
            

            for (Class clz : ShapeShifterX.proxy.compatibleEntities)
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
                            props.currentEntity = null;
                        }
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
        props.entModel = ModelList.getModelInfo(clz);
        props.entInstance = (EntityLivingBase) EntityList.createEntityByName(
                entityName, Minecraft.getMinecraft().theWorld);
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
    {
        // What to do when key is released/up

    }

    @Override
    public EnumSet<TickType> ticks()
    {
        return tickTypes;
    }
}