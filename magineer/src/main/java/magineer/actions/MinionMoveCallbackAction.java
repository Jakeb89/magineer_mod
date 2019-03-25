package magineer.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import magineer.minions.AutoMinion;
import magineer.minions.AutoMinionMove;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class MinionMoveCallbackAction extends AbstractGameAction {
    AutoMinionMove minionMove;

    public MinionMoveCallbackAction(AutoMinionMove minionMove){
        this.minionMove = minionMove;
    }

    @Override
    public void update() {
        minionMove.actionCallback();
        this.isDone = true;
    }
}
