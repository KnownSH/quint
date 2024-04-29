package knsh.quint;

import net.minecraft.core.item.Item;
import turniplabs.halplibe.helper.RecipeBuilder;

public class QuintRecipes {
	public static void register() {
		RecipeBuilder.Shaped(QuintMod.MOD_ID)
			.setShape("IRI", "RQQ", "IRI")
			.addInput('I', Item.ingotIron)
			.addInput('R', Item.dustRedstone)
			.addInput('Q', Item.quartz)
			.create("relayRecipe", QuintBlocks.relayInactive.getDefaultStack());
	}
}
