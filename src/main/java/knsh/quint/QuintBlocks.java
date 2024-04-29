package knsh.quint;

import knsh.quint.blocks.BlockRedirector;
import net.minecraft.client.render.block.model.BlockModelRenderBlocks;
import net.minecraft.core.block.Block;
import turniplabs.halplibe.helper.BlockBuilder;

public class QuintBlocks {
	public static Block redirectorInactive;
	public static Block redirectorActive;

	public static void register() {
		int quintStartingBlockId = 6600;

		redirectorInactive = new BlockBuilder(QuintMod.MOD_ID)
			.setTextures("redirector_off.png")
			.setBlockModel(new BlockModelRenderBlocks(0))
			.build(new BlockRedirector("redirector", quintStartingBlockId++, false));
		redirectorActive = new BlockBuilder(QuintMod.MOD_ID)
			.setTextures("redirector_on.png")
			.setBlockModel(new BlockModelRenderBlocks(0))
			.build(new BlockRedirector("redirector", quintStartingBlockId++, true));
	}
}
