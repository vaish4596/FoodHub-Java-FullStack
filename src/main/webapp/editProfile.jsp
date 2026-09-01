<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.tap.model.User"%>

<%
User user = (User)session.getAttribute("loggedInUser");

if(user == null){
    response.sendRedirect("login.html");
    return;
}
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Edit Profile</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

<style>

*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:'Poppins',sans-serif;
}

body{
background:#111119;
display:flex;
justify-content:center;
align-items:center;
height:100vh;
}

.container{

width:500px;
background:#1a1b2e;
padding:35px;
border-radius:20px;
box-shadow:0 10px 25px rgba(0,0,0,.4);

}

h2{

text-align:center;
color:#ff4d6d;
margin-bottom:30px;

}

.input-group{

margin-bottom:18px;

}

label{

display:block;
color:white;
margin-bottom:8px;

}

input{

width:100%;
padding:12px;
border:none;
border-radius:8px;
font-size:15px;

}

button{

width:100%;
padding:14px;
border:none;
background:#ff4d6d;
color:white;
font-size:16px;
border-radius:8px;
cursor:pointer;
margin-top:15px;

}

button:hover{

background:#ff6f87;

}

.back{

display:block;
text-align:center;
margin-top:18px;
color:white;
text-decoration:none;

}

</style>

</head>

<body>

<div class="container">

<h2>Edit Profile</h2>

<form action="editProfile" method="post">

<input type="hidden"
name="userId"
value="<%= user.getUserId() %>">

<div class="input-group">

<label>Username</label>

<input
type="text"
name="username"
value="<%= user.getUserName() %>"
required>

</div>

<div class="input-group">

<label>Email</label>

<input
type="email"
name="email"
value="<%= user.getEmail() %>"
required>

</div>

<div class="input-group">

<label>Address</label>

<input
type="text"
name="address"
value="<%= user.getAddress() %>"
required>

</div>

<div class="input-group">

<label>Role</label>

<input
type="text"
name="role"
value="<%= user.getRole() %>"
required>

</div>

<button>

Update Profile

</button>

</form>

<a href="profile.jsp" class="back">

← Back to Profile

</a>

</div>

</body>
</html>