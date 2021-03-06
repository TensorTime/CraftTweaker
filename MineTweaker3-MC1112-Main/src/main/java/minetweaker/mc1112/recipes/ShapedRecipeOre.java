package minetweaker.mc1112.recipes;

import minetweaker.api.recipes.*;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.ShapedOreRecipe;

import static minetweaker.api.minecraft.MineTweakerMC.getItemStack;

/**
 * @author Stan
 */
public class ShapedRecipeOre extends ShapedOreRecipe implements IMTRecipe {

    private final ShapedRecipe recipe;

    public ShapedRecipeOre(Object[] contents, ShapedRecipe recipe) {
        super(getItemStack(recipe.getOutput()), contents);

        this.recipe = recipe;
    }

    @Override
    public boolean matches(InventoryCrafting inventory, World world) {
        return recipe.matches(MCCraftingInventory.get(inventory));
    }

    @Override
    public ItemStack getCraftingResult(InventoryCrafting inventory) {
        if(recipe != null) {
            if(recipe.getCraftingResult(MCCraftingInventory.get(inventory)) != null) {
                return getItemStack(recipe.getCraftingResult(MCCraftingInventory.get(inventory))).copy();
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ICraftingRecipe getRecipe() {
        return recipe;
    }
}
