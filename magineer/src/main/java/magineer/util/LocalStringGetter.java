package magineer.util;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.localization.OrbStrings;

public class LocalStringGetter {
    public static CardStrings getCardStrings(String ID) {
        CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        if(cardStrings == null) {
            cardStrings = new CardStrings();
        }

        if (cardStrings.NAME == null)
            cardStrings.NAME = "[Missing Name]";
        if (cardStrings.DESCRIPTION == null)
            cardStrings.DESCRIPTION = "[Missing Desc.]";
        if (cardStrings.UPGRADE_DESCRIPTION == null)
            cardStrings.UPGRADE_DESCRIPTION = "[Missing Upg. Desc.]";
        if (cardStrings.UPGRADE_DESCRIPTION == null)
            cardStrings.UPGRADE_DESCRIPTION = "[Missing Upg. Desc.]";
        if (cardStrings.EXTENDED_DESCRIPTION == null) {
            cardStrings.EXTENDED_DESCRIPTION = new String[10];
            for (int i = 0; i < 10; i++) {
                cardStrings.EXTENDED_DESCRIPTION[i] = "[Ext.Desc.]";
            }
        }

        return cardStrings;
    }

    public static OrbStrings getActionStrings(String ID) {
        OrbStrings actionStrings = CardCrawlGame.languagePack.getOrbString(ID);
        if(actionStrings == null) {
            actionStrings = new OrbStrings();
        }

        if (actionStrings.NAME == null)
            actionStrings.NAME = "[Missing Name]";
        if (actionStrings.DESCRIPTION == null) {
            actionStrings.DESCRIPTION = new String[10];
            for (int i = 0; i < 10; i++) {
                actionStrings.DESCRIPTION[i] = "[Ext.Desc.]";
            }
        }

        return actionStrings;
    }
}
