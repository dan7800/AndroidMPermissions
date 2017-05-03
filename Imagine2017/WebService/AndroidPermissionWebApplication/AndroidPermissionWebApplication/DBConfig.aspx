<%@ Page Language="C#" AutoEventWireup="true" CodeBehind="DBConfig.aspx.cs" Inherits="AndroidPermissionWebApplication.DBConfig" %>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<head runat="server">
    <title></title>
</head>
<body>
    <form id="form1" runat="server">
        <div>
            DB Exists:
            <asp:Label ID="lblDBExists" runat="server" Text="Label"></asp:Label>
            <br />
            <br />
            <br />
            <asp:TextBox ID="txtDBConfirm" runat="server"></asp:TextBox>
&nbsp;Enter the answer (number) for two plus two and then press the button. To prevent accidental deletion.<br />
            <asp:Button ID="btnDB" runat="server" OnClick="btnDB_Click" Text="Recreate DB" />
            <br />
            <br />
            <asp:Label ID="lblStatus" runat="server"></asp:Label>
        </div>
    </form>
</body>
</html>
