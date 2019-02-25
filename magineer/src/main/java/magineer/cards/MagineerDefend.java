package magineer.cards;

import basemod.helpers.BaseModCardTags;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hexui_lib.util.RenderLayer;
import magineer.MagineerMod;
import magineer.actions.ImproveCardAction;
import magineer.characters.Magineer;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class MagineerDefend extends MagineerCard {

    // TEXT DECLARATION 

    public static final String ID = MagineerMod.makeID("MagineerDefend");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = "Defend"; //cardStrings.NAME;
    public static final String DESCRIPTION = "Gain !B! Block."; //cardStrings.DESCRIPTION;
    public static final String portraitFilename = "magineer_defend.png";

    // /TEXT DECLARATION/


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
        baseBlock = BLOCK;

        this.tags.add(BaseModCardTags.BASIC_DEFEND);
        cardArtLayers512.add(new RenderLayer(TextureLoader.getTexture(cardArt512+portraitFilename)));
        cardArtLayers1024.add(new RenderLayer(TextureLoader.getTexture(cardArt1024+portraitFilename)));

        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.GRAY);
        improvementSlots.add(SLOTTYPE.BLUE);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, block));
        AbstractDungeon.actionManager.addToBottom(
                new ImproveCardAction(this, 1));
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
            initializeDescription();
        }
    }
}
