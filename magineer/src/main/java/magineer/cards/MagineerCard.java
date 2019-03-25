package magineer.cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import hexui_lib.HexUILib;
import hexui_lib.interfaces.CustomCardPortrait;
import hexui_lib.interfaces.CustomCardTypeLocation;
import hexui_lib.interfaces.FlavorTooltips;
import hexui_lib.util.*;
import magineer.actions.DescriptiveAction;
import magineer.util.LangUtil;
import magineer.util.StarFoilEffect;
import magineer.util.TextureLoader;

import java.util.ArrayList;

import static hexui_lib.util.RenderLayer.CM_FULL;
import static magineer.characters.Magineer.logger;

public abstract class MagineerCard extends CustomCard implements CustomCardPortrait, CustomCardTypeLocation, FlavorTooltips {

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

    public static ArrayList<RenderLayer> outerCircuits512 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> outerCircuits1024 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> outerMagic512 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> outerMagic1024 = new ArrayList<RenderLayer>();

    public static ArrayList<RenderLayer> slotBlue512 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotBlue1024 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotOrange512 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotOrange1024 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotGray512 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotGray1024 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotGlow512 = new ArrayList<RenderLayer>();
    public static ArrayList<RenderLayer> slotGlow1024 = new ArrayList<RenderLayer>();

    public static RenderLayer improvementSlotsPanel512;
    public static RenderLayer improvementSlotsPanel1024;

    public static boolean decoRenderLayersInitialized = false;

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

    public static final String empty = "empty.png";

    public static final String attackBorder = "bg_attack_fullportait_gray.png";
    public static final String skillBorder = "bg_skill_fullportait_gray.png";
    public static final String powerBorder = "bg_power_fullportait_gray.png";

    public static final String effect_stars = "effect_stars.png";
    public static final String effect_rainbow = "effect_rainbow_clouds.png";
    public static final String mask_clouds = "mask_horizontal_clouds.png";
    public static final String mask_stars = "mask_stars.png";
    public static final String mask_stars2 = "mask_stars2.png";
    public static final String mask_crystallize = "mask_crystallize.png";

    private ArrayList<RenderLayer> portraitLayers512 = new ArrayList<RenderLayer>();
    private ArrayList<RenderLayer> portraitLayers1024 = new ArrayList<RenderLayer>();

    public ArrayList<RenderLayer> cardArtLayers512 = new ArrayList<RenderLayer>();
    public ArrayList<RenderLayer> cardArtLayers1024 = new ArrayList<RenderLayer>();

    protected String flavorText = "-";
    protected ArrayList<String> artistNames = new ArrayList<String>();
    protected boolean isFoil = (Math.random()<0.2 ? true : false);
    protected Wobbler foilWobbler = new Wobbler(20f, 2, 1);
    protected float foilSparkleCountdown = 2f;

    public boolean isStarFoil = isFoil;
    public StarFoilEffect starFoilEffect = null;

    public ArrayList<DescriptiveAction> actionList = new ArrayList<DescriptiveAction>();

    public enum SLOTTYPE{
        GRAY,
        ORANGE,
        BLUE
    }

    public ArrayList<SLOTTYPE> improvementSlots = new ArrayList<SLOTTYPE>();
    public int improvements = 0;

    public void addImprovements(int amount) {
        addImprovements(amount, SLOTTYPE.GRAY);
    }

    public void addImprovements(int amount, SLOTTYPE slotType) {
        if(amount > 1){
            for(int i=0; i<amount; i++){
                addImprovements(1, slotType);
            }
            return;
        }else if(amount < -1){
            for(int i=0; i>amount; i--){
                addImprovements(-1, slotType);
            }
            return;
        }

        int startingImprovements = improvements;

        if(amount == 1 && couldBeImprovedBy(slotType)) {
            improvements++;
        }else if(amount == -1){
            improvements --;
        }

        if(improvements > improvementSlots.size()){
            improvements = improvementSlots.size();
        }
        if(improvements < 0){
            improvements = 0;
        }

        if(startingImprovements < improvements){
            gainedImprovementLevel(improvements);
        }else if(startingImprovements > improvements){
            lostImprovementLevel(startingImprovements);
        }
    }


    public boolean couldBeImprovedBy(SLOTTYPE slotType) {
        if(improvementSlots.size() == 0) return false;
        if(improvements < improvementSlots.size()) {
            SLOTTYPE fillingSlotType = improvementSlots.get(improvements);
            switch (fillingSlotType) {
                case GRAY:
                    return true;
                case BLUE:
                    if (slotType == SLOTTYPE.BLUE) return true;
                    break;
                case ORANGE:
                    if (slotType == SLOTTYPE.ORANGE) return true;
                    break;
            }
        }
        return false;
    }


    public void gainedImprovementLevel(int level){
        //Do something if the gained level matters.
    }

    public void lostImprovementLevel(int level){
        //Do something if the lost level matters.
    }

    public void actionCallback(AbstractCard card) {
    }

    public void actionCallback(ArrayList<AbstractCard> cards) {
    }

    public boolean customCardTest(AbstractCard c) {
        return true;
    }


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

        initializeDecoRenderLayers();


        setBackgroundTexture(images512+"bg_genericback_fullportrait_gray.png",
                images1024+"bg_genericback_fullportrait_gray.png");
        /*setOrbTexture(images512+"empty.png",
                images1024+"empty.png");*/
        /*setBannerTexture(images512+"empty.png",
                images1024+"empty.png");*/

    }

    private void initializeDecoRenderLayers() {
        if(decoRenderLayersInitialized) return;

        for(int i=1; i<=3; i++) {
            outerCircuits512.add(new RenderImageLayer(TextureLoader.getTexture(images512 + "bg_circuits_outer_"+i+".png")));
            outerMagic512   .add(new RenderImageLayer(TextureLoader.getTexture(images512 + "bg_magic_outer_"+i+".png")));

            outerCircuits1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "bg_circuits_outer_"+i+".png")));
            outerMagic1024   .add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "bg_magic_outer_"+i+".png")));
        }

        for(int i=0; i<=6; i++) {
            slotBlue512   .add(new RenderImageLayer(TextureLoader.getTexture(images512 + "slot_blue_"+i+".png")));
            slotOrange512 .add(new RenderImageLayer(TextureLoader.getTexture(images512 + "slot_orange_"+i+".png")));
            slotGray512   .add(new RenderImageLayer(TextureLoader.getTexture(images512 + "slot_gray_"+i+".png")));
            slotGlow512   .add(new RenderImageLayer(TextureLoader.getTexture(images512 + "slot_glow_"+i+".png")));

            slotBlue1024  .add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "slot_blue_"+i+".png")));
            slotOrange1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "slot_orange_"+i+".png")));
            slotGray1024  .add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "slot_gray_"+i+".png")));
            slotGlow1024  .add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "slot_glow_"+i+".png")));
        }

        for(int i=0; i<=6; i++){
            slotGlow512   .add(new RenderImageLayer(TextureLoader.getTexture(images512 + "slot_glow_"+i+".png"), null, RenderLayer.BLENDMODE.CREATEMASK, null, 0f, CM_FULL));
            slotGlow1024  .add(new RenderImageLayer(TextureLoader.getTexture(images1024 + "slot_glow_"+i+".png"), null, RenderLayer.BLENDMODE.CREATEMASK, null, 0f, CM_FULL));
        }

        improvementSlotsPanel512  = new RenderImageLayer(TextureLoader.getTexture(images512  + "improvement_slots_panel.png"));
        improvementSlotsPanel1024 = new RenderImageLayer(TextureLoader.getTexture(images1024 + "improvement_slots_panel.png"));


        decoRenderLayersInitialized = true;
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

        portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+solidBack)));

        addCardArtLayers512(portraitLayers512);

        portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+descShadow)));
        portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+cardTypePanel)));
        portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+innerShadow)));
        portraitLayers512.add(improvementSlotsPanel512);

        switch(this.type){
            case ATTACK:
                portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+attackBorder)));
                break;
            case POWER:
                portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+powerBorder)));
                break;
            case SKILL:
            default:
                portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+skillBorder)));
        }

        for(int i=0; i<improvementSlots.size(); i++){
            switch(improvementSlots.get(i)){
                case GRAY:   portraitLayers512.add(slotGray512.get(i));   break;
                case ORANGE: portraitLayers512.add(slotOrange512.get(i)); break;
                case BLUE:   portraitLayers512.add(slotBlue512.get(i));   break;
            }
            if(improvements > i) {
                //portraitLayers512.add(slotGlow512.get(i));

                portraitLayers512.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_START));
                //portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+getFoilArtFilename()))); //Thing to be masked
                portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+effect_rainbow))); //Thing to be masked
                portraitLayers512.add(slotGlow512.get(i)); //Make light brighter in center.

                portraitLayers512.add(slotGlow512.get(i+7)); //The mask itself
                portraitLayers512.add(new RenderImageLayer(TextureLoader.getTexture(images512+empty), null,
                        RenderLayer.BLENDMODE.RECEIVEMASK, null, 0f, null)); //Finally, do magic because reasons.

                portraitLayers512.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_END_SCREEN));

            }
        }

        return portraitLayers512;
    }

    @Override
    public ArrayList<RenderLayer> getPortraitLayers1024() {
        portraitLayers1024.clear();

        portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+solidBack)));

        addCardArtLayers1024(portraitLayers1024);

        portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+descShadow)));
        portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+cardTypePanel)));
        portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+innerShadow)));
        portraitLayers1024.add(improvementSlotsPanel1024);

        switch(this.type){
            case ATTACK:
                portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+attackBorder)));
                break;
            case POWER:
                portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+powerBorder)));
                break;
            case SKILL:
            default:
                portraitLayers1024.add(new RenderImageLayer(TextureLoader.getTexture(images1024+skillBorder)));
        }

        for(int i=0; i<improvementSlots.size(); i++){
            switch(improvementSlots.get(i)){
                case GRAY:   portraitLayers1024.add(slotGray1024.get(i));   break;
                case ORANGE: portraitLayers1024.add(slotOrange1024.get(i)); break;
                case BLUE:   portraitLayers1024.add(slotBlue1024.get(i));   break;
            }
            if(improvements > i){
                portraitLayers1024.add(slotGlow1024.get(i));
            }
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

    protected void generateBetaArt(){
        //logger.info(this.cardID+".generateBetaArt()");
        cardArtLayers512 = BetaPortraitGenerator.generate(this.cardID, false);
        cardArtLayers1024 = BetaPortraitGenerator.generate(this.cardID, true);
    }

    public void addCardArtLayers512(ArrayList<RenderLayer> portraitLayers){
        for(RenderLayer layer : cardArtLayers512){
            portraitLayers.add(layer);
        }
        if(isFoil){
            renderFoilEffects(portraitLayers, false);
        }
    }

    public void addCardArtLayers1024(ArrayList<RenderLayer> portraitLayers){
        for(RenderLayer layer : cardArtLayers1024){
            portraitLayers.add(layer);
        }
        if(isFoil){
            renderFoilEffects(portraitLayers, false);
        }
    }

    private void renderFoilEffects(ArrayList<RenderLayer> portraitLayers, boolean isBigCard){
        foilWobbler.step();

        String images = images512;
        String cardArt = cardArt512;
        if(isBigCard){
            images = images1024;
            cardArt = cardArt1024;
        }

        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_START));
        //portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+getFoilArtFilename()))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_rainbow))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*4, foilWobbler.get(1)*8)));
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*4, foilWobbler.get(1)*8)));

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+mask_stars), null,
                RenderLayer.BLENDMODE.CREATEMASK, new FloatPair(foilWobbler.get(0)*4, foilWobbler.get(1)*8), 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt+getFoilArtFilename()), null,
                RenderLayer.BLENDMODE.CREATEMASK, null, 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+empty), null,
                RenderLayer.BLENDMODE.RECEIVEMASK, null, 0f, null)); //Finally, do magic because reasons.

        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_END_DOUBLESCREEN));


        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_START));
        //portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+getFoilArtFilename()))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_rainbow))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*-8, foilWobbler.get(1)*4)));
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*-8, foilWobbler.get(1)*4)));

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+mask_stars2), null,
                RenderLayer.BLENDMODE.CREATEMASK, new FloatPair(foilWobbler.get(0)*-8, foilWobbler.get(1)*4), 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt+getFoilArtFilename()), null,
                RenderLayer.BLENDMODE.CREATEMASK, null, 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+empty), null,
                RenderLayer.BLENDMODE.RECEIVEMASK, null, 0f, null)); //Finally, do magic because reasons.

        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_END_DOUBLESCREEN));


        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_START));
        //portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+getFoilArtFilename()))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_rainbow))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*-17, foilWobbler.get(1)*-5)));
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*-12, foilWobbler.get(1)*-4)));

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*3, foilWobbler.get(1)*-8)));
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*2, foilWobbler.get(1)*-15)));

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+mask_crystallize), null,
                RenderLayer.BLENDMODE.CREATEMASK, new FloatPair(foilWobbler.get(0)*-7, foilWobbler.get(1)*-3), 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt+getFoilArtFilename()), null,
                RenderLayer.BLENDMODE.CREATEMASK, null, 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+empty), null,
                RenderLayer.BLENDMODE.RECEIVEMASK, null, 0f, null)); //Finally, do magic because reasons.

        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_END_SCREEN));


        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_START));
        //portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt512+getFoilArtFilename()))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_rainbow))); //Thing to be masked
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*5, foilWobbler.get(1)*15)));

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*7, foilWobbler.get(1)*11)));

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*-19, foilWobbler.get(1)*-8)));
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+effect_stars), null,
                RenderLayer.BLENDMODE.SCREEN, new FloatPair(foilWobbler.get(0)*-5, foilWobbler.get(1)*-17)));


        if(isStarFoil){
            if(starFoilEffect == null){
                starFoilEffect = new StarFoilEffect(10);
            }
            starFoilEffect.step();
            portraitLayers.addAll(starFoilEffect.getImageLayers());
        }

        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+mask_crystallize), null,
                RenderLayer.BLENDMODE.CREATEMASK, new FloatPair(foilWobbler.get(0)*11, foilWobbler.get(1)*-5), 0f, null)); //The mask itself
        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(cardArt+getFoilArtFilename()), null,
                RenderLayer.BLENDMODE.CREATEMASK, null, 0f, null)); //The mask itself



        portraitLayers.add(new RenderImageLayer(TextureLoader.getTexture(images+empty), null,
                RenderLayer.BLENDMODE.RECEIVEMASK, null, 0f, null)); //Finally, do magic because reasons.


        portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.FBO_END_DOUBLESCREEN));


        //portraitLayers.add(new RenderCommandLayer(RenderCommandLayer.COMMAND.ATTEMPT_RESET));
    }

    public float rand(float min, float max){
        return (float) ( Math.random()*(max-min) - min );
    }

    public String getFlavorText(){
        return flavorText;
    }

    public ArrayList<String> getArtistNames(){
        return artistNames;
    }

    public String getFoilArtFilename(){
        return "magineer_strike.png";
    }

    public void updateDescription(){
        String description = "";
        for(DescriptiveAction action : actionList ){
            if(description != ""){
                description += " NL ";
            }
            description += action.getDescription();
        }

        //Some code here to check for description length being too long, in which case make use of the short descriptions.

        rawDescription = description;
        rawDescription = LangUtil.getDescriptionFromActionList(actionList);
        initializeDescription();
    }


    // Actions the card should do.
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        logger.info(this.getClass().getSimpleName()+".use(...)");
        for(DescriptiveAction action : actionList){
            logger.info("> Processing action: "+action.getClass().getSimpleName());
            action.isDone = false;
            action.setTarget(m);
            AbstractDungeon.actionManager.addToBottom(action);
        }
    }

    public void setRandomSlots(int slots, SLOTTYPE slottype){
        improvementSlots.clear();
        for(int i=0; i<slots; i++){
            if(Math.random()*i*2 > slots){
                improvementSlots.add(slottype);
            }else {
                improvementSlots.add(SLOTTYPE.GRAY);
            }
        }
    }

    public CardGroup getPlayerHand(){
        if(AbstractDungeon.player == null) return null;
        return AbstractDungeon.player.hand;
    }

}