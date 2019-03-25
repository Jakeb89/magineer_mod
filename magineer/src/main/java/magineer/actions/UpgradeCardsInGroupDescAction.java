package magineer.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

import java.util.ArrayList;

import static magineer.MagineerMod.logger;

public class UpgradeCardsInGroupDescAction extends DescriptiveAction {
    public static final String ID = MagineerMod.makeID(UpgradeCardsInGroupDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;

    private CardGroup.CardGroupType cardGroupType;
    private int numCards;
    private boolean randomizeView;
    public String customPileName = "";
    public CardGroup customCardGroup;

    public UpgradeCardsInGroupDescAction(MagineerCard sourceCard, CardGroup.CardGroupType cardGroupType, int numCards, boolean randomizeView){
        super(sourceCard);
        this.cardGroupType = cardGroupType;
        this.numCards = numCards;
        this.randomizeView = randomizeView;
    }

    @Override
    public String getDescription(){
        String desc = DESCRIPTION[0];
        if(numCards == 1){
            desc += DESCRIPTION[1];
        }else{
            desc += DESCRIPTION[2];
        }

        switch(cardGroupType){
            case HAND:
                desc += DESCRIPTION[3];
                break;
            case DRAW_PILE:
                desc += DESCRIPTION[4];
                break;
            case DISCARD_PILE:
                desc += DESCRIPTION[5];
                break;
            case EXHAUST_PILE:
                desc += DESCRIPTION[6];
                break;
            default:
                desc += customPileName + ".";
        }

        desc = desc.replace("!X!", numCards+"");
        return desc;
    }

    @Override
    public void update() {
        //Do stuff here.
        AbstractDungeon.actionManager.addToBottom(new ChooseCardsFromGroupDescAction(this, getCardGroup(), numCards, randomizeView));
        isDone = true;
    }

    private CardGroup getCardGroup(){
        switch(cardGroupType) {
            case HAND:
                return AbstractDungeon.player.hand;
            case DRAW_PILE:
                return AbstractDungeon.player.drawPile;
            case DISCARD_PILE:
                return AbstractDungeon.player.discardPile;
            case EXHAUST_PILE:
                return AbstractDungeon.player.exhaustPile;
            default:
                return customCardGroup;
        }
    }

    @Override
    public void receiveCallback(ArrayList<AbstractCard> cards){
        logger.info(UpgradeCardsInGroupDescAction.class.getSimpleName()+".receiveCallback()");
        for(AbstractCard card : cards){
            card.upgrade();
            card.update();
            card.flash();
        }
    }
}
