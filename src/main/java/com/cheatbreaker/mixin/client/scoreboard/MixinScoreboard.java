package com.cheatbreaker.mixin.client.scoreboard;

import com.cheatbreaker.client.CheatBreaker;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;

@Mixin(Scoreboard.class)
public abstract class MixinScoreboard {
    @Shadow public abstract ScorePlayerTeam getPlayersTeam(String p_96509_1_);

    @Shadow @Final private Map teamMemberships;

    /**
     * @author iAmSpace
     * @reason crash
     */
    @Overwrite
    public void removePlayerFromTeam(String p_96512_1_, ScorePlayerTeam p_96512_2_) {
        if (this.getPlayersTeam(p_96512_1_) != p_96512_2_) {
            CheatBreaker.getInstance().cbInfo("Couldn't remove " + p_96512_2_.getRegisteredName() + " from team.");
        } else {
            this.teamMemberships.remove(p_96512_1_);
            p_96512_2_.getMembershipCollection().remove(p_96512_1_);
        }
    }
}
