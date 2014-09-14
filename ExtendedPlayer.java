package com.yhamp.ssz;

//import ssx.iChun.ModelInfo;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.common.IExtendedEntityProperties;


public class ExtendedPlayer implements IExtendedEntityProperties
{
	/*
	snagged from
	http://www.minecraftforum.net/topic/1952901-eventhandler-and-iextendedentityproperties/#entry24051513
	*/
	
	/*
	Here I create a constant EXT_PROP_NAME for this class of properties. You need a unique name for every instance of IExtendedEntityProperties you make, and doing it at the top of each class as a constant makes
	it very easy to organize and avoid typos. It's easiest to keep the same constant name in every class, as it will be distinguished by the class name: ExtendedPlayer.EXT_PROP_NAME vs. ExtendedEntity.EXT_PROP_NAME
	
	Note that a single entity can have multiple extended properties, so each property should have a unique name. Try to come up with something more unique than the tutorial example.
	*/
	public final static String EXT_PROP_NAME = "ShapeShifterX_by_zacuke";
	
	// I always include the entity to which the properties belong for easy access
	// It's final because we won't be changing which player it is
	private final EntityPlayer player;
	
	// Declare other variables you want to add here
	private int currentShape, currentShapeSpecial, currentShapeSpecial2;
	
	
	public String currentEntity = "Player";
	
	public EntityLivingBase entInstance;

	public ModelBase entModel;
	public Render entRender
	;
	/*
	The default constructor takes no arguments,
	but I put in the Entity so I can initialize the above variable 'player'
	
	Also, it's best to initialize any other variables you may have added, 
	just like in any constructor.
	*/
	public ExtendedPlayer(EntityPlayer var1)
	{
		player = var1;
	//	setCurrentShape(currentShapeSpecial = currentShapeSpecial2 = 0);
	}
	
	/**
	* Used to register these extended properties for the player during EntityConstructing event
	* This method is for convenience only; it will make your code look nicer
	*/
	public static final void register(EntityPlayer player)
	{
		player.registerExtendedProperties(ExtendedPlayer.EXT_PROP_NAME, new ExtendedPlayer(player));
	}
	
	/**
	* Returns ExtendedPlayer properties for player
	* This method is for convenience only; it will make your code look nicer
	*/
	public static final ExtendedPlayer get(EntityPlayer player)
	{
		return (ExtendedPlayer) player.getExtendedProperties(EXT_PROP_NAME);
	}
	
	
	// Save any custom data that needs saving here
	@Override
	public void saveNBTData(NBTTagCompound compound)
	{
		// We need to create a new tag compound that will save everything for our Extended Properties
		NBTTagCompound properties = new NBTTagCompound();
		
		// save   to the new tag
		properties.setInteger("currentShape", currentShape);
		properties.setInteger("currentShapeSpecial", this.currentShapeSpecial);
		properties.setInteger("currentShapeSpecial2", this.currentShapeSpecial2);
		properties.setString("currentEntity", this.currentEntity);
		
		/*
		Now add our custom tag to the player's tag with a unique name (our property's name). 
		This will allow you to save multiple types of properties and distinguish between them. 
		If you only have one type, it isn't as important, 
		but it will still avoid conflicts between your tag names and vanilla tag names.
		For instance, if you add some "Items" tag, that will conflict with vanilla. 
		Not good. So just use a unique tag name.
		*/
		compound.setTag(EXT_PROP_NAME, properties);
	}
	
	// Load whatever data you saved
	@Override
	public void loadNBTData(NBTTagCompound compound)
	{
		
		// Just so you know it's working, add this line:
		//System.out.println("[Debug] shape shifter x: " + this.currentShape + "/" + this.currentShapeSpecial + "/" + this.currentShapeSpecial2);

		// Here we fetch the unique tag compound we set for this class of Extended Properties
		NBTTagCompound properties = (NBTTagCompound) compound.getTag(EXT_PROP_NAME);
		// Get our data from the custom tag compound
		//this.setCurrentShape(properties.getInteger("currentShape"));
		this.currentShapeSpecial = properties.getInteger("currentShapeSpecial");
		this.currentShapeSpecial2 = properties.getInteger("currentShapeSpecial2");
		}
	
	
	@Override
	public void init(Entity entity, World world)
	{
	}

//	public void setValues(int paramCurrentShape, int paramCurrentShapeSpecial, int paramCurrentShapeSpecial2 )
//	{
//		// Just so you know it's working, add this line:
//		System.out.println("[Debug] shape shifter x: " + this.getCurrentShape() + "/" + this.currentShapeSpecial + "/" + this.currentShapeSpecial2);
//
//		setCurrentShape(paramCurrentShape); 
//		currentShapeSpecial = paramCurrentShapeSpecial;
//		currentShapeSpecial2 = paramCurrentShapeSpecial2;
//	}

//	public int getCurrentShape() {
//		return currentShape;
//	}
//	currentShape
//	public ShapeTypes getCurrentShape() {
//		return currentShapeAsType;
//	}
//	public int getCurrentShapeSpecial() {
//		return currentShapeSpecial;
//	}
//	public int getCurrentShapeSpecial2() {
//		return currentShapeSpecial2;
//	}
//	public void setCurrentShape(int currentShape) {
//		this.currentShape = currentShape;
//		currentShapeAsType = ShapeTypes.fromInt(currentShape);
//	}
//	
//
//	public void toggleCurrentShape(boolean direction )
//	{
//		// Just so you know it's working, add this line:
//		System.out.println("[Debug] shape shifter x: toggle " + this.getCurrentShape());
//
//		if (direction)
//		{
//			if (currentShape == 0)
//				currentShape = maxShape;
//			else				
//				currentShape-=1;
//		}
//		else
//		{
//			if (currentShape == maxShape)
//				currentShape = 0;
//			else				
//				currentShape+=1;
//		}
//		currentShapeAsType = ShapeTypes.fromInt(currentShape);
//	}
	
	//chicken fields
    public float field_70886_e = 0.0F;
    public float destPos = 0.0F;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

	public int chickenHasDoubleJumped;


}