package magineer.cards;

import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.localization.CardStrings;
import hexui_lib.util.RenderImageLayer;
import magineer.MagineerMod;
import magineer.actions.UpgradeCardsInGroupDescAction;
import magineer.characters.Magineer;
import magineer.util.LocalStringGetter;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class Implement extends MagineerCard {

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("Implement");
    private static final CardStrings cardStrings = LocalStringGetter.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = "-"; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "implement.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Magineer.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int MAGIC = 1;
    private static final int UPGRADE_PLUS_MAGIC = 1;


    // /STAT DECLARATION/


    public Implement() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "A lever with which to move the world.";
        magicNumber = baseMagicNumber = MAGIC;

        cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));

        actionList.add(new UpgradeCardsInGroupDescAction(this, CardGroup.CardGroupType.HAND, 1, false));

        updateDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            updateDescription();
        }
    }

    @Override
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}
