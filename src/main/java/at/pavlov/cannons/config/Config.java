package at.pavlov.cannons.config;


import at.pavlov.cannons.Cannons;
import at.pavlov.cannons.cannon.CannonManager;
import at.pavlov.cannons.cannon.DesignStorage;
import at.pavlov.cannons.container.MaterialHolder;
import at.pavlov.cannons.inventory.InventoryManagement;
import at.pavlov.cannons.projectile.ProjectileManager;
import at.pavlov.cannons.projectile.ProjectileStorage;
import at.pavlov.cannons.utils.CannonsUtil;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author DerPavlov
 * 
 */



public class Config
{
	//general
	private boolean debugMode;
	
	//build limits
	private boolean buildLimitEnabled;
	private int buildLimitA;
	private int buildLimitB;
	//tools
	private MaterialHolder toolAdjust = new MaterialHolder(0, 0);
	private MaterialHolder toolAutoaim = new MaterialHolder(347, 0);
	private MaterialHolder toolFiring = new MaterialHolder(259, 0);
	private MaterialHolder toolRotating = new MaterialHolder(350, 0);
    private MaterialHolder toolThermometer = new MaterialHolder(371, 0);
    private List<MaterialHolder> toolCooling = new ArrayList<MaterialHolder>();
    private List<MaterialHolder> toolCoolingUsed = new ArrayList<MaterialHolder>();

    //superbreakerBlocks
    private List<MaterialHolder> superbreakerBlocks = new ArrayList<MaterialHolder>();

    //unbreakableBlocks
    private List<MaterialHolder> unbreakableBlocks = new ArrayList<MaterialHolder>();


    private final UserMessages userMessage;
	private final Cannons plugin;
	private final DesignStorage designStorage;
	private ProjectileStorage projectileStorage;
    private CannonManager cannonManager;
    private ProjectileManager projectileManager;

	public Config(Cannons plugin)
	{
		this.plugin = plugin;
		userMessage = new UserMessages(this.plugin);
		designStorage = new DesignStorage(this.plugin);
		projectileStorage = new ProjectileStorage(this.plugin);
        cannonManager = new CannonManager(plugin, userMessage, this);
        projectileManager = new ProjectileManager(plugin);
	}

	public void loadConfig()
	{
		// copy the default config to the disk if it does not exist
		plugin.saveDefaultConfig();
		
		//general
		setDebugMode(plugin.getConfig().getBoolean("general.debugMode", false));
		
		//limitOfCannons
		setBuildLimitEnabled(plugin.getConfig().getBoolean("cannonLimits.useLimits", true));
		setBuildLimitA(plugin.getConfig().getInt("cannonLimits.buildLimitA", 10));
		setBuildLimitB(plugin.getConfig().getInt("cannonLimits.buildLimitB", 2));

		//tools
		setToolAdjust(new MaterialHolder(plugin.getConfig().getString("tools.adjust", "0:0")));
		setToolAutoaim(new MaterialHolder(plugin.getConfig().getString("tools.autoaim", "347:0")));
		setToolFiring(new MaterialHolder(plugin.getConfig().getString("tools.firing", "259:0")));
		setToolRotating(new MaterialHolder(plugin.getConfig().getString("tools.adjust", "350:0")));

        setToolCooling(CannonsUtil.toMaterialHolderList(plugin.getConfig().getStringList("tools.coolingItems")));
        setToolCoolingUsed(CannonsUtil.toMaterialHolderList(plugin.getConfig().getStringList("tools.coolingItemsUsed")));
        if (getToolCooling().size() != getToolCoolingUsed().size())
           plugin.logSevere("CoolingToolUsed and CoolingTool lists must have the same size. Check if both lists have the same number of entries");

        //superbreakerBlocks
        setSuperbreakerBlocks(CannonsUtil.toMaterialHolderList(plugin.getConfig().getStringList("superbreakerBlocks")));
        //if this list is empty add some blocks
        if (superbreakerBlocks.size() == 0)
        {
            plugin.logInfo("superbreakerBlock list is empty");
        }

        //unbreakableBlocks
        setUnbreakableBlocks(CannonsUtil.toMaterialHolderList(plugin.getConfig().getStringList("unbreakableBlocks")));
        if (unbreakableBlocks.size() == 0)
        {
            plugin.logInfo("unbreakableBlocks list is empty");
        }
	
		//load other configs	
		projectileStorage.loadProjectiles();
		designStorage.loadCannonDesigns();
        cannonManager.updateCannonDesigns();
		userMessage.loadLanguage();

		


	}
	

	/**
	 * returns the class UserMessages
	 * @return
	 */
	public UserMessages getUserMessages()
	{
		return userMessage;
	}

	public DesignStorage getDesignStorage()
	{
		return designStorage;
	}

	public ProjectileStorage getProjectileStorage()
	{
		return projectileStorage;
	}

	public void setProjectileStorage(ProjectileStorage projectileStorage)
	{
		this.projectileStorage = projectileStorage;
	}

	public boolean isBuildLimitEnabled()
	{
		return buildLimitEnabled;
	}

	void setBuildLimitEnabled(boolean buildLimitEnabled)
	{
		this.buildLimitEnabled = buildLimitEnabled;
	}

	public int getBuildLimitA()
	{
		return buildLimitA;
	}

	void setBuildLimitA(int buildLimitA)
	{
		this.buildLimitA = buildLimitA;
	}

	public int getBuildLimitB()
	{
		return buildLimitB;
	}

	void setBuildLimitB(int buildLimitB)
	{
		this.buildLimitB = buildLimitB;
	}

	public MaterialHolder getToolAdjust()
	{
		return toolAdjust;
	}

	void setToolAdjust(MaterialHolder toolAdjust)
	{
		this.toolAdjust = toolAdjust;
	}

	public MaterialHolder getToolAutoaim()
	{
		return toolAutoaim;
	}

	void setToolAutoaim(MaterialHolder toolAutoaim)
	{
		this.toolAutoaim = toolAutoaim;
	}

	public MaterialHolder getToolRotating()
	{
		return toolRotating;
	}

	void setToolRotating(MaterialHolder toolRotating)
	{
		this.toolRotating = toolRotating;
	}

	public boolean isDebugMode()
	{
		return debugMode;
	}

	void setDebugMode(boolean debugMode)
	{
		this.debugMode = debugMode;
	}

	public MaterialHolder getToolFiring()
	{
		return toolFiring;
	}

	void setToolFiring(MaterialHolder toolFiring)
	{
		this.toolFiring = toolFiring;
	}


    public List<MaterialHolder> getSuperbreakerBlocks() {
        return superbreakerBlocks;
    }

    void setSuperbreakerBlocks(List<MaterialHolder> superbreakerBlocks) {
        this.superbreakerBlocks = superbreakerBlocks;
    }

    public List<MaterialHolder> getUnbreakableBlocks() {
        return unbreakableBlocks;
    }

    void setUnbreakableBlocks(List<MaterialHolder> unbreakableBlocks) {
        this.unbreakableBlocks = unbreakableBlocks;
    }

    public CannonManager getCannonManager() {
        return cannonManager;
    }

    public void setCannonManager(CannonManager cannonManager) {
        this.cannonManager = cannonManager;
    }

    public ProjectileManager getProjectileManager() {
        return projectileManager;
    }

    public void setProjectileManager(ProjectileManager projectileManager) {
        this.projectileManager = projectileManager;
    }

    public MaterialHolder getToolThermometer() {
        return toolThermometer;
    }

    public void setToolThermometer(MaterialHolder toolThermometer) {
        this.toolThermometer = toolThermometer;
    }

    public List<MaterialHolder> getToolCooling() {
        return toolCooling;
    }

    void setToolCooling(List<MaterialHolder> toolCooling) {
        this.toolCooling = toolCooling;
    }

    public List<MaterialHolder> getToolCoolingUsed() {
        return toolCoolingUsed;
    }

    void setToolCoolingUsed(List<MaterialHolder> toolCoolingUsed) {
        this.toolCoolingUsed = toolCoolingUsed;
    }

    /**
     * is this Item a cooling tool to cool down a cannon
     * @param item - item to check
     * @return - true if this item is in the list of cooling items
     */
    public boolean isCoolingTool(ItemStack item)
    {
        for (MaterialHolder mat : toolCooling)
        {
            if (mat.equalsFuzzy(item))
                return true;
        }
        return false;
    }

    /**
     * returns the used used item. E.g. a water bucket will be an empty bucket.
     * @param item - the item used for the event
     * @return the new item which replaces the old one
     */
    public ItemStack getCoolingToolUsed(ItemStack item)
    {
        for (int i=0; i < toolCooling.size(); i++)
        {
            if (toolCooling.get(i).equalsFuzzy(item))
            {
                return toolCoolingUsed.get(i).toItemStack(item.getAmount());
            }
        }
        return null;
    }
}
