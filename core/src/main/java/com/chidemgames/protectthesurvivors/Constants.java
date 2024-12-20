package com.chidemgames.protectthesurvivors;

public class Constants {

	public static final short CATEGORY_BUILDER = 0x0001;
	public static final short CATEGORY_TOWER = 0x0002;
	public static final short CATEGORY_PLATFORM = 0x0003;
	public static final short CATEGORY_WORLD_ENTITY = 0x0004;
	public static final short CATEGORY_WEAPON = 0x0005;
	public static final short CATEGORY_BULLET = 0x0006;
	public static final short CATEGORY_WALLBOX = 0x0007;
	public static final short CATEGORY_SURVIVOR = 0x0008;
	
	public static final short MASK_BUILDER = CATEGORY_TOWER | CATEGORY_WORLD_ENTITY | CATEGORY_SURVIVOR;
	public static final short MASK_TOWER = CATEGORY_BUILDER | CATEGORY_WORLD_ENTITY | CATEGORY_PLATFORM;
	public static final short MASK_PLATFORM = CATEGORY_TOWER | CATEGORY_WORLD_ENTITY;
	public static final short MASK_WEAPON = 0;
	public static final short MASK_BULLET = CATEGORY_WORLD_ENTITY | CATEGORY_BUILDER;
	public static final short MASK_SURVIVOR = CATEGORY_SURVIVOR | CATEGORY_BUILDER | CATEGORY_WALLBOX | CATEGORY_PLATFORM;
	public static final short MASK_WALLBOX = CATEGORY_WALLBOX | CATEGORY_BUILDER | CATEGORY_SURVIVOR;
	public static final short MASK_WORLD_ENTITY = -1;
	
	public static final short GROUP_BUILDER = -1;
	public static final short GROUP_TOWER = -2;
	public static final short GROUP_PLATFORM = -3;
	public static final short GROUP_BULLET = -4;
	public static final short GROUP_WORLD_ENTITY = 1;
	
}
