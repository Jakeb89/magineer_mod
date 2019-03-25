package magineer.util;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import kobting.friendlyminions.helpers.BasePlayerMinionHelper;
import kobting.friendlyminions.monsters.AbstractFriendlyMonster;
import kobting.friendlyminions.patches.PlayerAddFieldsPatch;
import magineer.minions.AegisMinion;
import magineer.minions.AutoMinion;
import magineer.minions.CrystalMinion;

import java.util.ArrayList;
import java.util.HashMap;

public class MinionManager {
    public static ArrayList<AutoMinion> minionList = new ArrayList<>();
    public static ArrayList<IntPair> freeMinionSpotList = new ArrayList<>();
    public static ArrayList<IntPair> usedMinionSpotList = new ArrayList<>();
    public static HashMap<MINIONTYPE, String> minionNames = new HashMap<>();

    public static void endOfTurn() {
        for(AutoMinion minion : minionList){
            minion.endOfTurn();
        }
    }

    public enum MINIONTYPE{
        CRYSTAL,
        AEGIS
    }

    static{
        freeMinionSpotList.add(new IntPair(-700, 50));
        freeMinionSpotList.add(new IntPair(-800, -50));
        freeMinionSpotList.add(new IntPair(-1100, 50));
        freeMinionSpotList.add(new IntPair(-1200, -50));
        //PlayerAddFieldsPatch.f_maxMinions.set(AbstractDungeon.player, 4);
        minionNames.put(MINIONTYPE.CRYSTAL, "Crystal");
        minionNames.put(MINIONTYPE.AEGIS, "Aegis");
    }

    public static String getMinionName(MINIONTYPE miniontype){
        if(miniontype == null) return "[null minion type]";
        return minionNames.get(miniontype);
    }


    public static void addMinion(MINIONTYPE minionType) {
        if(freeMinionSpotList.size() == 0){
            //No free spots.
            return;
        }


        IntPair freeSpot = freeMinionSpotList.remove(0);
        usedMinionSpotList.add(freeSpot);

        AbstractFriendlyMonster minion = null;
        switch(minionType){
            case AEGIS:
                minion = new AegisMinion(freeSpot.x, freeSpot.y, freeSpot);
                break;
            case CRYSTAL:
            default:
                minion = new CrystalMinion(freeSpot.x, freeSpot.y, freeSpot);
        }

        BasePlayerMinionHelper.addMinion(AbstractDungeon.player, minion);
    }

    public static void freeSlot(AutoMinion minion) {
        freeMinionSpotList.add(minion.minionSlot);
        usedMinionSpotList.remove(minion.minionSlot);
    }
}
