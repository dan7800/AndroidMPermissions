using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;

namespace AndroidPermissionWebApplication
{
    public partial class DBConfig : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            if (File.Exists(GetDatabasePath()))
            {
                lblDBExists.Text = "True";
            }
            else
            {
                lblDBExists.Text = "False";
            }

        }

        protected void btnDB_Click(object sender, EventArgs e)
        {
            int i;
            int.TryParse(txtDBConfirm.Text, out i);
            if (i == 4)
            {
                DropTables();

                CreateDatabase();

                lblStatus.Text = "Database recreated";
            }
            else
            {
                lblStatus.Text = "Value provided in the textbox is incorrect";
            }
        }

        private string GetDatabasePath()
        {
            //var fileName = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), "permission_survey.sqlite"); ;
            var fileName = string.Format(@"D:\home\site\wwwroot\{0}", "permission_survey.sqlite");
            return fileName;
        }


        private bool DropTables()
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            SQLiteConnection.CreateFile(GetDatabasePath());

            sqlite_conn = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;");
            sqlite_conn.Open();
            try
            {
                sqlite_cmd = sqlite_conn.CreateCommand();
                sqlite_cmd.CommandText = "DROP TABLE User;";
                sqlite_cmd.ExecuteNonQuery();
                sqlite_cmd.Dispose();
            }
            catch (Exception err) { }

            try
            {
                sqlite_cmd = sqlite_conn.CreateCommand();
                sqlite_cmd.CommandText = "DROP TABLE Result; ";
                sqlite_cmd.ExecuteNonQuery();
                sqlite_cmd.Dispose();
            }
            catch (Exception err) { }

            sqlite_conn.Close();
            sqlite_conn.Dispose();

            return true;
        }

        private bool CreateDatabase()
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            SQLiteConnection.CreateFile(GetDatabasePath());

            sqlite_conn = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;");
            sqlite_conn.Open();
            sqlite_cmd = sqlite_conn.CreateCommand();
            sqlite_cmd.CommandText = "CREATE TABLE Result (" +
                "id integer primary key, " +
                "UserID  integer," +
                "Permission  varchar(300)," +
                "Operation  varchar(300)," +
                "DateTicks varchar(300));";
            sqlite_cmd.ExecuteNonQuery();

            sqlite_cmd.CommandText = "CREATE TABLE User (" +
                "id integer primary key," +
                "APP varchar(300)," +
                "DateTicks varchar(300)); ";
            sqlite_cmd.ExecuteNonQuery();

            sqlite_cmd.Dispose();
            sqlite_conn.Close();
            sqlite_conn.Dispose();


            return true;
        }
    }
}