using System;
using System.Collections.Generic;
using System.Data.SQLite;
using System.IO;
using System.Linq;
using System.Web;
using System.Web.Services;

namespace AndroidPermissionWebApplication
{
    /// <summary>
    /// Summary description for PermSurvey
    /// </summary>
    [WebService(Namespace = "http://tempuri.org/")]
    [WebServiceBinding(ConformsTo = WsiProfiles.BasicProfile1_1)]
    [System.ComponentModel.ToolboxItem(false)]
    // To allow this Web Service to be called from script, using ASP.NET AJAX, uncomment the following line. 
    // [System.Web.Script.Services.ScriptService]
    public class PermSurvey : System.Web.Services.WebService
    {

        [WebMethod]
        public int NewUser(string app)
        {
           // DatabaseCheck();
            return CreateUser(app);
        }

        [WebMethod]
        public int SavePermission(int UserID, string Permission, string Operation)
        {
           // DatabaseCheck();
            return CreatePermissionResult(UserID, Permission, Operation);
        }

        [WebMethod]
        public List<string> GetAllUsers()
        {
           // DatabaseCheck();
            return GetUsers();
        }

        [WebMethod]
        public List<string> GetAllResults()
        {
          //  DatabaseCheck();
            return GetResults();
        }

        [WebMethod]
        public bool ProvisionDatabase()
        {            
            return CreateDatabase();
        }

        private List<string> GetUsers()
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            sqlite_conn = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;");
            sqlite_conn.Open();
            sqlite_cmd = sqlite_conn.CreateCommand();
            sqlite_cmd.CommandText = string.Format("SELECT * FROM User;");
            SQLiteDataReader reader = sqlite_cmd.ExecuteReader();
            List<string> result = new List<string>();
            result.Add(string.Format("ID,Date{0}", Environment.NewLine));
            while (reader.Read())
            {
                result.Add(string.Format("{0},{1}{2}", reader[0], reader[1], Environment.NewLine));
            }
            sqlite_cmd.Dispose();
            sqlite_conn.Close();            
            return result;
        }

        private List<string> GetResults()
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            sqlite_conn = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;");
            sqlite_conn.Open();
            sqlite_cmd = sqlite_conn.CreateCommand();
            sqlite_cmd.CommandText = string.Format("SELECT * FROM Result;");
            SQLiteDataReader reader = sqlite_cmd.ExecuteReader();
            List<string> result = new List<string>();
            result.Add(string.Format("ID,UserID,Permission,Operation,Date{0}", Environment.NewLine));
            while (reader.Read())
            {
                result.Add(string.Format("{0},{1},{2},{3},{4}{5}", reader[0], reader[1], reader[2], reader[3], reader[4],Environment.NewLine));
            }
            sqlite_cmd.Dispose();
            sqlite_conn.Close();
            return result;
        }

        private int CreatePermissionResult(int UserID, string Permission, string Operation)
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            sqlite_conn = new SQLiteConnection("Data Source="+ GetDatabasePath()+ ";Version=3;");
            sqlite_conn.Open();
            sqlite_cmd = sqlite_conn.CreateCommand();
            sqlite_cmd.CommandText = string.Format("INSERT INTO Result (UserID,Permission,Operation,DateTicks) VALUES ('{0}','{1}','{2}','{3}'); SELECT last_insert_rowid();", UserID,Permission,Operation, DateTime.Now.Ticks);
            object obj = sqlite_cmd.ExecuteScalar();
            sqlite_cmd.Dispose();
            sqlite_conn.Close();
            return Convert.ToInt32(obj);
        }

        private string GetDatabasePath()
        {
            //var fileName = Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData), "permission_survey.sqlite"); ;
            var fileName = string.Format(@"D:\home\site\wwwroot\{0}", "permission_survey.sqlite");
            return fileName;
        }

        private int CreateUser(string app)
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            sqlite_conn = new SQLiteConnection("Data Source=" + GetDatabasePath() + ";Version=3;");
            sqlite_conn.Open();
            sqlite_cmd = sqlite_conn.CreateCommand();
            sqlite_cmd.CommandText = string.Format("INSERT INTO User (DateTicks, APP) VALUES ('{0}','{1}'); SELECT last_insert_rowid();", DateTime.Now.Ticks,app);
            object obj = sqlite_cmd.ExecuteScalar();
            sqlite_cmd.Dispose();
            sqlite_conn.Close();
            return Convert.ToInt32(obj);
        }

        private bool CreateDatabase()
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

            if (!File.Exists(GetDatabasePath()))
            {
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
            }

            return true;
        }


        private void DatabaseCheck()
        {
            SQLiteConnection sqlite_conn;
            SQLiteCommand sqlite_cmd;

           // File.GetAttributes("permission_survey.sqlite");
            //FileInfo f = new FileInfo("permission_survey.sqlite");
            //var c = f.FullName;

            if (!File.Exists(GetDatabasePath()))
            {
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
            }
            else
            {
                sqlite_conn = new SQLiteConnection("DataSource=" + GetDatabasePath() + ";Version=3;");
                sqlite_conn.Open();
                sqlite_cmd = sqlite_conn.CreateCommand();
                sqlite_cmd.CommandText = @"SELECT * FROM sqlite_master WHERE name ='Result' and type='table';";
                sqlite_cmd.CommandType = System.Data.CommandType.Text;

                using (SQLiteDataReader sqlDataReader = sqlite_cmd.ExecuteReader())
                {
                    if (!sqlDataReader.Read())
                    {
                        using (SQLiteCommand command = new SQLiteCommand(sqlite_conn))
                        {
                            using (var transaction = sqlite_conn.BeginTransaction())
                            {
                                command.CommandText = "CREATE TABLE Result (" +
                                    "id integer primary key, " +
                                    "UserID  integer," +
                                    "Permission  varchar(300)," +
                                    "Operation  varchar(300)," +
                                    "DateTicks varchar(300));";
                                command.ExecuteNonQuery();
                                transaction.Commit();
                            }
                        }
                    }
                }

                sqlite_cmd.Dispose();

                sqlite_cmd = sqlite_conn.CreateCommand();
                sqlite_cmd.CommandText = @"SELECT * FROM sqlite_master WHERE name ='User' and type='table';";
                sqlite_cmd.CommandType = System.Data.CommandType.Text;

                using (SQLiteDataReader sqlDataReader = sqlite_cmd.ExecuteReader())
                {
                    if (!sqlDataReader.Read())
                    {
                        using (SQLiteCommand command = new SQLiteCommand(sqlite_conn))
                        {
                            using (var transaction = sqlite_conn.BeginTransaction())
                            {
                                command.CommandText = "CREATE TABLE User (" +
                                    "id integer primary key," +
                                    "APP varchar(300)," +
                                    "DateTicks varchar(300)); ";
                                command.ExecuteNonQuery();
                                transaction.Commit();
                            }
                        }
                    }
                }

                sqlite_cmd.Dispose();
                sqlite_conn.Close();
            }

            sqlite_conn.Close();
        }
    }
}
