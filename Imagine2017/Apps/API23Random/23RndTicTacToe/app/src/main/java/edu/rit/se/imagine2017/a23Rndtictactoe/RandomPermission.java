package edu.rit.se.imagine2017.a23Rndtictactoe;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;

import java.util.Arrays;
import java.util.Collections;

public final class RandomPermission {

    public static AppPermission[] GenerateRandomPermissionSet() {
        //AppPermission[] permissionArray = new AppPermission[4];
        AppPermission[] permissionArray = new AppPermission[2];

        AppPermission permission = new AppPermission();
        permission.Permission = "android.permission.WRITE_EXTERNAL_STORAGE";
        permission.PermissionDB = "WRITE_EXTERNAL_STORAGE";
        permission.PermissionRequestText="The app needs to access your device storage to save your score";
        permission.PermissionRequestDenyText="Unable access your device storage";
        permission.PermissionRequestAuthorizeText="Your score has been saved on the device";
        permissionArray[0] = permission;

        permission = new AppPermission();
        permission.Permission = "android.permission.GET_ACCOUNTS";
        permission.PermissionDB = "GET_ACCOUNTS";
        permission.PermissionRequestText="The app needs to access your contacts to share your score";
        permission.PermissionRequestDenyText="Unable to access your contacts";
        permission.PermissionRequestAuthorizeText="Your score will be shared with your contacts";
        permissionArray[1] = permission;

        permission = new AppPermission();
        permission.Permission = "android.permission.READ_PHONE_STATE";
        permission.PermissionDB = "READ_PHONE_STATE";
        permission.PermissionRequestText="The app needs to access your network operator for a better user experience";
        permission.PermissionRequestDenyText="Unable to access network operator";
        permission.PermissionRequestAuthorizeText="Network operator accessed";
      //  permissionArray[2] = permission;

        permission = new AppPermission();
        permission.Permission = "android.permission.ACCESS_FINE_LOCATION";
        permission.PermissionDB = "ACCESS_FINE_LOCATION";
        permission.PermissionRequestText="The app needs to access your location to search for nearby friends";
        permission.PermissionRequestDenyText="Unable to access your location";
        permission.PermissionRequestAuthorizeText="Friends located";
     //   permissionArray[3] = permission;

        Collections.shuffle(Arrays.asList(permissionArray));
        return permissionArray;
    }

}
