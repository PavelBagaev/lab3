package com.company;

public class GameConfig {
    private String ip;
    private int port;
    private int difficulty;
    private long updatingPeriod;
    private int savingPeriod;

    public GameConfig(String ip, int port, int difficulty, long updatePeriod, int savingPeriod) {
        this.ip = ip;
        this.port = port;
        this.difficulty = difficulty;
        this.updatingPeriod = updatePeriod;
        this.savingPeriod = savingPeriod;
    }

    @Override
    public String toString() {
        return "GameConfig : {" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", difficulty=" + difficulty +
                ", updatingPeriod=" + updatingPeriod +
                ", savingPeriod=" + savingPeriod +
                '}';
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public long getUpdatingPeriod() {
        return updatingPeriod;
    }

    public int getSavingPeriod() { return savingPeriod; }

}
