/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package minetweaker.mc11.player;

import minetweaker.MineTweakerAPI;
import minetweaker.api.chat.IChatMessage;
import minetweaker.api.data.IData;
import minetweaker.api.item.IItemStack;
import minetweaker.api.minecraft.MineTweakerMC;
import minetweaker.api.player.IPlayer;
import minetweaker.mc11.MineTweakerMod;
import minetweaker.mc11.data.NBTConverter;
import minetweaker.mc11.network.MineTweakerCopyClipboardPacket;
import minetweaker.mc11.network.MineTweakerOpenBrowserPacket;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;

/**
 * @author Stan
 */
public class MCPlayer implements IPlayer{
    private final EntityPlayer player;

    public MCPlayer(EntityPlayer player){
        this.player = player;
    }

    public EntityPlayer getInternal(){
        return player;
    }

    @Override
    public String getId(){
        return null; // TODO: we should be having this for MC 1.7.10, right?
    }

    @Override
    public String getName(){
        return player.getName();
    }

    @Override
    public IData getData(){
        return NBTConverter.from(player.getEntityData(), true);
    }

    @Override
    public void update(IData data){
        NBTConverter.updateMap(player.getEntityData(), data);
    }

    @Override
    public void sendChat(IChatMessage message){
        Object internal = message.getInternal();
        if(!(internal instanceof ITextComponent)){
            MineTweakerAPI.logError("not a valid chat message");
            return;
        }
        player.sendMessage((ITextComponent) internal);
    }

    @Override
    public void sendChat(String message){
        player.sendMessage(new TextComponentString(message));
    }

    @Override
    public int getHotbarSize(){
        return 9;
    }

    @Override
    public IItemStack getHotbarStack(int i){
        return i < 0 || i >= 9 ? null : MineTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
    }

    @Override
    public int getInventorySize(){
        return player.inventory.getSizeInventory();
    }

    @Override
    public IItemStack getInventoryStack(int i){
        return MineTweakerMC.getIItemStack(player.inventory.getStackInSlot(i));
    }

    @Override
    public IItemStack getCurrentItem(){
        return MineTweakerMC.getIItemStack(player.inventory.getCurrentItem());
    }

    @Override
    public boolean isCreative(){
        return player.capabilities.isCreativeMode;
    }

    @Override
    public boolean isAdventure(){
        return !player.capabilities.allowEdit;
    }

    @Override
    public void openBrowser(String url){
        if(player instanceof EntityPlayerMP){
            MineTweakerMod.NETWORK.sendTo(new MineTweakerOpenBrowserPacket(url), (EntityPlayerMP) player);
        }
    }

    @Override
    public void copyToClipboard(String value){
        if(player instanceof EntityPlayerMP){
            MineTweakerMod.NETWORK.sendTo(new MineTweakerCopyClipboardPacket(value), (EntityPlayerMP) player);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other.getClass() == this.getClass() && ((MCPlayer) other).player == player;

    }

    @Override
    public int hashCode(){
        int hash = 5;
        hash = 23 * hash + (this.player != null ? this.player.hashCode() : 0);
        return hash;
    }

    @Override
    public void give(IItemStack stack){
        player.inventory.addItemStackToInventory(MineTweakerMC.getItemStack(stack).copy());
    }
}
