package magineer.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import magineer.MagineerMod;
import magineer.actions.DamageDescAction;
import magineer.actions.SummonMinionDescAction;
import magineer.characters.Magineer;
import magineer.util.LocalStringGetter;
import magineer.util.MinionManager;

import static magineer.MagineerMod.makeCardPath;

public class SummonCrystal extends MagineerCard {

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID(SummonCrystal.class.getSimpleName());
    private static final CardStrings cardStrings = LocalStringGetter.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String portraitFilename = "default.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.COMMON;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Magineer.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int DAMAGE = 4;
    private static final int UPGRADE_PLUS_DAMAGE = 2;
    private static final int BLOCK = 4;
    private static final int UPGRADE_PLUS_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPGRADE_PLUS_MAGIC = 2;


    // /STAT DECLARATION/


    public SummonCrystal() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "[Flavor Text]";
        baseDamage = DAMAGE;
        baseBlock = BLOCK;
        magicNumber = baseMagicNumber = MAGIC;

        //cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        //cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));
        generateBetaArt();

        actionList.add(new SummonMinionDescAction(this, MinionManager.MINIONTYPE.CRYSTAL, 1));

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
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}
