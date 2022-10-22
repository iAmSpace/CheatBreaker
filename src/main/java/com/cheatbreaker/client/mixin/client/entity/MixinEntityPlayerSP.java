package com.cheatbreaker.client.mixin.client.entity;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.type.togglesprint.ToggleSprintModule;
import com.mojang.authlib.GameProfile;
import net.minecraft.MinecraftMovementInputHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovementInputFromOptions;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Session;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {
    @Shadow public int sprintingTicksLeft;

    public MixinEntityPlayerSP(World p_i45074_1_, GameProfile p_i45074_2_) {
        super(p_i45074_1_, p_i45074_2_);
    }

    @Shadow public abstract void setSprinting(boolean p_70031_1_);

    @Shadow protected int sprintToggleTimer;
    @Shadow protected Minecraft mc;
    @Shadow public float prevTimeInPortal;
    @Shadow public float timeInPortal;
    @Shadow public MovementInput movementInput;

    @Shadow public abstract boolean isRidingHorse();

    @Shadow private int horseJumpPowerCounter;
    @Shadow private float horseJumpPower;

    @Shadow protected abstract void func_110318_g();

    private MinecraftMovementInputHelper inputHelper;

    @Inject(method = "<init>(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/World;Lnet/minecraft/util/Session;I)V", at = @At("RETURN"))
    public void impl$init(Minecraft minecraft, World world, Session session, int p, CallbackInfo callbackInfo) {
        this.inputHelper = new MinecraftMovementInputHelper(Minecraft.getMinecraft().gameSettings);
    }

    /**
     * @author iAmSpace
     * @reason CheatBreaker Modules
     */
    @Overwrite
    public void onLivingUpdate()
    {
        if (this.sprintingTicksLeft > 0)
        {
            --this.sprintingTicksLeft;

            if (this.sprintingTicksLeft == 0)
            {
                this.setSprinting(false);
            }
        }

        if (this.sprintToggleTimer > 0)
        {
            --this.sprintToggleTimer;
        }

        if (this.mc.playerController.enableEverythingIsScrewedUpMode())
        {
            this.posX = this.posZ = 0.5D;
            this.posX = 0.0D;
            this.posZ = 0.0D;
            this.rotationYaw = (float)this.ticksExisted / 12.0F;
            this.rotationPitch = 10.0F;
            this.posY = 68.5D;
        }
        else
        {
            this.prevTimeInPortal = this.timeInPortal;

            if (this.inPortal)
            {
                if (this.mc.currentScreen != null)
                {
                    this.mc.displayGuiScreen((GuiScreen)null);
                }

                if (this.timeInPortal == 0.0F)
                {
                    this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("portal.trigger"), this.rand.nextFloat() * 0.4F + 0.8F));
                }

                this.timeInPortal += 0.0125F;

                if (this.timeInPortal >= 1.0F)
                {
                    this.timeInPortal = 1.0F;
                }

                this.inPortal = false;
            }
            else if (this.isPotionActive(Potion.confusion) && this.getActivePotionEffect(Potion.confusion).getDuration() > 60)
            {
                this.timeInPortal += 0.006666667F;

                if (this.timeInPortal > 1.0F)
                {
                    this.timeInPortal = 1.0F;
                }
            }
            else
            {
                if (this.timeInPortal > 0.0F)
                {
                    this.timeInPortal -= 0.05F;
                }

                if (this.timeInPortal < 0.0F)
                {
                    this.timeInPortal = 0.0F;
                }
            }

            if (this.timeUntilPortal > 0)
            {
                --this.timeUntilPortal;
            }

            boolean var1 = this.movementInput.jump;
            float f = 0.8F;
            boolean bl2 = this.movementInput.moveForward >= f;
            MinecraftMovementInputHelper.lIIIIlIIllIIlIIlIIIlIIllI(this.mc, (MovementInputFromOptions)this.movementInput, (EntityPlayerSP) ((AbstractClientPlayer)this));

            if (this.isUsingItem() && !this.isRiding())
            {
                this.movementInput.moveStrafe *= 0.2F;
                this.movementInput.moveForward *= 0.2F;
                this.sprintToggleTimer = 0;
            }

            if (this.movementInput.sneak && this.ySize < 0.2F)
            {
                this.ySize = 0.2F;
            }

            this.func_145771_j(this.posX - (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ + (double)this.width * 0.35D);
            this.func_145771_j(this.posX - (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ - (double)this.width * 0.35D);
            this.func_145771_j(this.posX + (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ - (double)this.width * 0.35D);
            this.func_145771_j(this.posX + (double)this.width * 0.35D, this.boundingBox.minY + 0.5D, this.posZ + (double)this.width * 0.35D);
            boolean bl3 = (float)this.getFoodStats().getFoodLevel() > 6.0F || this.capabilities.allowFlying;
            boolean bl4 = !CheatBreaker.getInstance().getModuleManager().toggleSprint.isEnabled() || !((Boolean) ToggleSprintModule.toggleSprint.getValue());
            boolean bl5 = (Boolean) ToggleSprintModule.doubleTap.getValue();

            if (ToggleSprintModule.buggedSprint) {
                this.setSprinting(false);
                this.inputHelper.setSprintState(false, false);
                ToggleSprintModule.buggedSprint = false;
            }

            if (bl4) {
                if ((Boolean) ToggleSprintModule.doubleTap.getValue() && this.onGround
                        && !bl2 && this.movementInput.moveForward >= f && !this.isSprinting()
                        && bl3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                    if (this.sprintToggleTimer <= 0 && !this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                        this.sprintToggleTimer = 7;
                    } else {
                        this.setSprinting(true);
                        this.inputHelper.setSprintState(true, false);
                    }
                }
                if (!this.isSprinting() && this.movementInput.moveForward >= f && bl3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness) && this.mc.gameSettings.keyBindSprint.getIsKeyPressed()) {
                    this.setSprinting(true);
                    this.inputHelper.setSprintState(true, false);
                }
            } else {
                boolean bl6 = MinecraftMovementInputHelper.isSprinting;
                if (!(!bl3 || this.isUsingItem() || this.isPotionActive(Potion.blindness) || MinecraftMovementInputHelper.superSusBoolean || bl5 && this.isSprinting())) {
                    this.setSprinting(bl6);
                }
                if (bl5 && !bl6 && this.onGround && !bl2 && this.movementInput.moveForward >= f && !this.isSprinting() && bl3 && !this.isUsingItem() && !this.isPotionActive(Potion.blindness)) {
                    if (this.sprintToggleTimer == 0) {
                        this.sprintToggleTimer = 7;
                    } else {
                        this.setSprinting(true);
                        this.inputHelper.setSprintState(true, true);
                        this.sprintToggleTimer = 0;
                    }
                }
            }
            if (this.isSprinting() && (this.movementInput.moveForward < f || this.isCollidedHorizontally || !bl3)) {
                this.setSprinting(false);
                if (MinecraftMovementInputHelper.superSusBoolean || bl4 || MinecraftMovementInputHelper.aSusBoolean || this.isRiding()) {
                    this.inputHelper.setSprintState(false, false);
                }
            }
            if ((Boolean) ToggleSprintModule.flyBoost.getValue() && this.capabilities.isFlying && this.mc.gameSettings.keyBindSprint.getIsKeyPressed() && this.capabilities.isCreativeMode) {
                this.capabilities.setFlySpeed(0.027272727f * 1.8333334f * (Float) ToggleSprintModule.flyBoostAmount.getValue());
                if (this.movementInput.sneak) {
                    this.motionY -= 0.6526315808296204 * 0.2298387090145425 * (double) (Float) ToggleSprintModule.flyBoostAmount.getValue();
                }
                if (this.movementInput.jump) {
                    this.motionY += 0.01084337374315776 * 13.833333015441895 * (double) (Float) ToggleSprintModule.flyBoostAmount.getValue();
                }
            } else if (this.capabilities.getFlySpeed() != 0.0129629625f * 3.857143f) {
                this.capabilities.setFlySpeed(4.714286f * 0.010606061f);
            }

            if (this.capabilities.allowFlying && !var1 && this.movementInput.jump)
            {
                if (this.flyToggleTimer == 0)
                {
                    this.flyToggleTimer = 7;
                }
                else
                {
                    this.capabilities.isFlying = !this.capabilities.isFlying;
                    this.sendPlayerAbilities();
                    this.flyToggleTimer = 0;
                }
            }

            if (this.capabilities.isFlying)
            {
                if (this.movementInput.sneak)
                {
                    this.motionY -= 0.15D;
                }

                if (this.movementInput.jump)
                {
                    this.motionY += 0.15D;
                }
            }

            if (this.isRidingHorse())
            {
                if (this.horseJumpPowerCounter < 0)
                {
                    ++this.horseJumpPowerCounter;

                    if (this.horseJumpPowerCounter == 0)
                    {
                        this.horseJumpPower = 0.0F;
                    }
                }

                if (var1 && !this.movementInput.jump)
                {
                    this.horseJumpPowerCounter = -10;
                    this.func_110318_g();
                }
                else if (!var1 && this.movementInput.jump)
                {
                    this.horseJumpPowerCounter = 0;
                    this.horseJumpPower = 0.0F;
                }
                else if (var1)
                {
                    ++this.horseJumpPowerCounter;

                    if (this.horseJumpPowerCounter < 10)
                    {
                        this.horseJumpPower = (float)this.horseJumpPowerCounter * 0.1F;
                    }
                    else
                    {
                        this.horseJumpPower = 0.8F + 2.0F / (float)(this.horseJumpPowerCounter - 9) * 0.1F;
                    }
                }
            }
            else
            {
                this.horseJumpPower = 0.0F;
            }

            super.onLivingUpdate();

            if (this.onGround && this.capabilities.isFlying)
            {
                this.capabilities.isFlying = false;
                this.sendPlayerAbilities();
            }
        }
    }
}
