package com.company;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class World {
    private static int idCounter = 1;
    private final long id;
    private String nameOfWorld;
    private List<Entity> entities = new ArrayList<>();

    public World(String nameOfWorld) {
        this.id = idCounter;
        idCounter ++;
        this.nameOfWorld = nameOfWorld;
    }

    public void update() {
        for (int i = entities.size() -1 ; i >= 0 ; i--) {
            if(entities.get(i) != null && entities.get(i).getHealth() <= 0){
                entities.remove(i);
            }
            if(entities.get(i) != null) {
                entities.get(i).update();
            }
        }
    }

    public List<Entity> getEntitiesInRegion(double posX, double posZ, double range) {
        List<Entity> sortedEntityList = new ArrayList<>();
        for (Entity e : entities) {
            if (e != null && !e.isAggressive()) {
                double entityX = e.getPosX();
                double entityZ = e.getPosZ();
                e.setDistance(Math.sqrt(Math.pow((posX - entityX), 2) + Math.pow((posZ - entityZ), 2)));
                if (e.getDistance() <= range) sortedEntityList.add(e);
            }
        }
        Collections.sort(sortedEntityList, new Comparator<Entity>() {
            @Override
            public int compare(Entity o1, Entity o2) {
                return Double.compare(o1.getDistance(), o2.getDistance());
            }
        });
        return sortedEntityList;
    }

    public List<Entity> getEntitiesNearEntity(Entity entity, double range) {
        return getEntitiesInRegion(entity.getPosX(), entity.getPosZ(), range);
    }

    @Override
    public String toString() {
        return "World{" +
                "id=" + id +
                ", worldName='" + nameOfWorld + '\'' +
                ", entities=" + entities +
                '}';
    }

    public String getNameOfWorld() {
        return nameOfWorld;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void addWorld() {
        for (Entity e: entities) {
            e.setWorld(this);
        }
    }

    public void addEntities(Entity entity){
        entity.setWorld(this);
        entities.add(entity);
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

}

