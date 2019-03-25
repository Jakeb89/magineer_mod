package magineer.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

public class SelfImprovingDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(SelfImprovingDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    public SelfImprovingDescAction(MagineerCard sourceCard){
        super(sourceCard);
    }

    @Override
    public String getDescription(){
        return DESCRIPTION[0];
    }

    @Override
    public String getShortDescription(){
        if(DESCRIPTION.length > 1){
            return DESCRIPTION[1];
        }
        return DESCRIPTION[0];
    }

    @Override
    public void update() {
        //Do stuff here.
        sourceCard.addImprovements(1);
        isDone = true;
    }
}
