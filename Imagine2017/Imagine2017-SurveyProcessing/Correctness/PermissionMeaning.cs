using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Correctness
{
    class PermissionMeaning
    {
        public static List<string> GetCorrectLocationMeaning()
        {
            List<string> meaning = new List<string>();

            meaning.Add("Access approximate location");

            return meaning;
        }

        public static List<string> GetCorrectContactsMeaning()
        {
            List<string> meaning = new List<string>();

            meaning.Add("Read your list of contacts");

            return meaning;
        }

        public static List<string> GetCorrectSmsMeaning()
        {
            List<string> meaning = new List<string>();

            meaning.Add("Send SMS messages");
            meaning.Add("Read SMS messages");
            
            return meaning;
        }

        public static List<string> GetSurveyContactsChoices()
        {
            List<string> meaning = new List<string>();

            meaning.Add("Read your list of contacts");
            meaning.Add("Add the app to your list of contacts");
            meaning.Add("Know when you communicate with one of your contacts");
            meaning.Add("Add & remove users from your contact list");
                   
            return meaning;
        }

        public static List<string> GetSurveySmsChoices()
        {
            List<string> meaning = new List<string>();

            meaning.Add("Send SMS messages");
            meaning.Add("Read SMS messages");
            meaning.Add("Delete existing SMS messages");
            meaning.Add("Disable the ability for the device to send SMS messages");
            meaning.Add("Share your phone number with 3rd parties");

            return meaning;
        }

        public static List<string> GetSurveyLocationChoices()
        {
            List<string> meaning = new List<string>();

            meaning.Add("Access approximate location");
            meaning.Add("View local Wi-Fi connections");
            meaning.Add("Access home location");
            meaning.Add("Update Google Maps preferences");
            meaning.Add("Change network connectivity status");

            return meaning;
        }
    }
}
