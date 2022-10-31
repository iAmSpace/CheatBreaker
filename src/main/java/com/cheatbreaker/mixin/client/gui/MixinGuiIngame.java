package com.cheatbreaker.mixin.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.scoreboard.ScoreObjective;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(GuiIngame.class)
public abstract class MixinGuiIngame extends Gui {
    /**
     * @author iAmSpace
     * @reason big brain
     */
    @Overwrite
    protected void func_96136_a(ScoreObjective p_96136_1_, int p_96136_2_, int p_96136_3_, FontRenderer p_96136_4_) {

    }
}
