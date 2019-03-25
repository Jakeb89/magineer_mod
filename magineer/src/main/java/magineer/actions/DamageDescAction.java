package magineer.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

import static magineer.MagineerMod.makeCardPath;
import static magineer.characters.Magineer.logger;

public class DamageDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(DamageDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    public DamageDescAction(MagineerCard sourceCard){
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
        logger.info(this.getClass().getSimpleName()+".update()");
        AbstractDungeon.actionManager.addToBottom(new DamageAction(cardTarget, new DamageInfo(player, sourceCard.damage)));
        isDone = true;
    }
}
