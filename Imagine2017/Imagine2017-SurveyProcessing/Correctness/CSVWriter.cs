using System;
using System.Collections.Generic;
using System.IO;

namespace Correctness
{
    class CSVWriter
    {
        public static void WriteOuput(List<string> columnNames, List<string[]> values)
        {
            string fileName = string.Format("Output_{0}.txt", DateTime.Now.Ticks.ToString());

            using (StreamWriter w = File.AppendText(fileName))
            {

                for (int i = 0; i < columnNames.Count; i++)
                {
                    w.Write(string.Format("{0}{1}",
                        columnNames[i],
                        i == columnNames.Count - 1 ? Environment.NewLine : ";"));
                }

                for (int i = 0; i < values.Count; i++)
                {
                    for (int j = 0; j < values[i].Length; j++)
                    {
                        w.Write(string.Format("{0}{1}",
                            values[i][j],
                            j == values[i].Length - 1 ? Environment.NewLine : ";"));
                    }

                }
            }

        }
    }
}
