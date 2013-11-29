package ssx;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
        
        
        //sprinkle magic dust
        props.entModel = ShapeShifterX.proxy.modelMap.get(clz);
        props.entRender = ShapeShifterX.proxy.renderMap.get(clz);
        
        //spin up a copy of the entity that is essentially treated like a puppet
        //during the PlayerTick even in the TickHandlerClient
        //it copies the state of the player onto this entity 
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