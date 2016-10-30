package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.tilegame.ResourceManager;
import com.brackeen.javagamebook.tilegame.TileMap;
import com.brackeen.javagamebook.tilegame.TileMapRenderer;
import ca.sprites.*;
import java.util.*;
import ca.thread.*;

/**
    The Player.
*/
public class Player extends Creature{

    private static final float JUMP_SPEED = -.95f;

    private boolean onGround;
    private boolean onCooldown;
    private int autoShots;

    public Player(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
    }


    public void collideHorizontal() {
        setVelocityX(0);
    }


    public void collideVertical() {
        // check if collided with ground
        if (getVelocityY() > 0) {
            onGround = true;
        }
        setVelocityY(0);
    }


    public void setY(float y) {
        // check if falling
        if (Math.round(y) > Math.round(getY())) {
            onGround = false;
        }
        super.setY(y);
    }


    public void wakeUp() {
    	onCooldown = false;
    	autoShots = 0;
    	awake = true;
    }


    /**
        Makes the player jump if the player is on the ground or
        if forceJump is true.
    */
    public void jump(boolean forceJump) {
        if (onGround || forceJump) {
            onGround = false;
            setVelocityY(JUMP_SPEED);
        }
    }
    
    public void updateAuto(int autoPeriod){
    	if (!onCooldown) {
			autoShots += 1;
			autoShots %= autoPeriod * 10;
			if (autoShots == 0) {
				startCooldown();
			}
		}
    }
    public void updateAuto(long autoTime){
    	if (!onCooldown){
    		autoShots += 1;
    		if (autoShots >= 10){
    			startCooldown();
    		}
    	}
    }
    
    public void startCooldown(){
    	onCooldown = true;
    	Timer timer = new Timer();
    	timer.schedule(new TimerThread(this), 1000);
    }
   
    
    public boolean canShoot(int autoPeriod){
    	if (onCooldown){
    		return false;
    	}
    	else if (autoShots % autoPeriod == 0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }
    
    public boolean canShoot(long autoPeriod){
    	return super.canShoot(autoPeriod) && !onCooldown;
    }

    public float getMaxSpeed() {
        return 0.5f;
    }

}
