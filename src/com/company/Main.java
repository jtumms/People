package com.company;

import jodd.json.JsonSerializer;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {

        HashMap<String, ArrayList<Person>>  mapByCountry = new HashMap<>();
        ArrayList<Person> personArray = new ArrayList<>();

        File f = new File("people.csv");
        Scanner fileScanner = new Scanner(f);
        fileScanner.nextLine();                                 //<---force ignore on first line of file
        while (fileScanner.hasNext()){
            String fileContent = fileScanner.nextLine();
            String[] fileSplit = fileContent.split("\\,");
            Person person = new Person(Integer.valueOf(fileSplit[0]), fileSplit[1], fileSplit[2], fileSplit[3], fileSplit[4], fileSplit[5]);
            personArray.add(person);

        }

        for (Person p : personArray) {

            if (!mapByCountry.containsKey(p.country)) {
                ArrayList<Person> subArray = new ArrayList<>();
                mapByCountry.put(p.country, subArray);
                subArray.add(p);
//                Collections.sort(subArray);

            } else {
                ArrayList<Person> subArray = mapByCountry.get(p.country);
                subArray.add(p);
                Collections.sort(subArray);

            }
            System.out.printf("%s -- %s %s from %s\n", p.id, p.firstName, p.lastName, p.country);
        }
        saveToJson(mapByCountry);
    }
    public static void saveToJson (HashMap<String, ArrayList<Person>>mapByCountry) throws IOException {
        File file = new File("people.json");
        JsonSerializer serializer = new JsonSerializer();
        String json = serializer.deep(true).serialize(mapByCountry);
        FileWriter fw = new FileWriter(file);
        fw.write(json);
        fw.close();
    }
    public static void scanFile () {

    }

}

