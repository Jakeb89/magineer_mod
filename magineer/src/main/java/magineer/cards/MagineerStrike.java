package magineer.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import hexui_lib.util.RenderImageLayer;
import magineer.MagineerMod;
import magineer.actions.DamageDescAction;
import magineer.characters.Magineer;
import magineer.util.LocalStringGetter;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class MagineerStrike extends MagineerCard{

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("MagineerStrike");
    private static final CardStrings cardStrings = LocalStringGetter.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name
    // and make sure it's in the correct folder. That's all.
    // There's makeCardPath, makeRelicPath, power, orb, event, etc..
    // The list of all of them can be found in the main MagineerMod.java file in the
    // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==



    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = "-"; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "magineer_strike.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.ENEMY;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Magineer.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 7;
    private static final int UPGRADE_PLUS_DMG = 2;

    // Hey want a second damage/magic/block/unique number??? Great!
    // Go check out DefaultAttackWithVariable and magineer.variable.DefaultCustomVariable
    // that's how you get your own custom variable that you can use for anything you like.
    // Feel free to explore other mods to see what variabls they personally have and create your own ones.

    // /STAT DECLARATION/

    public MagineerStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "There is a reason that spears are one of the oldest weapons known to man. " +
                "You may not be a swordfighter, but at the very least you /will/ learn to use a spear.";

        MagineerMod.logger.info("ID: "+ID);
        MagineerMod.logger.info("cardStrings: "+cardStrings);
        MagineerMod.logger.info(CardCrawlGame.languagePack);
        MagineerMod.logger.info("CardCrawlGame.languagePack: "+CardCrawlGame.languagePack);


        // Aside from baseDamage/MagicNumber/Block there's also a few more.
        // Just type this.base and let intelliJ auto complete for you, or, go read up AbstractCard

        baseDamage = DAMAGE;

        this.tags.add(BaseModCardTags.BASIC_STRIKE); //Tag your strike, defend and form (Shadow form, demon form, echo form, etc.) cards so that they function correctly.
        this.tags.add(CardTags.STRIKE);

        cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));

        actionList.add(new DamageDescAction(this));

        updateDescription();
    }

    // Upgraded stats.
    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DMG);
            updateDescription();
        }
    }

    @Override
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}
