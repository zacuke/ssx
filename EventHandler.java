package ssx;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.EntityConstructing;



public class EventHandler
{

    @ForgeSubscribe
    public void onEntityConstructing(EntityConstructing event)
    {
        /*
         * Be sure to check if the entity being constructed is the correct type
         * for the extended properties you're about to add! The null check may
         * not be necessary - I only use it to make sure properties are only
         * registered once per entity
         */
        if (event.entity instanceof EntityPlayer
                && ExtendedPlayer.get((EntityPlayer) event.entity) == null)
            // This is how extended properties are registered using our
            // convenient method from earlier
            ExtendedPlayer.register((EntityPlayer) event.entity);

        // That will call the constructor as well as cause the init() method
        // to be called automatically

        // If you didn't make the two convenient methods from earlier, your code
        // would be
        // much uglier:
        // if (event.entity instanceof EntityPlayer &&
        // event.entity.getExtendedProperties(ExtendedPlayer.EXT_PROP_NAME) ==
        // null)
        // event.entity.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME,
        // new ExtendedPlayer((EntityPlayer) event.entity));
    }

    // public static RendererLivingEntity renderShifterInstance;
    @SideOnly(Side.CLIENT)
    @ForgeSubscribe
    public void onRenderPlayer(RenderPlayerEvent.Pre event)
    {

        EntityPlayer thePlayer;
        ExtendedPlayer props;

        // hide original render
        //
        // switch (ShapeShifterX.proxy.tickHandlerClient.renderPass)
        // {
        // case 0:
        // ShapeShifterX.proxy.tickHandlerClient.renderPass++;

        // return;
        //
        // case 1:
        // event.setCanceled(true);
        // break;
        // case 2:
        // ShapeShifterX.proxy.tickHandlerClient.renderPass = 1;
        // return;
        //
        // }
        //

        thePlayer = Minecraft.getMinecraft().thePlayer;
        props = ExtendedPlayer.get(thePlayer);

        if (props.currentEntity == null)
        {

            return;
        }

        // else render shape shifter
        event.setCanceled(true);
        GL11.glPushMatrix();

        // get information about this render
        float tick = ShapeShifterX.proxy.tickHandlerClient.renderTick;
        double x = event.entityPlayer.lastTickPosX
                + (event.entityPlayer.posX - event.entityPlayer.lastTickPosX)
                * (double) tick;
        double y = event.entityPlayer.lastTickPosY
                + (event.entityPlayer.posY - event.entityPlayer.lastTickPosY)
                * (double) tick;
        double z = event.entityPlayer.lastTickPosZ
                + (event.entityPlayer.posZ - event.entityPlayer.lastTickPosZ)
                * (double) tick;
        float yaw = event.entityPlayer.prevRotationYaw
                + (event.entityPlayer.rotationYaw - event.entityPlayer.prevRotationYaw)
                * (float) tick;

        // translate the player into place, since we are replacing the original
        GL11.glTranslated(1 * (x - RenderManager.renderPosX),
                1 * (y - RenderManager.renderPosY) + (0D),
                1 * (z - RenderManager.renderPosZ));

        props.entModel.forceRender(props.entInstance, 0.0D,
                0.0D - event.entityPlayer.yOffset, 0.0D, yaw, tick);

        GL11.glPopMatrix();

    }
}
