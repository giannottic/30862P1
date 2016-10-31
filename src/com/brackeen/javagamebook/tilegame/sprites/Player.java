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
    private static final int MAX_HEALTH = 40;
    public static final long IDLE_DURATION = 1000;
    public static final int IDLE_BONUS = 5;
    
    private boolean onGround;
    private boolean onCooldown;
    private int autoShots;
    private boolean semiAutoForce;
    private int health;
    private float refPos;
    private long idleTime;

    public Player(Animation left, Animation right,
        Animation deadLeft, Animation deadRight)
    {
        super(left, right, deadLeft, deadRight);
        health = 20;
    }
    
    public void setX(float x){
    	if (refPos == 0)
    	{
    		refPos = x;
    	}
    	if (Math.abs(refPos - x) > getWidth()){
    		updateHealth(1);
    		refPos = getX();
    	}
    	super.setX(x);
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
   
       
    public boolean canShoot(long autoPeriod){
    	boolean retVal = super.canShoot(autoPeriod) && !onCooldown;
    	retVal = retVal || semiAutoForce;
    	semiAutoForce = false;
    	return retVal;
    }
    
    public void setForce(boolean value){
    	semiAutoForce = value;
    	if (value == true){
    		wakeUp();
    	}
    }

    public float getMaxSpeed() {
        return 0.5f;
    }
    
    public void updateHealth(int change){
    	health += change;
    	if (health > MAX_HEALTH){
    		health = MAX_HEALTH;
    	}
    	else if (health <= 0){
    		health = 0;
    		setState(STATE_DYING);
    	}
    }
    
    public int getHealth(){
    	return health;
    }
    
    public void checkIdle(long elapsedTime){
    	if (this.getVelocityX() == 0f && this.getVelocityY() == 0f){
    		idleTime += elapsedTime;
    		if (idleTime >= IDLE_DURATION){
    			idleTime -= IDLE_DURATION;
    			updateHealth(IDLE_BONUS);
    			System.out.println(getHealth());
    		}
    	}
    	else {
    		idleTime = 0l;
    	}
    }
}
