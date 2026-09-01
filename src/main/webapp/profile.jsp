<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.tap.model.User"%>
<%@ page import="com.tap.DAOImpl.UserDAOImpl"%>

<%
User sessionUser = (User) session.getAttribute("loggedInUser");

if(sessionUser == null){
    response.sendRedirect("login.html");
    return;
}

UserDAOImpl userDao = new UserDAOImpl();
User user = userDao.getUser(sessionUser.getUserId());

if(user == null){
    response.sendRedirect("login.html");
    return;
}

session.setAttribute("loggedInUser", user);

String profileLetter = "";
if(user.getUserName() != null && !user.getUserName().trim().isEmpty()){
    profileLetter = String.valueOf(Character.toUpperCase(user.getUserName().trim().charAt(0)));
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Profile</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

<style>

*{
    margin:0;
    padding:0;
    box-sizing:border-box;
    font-family:'Poppins',sans-serif;
}

body{

    background:#111119;
    color:white;
}

.container{

    width:80%;
    margin:40px auto;
}

.profile-card{

    background:#1b1b2b;
    border-radius:20px;
    padding:40px;
    box-shadow:0 10px 30px rgba(0,0,0,.4);
}

.header{

    display:flex;
    align-items:center;
    gap:30px;
    margin-bottom:40px;
}

.avatar{

    width:120px;
    height:120px;
    border-radius:50%;
    background:#ff4d6d;
    display:flex;
    justify-content:center;
    align-items:center;
    font-size:42px;
    font-weight:bold;
    color:white;
}

.user-info h1{

    font-size:34px;
}

.user-info p{

    color:#bbb;
    margin-top:8px;
}

.profile-form{

    display:grid;
    grid-template-columns:1fr 1fr;
    gap:25px;
}

.input-group{

    display:flex;
    flex-direction:column;
}

.input-group label{

    margin-bottom:8px;
    font-weight:600;
}

.input-group input{

    padding:14px;
    border:none;
    border-radius:10px;
    background:#2b2b3f;
    color:white;
    font-size:15px;
}

.input-group input:focus{

    outline:none;
    border:2px solid #ff4d6d;
}

.full{

    grid-column:1/3;
}

button{

    margin-top:30px;
    background:#ff4d6d;
    color:white;
    border:none;
    padding:14px 35px;
    border-radius:10px;
    font-size:16px;
    cursor:pointer;
    transition:.3s;
}

button:hover{

    background:#ff6f87;
}

.back{

    display:inline-block;
    margin-bottom:25px;
    color:white;
    text-decoration:none;
    background:#2b2b3f;
    padding:10px 20px;
    border-radius:10px;
}

.back:hover{

    background:#ff4d6d;
}

.section-title{

    margin:35px 0 20px;
    color:#ff4d6d;
    font-size:24px;
}

.info-card{

    margin-top:35px;
    background:#2b2b3f;
    border-radius:15px;
    padding:25px;
}

.info-card h3{

    margin-bottom:18px;
}

.info-card p{

    margin:12px 0;
    color:#ddd;
}

</style>

</head>

<body>

<div class="container">

<a href="home.jsp" class="back">⬅ Back to Home</a>

<div class="profile-card">

<div class="header">

<div class="avatar">
<%= profileLetter %>
</div>

<div class="user-info">

<h1><%= user.getUserName() %></h1>

<p>
<%= user.getEmail() %>
</p>

</div>

</div>


<form action="profile" method="post">

    <div class="section-title">
        Edit Profile
    </div>

    <div class="profile-form">

        <div class="input-group">
            <label>👤 Username</label>
            <input
                type="text"
                name="username"
                value="<%= user.getUserName() %>"
                required>
        </div>

        <div class="input-group">
            <label>📧 Email</label>
            <input
                type="email"
                name="email"
                value="<%= user.getEmail() %>"
                required>
        </div>

        <div class="input-group full">
            <label>🏠 Address</label>
            <input
                type="text"
                name="address"
                value="<%= user.getAddress() %>"
                required>
        </div>

        <div class="input-group">
            <label>💼 Role</label>
            <input
                type="text"
                name="role"
                value="<%= user.getRole() %>"
                readonly>
        </div>

    </div>

    <button type="submit">
        💾 Save Changes
    </button>

</form>

<form action="changePassword" method="post" style="margin-top:35px;">

    <div class="section-title">
        Change Password
    </div>

    <div class="profile-form">

        <div class="input-group">
            <label>🔒 Current Password</label>
            <input type="password" name="oldPassword" required>
        </div>

        <div class="input-group">
            <label>🔑 New Password</label>
            <input type="password" name="newPassword" required>
        </div>

        <div class="input-group full">
            <label>🔑 Confirm New Password</label>
            <input type="password" name="confirmPassword" required>
        </div>

    </div>

    <button type="submit">
        Update Password
    </button>

</form>

<%
String pwdError = request.getParameter("pwdError");
if("old".equals(pwdError)){
%>
<p style="color:#ff6b6b;margin-top:15px;">Current password is incorrect.</p>
<%
} else if("mismatch".equals(pwdError)){
%>
<p style="color:#ff6b6b;margin-top:15px;">New passwords do not match.</p>
<%
} else if("missing".equals(pwdError)){
%>
<p style="color:#ff6b6b;margin-top:15px;">Please fill in all password fields.</p>
<%
} else if("1".equals(request.getParameter("pwdUpdated"))){
%>
<p style="color:#2ecc71;margin-top:15px;">Password updated successfully.</p>
<%
} else if("1".equals(request.getParameter("updated"))){
%>
<p style="color:#2ecc71;margin-top:15px;">Profile updated successfully.</p>
<%
}
%>

<div style="margin-top:25px;text-align:center;">

    <a href="myorders"
       style="
       display:inline-block;
       padding:12px 25px;
       background:#ff4d73;
       color:white;
       text-decoration:none;
       border-radius:8px;
       font-weight:bold;">

        📦 My Orders

    </a>


</div>


<div class="info-card">

    <h3>⚙️ Account Information</h3>

    <p>
        <strong>User ID :</strong>
        <%= user.getUserId() %>
    </p>

    <p>
        <strong>Created On :</strong>
        <%= user.getCreatedDate() %>
    </p>

    <p>
        <strong>Last Login :</strong>
        <%= user.getLastLoginDate() %>
    </p>

</div>

</div>

</div>

</body>
</html>