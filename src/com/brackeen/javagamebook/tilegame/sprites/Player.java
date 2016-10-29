package com.brackeen.javagamebook.tilegame.sprites;

import com.brackeen.javagamebook.graphics.Animation;
import com.brackeen.javagamebook.graphics.Sprite;
import com.brackeen.javagamebook.test.GameCore;

/**
    The Player.
*/
public class Player extends Creature {

    private static final float JUMP_SPEED = -.95f;

    private boolean onGround;

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
    
    public void shoot(boolean forceShoot) {
    	System.out.println("shoot");
    }


    public float getMaxSpeed() {
        return 0.5f;
    }
    
    //Alan
    public int updateHealth() {
    	health += 1;
    	return health;
    }
    
    //Alan
    public int getHealth() {
    	if (!isAlive()) {
    		return 0;
    	}
    	
//    	boolean vel_bool = false;
//    	if (getVelocityX() != 0.0) {
//    		vel_bool = true;
//    	}
    	
//    	if (!isFlying() & Math.round(Math.abs(getVelocityX())) == 0) {
//    		if (GameCore.time > 2000) {
//    			health += 5;
//        		GameCore.time = 0;
//    		}
//    	}
    	
    	double nxt_pos = getX();
    	if (Math.abs(nxt_pos - cur_pos) > getWidth()) {
    		System.out.println(cur_pos - nxt_pos);
    		health += 1;
    		cur_pos = nxt_pos;
    	}
    	
    	
    	if (health > 40) {
    		return 40;
    	}
    
    	//System.out.println(sprite.getWidth()); //80
    	//System.out.println(curr_pos - nxt_pos);
    	//System.out.println(cur_position);
    	return health;
    }

}