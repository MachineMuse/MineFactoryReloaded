package powercrystals.minefactoryreloaded.tile.machine;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.ISyringe;
import powercrystals.minefactoryreloaded.core.HarvestAreaManager;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityVet extends TileEntityFactoryPowered
{
	private HarvestAreaManager _areaManager;
	
	public TileEntityVet()
	{
		super(320);
		_areaManager = new HarvestAreaManager(this, 2, 2, 1);
	}
	
	@Override
	public String getGuiBackground()
	{
		return "vet.png";
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFactoryPowered(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFactoryPowered(this, inventoryPlayer);
	}

	@Override
	public int getEnergyStoredMax()
	{
		return 32000;
	}

	@Override
	public int getWorkMax()
	{
		return 1;
	}

	@Override
	public int getIdleTicksMax()
	{
		return 200;
	}

	@Override
	public boolean activateMachine()
	{
		List<?> entities = worldObj.getEntitiesWithinAABB(EntityLiving.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		for(Object o : entities)
		{
			if(!(o instanceof EntityLiving) || o instanceof EntityPlayer || o instanceof EntityMob)
			{
				continue;
			}
			EntityLiving e = (EntityLiving)o;
			
			for(int i = 0; i < getSizeInventory(); i++)
			{
				ItemStack s = getStackInSlot(i);
				if(s != null && s.getItem() instanceof ISyringe)
				{
					if(((ISyringe)s.getItem()).canInject(worldObj, e, s))
					{
						if(((ISyringe)s.getItem()).inject(worldObj, e, s))
						{
							s.itemID = MineFactoryReloadedCore.syringeEmptyItem.itemID;
							return true;
						}
					}
				}
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}

	@Override
	public String getInvName()
	{
		return "Veterinary";
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}
}
