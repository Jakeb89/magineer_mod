package magineer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import magineer.cards.MagineerCard;
import magineer.characters.Magineer;

import java.util.ArrayList;

import static magineer.MagineerMod.logger;

public class DescriptiveAction extends AbstractGameAction {
    public MagineerCard sourceCard = null;
    public DescriptiveAction sourceAction = null;
    public AbstractPlayer player = AbstractDungeon.player;
    public AbstractMonster cardTarget = null;

    public DescriptiveAction(MagineerCard sourceCard){
        this.sourceCard = sourceCard;
    }

    public String getDescription(){

        return "";
    }

    public String getShortDescription(){

        return getDescription();
    }

    public void setTarget(AbstractMonster monster){
        this.cardTarget = monster;
    }

    public int getDescLengthDelta(){
        int longDesc = getDescription().length();
        int shortDesc = getShortDescription().length();
        return longDesc - shortDesc;
    }

    @Override
    public void update() {
        //Do stuff here.
        Magineer.logger.info(this.getClass().getSimpleName()+".update()");
        isDone = true;
    }

    public void receiveCallback(AbstractCard card){
        logger.info(DescriptiveAction.class.getSimpleName()+".receiveCallback()");

    }

    public void receiveCallback(ArrayList<AbstractCard> chosenCards){
        logger.info(DescriptiveAction.class.getSimpleName()+".receiveCallback()");

    }

    public void sendCallback(AbstractCard chosenCard) {
        logger.info(DescriptiveAction.class.getSimpleName()+".sendCallback()");
        if(sourceAction != null){
            logger.info("sending callback to "+sourceAction);
            sourceAction.receiveCallback(chosenCard);
            return;
        }
        if(sourceCard != null) {
            logger.info("sending callback to "+sourceCard);
            sourceCard.actionCallback(chosenCard);
        }
    }

    public void sendCallback(ArrayList<AbstractCard> chosenCards) {
        logger.info(DescriptiveAction.class.getSimpleName()+".sendCallback()");
        if(sourceAction != null){
            logger.info("sending callback to "+sourceAction);
            sourceAction.receiveCallback(chosenCards);
            return;
        }
        if(sourceCard != null) {
            logger.info("sending callback to "+sourceCard);
            sourceCard.actionCallback(chosenCards);
        }
    }
}
