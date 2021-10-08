package com.company;

import java.io.File;
import java.io.IOException;

public class GameServer {
    private GameConfig gameConfiguration;
    private String ip;
    private int difficulty;
    private World world;
    private static GameServer instance;
    private int tick = 1;

    public GameServer(String ip, int difficulty) {
        instance = this;
        this.ip = ip;
        this.difficulty = difficulty;
    }

    private final File worldTxt = new File("worldTxt.txt");
    private final File configTxt = new File("configTxt.txt");

    public static void main(String[] args) throws IOException {
        try {
            new GameServer("127.0.0.1",2);
            long start = System.currentTimeMillis();
            instance.loadConfig_();
            System.out.println("Loading config " + (System.currentTimeMillis() - start) + "ms");
            System.out.println(instance.gameConfiguration);

            instance.setIp(instance.gameConfiguration.getIp());
            instance.setDifficulty(instance.gameConfiguration.getDifficulty());
            instance.saveConfig_();
            start = System.currentTimeMillis();
            instance.loadWorld_();
            System.out.println("Loading world " + (System.currentTimeMillis() - start) + "ms");
            System.out.println(instance.world);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(instance);
        while (instance.tick!=30) {
            instance.updateServer();
            try {
                Thread.sleep(instance.gameConfiguration.getUpdatingPeriod());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateServer() throws IOException {
        world.update();
        if(tick % gameConfiguration.getSavingPeriod() == 0) {
            FileUtils.saveWorld(worldTxt, world);
            System.out.println("World was saved");
        }
        tick++;
    }

    private void loadConfig_() throws IOException {
        GameConfig gameConfig = FileUtils.loadConfig(instance.configTxt);
        setGameConfiguration(gameConfig);
    }

    private void loadWorld_() throws IOException {
        World serverWorld = FileUtils.loadWorld(instance.worldTxt);
        serverWorld.addWorld();
        setWorld(serverWorld);
    }

    private void saveConfig_() throws IOException {
        FileUtils.saveConfig(instance.configTxt, gameConfiguration);
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public static GameServer getInstance() {
        return instance;
    }

    public int getTick() {
        return tick;
    }

    public void setWorld(World serverWorld) {
        this.world = serverWorld;
    }

    public void setGameConfiguration(GameConfig gameConfiguration) {
        this.gameConfiguration = gameConfiguration;
    }

    @Override
    public String toString() {
        return "GameServer{" +
                "ip='" + ip + '\'' +
                ", difficulty=" + difficulty +
                ", serverWorld=" + world +
                ", tick=" + tick +
                '}';
    }
}

