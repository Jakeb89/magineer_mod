package magineer.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hexui_lib.util.RenderImageLayer;
import hexui_lib.util.RenderLayer;
import magineer.MagineerMod;
import magineer.actions.ChooseCardsFromGroupAction;
import magineer.characters.Magineer;
import magineer.util.TextureLoader;

import static magineer.MagineerMod.makeCardPath;

public class Implement extends MagineerCard {

    // chooseDesc DECLARATION

    public static final String ID = MagineerMod.makeID("Implement");
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);

    public static final String IMG = makeCardPath("Skill.png");

    public static final String NAME = "Implement"; //cardStrings.NAME;
    public static final String DESCRIPTION = "Upgrade !M! card in your hand. It loses all magineer:Improvement."; //cardStrings.DESCRIPTION;
    public static final String DESCRIPTION_UPGRADED = "Upgrade !M! cards in your hand. They lose all magineer:Improvement."; //cardStrings.DESCRIPTION;
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

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom(new ChooseCardsFromGroupAction(this, AbstractDungeon.player.hand, 1, false, " to upgrade"));
    }

    @Override
    public void actionCallback(AbstractCard card) {
        MagineerMod.logger.info("Implement.actionCallback(...)");
        MagineerMod.logger.info("card: " + card);
        //AbstractDungeon.player.hand.addToTop(card);
        //AbstractDungeon.player.hand.refreshHandLayout();
        if(card instanceof MagineerCard){
            ((MagineerCard)card).addImprovements(-7);
        }
        card.upgrade();
        card.flash();
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC);
            this.rawDescription = DESCRIPTION_UPGRADED;
            initializeDescription();
        }
    }
}
