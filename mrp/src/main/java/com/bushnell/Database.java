package com.bushnell;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Database {
    public static String DBName = "jdbc:sqlite:C:\\\\Users\\\\branm\\\\OneDrive\\\\College\\\\Bushnell2024-2025\\\\Spring\\\\SFTE212\\\\project_repos\\\\brandon\\\\Java2-Project\\\\VR-Factory.db";
    public static String DBDir = "";

    public static boolean setDBDirectory(String directory) {
        try {
            Path fullPath = Paths.get(directory, "VR-Factory.db");
            Path basePath = Paths.get(directory);
            DBName = "jdbc:sqlite:" + fullPath.toString();
            DBDir = basePath.toString();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
