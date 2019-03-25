package magineer.actions;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

public class ImprovingMagicDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(ImprovingMagicDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    int amount = 1;

    public ImprovingMagicDescAction(MagineerCard sourceCard){
        super(sourceCard);
    }

    @Override
    public String getDescription(){
        return DESCRIPTION[0].replace("!X!", amount+"");
    }

    @Override
    public String getShortDescription(){
        return DESCRIPTION[1].replace("!X!", amount+"");
    }

    @Override
    public void update() {
        //Do stuff here.
        isDone = true;
    }
}
