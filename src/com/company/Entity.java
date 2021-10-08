package com.company;


import java.util.List;

public class Entity {
    private static int idCounter = 1;
    private final long id;
    private String title;
    private double posX;
    private double posZ;
    private boolean aggressive;
    private int maxHealth;
    private int health;
    private int attackDamage;
    private Entity target;
    private World world;
    protected double distance;

    public Entity(String title, double posX, double posZ,
                  boolean aggressive, int maxHealth,
                  int health, int attackDamage) {
        this.id = idCounter;
        idCounter++;
        this.title = title;
        this.posX = posX;
        this.posZ = posZ;
        this.aggressive = aggressive;
        this.maxHealth = maxHealth;
        this.health = health;
        this.attackDamage = attackDamage;
    }


    public void update() {
        if (aggressive) {
            if (target == null) this.searchTarget();
            if (target == null) return;
            double entityX = target.getPosX();
            double entityZ = target.getPosZ();
            this.setDistance(Math.sqrt(Math.pow((posX - entityX), 2) + Math.pow((posZ - entityZ), 2)));
            if (this.distance <= 2) {
                attack(target);
            } else {
                if (target.getPosX() > this.posX) this.posX++;
                else if (target.getPosX() < this.posX) this.posX--;
                if (target.getPosZ() > this.posZ) this.posZ++;
                else if (target.getPosZ() < this.posZ) this.posZ--;
            }
        }
    }

    public void searchTarget() {
        List<Entity> nearby;
        nearby = world.getEntitiesNearEntity(this, 20);
        int schet = 0;
        if (!nearby.isEmpty()) {
            if (!nearby.get(schet).aggressive) {
                target = nearby.get(schet);
            } else schet++;
        }
    }

    public void attack(Entity entity) {
        if (entity instanceof EntityPlayer) {
            entity.setHealth((entity.health - this.getAttackDamage()));

            System.out.println(this.title + " attacked " + ((EntityPlayer) entity).nickname + " for " + this.getAttackDamage() + " damage." + entity.health + "HP");

            if (entity.getHealth() > 0) {
                this.setHealth(this.getHealth() - entity.getAttackDamage());
                System.out.println(((EntityPlayer) entity).nickname + " attacked " + this.title + " for " + entity.getAttackDamage() + " damage." + this.health + "HP");
            } else if (entity.getHealth() <= 0) {
                System.out.println(((EntityPlayer) entity).nickname + " was slain by " + this.title);
                this.target = null;
            }
            if (this.getHealth() <= 0) {
                System.out.println(this.title + " was slain by " + ((EntityPlayer) entity).nickname);
            }
        } else {
            entity.setHealth(entity.getHealth() - getAttackDamage());
            System.out.println(this.title + " attacked " + entity.title + " for " + this.getAttackDamage() + " damage." + entity.health + "HP");
            if (entity.getHealth() <= 0) {
                System.out.println(entity.title + " was slain by " + this.title);
                this.target = null;
            }
        }

    }

    @Override
    public String toString() {
        return "Entity{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", posX=" + posX +
                ", posZ=" + posZ +
                ", aggressive=" + aggressive +
                ", maxHealth=" + maxHealth +
                ", health=" + health +
                ", attackDamage=" + attackDamage +
                ", target=" + target +
                '}';
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getTitle() {
        return title;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosZ() {
        return posZ;
    }

    public boolean isAggressive() {
        return aggressive;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return (int) (attackDamage + 0.5 * GameServer.getInstance().getDifficulty());
    }

}
