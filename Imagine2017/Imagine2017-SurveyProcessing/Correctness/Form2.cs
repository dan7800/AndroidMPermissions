using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows.Forms;

namespace Correctness
{
    public partial class Form2 : Form
    {
        public Form2()
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

            columnNames.Add("UserSurvey_Location");
            columnNames.Add("UserSurvey_Location_Count");
            columnNames.Add("Location_TP");
            columnNames.Add("Location_TP_Count");
            columnNames.Add("Location_TN");
            columnNames.Add("Location_TN_Count");
            columnNames.Add("Location_FP");
            columnNames.Add("Location_FP_Count");
            columnNames.Add("Location_FN");
            columnNames.Add("Location_FN_Count");

            columnNames.Add("UserSurvey_Contacts");
            columnNames.Add("UserSurvey_Contacts_Count");
            columnNames.Add("Contacts_TP");
            columnNames.Add("Contacts_TP_Count");
            columnNames.Add("Contacts_TN");
            columnNames.Add("Contacts_TN_Count");
            columnNames.Add("Contacts_FP");
            columnNames.Add("Contacts_FP_Count");
            columnNames.Add("Contacts_FN");
            columnNames.Add("Contacts_FN_Count");

            columnNames.Add("UserSurvey_SMS");
            columnNames.Add("UserSurvey_SMS_Count");
            columnNames.Add("SMS_TP");
            columnNames.Add("SMS_TP_Count");
            columnNames.Add("SMS_TN");
            columnNames.Add("SMS_TN_Count");
            columnNames.Add("SMS_FP");
            columnNames.Add("SMS_FP_Count");
            columnNames.Add("SMS_FN");
            columnNames.Add("SMS_FN_Count");

            List<string[]> values = new List<string[]>();
            foreach (SurveyResult result in surveyResults)
            {
                values.Add(new string[32] {
                    result.UserID,
                    result.AppID,

                    String.Join(",",result.LocationPermissionMeaningList),
                    result.LocationPermissionMeaningList.Count.ToString(),
                    String.Join(",",result.loc_TruePositive),
                    result.loc_TruePositive.Count.ToString(),
                    String.Join(",",result.loc_TrueNegative),
                    result.loc_TrueNegative.Count.ToString(),
                    String.Join(",",result.loc_FalsePositive),
                    result.loc_FalsePositive.Count.ToString(),
                    String.Join(",",result.loc_FalseNegative),
                    result.loc_FalseNegative.Count.ToString(),

                    String.Join(",",result.ContactsPermissionMeaningList),
                    result.ContactsPermissionMeaningList.Count.ToString(),
                    String.Join(",",result.cnt_TruePositive),
                    result.cnt_TruePositive.Count.ToString(),
                    String.Join(",",result.cnt_TrueNegative),
                    result.cnt_TrueNegative.Count.ToString(),
                    String.Join(",",result.cnt_FalsePositive),
                    result.cnt_FalsePositive.Count.ToString(),
                    String.Join(",",result.cnt_FalseNegative),
                    result.cnt_FalseNegative.Count.ToString(),

                    String.Join(",",result.SmsPermissionMeaningList),
                    result.SmsPermissionMeaningList.Count.ToString(),
                    String.Join(",",result.sms_TruePositive),
                    result.sms_TruePositive.Count.ToString(),
                    String.Join(",",result.sms_TrueNegative),
                    result.sms_TrueNegative.Count.ToString(),
                    String.Join(",",result.sms_FalsePositive),
                    result.sms_FalsePositive.Count.ToString(),
                    String.Join(",",result.sms_FalseNegative),
                    result.sms_FalseNegative.Count.ToString(),
                });
            }
            CSVWriter.WriteOuput(columnNames, values);
        }



        private void ProcessCorrectness(List<SurveyResult> surveyResults)
        {
            for (int i = 0; i < surveyResults.Count; i++)
            {
                /****Location Precision & Recall****/
                for (int j = 0; j < surveyResults[i].LocationPermissionMeaningList.Count; j++)
                {
                    //user selected meaning is correct meaning
                    if (PermissionMeaning.GetCorrectLocationMeaning().Contains(surveyResults[i].LocationPermissionMeaningList[j]))
                    {
                        surveyResults[i].loc_TruePositive.Add(surveyResults[i].LocationPermissionMeaningList[j]);
                    }
                    //user selected meaning is not correct meaning
                    else
                    {
                        surveyResults[i].loc_FalsePositive.Add(surveyResults[i].LocationPermissionMeaningList[j]);
                    }
                }

                foreach (string meaning in PermissionMeaning.GetSurveyLocationChoices())
                {
                    //user not selected meaning
                    if (!surveyResults[i].LocationPermissionMeaningList.Contains(meaning))
                    {
                        // app not requested perm
                        if (!PermissionMeaning.GetCorrectLocationMeaning().Contains(meaning))
                        {
                            surveyResults[i].loc_TrueNegative.Add(meaning);
                        }
                        //app requested the perm
                        else
                        {
                            surveyResults[i].loc_FalseNegative.Add(meaning);
                        }
                    }
                }
                /**************************************************************/
                /****Contacts Precision & Recall****/
                for (int j = 0; j < surveyResults[i].ContactsPermissionMeaningList.Count; j++)
                {
                    //user selected meaning is correct meaning
                    if (PermissionMeaning.GetCorrectContactsMeaning().Contains(surveyResults[i].ContactsPermissionMeaningList[j]))
                    {
                        surveyResults[i].cnt_TruePositive.Add(surveyResults[i].ContactsPermissionMeaningList[j]);
                    }
                    //user selected meaning is not correct meaning
                    else
                    {
                        surveyResults[i].cnt_FalsePositive.Add(surveyResults[i].ContactsPermissionMeaningList[j]);
                    }
                }

                foreach (string meaning in PermissionMeaning.GetSurveyContactsChoices())
                {
                    //user not selected meaning
                    if (!surveyResults[i].ContactsPermissionMeaningList.Contains(meaning))
                    {
                        // app not requested perm
                        if (!PermissionMeaning.GetCorrectContactsMeaning().Contains(meaning))
                        {
                            surveyResults[i].cnt_TrueNegative.Add(meaning);
                        }
                        //app requested the perm
                        else
                        {
                            surveyResults[i].cnt_FalseNegative.Add(meaning);
                        }
                    }
                }
                /**************************************************************/
                /****SMS Precision & Recall****/
                for (int j = 0; j < surveyResults[i].SmsPermissionMeaningList.Count; j++)
                {
                    //user selected meaning is correct meaning
                    if (PermissionMeaning.GetCorrectSmsMeaning().Contains(surveyResults[i].SmsPermissionMeaningList[j]))
                    {
                        surveyResults[i].sms_TruePositive.Add(surveyResults[i].SmsPermissionMeaningList[j]);
                    }
                    //user selected meaning is not correct meaning
                    else
                    {
                        surveyResults[i].sms_FalsePositive.Add(surveyResults[i].SmsPermissionMeaningList[j]);
                    }
                }

                foreach (string meaning in PermissionMeaning.GetSurveySmsChoices())
                {
                    //user not selected meaning
                    if (!surveyResults[i].SmsPermissionMeaningList.Contains(meaning))
                    {
                        // app not requested perm
                        if (!PermissionMeaning.GetCorrectSmsMeaning().Contains(meaning))
                        {
                            surveyResults[i].sms_TrueNegative.Add(meaning);
                        }
                        //app requested the perm
                        else
                        {
                            surveyResults[i].sms_FalseNegative.Add(meaning);
                        }
                    }
                }
                /**************************************************************/
            }
        }
    }
}
