package knsh.quint.mixin;

import knsh.quint.QuintBlocks;
import net.minecraft.core.block.BlockRedstoneWire;
import net.minecraft.core.world.WorldSource;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRedstoneWire.class)
public class BlockRedstoneWireMixin {
	@Inject(
		method = "isPowerProviderOrWire",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/core/world/WorldSource;getBlockId(III)I"),
		remap = false,
		cancellable = true
	)
	private static void quint$directsRedstoneWire(WorldSource iblockaccess, int i, int j, int k, int l, CallbackInfoReturnable<Boolean> cir) {
		int i1 = iblockaccess.getBlockId(i, j, k);
		if (i1 == QuintBlocks.redirectorInactive.id || i1 == QuintBlocks.redirectorActive.id) {
			cir.setReturnValue(true);
		}
	}
}
