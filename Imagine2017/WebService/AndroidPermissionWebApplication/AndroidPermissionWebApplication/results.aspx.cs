using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Text;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace AndroidPermissionWebApplication
{
    public partial class results : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            StringBuilder sbUsers = new StringBuilder();
            foreach (var item in GetUsers())
            {
                sbUsers.AppendLine(string.Format("{0}<br>", item));
            }
            lblUsers.Text = sbUsers.ToString();

            StringBuilder sbresults = new StringBuilder();
            foreach (var item in GetResults())
            {
                sbresults.AppendLine(string.Format("{0}<br>", item));
            }
            lblResults.Text = sbresults.ToString();
        }

        private List<string> GetUsers()
        {

            List<string> result = new List<string>();
            result.Add(string.Format("ID,App,Date_Ticks,Date_Text"));

            using (var dbConnection = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;"))
            {
                using (SQLiteCommand command = new SQLiteCommand(dbConnection))
                {
                    dbConnection.Open();
                    using (var transaction = dbConnection.BeginTransaction())
                    {

                        command.CommandText = "SELECT * FROM User;";
                        command.CommandType = System.Data.CommandType.Text;
                        SQLiteDataReader reader = command.ExecuteReader();
                        while (reader.Read())
                        {
                            result.Add(string.Format("{0},{1},{2},{3}", reader[0], reader[1], reader[2], new DateTime(Convert.ToInt64(reader[2])).ToString()));
                        }
                    }
                    dbConnection.Close();
                }
            }

            return result;
        }

        private List<string> GetResults()
        {
            List<string> result = new List<string>();
            result.Add(string.Format("ID,UserID,Permission,Operation,Date_Ticks,Date_Text"));

            using (var dbConnection = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;"))
            {
                using (SQLiteCommand command = new SQLiteCommand(dbConnection))
                {
                    dbConnection.Open();
                    using (var transaction = dbConnection.BeginTransaction())
                    {

                        command.CommandText = "SELECT* FROM Result; ";
                        command.CommandType = System.Data.CommandType.Text;
                        SQLiteDataReader reader = command.ExecuteReader();
                        while (reader.Read())
                        {
                            result.Add(string.Format("{0},{1},{2},{3},{4},{5}", reader[0], reader[1], reader[2], reader[3], reader[4], new DateTime(Convert.ToInt64(reader[4])).ToString()));
                        }
                    }
                    dbConnection.Close();
                }
            }

            return result;
        }

        private string GetDatabasePath()
        {
            //  var fileName = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), "permission_survey.sqlite"); ;
            var fileName = string.Format(@"D:\home\site\wwwroot\{0}", "permission_survey.sqlite");
            return fileName;
        }
    }
}