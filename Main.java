package com.company;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class Main {

    public static void main(String[] args) {
        openZip("C://Games//savegames//gameZip.zip", "C://Games//savegames//");

        System.out.println(openProgress("C://Games//savegames//game1"));
        System.out.println(openProgress("C://Games//savegames//game2"));
        System.out.println(openProgress("C://Games//savegames//game3"));

    }

    static void openZip(String zipDirectory, String unpackingDirectory) {
        try (ZipInputStream zin = new ZipInputStream(new FileInputStream(zipDirectory))) {
            ZipEntry entry;
            String name;
            while ((entry = zin.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(unpackingDirectory + name);
                for (int c = zin.read(); c != -1; c = zin.read()) {
                    fout.write(c);
                }
                fout.flush();
                zin.closeEntry();
                fout.close();
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    static GameProgress openProgress(String fileDirectory) {
        GameProgress gameProgress = null;
        try (FileInputStream fis = new FileInputStream(fileDirectory);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gameProgress = (GameProgress) ois.readObject();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
        return gameProgress;
    }
}