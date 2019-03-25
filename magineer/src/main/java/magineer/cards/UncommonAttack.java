package magineer.cards;

import com.megacrit.cardcrawl.localization.CardStrings;
import hexui_lib.util.RenderImageLayer;
import magineer.MagineerMod;
import magineer.actions.*;
import magineer.characters.Magineer;
import magineer.util.LocalStringGetter;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class UncommonAttack extends MagineerCard {

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("UncommonAttack");
    private static final CardStrings cardStrings = LocalStringGetter.getCardStrings(ID);

    public static final String IMG = makeCardPath("Attack.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = "-"; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "default.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.UNCOMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.ATTACK;
    public static final CardColor COLOR = Magineer.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 8;
    private static final int UPGRADE_PLUS_DAMAGE = 4;
    private static final int BLOCK = 0;
    private static final int UPGRADE_PLUS_BLOCK = 0;
    private static final int MAGIC = 0;
    private static final int UPGRADE_PLUS_MAGIC = 0;


    // /STAT DECLARATION/


    public UncommonAttack() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "[Flavor Text]";
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;

        cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));

        setRandomSlots(5, SLOTTYPE.BLUE);

        actionList.add(new ImprovingDamageDescAction(this));
        actionList.add(new DamageDescAction(this));
        actionList.add(new SelfImprovingDescAction(this));

        updateDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_DAMAGE);
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            initializeDescription();
        }
    }

    @Override
    public void gainedImprovementLevel(int level){
        upgradeDamage(2);
    }

    @Override
    public void lostImprovementLevel(int level){
        upgradeDamage(-2);
    }

    @Override
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}
