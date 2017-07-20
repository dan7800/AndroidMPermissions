using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Data.SQLite;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Correctness
{
    public partial class Form1 : Form
    {


        public Form1()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            Database db = new Database();
            List<SurveyResult> list = db.GetSurveyResults();
            ProcessCorrectness(list);
            OutputResults(list);

            MessageBox.Show("Done!");

        }

        private void OutputResults(List<SurveyResult> surveyResults)
        {
            List<string> columnNames = new List<string>();
            columnNames.Add("UserID");
            columnNames.Add("AppID");
            columnNames.Add("UserSurveyPerms");
            columnNames.Add("UserSurveyPerms_Count");
            columnNames.Add("TruePositivePerms");
            columnNames.Add("TruePositivePerms_Count");
            columnNames.Add("FalsePositivePerms");
            columnNames.Add("FalsePositivePerms_Count");
            columnNames.Add("TrueNegativePerms");
            columnNames.Add("TrueNegativePerms_Count");
            columnNames.Add("FalseNegativePerms");
            columnNames.Add("FalseNegativePerms_Count");

            List<string[]> values = new List<string[]>();
            foreach (SurveyResult result in surveyResults)
            {
                values.Add(new string[12] {
                    result.UserID,
                    result.AppID,
                    String.Join(",",result.UserSelectedPermissionsList),
                    result.UserSelectedPermissionsList.Count.ToString(),
                    String.Join(",",result.PermTruePositive),
                    result.PermTruePositive.Count.ToString(),
                    String.Join(",",result.PermFalsePositive),
                    result.PermFalsePositive.Count.ToString(),
                    String.Join(",",result.Perm_TrueNegative),
                    result.Perm_TrueNegative.Count.ToString(),
                    String.Join(",",result.Perm_FalseNegative),
                    result.Perm_FalseNegative.Count.ToString()});
            }

            CSVWriter.WriteOuput(columnNames, values);
        }


        private void ProcessCorrectness(List<SurveyResult> surveyResults)
        {
            for (int i = 0; i < surveyResults.Count; i++)
            {
                switch (surveyResults[i].AppID)
                {
                    case "21":
                    case "23":
                    case "21E":
                        for (int j = 0; j < surveyResults[i].UserSelectedPermissionsList.Count; j++)
                        {
                            //user selected perm is requested by app
                            if (AppPermissions.GetFixedPermissions().Contains(surveyResults[i].UserSelectedPermissionsList[j]))
                            {
                                surveyResults[i].PermTruePositive.Add(surveyResults[i].UserSelectedPermissionsList[j]);
                            }
                            //user selected perm is not requested by app
                            else
                            {
                                surveyResults[i].PermFalsePositive.Add(surveyResults[i].UserSelectedPermissionsList[j]);//==null?string.Empty:surveyResults[i].RequestedPermissionsList[j]);
                            }
                        }

                        foreach (string perm in AppPermissions.GetSurveyPermissionChoices())
                        {
                            //user not selected perm
                            if (!surveyResults[i].UserSelectedPermissionsList.Contains(perm))
                            {
                                // app not requested perm
                                if (!AppPermissions.GetFixedPermissions().Contains(perm))
                                {
                                    surveyResults[i].Perm_TrueNegative.Add(perm);
                                }
                                //app requested the perm
                                else
                                {
                                    surveyResults[i].Perm_FalseNegative.Add(perm);
                                }
                            }
                        }


                        break;
                    case "23Rnd":
                    case "21ERnd":
                        for (int j = 0; j < surveyResults[i].UserSelectedPermissionsList.Count; j++)
                        {
                            if (AppPermissions.GetRandomPermissions(surveyResults[i].UserID).Contains(surveyResults[i].UserSelectedPermissionsList[j]))
                            {
                                surveyResults[i].PermTruePositive.Add(surveyResults[i].UserSelectedPermissionsList[j]);
                            }
                            else
                            {
                                surveyResults[i].PermFalsePositive.Add(surveyResults[i].UserSelectedPermissionsList[j]);// == null ? string.Empty : surveyResults[i].RequestedPermissionsList[j]);
                            }
                        }

                        foreach (string perm in AppPermissions.GetSurveyPermissionChoices())
                        {
                            //user not selected perm
                            if (!surveyResults[i].UserSelectedPermissionsList.Contains(perm))
                            {
                                // app not requested perm
                                if (!AppPermissions.GetRandomPermissions(surveyResults[i].UserID).Contains(perm))
                                {
                                    surveyResults[i].Perm_TrueNegative.Add(perm);
                                }
                                // app requested the perm
                                else
                                {
                                    surveyResults[i].Perm_FalseNegative.Add(perm);
                                }
                            }
                        }
                        break;
                }
            }
        }


    }
}
