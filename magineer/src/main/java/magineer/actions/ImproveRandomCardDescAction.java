package magineer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

import java.util.ArrayList;

public class ImproveRandomCardDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(ImproveRandomCardDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    private final CardGroup cardGroup;
    private final int numCards;
    private final MagineerCard.SLOTTYPE slotType;


    public ImproveRandomCardDescAction(MagineerCard sourceCard, CardGroup cardGroup, int numCards, MagineerCard.SLOTTYPE slotType, int amount) {
        super(sourceCard);
        this.cardGroup = cardGroup;
        this.numCards = numCards;
        this.slotType = slotType;
        this.amount = amount;
    }

    @Override
    public String getDescription(){
        String desc = "";
        switch(slotType){
            case ORANGE:
                desc += DESCRIPTION[0];
                break;
            case BLUE:
                desc += DESCRIPTION[1];
                break;
            case GRAY:
            default:
                desc += DESCRIPTION[2];
        }
        if(numCards == 1){
            return desc + DESCRIPTION[3];
        }
        return desc + numCards + DESCRIPTION[4];
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
