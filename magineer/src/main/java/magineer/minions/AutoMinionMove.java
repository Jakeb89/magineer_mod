package magineer.minions;

import basemod.BaseMod;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.Hitbox;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.BobEffect;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.monsters.MinionMove;
import magineer.util.TextureLoader;

import static magineer.characters.Magineer.logger;

public class AutoMinionMove extends MinionMove {
    public Runnable moveActions;
    public AutoMinion owner;
    public BobEffect bobEffect;
    public String ID;
    public float intentAngle;
    public int num1 = 0;
    public int num2 = 0;
    public String moveDescription = "";

    public static final String intentIconsFolder = "magineerResources/images/minions/intents/";
    public static final Texture[] intentAttack = new Texture[7];
    public static final Texture intentDefend = TextureLoader.getTexture(intentIconsFolder +"_0009_defendL.png");

    static{
        intentAttack[0] = TextureLoader.getTexture(intentIconsFolder +"_0006_attack_intent_1.png");
        intentAttack[1] = TextureLoader.getTexture(intentIconsFolder +"_0005_attack_intent_2.png");
        intentAttack[2] = TextureLoader.getTexture(intentIconsFolder +"_0004_attack_intent_3.png");
        intentAttack[3] = TextureLoader.getTexture(intentIconsFolder +"_0003_attack_intent_4.png");
        intentAttack[4] = TextureLoader.getTexture(intentIconsFolder +"_0002_attack_intent_5.png");
        intentAttack[5] = TextureLoader.getTexture(intentIconsFolder +"_0001_attack_intent_6.png");
        intentAttack[6] = TextureLoader.getTexture(intentIconsFolder +"_0000_attack_intent_7.png");
    }

    public void run(){
        //Override this.
    }

    public boolean isAttack() {
        if(intent.name().toLowerCase().contains("attack")) return true;
        return false;
    }

    public boolean isDefend() {
        if(intent.name().toLowerCase().contains("defend")) return true;
        return false;
    }

    /*
    public enum MOVETYPE{
        ATTACK,
        DEFEND,
        DEBUFF,
        STRONG_DEBUFF
    }

    private MOVETYPE moveType;
    */
    public AbstractMonster.Intent intent;

    public AutoMinionMove(String ID, AbstractFriendlyMonster owner) {
        super(ID, owner, null, "", null);
        this.ID = ID;
        this.bobEffect = new BobEffect();
        int INTENT_HB_W = 128;
        this.owner = (AutoMinion) owner;
        this.intentAngle = 0.0F;
        this.intent = AbstractMonster.Intent.UNKNOWN;
        this.image = this.getIntentImage();
    }

    @Override
    protected void onClick() {
        //No clicky!
        return;
    }
    @Override
    protected void onHover() {
        //No hovery!
        return;
    }

    public void render(SpriteBatch sb) {
        renderIntent(sb);
        renderCountdown(sb, owner.countdown);
        renderEffectAmount(sb);
    }

    public void renderIntent(SpriteBatch sb) {
        bobEffect.update();
        sb.setColor(Color.WHITE.cpy());

        if (intent != AbstractMonster.Intent.DEBUFF && intent != AbstractMonster.Intent.STRONG_DEBUFF) {
            this.intentAngle = 0.0F;
        } else {
            this.intentAngle += Gdx.graphics.getDeltaTime() * 150.0F;
        }

        sb.draw(image, this.owner.hb.cX - 64.0F, this.owner.hb.cY - 64.0F + (this.bobEffect.y + 80f) * Settings.scale,
                64.0F, 64.0F, 128.0F, 128.0F, Settings.scale, Settings.scale, this.intentAngle,
                0, 0, 128, 128, false, false);
    }

    public void renderCountdown(SpriteBatch sb, int countdown) {
        Color intentColor = Color.DARK_GRAY.cpy();
        intentColor.lerp(Color.WHITE.cpy(), 1/countdown);
        FontHelper.renderFontRightTopAligned(sb, FontHelper.topPanelInfoFont, Integer.toString(countdown),
                this.owner.hb.cX + 16f * Settings.scale, this.owner.hb.cY + (this.bobEffect.y + 80f + 20.0F) * Settings.scale, intentColor);
    }

    public void renderEffectAmount(SpriteBatch sb){
        //Override this.
    }

    public Texture getIntentImage() {
        switch(intent){
            case ATTACK:
                return intentAttack[0];
            case DEFEND:
                return intentDefend;
        }
        return null;
    }

    @Override
    public String getMoveDescription(){
        return moveDescription;
    }

    public void actionCallback() {
        //Override this if you need it.
    }
}
