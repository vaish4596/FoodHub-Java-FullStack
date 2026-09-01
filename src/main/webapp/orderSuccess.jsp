<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>


<!DOCTYPE html>
<html>

<head>

<title>Order Success</title>


<style>

body{

font-family: Arial, sans-serif;
background:#f5f5f5;
display:flex;
justify-content:center;
align-items:center;
height:100vh;

}


.success-box{

background:white;
padding:40px;
border-radius:15px;
text-align:center;
box-shadow:0px 5px 20px rgba(0,0,0,0.2);

}


.icon{

font-size:70px;

}


h1{

color:green;

}


.order-id{

font-size:20px;
margin:20px;

}


button{

padding:12px 25px;
border:none;
border-radius:8px;
background:#ff5722;
color:white;
font-size:16px;
cursor:pointer;

}


a{

text-decoration:none;

}

</style>


</head>


<body>


<div class="success-box">


<div class="icon">

✅

</div>


<h1>
Order Placed Successfully!
</h1>


<div class="order-id">

Your Order ID :

<b>
#<%= request.getAttribute("orderId") %>
</b>

</div>



<a href="home.jsp">

<button>
Continue Shopping
</button>

</a>


</div>



</body>

</html>