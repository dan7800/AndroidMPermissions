package edu.rit.se.imagine2017.a21ERndtictactoe;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;


public final class ServiceCall {

    /*
      Web Service to store user record
     */
    public static String SaveUser(String AppVersion) {
        SoapObject request = new SoapObject("http://tempuri.org/", "NewUser");
        PropertyInfo propertyInfo = new PropertyInfo();
        propertyInfo.setName("app");
        propertyInfo.setValue(AppVersion);
        propertyInfo.setType(String.class);
        request.addProperty(propertyInfo);

        SoapSerializationEnvelope envp = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envp.dotNet = true;
        envp.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE("http://androidpermissionwebapplication20170501040559.azurewebsites.net/Permsurvey.asmx");
        try {
            androidHttpTransport.call("http://tempuri.org/NewUser", envp);
            Object response = (SoapPrimitive) envp.getResponse();
            return response.toString();
        } catch (Exception e) {
            Log.i("WS Error->", e.toString());
            return "Null";
        }
    }


    /*
    This method calls the web service to store details related to user authorization of permissions
     */
    public static void SavePermissionAction(String UserID, String Permission, String Operation) {
        SoapObject request = new SoapObject("http://tempuri.org/", "SavePermission");

        PropertyInfo propertyInfo1 = new PropertyInfo();
        propertyInfo1.setName("UserID");
        propertyInfo1.setValue(UserID);
        propertyInfo1.setType(Integer.class);
        request.addProperty(propertyInfo1);

        PropertyInfo propertyInfo2 = new PropertyInfo();
        propertyInfo2.setName("Permission");
        propertyInfo2.setValue(Permission);
        propertyInfo2.setType(String.class);
        request.addProperty(propertyInfo2);

        PropertyInfo propertyInfo3 = new PropertyInfo();
        propertyInfo3.setName("Operation");
        propertyInfo3.setValue(Operation);
        propertyInfo3.setType(String.class);
        request.addProperty(propertyInfo3);

        SoapSerializationEnvelope envp = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envp.dotNet = true;
        envp.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE("http://androidpermissionwebapplication20170501040559.azurewebsites.net/Permsurvey.asmx");
        try {
            androidHttpTransport.call("http://tempuri.org/SavePermission", envp);
            //we don't need a response from the service call
            //Object response = (SoapPrimitive)envp.getResponse();
        } catch (Exception e) {
            Log.i("WS Error->", e.toString());
        }
    }

}
