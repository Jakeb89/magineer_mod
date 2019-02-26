package magineer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import magineer.cards.MagineerCard;

public class ChooseCardsFromGroupAction extends AbstractGameAction {

    // private static final UIStrings uiStrings =
    // CardCrawlGame.languagePack.getUIString("SkillFromDeckToHandAction");
    // public static final String[] TEXT = uiStrings.TEXT;
    private AbstractPlayer p;
    private CardGroup cardGroup;
    private MagineerCard source = null;
    private boolean randomizeView = false;
    private String choosingText;

    public ChooseCardsFromGroupAction(MagineerCard source, CardGroup cardGroup, int amount, boolean randomizeView, String choosingText) {
        this.p = AbstractDungeon.player;
        setValues(this.p, AbstractDungeon.player, amount);
        this.actionType = AbstractGameAction.ActionType.CARD_MANIPULATION;
        this.duration = Settings.ACTION_DUR_MED;
        this.cardGroup = cardGroup;
        this.source = source;
        this.randomizeView = randomizeView;
        this.choosingText = choosingText;
    }

    public void update() {
        CardGroup tmp;
        if (this.duration == Settings.ACTION_DUR_MED) {
            tmp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : cardGroup.group) {
                if(source != null) {
                    if(source.customCardTest(c)) {
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
                doActionCallback(null);
                this.isDone = true;
                return;
            }
            if (tmp.size() == 1) {
                AbstractCard card = tmp.getTopCard();

                doActionCallback(card);

                this.isDone = true;
                return;
            }
            AbstractDungeon.gridSelectScreen.open(tmp, this.amount, "Choose "+amount+" card"+(amount!=1?"s":"")+choosingText+".", false);
            tickDuration();
            return;
        }

        if (AbstractDungeon.gridSelectScreen.selectedCards.size() != 0) {
            for (AbstractCard c : AbstractDungeon.gridSelectScreen.selectedCards) {
                c.unhover();
                if (this.p.hand.size() == 10) {
                    //cardGroup.moveToDiscardPile(c);
                    doActionCallback(c);
                    //this.p.createHandIsFullDialog();
                } else {
                    //cardGroup.removeCard(c);
                    //this.p.hand.addToTop(c);
                    doActionCallback(c);
                }
                this.p.hand.refreshHandLayout();
                this.p.hand.applyPowers();
            }
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            this.p.hand.refreshHandLayout();
        }

        tickDuration();
    }

    private void doActionCallback(AbstractCard card) {
        if(source != null) {
            source.actionCallback(card);
        }
    }
}
