package magineer.actions;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import magineer.MagineerMod;
import magineer.cards.MagineerCard;
import magineer.util.LocalStringGetter;

import java.util.ArrayList;

import static magineer.MagineerMod.logger;

public class ChooseCardsFromGroupDescAction extends DescriptiveAction {

    public static final String ID = MagineerMod.makeID(ChooseCardsFromGroupDescAction.class.getSimpleName());
    private static final OrbStrings actionStrings = LocalStringGetter.getActionStrings(ID);
    public static final String[] DESCRIPTION = actionStrings.DESCRIPTION;


    // private static final UIStrings uiStrings =
    // CardCrawlGame.languagePack.getUIString("SkillFromDeckToHandAction");
    // public static final String[] TEXT = uiStrings.TEXT;
    public AbstractPlayer p;
    public CardGroup cardGroup;
    public boolean randomizeView = false;
    public ArrayList<AbstractCard> chosenCards = new ArrayList<AbstractCard>();

    public ChooseCardsFromGroupDescAction(MagineerCard sourceCard, CardGroup cardGroup, int amount, boolean randomizeView) {
        super(sourceCard);
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.cardGroup = cardGroup;
        this.randomizeView = randomizeView;
    }

    public ChooseCardsFromGroupDescAction(DescriptiveAction sourceAction, CardGroup cardGroup, int amount, boolean randomizeView) {
        this(sourceAction.sourceCard, cardGroup, amount, randomizeView);
        this.sourceAction = sourceAction;
    }

    public void update() {
        CardGroup tmp;
        if (this.duration == Settings.ACTION_DUR_MED) {
            tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : cardGroup.group) {
                if(sourceCard != null) {
                    if(sourceCard.customCardTest(c)) {
                        if(randomizeView){
                            tmp.addToRandomSpot(c);
                        }else {
                            tmp.addToTop(c);
                        }
                    }
                }else {
                    if(randomizeView){
                        tmp.addToRandomSpot(c);
                    }else {
                        tmp.addToTop(c);
                    }
                }
            }
            if (tmp.size() == 0) {
                isDone();
                return;
            }
            if (tmp.size() == 1) {
                AbstractCard card = tmp.getTopCard();

                addChosenCard(card);

                isDone();
                return;
            }
            String tipMsg = DESCRIPTION[0];
            if(amount != 1){
                tipMsg = DESCRIPTION[1];
                tipMsg = tipMsg.replace("!X!", amount+"");
            }
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, tipMsg, false);
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                if (this.p.hand.size() == 10) {
                    //cardGroup.moveToDiscardPile(c);
                    addChosenCard(c);
                    //this.p.createHandIsFullDialog();
                } else {
                    //cardGroup.removeCard(c);
                    //this.p.hand.addToTop(c);
                    addChosenCard(c);
                }
                this.p.hand.refreshHandLayout();
                this.p.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }

        tickDuration();
    }

    private void addChosenCard(AbstractCard card){
        chosenCards.add(card);
    }

    @Override
    protected void tickDuration() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone();
        }
    }

    private void isDone(){
        logger.info(ChooseCardsFromGroupDescAction.class.getSimpleName()+" > isDone(). Cards Chosen:");
        for(AbstractCard card : chosenCards){
            logger.info(">"+card.cardID);
        }
        logger.info("sourceAction: " + sourceAction);
        logger.info("sourceCard: " + sourceCard);
        sendCallback(chosenCards);
        this.isDone = true;
    }
}
