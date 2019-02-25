package magineer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import magineer.cards.MagineerCard;
import magineer.powers.CommonPower;

public class ImproveCardAction extends AbstractGameAction {
    private AbstractCard card;
    private int amount;

    public ImproveCardAction(final AbstractCard card, final int amount){
        this.card = card;
        this.amount = amount;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        if(card instanceof MagineerCard){
            ((MagineerCard)card).addImprovements(amount);
        }
        isDone = true;
    }
}
