package magineer.cards;

import basemod.abstracts.CustomCard;
import hexui_lib.interfaces.CustomCardPortrait;
import hexui_lib.interfaces.CustomCardTypeLocation;
import hexui_lib.util.FloatPair;
import hexui_lib.util.RenderLayer;
import magineer.util.TextureLoader;

import java.util.ArrayList;

public abstract class MagineerCard extends CustomCard implements CustomCardPortrait, CustomCardTypeLocation {

    // Custom Abstract Cards can be a bit confusing. While this is a simple base for simply adding a second magic number,
    // if you're new to modding I suggest you skip this file until you know what unique things that aren't provided
    // by default, that you need in your own cards. For now, go check out the other cards.

    // In this example, we use a custom Abstract Card in order to define a new magic number. From here on out, we can
    // simply use that in our cards, so long as we put "extends MagineerCard" instead of "extends CustomCard" at the start.
    // In simple terms, it's for things that we don't want to define again and again in every single card we make.

    public int defaultSecondMagicNumber;        // Just like magic number, or any number for that matter, we want our regular, modifiable stat
    public int defaultBaseSecondMagicNumber;    // And our base stat - the number in it's base state. It will reset to that by default.
    public boolean upgradedDefaultSecondMagicNumber; // A boolean to check whether the number has been upgraded or not.
    public boolean isDefaultSecondMagicNumberModified; // A boolean to check whether the number has been modified or not, for coloring purposes. (red/green)


    private ArrayList<RenderLayer> portraitLayers512 = new ArrayList<RenderLayer>();
    private ArrayList<RenderLayer> portraitLayers1024 = new ArrayList<RenderLayer>();

    public ArrayList<RenderLayer> cardArtLayers512 = new ArrayList<RenderLayer>();
    public ArrayList<RenderLayer> cardArtLayers1024 = new ArrayList<RenderLayer>();

    public static final String images512 = "magineerResources/images/512/";
    public static final String images1024 = "magineerResources/images/1024/";
    public static final String cardArt512 = "magineerResources/images/cards/512/";
    public static final String cardArt1024 = "magineerResources/images/cards/1024/";
    public static final String cards = "magineerResources/images/cards/";
    public static final String uiFolder = "magineerResources/images/ui/";

    public static final String solidBack = "bg_genericback_fullportrait_gray.png";
    public static final String innerShadow = "bg_fullportrait_inner_shadow.png";
    public static final String descShadow = "bg_fullportrait_desc_shadow.png";
    public static final String cardTypePanel = "bg_cardtype_gray.png";

    public static final String attackBorder = "bg_attack_fullportait_gray.png";
    public static final String skillBorder = "bg_skill_fullportait_gray.png";
    public static final String powerBorder = "bg_power_fullportait_gray.png";

    public MagineerCard(final String id, final String name, final String img, final int cost, final String rawDescription,
                        final CardType type, final CardColor color,
                        final CardRarity rarity, final CardTarget target) {
        super(id, name, img, cost, rawDescription, type, color, rarity, target);

        // Set all the things to their default values.
        isCostModified = false;
        isCostModifiedForTurn = false;
        isDamageModified = false;
        isBlockModified = false;
        isMagicNumberModified = false;
        isDefaultSecondMagicNumberModified = false;


        setBackgroundTexture(images512+"bg_genericback_fullportrait_gray.png",
                images1024+"bg_genericback_fullportrait_gray.png");
        /*setOrbTexture(images512+"empty.png",
                images1024+"empty.png");*/
        /*setBannerTexture(images512+"empty.png",
                images1024+"empty.png");*/

    }

    public void displayUpgrades() { // Display the upgrade - when you click a card to upgrade it

        if (upgradedDefaultSecondMagicNumber) { // If we set upgradedDefaultSecondMagicNumber = true in our card.
            defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Show how the number changes, as out of combat, the base number of a card is shown.
            isDefaultSecondMagicNumberModified = true; // Modified = true, color it green to highlight that the number is being changed.
        }

    }

    public void upgradeDefaultSecondMagicNumber(int amount) { // If we're upgrading (read: changing) the number. Note "upgrade" and NOT "upgraded" - 2 different things. One is a boolean, and then this one is what you will usually use - change the integer by how much you want to upgrade.
        defaultBaseSecondMagicNumber += amount; // Upgrade the number by the amount you provide in your card.
        defaultSecondMagicNumber = defaultBaseSecondMagicNumber; // Set the number to be equal to the base value.
        upgradedDefaultSecondMagicNumber = true; // Upgraded = true - which does what the above method does.
    }

    @Override
    public ArrayList<RenderLayer> getPortraitLayers512() {
        portraitLayers512.clear();

        portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+solidBack)));
        portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+descShadow)));

        addCardArtLayers512(portraitLayers512);

        portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+cardTypePanel)));
        portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+innerShadow)));

        switch(this.type){
            case ATTACK:
                portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+attackBorder)));
                break;
            case POWER:
                portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+powerBorder)));
                break;
            case SKILL:
            default:
                portraitLayers512.add(new RenderLayer(TextureLoader.getTexture(images512+skillBorder)));
        }

        return portraitLayers512;
    }

    @Override
    public ArrayList<RenderLayer> getPortraitLayers1024() {
        portraitLayers1024.clear();

        portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+solidBack)));
        portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+descShadow)));

        addCardArtLayers512(portraitLayers1024);

        portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+cardTypePanel)));
        portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+innerShadow)));

        switch(this.type){
            case ATTACK:
                portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+attackBorder)));
                break;
            case POWER:
                portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+powerBorder)));
                break;
            case SKILL:
            default:
                portraitLayers1024.add(new RenderLayer(TextureLoader.getTexture(images1024+skillBorder)));
        }

        return portraitLayers1024;
    }

    @Override
    public FloatPair getCardTypeLocation(FloatPair pair, boolean isBigCard) {
        if(isBigCard){
            pair.y -= 7f;
            pair.y += 324f;
        }else{
            pair.y += 162f;
        }
        return pair;
    }

    public void addCardArtLayers512(ArrayList<RenderLayer> portraitLayers){
        for(RenderLayer layer : cardArtLayers512){
            portraitLayers.add(layer);
        }
    }

    public void addCardArtLayers1024(ArrayList<RenderLayer> portraitLayers){
        for(RenderLayer layer : cardArtLayers1024){
            portraitLayers.add(layer);
        }

    }
}