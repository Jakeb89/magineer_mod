package magineer.util;

import basemod.BaseMod;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import magineer.cards.*;

import java.util.ArrayList;
import java.util.HashMap;

public class CardList {
    private static ArrayList<AbstractCard> cardList = new ArrayList<>();
    private static HashMap<String, Integer> startingDeckAmountMap = new HashMap<>();
    private static HashMap<String, Boolean> autoUnlockMap = new HashMap<>();

    static{
        addCard(new MagineerStrike(), 5, true);
        addCard(new MagineerDefend(), 4, true);
        addCard(new Blueprint(), 1, true);
        addCard(new Implement(), 1, true);

        addCard(new SummonCrystal());
        addCard(new SummonAegis());

        addCard(new UncommonAttack());
        addCard(new UncommonPower());

        addCard(new RareAttack());
        addCard(new RareSkill());
        addCard(new RarePower());

        addCard(new ImprovingStrike());
        addCard(new ImprovingDefend());
    }

    private static void addCard(AbstractCard card){
        addCard(card, 0);
    }

    private static void addCard(AbstractCard card, int startingDeckAmount){
        addCard(card, 0, false);
    }

    private static void addCard(AbstractCard card, int startingDeckAmount, boolean autoUnlock){
        cardList.add(card);
        startingDeckAmountMap.put(card.cardID, startingDeckAmount);
        autoUnlockMap.put(card.cardID, autoUnlock);
    }

    public static void addCards() {
        for(AbstractCard card : cardList){
            BaseMod.addCard(card);
        }
    }

    public static void unlockCards() {
        for(AbstractCard card : cardList){
            UnlockTracker.unlockCard(card.cardID);
        }
    }

    public static ArrayList<String> getStartingDeck() {
        ArrayList<String> startingDeck = new ArrayList<>();

        for(AbstractCard card : cardList){
            for(int i=0; i<startingDeckAmountMap.get(card.cardID); i++) {
                startingDeck.add(card.cardID);
            }
        }

        return startingDeck;
    }

}
