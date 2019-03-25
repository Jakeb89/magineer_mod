package magineer.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import magineer.util.MinionManager;

@SpirePatch(clz = AbstractRoom.class, method = "endTurn")
public class AbstractRoomEndTurnPatch {
    public static void Postfix(AbstractRoom __instance){
        if (AbstractDungeon.player != null){
            MinionManager.endOfTurn();
        }
    }
}