package com.example.weather;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class Temperature implements Serializable {
    private String date;
    private double temp;
    private transient Bitmap img;

    public Temperature(String date, double temp, String url){
        setDate(date);
        setTemp(temp);
        DownloadPic(url);
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public String getDate() {
        return date;
    }

    public double getTemp() {
        return temp;
    }

    public Bitmap getImg() {
        return img;
    }

    private void DownloadPic(String src){
        HttpsURLConnection conn = null;
        URL url = null;
        try {
            url = new URL("https:" + src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            int responsecode = connection.getResponseCode();
            if (responsecode != 200) {
                throw new RuntimeException("HttpResponseCode: " + responsecode);
            } else {
                InputStream input = connection.getInputStream();
                img = BitmapFactory.decodeStream(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException{
        oos.defaultWriteObject();

        ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
        boolean success = img.compress(
                Bitmap.CompressFormat.PNG,
                100,
                byteStream);
        if(success){
            oos.writeObject(byteStream.toByteArray());
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException{
        ois.defaultReadObject();
        byte[] image = (byte[]) ois.readObject();
        if(image != null && image.length > 0){
            img = BitmapFactory.decodeByteArray(image, 0, image.length);
        }
    }
}
