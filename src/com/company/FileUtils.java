package com.company;

import java.io.*;

public class FileUtils {
    public static void saveConfig(File file, GameConfig config) throws IOException {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(config.getIp()).append("\n")
                    .append(config.getPort()).append("\n")
                    .append(config.getDifficulty()).append("\n")
                    .append(config.getUpdatingPeriod()).append("\n")
                    .append(config.getSavingPeriod());
            bufferedWriter.write(stringBuilder.toString());
        }
    }

    public static GameConfig loadConfig(File configTxt) throws IOException {
        if(configTxt.exists() && !configTxt.isDirectory()){
            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(configTxt)))
            {
                GameConfig conf = new GameConfig(
                        bufferedReader.readLine(),
                        Integer.parseInt(bufferedReader.readLine()),
                        Integer.parseInt(bufferedReader.readLine()),
                        Long.parseLong(bufferedReader.readLine()),
                        Integer.parseInt(bufferedReader.readLine())
                );
                return conf;
            }
        } else {
            GameConfig conf = new GameConfig("127.0.0.1", 25565, 2, 1000, 5);
            return conf;
        }
    }

    public static void saveWorld(File file, World world) throws IOException{
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
        {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(world.getNameOfWorld()).append("\n");
            for(Entity e : world.getEntities()) {
                if (e instanceof EntityPlayer) {
                    stringBuilder.append(e.getTitle()).append(";")
                            .append(e.getPosX()).append(";")
                            .append(e.getPosZ()).append(";")
                            .append(e.isAggressive()).append(";")
                            .append(e.getMaxHealth()).append(";")
                            .append(e.getHealth()).append(";")
                            .append(e.getAttackDamage()).append(";")
                            .append(((EntityPlayer) e).getNickname()).append(";")
                            .append("\n");
                } else {
                    stringBuilder.append(e.getTitle()).append(";")
                            .append(e.getPosX()).append(";")
                            .append(e.getPosZ()).append(";")
                            .append(e.isAggressive()).append(";")
                            .append(e.getMaxHealth()).append(";")
                            .append(e.getHealth()).append(";")
                            .append(e.getAttackDamage())
                            .append("\n");
                }
            }
            bufferedWriter.write(stringBuilder.toString());
        }
    }

    public static World loadWorld(File worldTxt) throws IOException{
        if(worldTxt.exists() && !worldTxt.isDirectory()) {
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(worldTxt))) {
                String readLine = bufferedReader.readLine();
                World world = new World(readLine);
                while ((readLine = bufferedReader.readLine()) != null) {
                    String[] arr = readLine.split(";");
                    if(arr.length == 7) {
                        world.getEntities().add(new Entity(
                                arr[0],
                                Double.parseDouble(arr[1]),
                                Double.parseDouble(arr[2]),
                                Boolean.parseBoolean(arr[3]),
                                Integer.parseInt(arr[4]),
                                Integer.parseInt(arr[5]),
                                Integer.parseInt(arr[6])
                        ));
                    } else {
                        world.getEntities().add(new EntityPlayer(
                                arr[0],
                                Double.parseDouble(arr[1]),
                                Double.parseDouble(arr[2]),
                                Boolean.parseBoolean(arr[3]),
                                Integer.parseInt(arr[4]),
                                Integer.parseInt(arr[5]),
                                Integer.parseInt(arr[6]),
                                arr[7]
                        ));
                    }
                }
                return world;
            }
        } else {
            World world = new World("This is new world");
            return world;
        }
    }
}
