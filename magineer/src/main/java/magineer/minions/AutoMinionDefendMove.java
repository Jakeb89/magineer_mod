package magineer.minions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;

public class AutoMinionDefendMove extends AutoMinionMove{
    public int blockSize = 0;

    public AutoMinionDefendMove(AbstractFriendlyMonster owner, int blockSize) {
        super("Defend", owner);
        this.blockSize = blockSize;
        this.intent = AbstractMonster.Intent.DEFEND;
        this.image = this.getIntentImage();
    }

    @Override
    public void run(){
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, blockSize));
    }

    @Override
    public void renderEffectAmount(SpriteBatch sb){
        renderBlockAmount(sb, blockSize);
    }

    private void renderBlockAmount(SpriteBatch sb, int blockAmount) {
        Color intentColor = Color.WHITE.cpy();
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(blockAmount),
                this.owner.hb.cX + 16f * Settings.scale, this.owner.hb.cY + (this.bobEffect.y + 80f - 8.0F) * Settings.scale, intentColor);
    }

    @Override
    public String getMoveDescription(){
        return "Gain "+blockSize+" Block.";
    }
}
