package knsh.quint;

import knsh.quint.blocks.BlockRelay;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.Block;
import net.minecraft.core.block.tag.BlockTags;
import turniplabs.halplibe.helper.BlockBuilder;

public class QuintBlocks {
	public static Block
		relayInactive,
		relayActive;

	public static void register() {
		int quintStartingBlockId = 6600;

		relayInactive = new BlockBuilder(QuintMod.MOD_ID)
			.setBlockModel(new BlockModelRenderBlocks(0))
			.build(new BlockRelay("relay", quintStartingBlockId++, false));
		relayActive = new BlockBuilder(QuintMod.MOD_ID)
			.setLuminance(5)
			.addTags(BlockTags.NOT_IN_CREATIVE_MENU)
			.setBlockModel(new BlockModelRenderBlocks(0))
			.build(new BlockRelay("relay", quintStartingBlockId++, true));
	}
}
