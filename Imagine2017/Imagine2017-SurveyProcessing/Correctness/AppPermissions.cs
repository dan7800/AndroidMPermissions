using System.Collections.Generic;


namespace Correctness
{
    class AppPermissions
    {
        public static List<string> GetFixedPermissions()
        {
            List<string> permissions = new List<string>();

            permissions.Add(MapAndroidPermissionToSurveyText("WRITE_EXTERNAL_STORAGE"));
            permissions.Add(MapAndroidPermissionToSurveyText("READ_PHONE_STATE"));
            permissions.Add(MapAndroidPermissionToSurveyText("ACCESS_FINE_LOCATION"));
            permissions.Add(MapAndroidPermissionToSurveyText("GET_ACCOUNTS"));

            return permissions;
        }

        public static List<string> GetRandomPermissions(string userID)
        {
            Database db = new Database();
            List<string> permissions = db.GetRequestedPermissions(userID);

            for (int i = 0; i < permissions.Count; i++)
            {
                permissions[i] = MapAndroidPermissionToSurveyText(permissions[i]);
            }

            return permissions;
        }

        public static List<string> GetSurveyPermissionChoices()
        {
            List<string> permissions = new List<string>();

            permissions.Add("Record Audio");
            permissions.Add("External Storage");
            permissions.Add("Access Location");
            permissions.Add("Record Audio");
            permissions.Add("Read SMS Messages");
            permissions.Add("Read Contacts");
            permissions.Add("Access Photographs");
            permissions.Add("Manage Phone Calls");

            return permissions;
        }

        /*
        public static List<string> Get21Permissions()
        {
            List<string> permissions = new List<string>();

            permissions.Add(MapAndroidPermissionToSurveyText("WRITE_EXTERNAL_STORAGE"));
            permissions.Add(MapAndroidPermissionToSurveyText("READ_PHONE_STATE"));
            permissions.Add(MapAndroidPermissionToSurveyText("ACCESS_FINE_LOCATION"));
            permissions.Add(MapAndroidPermissionToSurveyText("GET_ACCOUNTS"));

            return permissions;
        }

        public static List<string> Get23Permissions()
        {
            List<string> permissions = new List<string>();

            permissions.Add(MapAndroidPermissionToSurveyText("WRITE_EXTERNAL_STORAGE"));
            permissions.Add(MapAndroidPermissionToSurveyText("READ_PHONE_STATE"));
            permissions.Add(MapAndroidPermissionToSurveyText("ACCESS_FINE_LOCATION"));
            permissions.Add(MapAndroidPermissionToSurveyText("GET_ACCOUNTS"));

            return permissions;
        }


        public static List<string> Get21EPermissions()
        {
            List<string> permissions = new List<string>();

            permissions.Add(MapAndroidPermissionToSurveyText("WRITE_EXTERNAL_STORAGE"));
            permissions.Add(MapAndroidPermissionToSurveyText("READ_PHONE_STATE"));
            permissions.Add(MapAndroidPermissionToSurveyText("ACCESS_FINE_LOCATION"));
            permissions.Add(MapAndroidPermissionToSurveyText("GET_ACCOUNTS"));

            return permissions;
        }

        public static List<string> Get23RPermissions(string userID)
        {
            Database db = new Database();
            List<string> permissions = db.GetRequestedPermissions(userID);

            for (int i = 0; i < permissions.Count; i++)
            {
                permissions[i] = MapAndroidPermissionToSurveyText(permissions[i]);
            }

            return permissions;
        }

        public static List<string> Get21ERPermissions(string userID)
        {
            Database db = new Database();
            List<string> permissions = db.GetRequestedPermissions(userID);

            for (int i = 0; i < permissions.Count; i++)
            {
                permissions[i] = MapAndroidPermissionToSurveyText(permissions[i]);
            }

            return permissions;
        }
        */

        private static string MapAndroidPermissionToSurveyText(string permission)
        {
            string permissionText = string.Empty;

            switch (permission)
            {
                case "WRITE_EXTERNAL_STORAGE":
                    permissionText = "External Storage";
                    break;
                case "READ_PHONE_STATE":
                    permissionText = "Manage Phone Calls";
                    break;
                case "ACCESS_FINE_LOCATION":
                    permissionText = "Access Location";
                    break;
                case "GET_ACCOUNTS":
                    permissionText = "Read Contacts";
                    break;
            }

            return permissionText;
        }

    }
}
