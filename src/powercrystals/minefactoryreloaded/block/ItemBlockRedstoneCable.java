package powercrystals.minefactoryreloaded.block;

import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;

public class ItemBlockRedstoneCable extends ItemBlock {
	private final static String[] subNames = { "redstone", "stickyredstone" };

	public ItemBlockRedstoneCable(int par1) {
		super(par1);
		setHasSubtypes(true);
		setUnlocalizedName("mfr.cable");
	}

	@Override
	public int getMetadata(int damagevalue) {
		return damagevalue;
	}

	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack) {
		int meta = MathHelper.clamp_int(par1ItemStack.getItemDamage(), 0, subNames.length - 1);
		return getUnlocalizedName() + "." + subNames[meta];
	}

}