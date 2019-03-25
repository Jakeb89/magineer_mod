package magineer.actions;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LangUtil;
import magineer.util.LocalStringGetter;

import java.util.ArrayList;

public class ImplementDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(ImplementDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    private final CardGroup cardGroup;
    private final int numCards;
    private final MagineerCard.SLOTTYPE slotType;

    public ImplementDescAction(MagineerCard sourceCard, CardGroup cardGroup, int numCards, MagineerCard.SLOTTYPE slotType) {
        super(sourceCard);
        this.cardGroup = cardGroup;
        this.numCards = numCards;
        this.slotType = slotType;
    }

    @Override
    public String getDescription(){
        if(sourceCard.magicNumber == 1){
            return DESCRIPTION[0];
        }
        return DESCRIPTION[1];
    }

    @Override
    public void update() {
        ArrayList<MagineerCard> cardChoices = new ArrayList<MagineerCard>();
        for(AbstractCard card : cardGroup.group){
            if(card instanceof MagineerCard){
                MagineerCard mCard = (MagineerCard) card;
                if(amount > 0){
                    if(mCard.couldBeImprovedBy(slotType)){
                        cardChoices.add(mCard);
                    }
                }else{
                    cardChoices.add(mCard);
                }
            }
        }
        while(cardChoices.size() > numCards){
            cardChoices.remove((int)(Math.random()*cardChoices.size()));
        }
        for(MagineerCard card : cardChoices){
            card.addImprovements(amount, slotType);
            card.flash();
        }
        isDone = true;
    }
}
