package magineer.minions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

public class AutoMinionAttackMove extends AutoMinionMove{
    public int numAttacks = 1;
    public int attackSize = 0;

    public AutoMinionAttackMove(AbstractFriendlyMonster owner, int numAttacks, int attackSize) {
        super("Attack", owner);
        this.numAttacks = numAttacks;
        this.attackSize = attackSize;
        this.intent = AbstractMonster.Intent.ATTACK;
        this.image = this.getIntentImage();
    }

    @Override
    public void run(){
        AbstractMonster target = AbstractDungeon.getRandomMonster();
        for(int i=0; i<numAttacks; i++){
            DamageInfo info = new DamageInfo(owner, attackSize, DamageInfo.DamageType.NORMAL);
            info.applyPowers(owner, target); // <--- This lets powers effect minions attacks
            AbstractDungeon.actionManager.addToBottom(new DamageAction(target, info));
        }
    }

    @Override
    public void renderEffectAmount(SpriteBatch sb){
        renderDamageRange(sb, numAttacks, attackSize);
    }

    private void renderDamageRange(SpriteBatch sb, int attackNum, int attackDmg) {
        Color intentColor = Color.WHITE.cpy();
        if (attackNum == 1) {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(attackDmg),
                    this.owner.hb.cX + 16f * Settings.scale, this.owner.hb.cY + (this.bobEffect.y + 80f - 8.0F) * Settings.scale, intentColor);
        } else {
            FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(attackNum) + "x" + Integer.toString(attackDmg),
                    this.owner.hb.cX + 16f * Settings.scale, this.owner.hb.cY + (this.bobEffect.y + 80f - 8.0F) * Settings.scale, intentColor);
        }
    }

    @Override
    public String getMoveDescription(){
        if(numAttacks == 1){
            return "Deal "+attackSize+" damage.";
        }
        return "Deal "+attackSize+" damage "+numAttacks+" times.";
    }
}
