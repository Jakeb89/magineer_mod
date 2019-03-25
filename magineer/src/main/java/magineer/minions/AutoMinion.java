package magineer.minions;

import basemod.BaseMod;
import basemod.interfaces.OnCardUseSubscriber;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.TooltipManager;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BobEffect;
import com.megacrit.cardcrawl.vfx.TintEffect;
import com.sun.java.swing.action.ActionManager;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import kobting.friendlyminions.monsters.MinionMoveGroup;
import magineer.util.IntPair;
import magineer.util.MinionManager;

import java.util.ArrayList;

import static magineer.characters.Magineer.logger;

public class AutoMinion extends AbstractFriendlyMonster implements PostEnergyRechargeSubscriber, OnCardUseSubscriber {

    private static String NAME = "Crystal Minion";
    private static String ID = "CrystalMinion";
    private AbstractMonster target;
    public AutoMinionMove pendingMove = null;
    public int countdownMax = 2;
    public int countdown = 2;
    public BobEffect bobEffect;
    public IntPair minionSlot;
    public ArrayList<AutoMinionMove> minionMoveList = new ArrayList<>();

    public static String blankIcon = "magineerResources/images/minions/actions/blank.png"; //This is correct.

    public AutoMinion(String NAME, String ID, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h,
                      String minionImage, int offsetX, int offsetY, IntPair minionSlot) {
        super(NAME, ID, maxHealth, hb_x, hb_y, hb_w, hb_h, minionImage, offsetX, offsetY);

        //Because I dislike the method used to load minion images.
        this.img.dispose();
        img = ImageMaster.loadImage(minionImage);

        BaseMod.subscribe(this);
        bobEffect = new BobEffect();
        this.minionSlot = minionSlot;
    }

    @Override
    public void die() {
        DamageInfo info = new DamageInfo(this, this.maxHealth, DamageInfo.DamageType.NORMAL);
        info.applyPowers(this, AbstractDungeon.player); // <--- This lets powers effect minions attacks
        AbstractDungeon.actionManager.addToBottom(new DamageAction(AbstractDungeon.player, info));
        super.die();
        MinionManager.freeSlot(this);
    }

    public void addMoves(){
        //Override this.
    }

    public AutoMinionMove autoPickMove() {
        moves.clearMoves();
        addMoves();
        ArrayList<MinionMove> minionMoveList = moves.getMoves();
        int chosenMoveIndex = (int) (Math.random()*minionMoveList.size());
        if(minionMoveList.size() == 0){
            logger.info("moves.getMoves() returned an empty arraylist.");
            return null;
        }
        return (AutoMinionMove) minionMoveList.get(chosenMoveIndex);
    }

    @Override
    public void receivePostEnergyRecharge() {
        //logger.info(this.getClass().getSimpleName()+".receivePostEnergyRecharge()");
        if(pendingMove == null){
            pendingMove = autoPickMove();
            countdown = countdownMax;
        }
    }

    public void endOfTurn() {
        //pendingMove.doMove();
    }

    @Override
    public void render(SpriteBatch sb) {
        //logger.info(this.getClass().getSimpleName()+".render(...)");
        this.bobEffect.update();
        this.animY = this.bobEffect.y;
        this.moves = new MinionMoveGroup(new ArrayList<MinionMove>(), 0, 0);
        super.render(sb);
        if(pendingMove != null && pendingMove instanceof AutoMinionMove){
            pendingMove.render(sb);
            //pendingMove.renderIntent(sb, this.countdown);
            //pendingMove.renderCountdown(sb, countdown);
            //pendingMove.renderDamageRange(sb);
        }
        if(hb.hovered){
            ArrayList<PowerTip> tips = new ArrayList();
            String tooltip = "";
            if(pendingMove != null){
                tooltip = "Afters spending";
                for(int i=0; i<countdown; i++){
                    tooltip += " [E]";
                }
                tooltip += " this minion will:";
                tooltip += " NL " + pendingMove.getMoveDescription();
            }else{
                tooltip = "This minion has no pending action.";
            }
            tips.add(new PowerTip(this.NAME, tooltip));
            TipHelper.queuePowerTips(hb.x, hb.y, tips);
        }
    }

    @Override
    public void receiveCardUsed(AbstractCard abstractCard) {
        logger.info(this.getClass().getSimpleName()+".receiveCardUsed(...)");
        logger.info(">abstractCard.energyOnUse: " + abstractCard.energyOnUse);
        logger.info(">abstractCard.cost: " + abstractCard.cost);
        logger.info(">abstractCard.costForTurn: " + abstractCard.costForTurn);

        int cardCost = 0;
        if(abstractCard.freeToPlayOnce){
            cardCost = 0;
        }else{
            cardCost = abstractCard.costForTurn;
        }

        if(cardCost < 0){
            cardCost = 0;
        }

        countdown -= cardCost;
        if(countdown <= 0 && pendingMove != null){
            pendingMove.run();
            pendingMove = null;
        }
    }
}