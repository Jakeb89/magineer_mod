package magineer.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hexui_lib.util.RenderCommandLayer;
import hexui_lib.util.RenderImageLayer;
import hexui_lib.util.RenderLayer;
import magineer.MagineerMod;
import magineer.actions.ImproveCardAction;
import magineer.characters.Magineer;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class MagineerStrike extends MagineerCard{

    /*
     * Wiki-page: https://github.com/daviscook477/BaseMod/wiki/Custom-Cards
     *
     * Strike Deal 7(9) damage.
     */

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("MagineerStrike");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name
    // and make sure it's in the correct folder. That's all.
    // There's makeCardPath, makeRelicPath, power, orb, event, etc..
    // The list of all of them can be found in the main MagineerMod.java file in the
    // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==



    public static final String NAME = "Strike"; //cardStrings.NAME;
    public static final String DESCRIPTION = "Deal !D! damage. NL magineer:Self-Improving."; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "magineer_strike.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Magineer.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 5;
    private static final int UPGRADE_PLUS_DMG = 3;

    // Hey want a second damage/magic/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and magineer.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variabls they personally have and create your own ones.

    // /STAT DECLARATION/

    public MagineerStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "There is a reason that spears are one of the oldest weapons known to man. " +
                "You may not be a swordfighter, but you /will/ learn to use a spear.";

        MagineerMod.logger.info("ID: "+ID);
        MagineerMod.logger.info("cardStrings: "+cardStrings);
        MagineerMod.logger.info(CardCrawlGame.languagePack);
        MagineerMod.logger.info("CardCrawlGame.languagePack: "+CardCrawlGame.languagePack);


        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = DAMAGE;

        this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Shadow form, demon form, echo form, etc.) cards so that they function correctly.
        this.tags.add(CardTags.STRIKE);
        isFoil = true;

        cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));

        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.ORANGE);
    }

    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(
                new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn),
                        AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        AbstractDungeon.actionManager.addToBottom(new ImproveCardAction(this, 1));
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            initializeDescription();
        }
    }

    @Override
    public void gainedImprovementLevel(int level){
        upgradeDamage(1);
    }

    @Override
    public void lostImprovementLevel(int level){
        upgradeDamage(-1);
    }

    public void update(){
        super.update();
    }

    @Override
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}
