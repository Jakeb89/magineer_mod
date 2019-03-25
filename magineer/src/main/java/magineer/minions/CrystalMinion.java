package magineer.minions;

import basemod.BaseMod;
import basemod.interfaces.PostEnergyRechargeSubscriber;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import magineer.util.IntPair;
import magineer.util.MinionManager;

import java.util.ArrayList;

public class CrystalMinion extends AutoMinion {

    private static String NAME = "Crystal Minion";
    private static String ID = "CrystalMinion";
    private AbstractMonster target;

    public static String minionImage = "magineerResources/images/minions/crystal.png"; //Should be correct.

    public CrystalMinion(int offsetX, int offsetY, IntPair minionSlot) {
        super(NAME, ID, 10, 0f, 0f, 320f, 320f, minionImage, offsetX, offsetY, minionSlot);
        addMoves();
        pendingMove = autoPickMove();
    }

    @Override
    public void addMoves(){
        this.moves.addMove(new AutoMinionAttackMove(this, 1, 5));
        this.moves.addMove(new AutoMinionDefendMove(this, 4));
    }

}