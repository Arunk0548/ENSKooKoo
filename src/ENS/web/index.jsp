<%-- 
    Document   : index
    Created on : Dec 26, 2015, 12:28:42 AM
    Author     : Arun Kumar
--%>
<jsp:useBean id="cust" scope="session" class="ens.ivr.datastore.Customer"/>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta content="text/html; charset=utf-8" http-equiv="Content-Type"/>
        <title>Emergency Notification System</title>    
    </head>
    <body>
        
        <%if(null == session.getAttribute("twitter")){
            response.sendRedirect(request.getContextPath()+ "/");
        }%>
        
        <h1>Welcome ${twitter.screenName} (${twitter.id})</h1> <a href="./logout">logout</a>

        <br>
        <h5>Update your emergency details</h5>
        <form name="form1" action="./update" method="post" >
            <table>
                <tr>
                    <td>Reg Mobile No:</td>
                    <td><input type="text" pattern="^91[789]\d{9}$" maxlength="12" min="12" name="mobile" value="${cust.mobileNo}" required="" placeholder="add 91 like 918904676767">*</input><br/>     </td>
                </tr>
                <tr>
                    <td>Emergency No 1:</td>
                    <td><input type="text" pattern="^0[789]\d{9}$" maxlength="11" min="11" name="eno1" value="${cust.emergencyNo1}" required="" placeholder="add 0 like 08904676767">*</input><br/> </td>
                </tr>
                <tr>
                    <td>Emergency No 2:</td>
                    <td><input type="text" pattern="^0[789]\d{9}$" maxlength="11" min="11" name="eno2" value="${cust.emergencyNo2}" required="" placeholder="add 0 like 08904676767">*</input><br/>  </td>
                </tr>
                <tr>
                    <td>Emergency No 3:</td>
                    <td><input type="text" pattern="^0[789]\d{9}$" maxlength="11" min="11"  name="eno3" value="${cust.emergencyNo3}" required="" placeholder="add 0 like 08904676767">* </input><br/>  </td>
                </tr>
                <tr>
                    <td colspan="2">
                         <input type="submit" value="Update"/>
                    </td>
                </tr>
                <tr></tr>
                <tr></tr>
                <tr></tr>
                <tr><td colspan="20">
                    To use this emergency notification system,
                    
                    Whenever you need help, dial to "08025149732" from your registered number and IVR will receive the call<br>
                    Follow the instruction and record your message, Notification system will dial to all your given<br>
                    emergency number and will play your recorded voice and will instruct them to take necessary action to help you.<br>
                    It will also send SMS to your all emergency number.
                    </td></tr>
            </table>      
        </form>
        
</body>
</html>
