package com.example.weather;

import android.app.Activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

public class DataController {

    public static void SerializeData(String city, List<Temperature> data, Activity context){
        try (ObjectOutputStream oos = new ObjectOutputStream(
                context.openFileOutput(city + ".dat", context.MODE_PRIVATE))){
            oos.writeObject(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static List<Temperature> DeserializeData(String city, Activity context){
        try (ObjectInputStream ois = new ObjectInputStream(
                context.openFileInput(city + ".dat"))){
            List<Temperature> data = (List<Temperature>)ois.readObject();
            return data;
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
