package magineer.minions;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.EnemyMoveInfo;
import com.megacrit.cardcrawl.monsters.beyond.WrithingMass;
import kobting.friendlyminions.enums.MonsterIntentEnum;
import kobting.friendlyminions.helpers.MonsterHelper;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import magineer.actions.MinionMoveCallbackAction;

import java.lang.reflect.Field;
import java.util.ArrayList;

public class AutoMinionTauntDefendMove extends AutoMinionMove{
    public int blockSize = 0;

    public AutoMinionTauntDefendMove(AbstractFriendlyMonster owner, int blockSize) {
        super("Taunt", owner);
        this.blockSize = blockSize;
        this.intent = AbstractMonster.Intent.DEFEND;
        this.image = this.getIntentImage();
    }

    @Override
    public void run(){
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(owner, owner, blockSize));
        AbstractDungeon.actionManager.addToBottom(new MinionMoveCallbackAction(this));
    }

    @Override
    public void renderEffectAmount(SpriteBatch sb){
        renderBlockAmount(sb, blockSize);
    }

    private void renderBlockAmount(SpriteBatch sb, int blockAmount) {
        Color intentColor = Color.WHITE.cpy();
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(blockAmount),
                this.owner.hb.cX + 16f * Settings.scale, this.owner.hb.cY + (this.bobEffect.y + 80f - 8.0F) * Settings.scale, intentColor);
        //WrithingMass
        //StunMonsterAction
    }

    @Override
    public String getMoveDescription(){
        return "Gain "+blockSize+" Block. NL Taunt a random attacking enemy.";
    }

    @Override
    public void actionCallback() {
        ArrayList<AbstractMonster> monsterList = new ArrayList<>();
        for(AbstractMonster monster : AbstractDungeon.getMonsters().monsters){
            if(monster.intent.name().toLowerCase().contains("attack")){
                if(MonsterHelper.getTarget(monster) != owner) {
                    monsterList.add(monster);
                }
            }
        }

        if(monsterList.size() == 0) return;

        int chosenMonster = (int)(Math.random()*monsterList.size());
        //MonsterHelper.setTarget(monsterList.get(chosenMonster), owner);
        AbstractMonster monster = monsterList.get(chosenMonster);

        AbstractMonster.Intent newIntent;

        switch (intent) {
            case ATTACK_BUFF:
                newIntent = MonsterIntentEnum.ATTACK_MINION_BUFF;
                break;
            case ATTACK_DEBUFF:
                newIntent = MonsterIntentEnum.ATTACK_MINION_DEBUFF;
                break;
            case ATTACK_DEFEND:
                newIntent = MonsterIntentEnum.ATTACK_MINION_DEFEND;
                break;
            case ATTACK:
            default:
                newIntent = MonsterIntentEnum.ATTACK_MINION;
                break;
        }

        try {
            Field moveInfo = AbstractMonster.class.getDeclaredField("move");
            moveInfo.setAccessible(true);


            Field f = AbstractMonster.class.getDeclaredField("move");
            f.setAccessible(true);
            EnemyMoveInfo move = (EnemyMoveInfo)f.get(monster);
            int multiplier = 1;
            if (move.isMultiDamage) {
                multiplier = move.multiplier;
            }

            f = AbstractMonster.class.getDeclaredField("intentDmg");
            f.setAccessible(true);
            move.baseDamage = f.getInt(monster);

            EnemyMoveInfo newInfo = new EnemyMoveInfo(monster.nextMove, newIntent, move.baseDamage, multiplier, move.isMultiDamage);
            moveInfo.set(monster, newInfo);

            monster.setMove(monster.nextMove, newIntent, move.baseDamage, multiplier, move.isMultiDamage);
            MonsterHelper.setTarget(monster, owner);

            monster.createIntent();
            //monster.applyPowers();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
