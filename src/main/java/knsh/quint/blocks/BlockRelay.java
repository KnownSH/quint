package knsh.quint.blocks;

import knsh.quint.QuintBlocks;
import knsh.quint.QuintMod;
import knsh.quint.util.DirectionConst;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.material.Material;
import net.minecraft.core.entity.EntityLiving;
import net.minecraft.core.util.helper.Side;
import net.minecraft.core.world.World;
import net.minecraft.core.world.WorldSource;
import turniplabs.halplibe.helper.TextureHelper;

import java.util.Random;

public class BlockRelay extends Block { ;
	private static int frontTexture = TextureHelper.getOrCreateBlockTextureIndex(QuintMod.MOD_ID, "relay_back.png");
	private static int offTexture = TextureHelper.getOrCreateBlockTextureIndex(QuintMod.MOD_ID, "relay_off.png");
	private static int onTexture = TextureHelper.getOrCreateBlockTextureIndex(QuintMod.MOD_ID, "relay_on.png");

	boolean isActive;

	public BlockRelay(String key, int id, boolean isActivated) {
		super(key, id, Material.piston);
		this.isActive = isActivated;
		this.setTicking(true);
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(Side side, int facing) {
		if (side == Side.NORTH && facing == DirectionConst.SOUTH) {
			return frontTexture;
		} else if (side == Side.SOUTH && facing == DirectionConst.NORTH) {
			return frontTexture;
		} else if (side == Side.EAST && facing == DirectionConst.WEST) {
			return frontTexture;
		} else if (side == Side.WEST && facing == DirectionConst.EAST) {
			return frontTexture;
		}
		if (!isActive) {
			return offTexture;
		}
		return onTexture;
	}

	public int tickRate() {
		return 2;
	}

	public boolean isIndirectlyPoweringTo(World world, int x, int y, int z, int side) {
		return false;
	}

	public boolean canProvidePower() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	public boolean isPoweringTo(WorldSource blockAccess, int x, int y, int z, int side) {
		if (!this.isActive) {
			return false;
		} else {
			int facingDirection = blockAccess.getBlockMetadata(x, y, z) & 3;
			if (facingDirection == DirectionConst.NORTH && side != 2) {
				return true;
			} else if (facingDirection == DirectionConst.EAST && side != 5) {
				return true;
			} else if (facingDirection == DirectionConst.SOUTH && side != 3) {
				return true;
			} else {
				return facingDirection == DirectionConst.WEST && side != 4;
			}
		}
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand) {
		int l = world.getBlockMetadata(x, y, z);
		boolean flag = this.checkForPoweringRedstone(world, x, y, z, l);
		if (this.isActive) {
			if (!flag) {
				world.setBlockAndMetadataWithNotify(x, y, z, QuintBlocks.relayInactive.id, world.getBlockMetadata(x, y, z));
			}
		} else if (flag) {
			world.setBlockAndMetadataWithNotify(x, y, z, QuintBlocks.relayActive.id, world.getBlockMetadata(x, y, z));
		}
	}

	public void onBlockPlaced(World world, int x, int y, int z, Side side, EntityLiving entity, double sideHeight) {
		int l = entity.getHorizontalPlacementDirection(side).index;
		world.setBlockMetadataWithNotify(x, y, z, l);
		boolean flag = this.checkForPoweringRedstone(world, x, y, z, l);
		if (flag) {
			world.scheduleBlockUpdate(x, y, z, this.id, 1);
		}
	}

	private boolean checkForPoweringRedstone(World world, int i, int j, int k, int l) {
		int i1 = l & 3;
		switch (i1) {
			case 0:
				return world.isBlockIndirectlyProvidingPowerTo(i, j, k + 1, 3) || world.getBlockId(i, j, k + 1) == Block.wireRedstone.id && world.getBlockMetadata(i, j, k + 1) > 0;
			case 1:
				return world.isBlockIndirectlyProvidingPowerTo(i - 1, j, k, 4) || world.getBlockId(i - 1, j, k) == Block.wireRedstone.id && world.getBlockMetadata(i - 1, j, k) > 0;
			case 2:
				return world.isBlockIndirectlyProvidingPowerTo(i, j, k - 1, 2) || world.getBlockId(i, j, k - 1) == Block.wireRedstone.id && world.getBlockMetadata(i, j, k - 1) > 0;
			case 3:
				return world.isBlockIndirectlyProvidingPowerTo(i + 1, j, k, 5) || world.getBlockId(i + 1, j, k) == Block.wireRedstone.id && world.getBlockMetadata(i + 1, j, k) > 0;
			default:
				return false;
		}
	}

	public void onBlockAdded(World world, int x, int y, int z) {
		super.onBlockAdded(world, x, y, z);
		world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
	}

	public void onNeighborBlockChange(World world, int x, int y, int z, int blockId) {
		world.scheduleBlockUpdate(x, y, z, this.id, this.tickRate());
	}
}
