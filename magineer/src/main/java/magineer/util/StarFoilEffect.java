package magineer.util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import hexui_lib.util.FloatPair;
import hexui_lib.util.RenderImageLayer;
import hexui_lib.util.RenderLayer;

import java.util.ArrayList;

public class StarFoilEffect {
    ArrayList<StarLayer> starList = new ArrayList<>();
    ArrayList<RenderImageLayer> imageLayers = new ArrayList<RenderImageLayer>();

    public StarFoilEffect(int count){
        for(int i=0; i<count; i++){
            starList.add(new StarLayer());
        }
    }

    public void step(){
        for(StarLayer star : starList){
            star.step();
            if(star.age > star.lifespan){
                star.reset();
            }
        }
    }

    public ArrayList<RenderImageLayer> getImageLayers(){
        imageLayers.clear();
        for(StarLayer star : starList){
            imageLayers.add(star.getImageLayer());
        }
        return imageLayers;
    }
}


class StarLayer {
    public static ArrayList<Texture> starTextures = new ArrayList<>();

    public float age = 0f;
    public float lifespan = 5f;
    public RenderImageLayer imageLayer;
    public float alpha = 0f;
    public int starIndex;

    static{
        starTextures.add( ImageMaster.loadImage("magineerResources/images/cards/512/foilEffects/starfoil_0.png") );
        starTextures.add( ImageMaster.loadImage("magineerResources/images/cards/512/foilEffects/starfoil_1.png") );
        starTextures.add( ImageMaster.loadImage("magineerResources/images/cards/512/foilEffects/starfoil_2.png") );
        starTextures.add( ImageMaster.loadImage("magineerResources/images/cards/512/foilEffects/starfoil_3.png") );
    }

    StarLayer(){
        starIndex = (int) (Math.random()*starTextures.size()*2);
        //imageLayer = new RenderImageLayer(starTextures.get(starIndex % starTextures.size()), null, RenderLayer.BLENDMODE.SCREEN, new FloatPair(0, 0), (starIndex%2)*180);
        imageLayer = new RenderImageLayer(starTextures.get(starIndex % starTextures.size()));
        reset();
        age = (float) Math.random()*lifespan;
    }

    public void reset(){
        age = 0;
    }

    public void step(){
        age += Gdx.graphics.getDeltaTime();
        alpha = (float) Math.sin(Math.PI * age / lifespan);
    }

    public RenderImageLayer getImageLayer(){
        //imageLayer.color.a = alpha;
        return imageLayer;
    }
}