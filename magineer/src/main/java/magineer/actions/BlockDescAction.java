package magineer.actions;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

import static magineer.characters.Magineer.logger;

public class BlockDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(BlockDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    public BlockDescAction(MagineerCard sourceCard){
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
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(player, player, sourceCard.block));
        isDone = true;
    }
}
