package magineer.cards;

import com.megacrit.cardcrawl.localization.CardStrings;
import magineer.MagineerMod;
import magineer.actions.DamageDescAction;
import magineer.actions.ImprovingDamageDescAction;
import magineer.actions.SelfImprovingDescAction;
import magineer.characters.Magineer;
import magineer.util.LocalStringGetter;

import static magineer.MagineerMod.makeCardPath;

public class ImprovingStrike extends MagineerCard{

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("ImprovingStrike");
    private static final CardStrings cardStrings = LocalStringGetter.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");
    // Setting the image as as easy as can possibly be now. You just need to provide the image name
    // and make sure it's in the correct folder. That's all.
    // There's makeCardPath, makeRelicPath, power, orb, event, etc..
    // The list of all of them can be found in the main MagineerMod.java file in the
    // ==INPUT TEXTURE LOCATION== section under ==MAKE IMAGE PATHS==



    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = "-"; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "default.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
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

    public ImprovingStrike() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "Again. Again. Again. He was beginning to hate that word.";

        baseDamage = DAMAGE;

        this.tags.add(CardTags.STRIKE);

        //cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        //cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));
        generateBetaArt();

        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.ORANGE);

        actionList.add(new ImprovingDamageDescAction(this));
        actionList.add(new DamageDescAction(this));
        actionList.add(new SelfImprovingDescAction(this));

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
    public void gainedImprovementLevel(int level){
        upgradeDamage(1);
    }

    @Override
    public void lostImprovementLevel(int level){
        upgradeDamage(-1);
    }

    @Override
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}