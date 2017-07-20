using System.Collections.Generic;
using System.Linq;

namespace Correctness
{
    class SurveyResult
    {
        private string userID;
        private string userSelectedPermissions;
        private string appID;
        private string locationPermissionMeaning;
        private string contactsPermissionMeaning;
        private string smsPermissionMeaning;

        // Permissions requested correct identified as being requested
        private List<string> perm_TruePositive = new List<string>();

        // The user says it wasn’t requested, and it wasn’t requested
        private List<string> perm_TrueNegative = new List<string>();

        // The user says it was requested, when it wasn't
        private List<string> perm_FalsePositive = new List<string>();

        // The user says it wasn’t requested, when it was
        private List<string> perm_FalseNegative = new List<string>();


        /*****Location Permission Meaning*****/
        public List<string> loc_TruePositive = new List<string>();            // Permissions meaning correct; identified as being correct
        public List<string> loc_TrueNegative = new List<string>();            // The user says it isn't the meaning, and it its not the meaning
        public List<string> loc_FalsePositive = new List<string>();            // The user says it is the meaning, when it isn't        
        public List<string> loc_FalseNegative = new List<string>();            // The user says it isn’t the meaning, when it was

        /*****SMS Permission Meaning*****/
        public List<string> sms_TruePositive = new List<string>();            // Permissions meaning correct; identified as being correct
        public List<string> sms_TrueNegative = new List<string>();            // The user says it isn't the meaning, and it its not the meaning
        public List<string> sms_FalsePositive = new List<string>();            // The user says it is the meaning, when it isn't        
        public List<string> sms_FalseNegative = new List<string>();            // The user says it isn’t the meaning, when it was

        /*****Contacts Permission Meaning*****/
        public List<string> cnt_TruePositive = new List<string>();            // Permissions meaning correct; identified as being correct
        public List<string> cnt_TrueNegative = new List<string>();            // The user says it isn't the meaning, and it its not the meaning
        public List<string> cnt_FalsePositive = new List<string>();            // The user says it is the meaning, when it isn't        
        public List<string> cnt_FalseNegative = new List<string>();            // The user says it isn’t the meaning, when it was


        public string UserID { get => userID; set => userID = value; }
        public string UserSelectedPermissions { get => userSelectedPermissions; set => userSelectedPermissions = value; }
        public string AppID { get => appID; set => appID = value; }

        public List<string> UserSelectedPermissionsList
        {
            get =>
                !string.IsNullOrEmpty(userSelectedPermissions) ?
                userSelectedPermissions.Split(',').Select(sValue => sValue.Trim()).ToList() :
                new List<string>();
        }

        public List<string> LocationPermissionMeaningList
        {
            get =>
                !string.IsNullOrEmpty(LocationPermissionMeaning) ?
                LocationPermissionMeaning.Split(',').Select(sValue => sValue.Trim()).ToList() :
                new List<string>();
        }

        public List<string> SmsPermissionMeaningList
        {
            get =>
                !string.IsNullOrEmpty(smsPermissionMeaning) ?
                smsPermissionMeaning.Split(',').Select(sValue => sValue.Trim()).ToList() :
                new List<string>();
        }

        public List<string> ContactsPermissionMeaningList
        {
            get =>
                !string.IsNullOrEmpty(contactsPermissionMeaning) ?
                contactsPermissionMeaning.Split(',').Select(sValue => sValue.Trim()).ToList() :
                new List<string>();
        }
        public List<string> PermTruePositive { get => perm_TruePositive; set => perm_TruePositive = value; }
        public List<string> PermFalsePositive { get => perm_FalsePositive; set => perm_FalsePositive = value; }
        public List<string> Perm_FalseNegative { get => perm_FalseNegative; set => perm_FalseNegative = value; }
        public List<string> Perm_TrueNegative { get => perm_TrueNegative; set => perm_TrueNegative = value; }
        public string LocationPermissionMeaning { get => locationPermissionMeaning; set => locationPermissionMeaning = value; }
        public string ContactsPermissionMeaning { get => contactsPermissionMeaning; set => contactsPermissionMeaning = value; }
        public string SmsPermissionMeaning { get => smsPermissionMeaning; set => smsPermissionMeaning = value; }
    }

}

