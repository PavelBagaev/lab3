package com.company;

public class EntityPlayer extends Entity {
    protected String nickname;
    public EntityPlayer(String title, double posX, double posZ, boolean b, int maxHealth, int health, int attackDamage, String nickname) {
        super(title, posX, posZ, false, maxHealth, health, attackDamage);
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return super.toString() + ("EntityPlayer{" +
                "nickname='" + nickname + '\'' +
                '}');
    }

    public void update(){
        super.update();
        if (GameServer.getInstance().getTick() % 2 == 0 && getHealth() < getMaxHealth()){
            setHealth(getHealth() + 1);
            System.out.println(this.nickname + " +hp" );
        }
    }
    public String getNickname() {
        return nickname;
    }

}

