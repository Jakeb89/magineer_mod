package magineer.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;
import magineer.util.MinionManager;

import static magineer.characters.Magineer.logger;

public class SummonMinionDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(SummonMinionDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    private MinionManager.MINIONTYPE minionType = null;
    private int minionCount = 1;

    public SummonMinionDescAction(MagineerCard sourceCard, MinionManager.MINIONTYPE minionType, int minionCount) {
        super(sourceCard);
        this.minionType = minionType;
        this.minionCount = minionCount;
    }

    @Override
    public String getDescription(){
        String desc = "";
        if(minionCount == 1){
            desc = DESCRIPTION[0];
        }else{
            desc = DESCRIPTION[1];
        }
        desc = desc.replace("!X!", minionCount+"");
        desc = desc.replace("!Y!", MinionManager.getMinionName(minionType));
        return desc;
    }

    @Override
    public void update() {
        //Do stuff here.
        logger.info(this.getClass().getSimpleName()+".update()");
        for(int i=0; i<minionCount; i++){
            MinionManager.addMinion(minionType);
        }
        isDone = true;
    }
}
