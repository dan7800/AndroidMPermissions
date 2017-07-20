using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Correctness
{


    class Database
    {

        private const string connection_string = "Data Source=permission_survey.sqlite;Version=3;";
        private const string file_name = "permission_survey.sqlite";

        public List<SurveyResult> GetSurveyResults()
        {
            List<SurveyResult> resultList = new List<SurveyResult>();
            SurveyResult result;

            using (var dbConnection = new SQLiteConnection(connection_string))
            {
                using (SQLiteCommand command = new SQLiteCommand(dbConnection))
                {
                    dbConnection.Open();
                    using (var transaction = dbConnection.BeginTransaction())
                    {
                        command.CommandText = "select * from view_join_user_RVEVcombined";
                        command.CommandType = System.Data.CommandType.Text;
                        SQLiteDataReader reader = command.ExecuteReader();
                        while (reader.Read())
                        {
                            result = new SurveyResult();
                            result.AppID = reader["APP"].ToString();
                            result.UserID = reader["UserID"].ToString();
                            result.UserSelectedPermissions = reader["Check_All_The_Permissions_The_App_Asked_For"].ToString();
                            result.LocationPermissionMeaning = reader["The_Location_Permission_Means_The_App_Can"].ToString();
                            result.ContactsPermissionMeaning = reader["The_Contacts_Permission_Means_The_App_Can"].ToString();
                            result.SmsPermissionMeaning = reader["The_SMS_Permission_Means_The_App_can"].ToString();
                            resultList.Add(result);
                        }
                    }
                    dbConnection.Close();
                }
            }

            return resultList;
        }

        public List<string> GetRequestedPermissions(string userID)
        {
            List<string> permissions = new List<string>();

            using (var dbConnection = new SQLiteConnection(connection_string))
            {
                using (SQLiteCommand command = new SQLiteCommand(dbConnection))
                {
                    dbConnection.Open();
                    using (var transaction = dbConnection.BeginTransaction())
                    {
                        command.CommandText = string.Format("select Permission from Result where UserID='{0}'",userID);
                        command.CommandType = System.Data.CommandType.Text;
                        SQLiteDataReader reader = command.ExecuteReader();
                        while (reader.Read())
                        {
                            permissions.Add(reader["Permission"].ToString());
                        }
                    }
                    dbConnection.Close();
                }
            }

            return permissions;
        }
    }
}
