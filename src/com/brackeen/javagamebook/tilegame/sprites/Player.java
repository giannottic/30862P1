package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.tilegame.ResourceManager;
import com.brackeen.javagamebook.tilegame.TileMap;
import com.brackeen.javagamebook.tilegame.TileMapRenderer;
import ca.sprites.*;

/**
    The Player.
*/
public class Player extends Creature {

    private static final float JUMP_SPEED = -.95f;

    private boolean onGround;
    private int autoShots = 0;
    private int direction;

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
        // do nothing
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
    	autoShots += 1;
    	autoShots %= autoPeriod * 10;
    	if (autoShots == 0){
    		//startCooldown
    	}
    }
    
    public void endCooldown(){
    	//autoShots = 0;
    }
    
    public void startCooldown(){
    	//autoShots = 10;
    }
    
    public boolean canShoot(int autoPeriod){
    	//if (coolDown){
    		//false
    	if (autoShots % autoPeriod == 0){
    		return true;
    	}
    	else{
    		return false;
    	}
    }

    public float getMaxSpeed() {
        return 0.5f;
    }

}
