package magineer.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.localization.CardStrings;
import hexui_lib.util.RenderImageLayer;
import magineer.MagineerMod;
import magineer.actions.*;
import magineer.characters.Magineer;
import magineer.util.LocalStringGetter;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class MagineerDefend extends MagineerCard {

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("MagineerDefend");
    private static final CardStrings cardStrings = LocalStringGetter.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = "-"; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "magineer_defend.png";

    // /chooseDesc DECLARATION/


    // STAT DECLARATION 	

    private static final CardRarity RARITY = CardRarity.BASIC;
    private static final CardTarget TARGET = CardTarget.SELF;
    private static final CardType TYPE = CardType.SKILL;
    public static final CardColor COLOR = Magineer.Enums.COLOR_GRAY;

    private static final int COST = 1;
    private static final int BLOCK = 5;
    private static final int UPGRADE_PLUS_BLOCK = 3;


    // /STAT DECLARATION/


    public MagineerDefend() {
        super(ID, NAME, IMG, COST, DESCRIPTION, TYPE, COLOR, RARITY, TARGET);
        artistNames.add("Jake Berry");
        flavorText = "Conserve your mana, and a free hand will serve you better than any flat piece of wood or metal.";
        baseBlock = BLOCK;

        this.tags.add(BaseModCardTags.BASIC_DEFEND);



        cardArtLayers512.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        cardArtLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));


        actionList.add(new BlockDescAction(this));

        updateDescription();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            updateDescription();
        }
    }

    @Override
    public String getFoilArtFilename(){
        return portraitFilename;
    }
}
