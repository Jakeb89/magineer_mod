package magineer.minions;

import com.megacrit.cardcrawl.monsters.AbstractMonster;
import magineer.util.IntPair;

public class AegisMinion extends AutoMinion {

    private static String NAME = "Aegis Minion";
    private static String ID = "AegisMinion";
    private AbstractMonster target;

    public static String minionImage = "magineerResources/images/minions/aegis_crystal.png"; //Should be correct.

    public AegisMinion(int offsetX, int offsetY, IntPair minionSlot) {
        super(NAME, ID, 20, 0f, 0f, 320f, 320f, minionImage, offsetX, offsetY, minionSlot);
        countdown = countdownMax = 3;
        addMoves();
        pendingMove = autoPickMove();
    }

    @Override
    public void addMoves(){
        this.moves.addMove(new AutoMinionDefendMove(this, 8));
        this.moves.addMove(new AutoMinionTauntDefendMove(this, 8));
    }

}