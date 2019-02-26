package magineer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import magineer.cards.MagineerCard;

import java.util.ArrayList;

public class ImproveRandomCardAction extends AbstractGameAction {
    private final CardGroup cardGroup;
    private final int numCards;
    private final int amount;
    private final MagineerCard.SLOTTYPE slotType;


    public ImproveRandomCardAction(CardGroup cardGroup, int numCards, int amount, MagineerCard.SLOTTYPE slotType) {
        this.cardGroup = cardGroup;
        this.numCards = numCards;
        this.amount = amount;
        this.slotType = slotType;
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
