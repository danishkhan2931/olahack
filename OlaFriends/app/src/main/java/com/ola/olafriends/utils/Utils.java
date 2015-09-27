package com.ola.olafriends.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.Environment;
import android.util.Base64;

import java.io.File;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by thecodegame on 13-09-2015.
 */
public class Utils {

    private Utils() {
    }

    public static Bitmap loadBitmap(String path) {
        return BitmapFactory.decodeFile(Environment.getExternalStorageDirectory().getPath() + path);
    }

    public static boolean checkIfFileExists(String path) {
        File f = new File(Environment.getExternalStorageDirectory() + path);
        boolean ret = false;
        if (f.exists() && f.isFile()) {
            ret = true;
        }
        Debug.i("Checking for file: " + path + " ret = " + ret);
        return ret;
    }

    public static boolean checkForDirectory(Context context, String path) {
        File f = new File(Environment.getExternalStorageDirectory().getPath());
        f = new File(f, path);
        if (f.exists() == true && f.isDirectory() == true) {
            Debug.e(path + " already exists.");
            return true;
        }
        Debug.e(path + " doesnt exists. So trying to create");
        f.mkdirs();
        f.setExecutable(true);
        f.setReadable(true);
        f.setWritable(true);
        // initiate media scan and put the new things into the path array to
        // make the scanner aware of the location and the files you want to see
        MediaScannerConnection.scanFile(context, new String[]{f.toString()}, null, null);

        Boolean successs = (f.exists() && f.isDirectory());
        Debug.i("checking if it was successfully created. " + successs);
        return successs;
    }


    public static String printKeyHash(Context context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Debug.e("Package Name=" + context.getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Debug.e("Key Hash=" + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Debug.e("Name not found" + e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Debug.e("No such an algorithm" + e.toString());
        } catch (Exception e) {
            Debug.e("Exception" + e.toString());
        }

        return key;
    }
}
