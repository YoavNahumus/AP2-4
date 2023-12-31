package com.example.ap2_4;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;
import java.text.DateFormat;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static String fromBitmap(Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, outputStream);
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT);
    }

    @TypeConverter
    public static Bitmap toBitmap(String encoded) {
        if (encoded == null) {
            return null;
        }
        int index = encoded.indexOf(',');
        if (index != -1) {
            encoded = encoded.substring(index + 1);
        }
        byte[] bytes = Base64.decode(encoded, Base64.DEFAULT);
        Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return Bitmap.createBitmap(bitmap);
    }

    @TypeConverter
    public static String fromDate(Date date) {
        if (date == null) {
            return null;
        }
        return date.toString();
    }

    @TypeConverter
    public static Date toDate(String date) {
        if (date == null) {
            return null;
        }
        try {
            return DateFormat.getDateTimeInstance().parse(date);
        } catch (Exception e) {
            return null;
        }
    }
}
