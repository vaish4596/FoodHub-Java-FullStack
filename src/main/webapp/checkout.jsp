<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="com.tap.model.User"%>
<%@ page import="com.tap.model.Cart"%>
<%@ page import="com.tap.model.CartItem"%>

<%
User user = (User) request.getAttribute("user");
if (user == null) {
    user = (User) session.getAttribute("loggedInUser");
}
Cart cart = (Cart) session.getAttribute("cart");

if (user == null) {
    response.sendRedirect("login.html");
    return;
}
if (cart == null || cart.getItems().isEmpty()) {
    response.sendRedirect("cart.jsp");
    return;
}

double subtotal = (Double) request.getAttribute("subtotal");
double delivery = (Double) request.getAttribute("delivery");
double gst = (Double) request.getAttribute("gst");
double grandTotal = (Double) request.getAttribute("grandTotal");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Checkout</title>

<style>

*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:Arial,sans-serif;
}

body{
background:#f5f5f5;
}

.container{

width:90%;
margin:40px auto;

}

h1{

text-align:center;
color:#ff4d6d;
margin-bottom:35px;

}

.checkout-layout{

display:flex;
gap:30px;
align-items:flex-start;

}

.left{

flex:2;

}

.right{

flex:1;

}

.card{

background:white;
padding:25px;
border-radius:12px;
box-shadow:0 3px 10px rgba(0,0,0,.15);
margin-bottom:25px;

}

.card h2{

color:#ff4d6d;
margin-bottom:20px;

}

.info{

margin:12px 0;
font-size:17px;

}

.payment-option{

margin:15px 0;
font-size:18px;

}

.summary-row{

display:flex;
justify-content:space-between;
margin:15px 0;
font-size:17px;

}

hr{

margin:20px 0;

}

.grand{

font-size:24px;
font-weight:bold;
color:#ff4d6d;

}

.place-order{

width:100%;
padding:16px;
margin-top:20px;
background:#ff4d6d;
color:white;
border:none;
border-radius:10px;
font-size:18px;
cursor:pointer;
font-weight:bold;

}

.place-order:hover{

background:#ff6785;

}

.item{

display:flex;
justify-content:space-between;
align-items:center;
margin:12px 0;
gap:12px;

}

.checkout-item-left{

display:flex;
align-items:center;
gap:12px;
flex:1;
}

.checkout-item-image{

width:48px;
height:48px;
object-fit:cover;
border-radius:8px;
}

.checkout-item-image-placeholder{

width:48px;
height:48px;
border-radius:8px;
background:#eee;
display:flex;
align-items:center;
justify-content:center;
font-size:20px;
}

</style>

</head>

<body>

<div class="container">

<h1>Checkout</h1>

<div class="checkout-layout">

<!-- LEFT -->

<div class="left">

<div class="card">

<h2>Delivery Information</h2>

<div class="info">
<b>Name :</b> <%= user.getUserName() %>
</div>

<div class="info">
<b>Email :</b> <%= user.getEmail() %>
</div>

<div class="info">
<b>Address :</b> <%= user.getAddress() %>
</div>

</div>




</div>


<!-- RIGHT -->

<div class="right">

<div class="card">

<h2>Order Summary</h2>

<%
for(CartItem item : cart.getItems().values()){
%>

<div class="item">

<div class="checkout-item-left">

<%
if(item.hasImage()){
%>
<img class="checkout-item-image" src="<%= item.getImagePath() %>" alt="<%= item.getName() %>">
<%
} else {
%>
<span class="checkout-item-image-placeholder" aria-hidden="true">🍽</span>
<%
}
%>

<span>
<%= item.getName() %> x <%= item.getQuantity() %>
</span>

</div>

<span>
₹ <%= item.getPrice()*item.getQuantity() %>
</span>

</div>

<%
}
%>

<hr>

<div class="summary-row">

<span>Subtotal</span>

<span>₹ <%= subtotal %></span>

</div>

<div class="summary-row">

<span>Delivery</span>

<span>₹ <%= delivery %></span>

</div>

<div class="summary-row">

<span>GST (5%)</span>

<span>₹ <%= String.format("%.2f", gst) %></span>

</div>


<hr>

<div class="summary-row grand">

<span>Total</span>

<span>₹ <%= String.format("%.2f", grandTotal) %></span>

</div>

<form action="placeOrder" method="post">

    <div class="card">

        <h2>Payment Method</h2>

        <div class="payment-option">
            <input type="radio" name="paymentMethod" value="Cash On Delivery" checked>
            Cash On Delivery
        </div>

        <div class="payment-option">
            <input type="radio" name="paymentMethod" value="UPI">
            UPI
        </div>

        <div class="payment-option">
            <input type="radio" name="paymentMethod" value="Credit / Debit Card">
            Credit / Debit Card
        </div>

    </div>

    <!-- Order Summary Card -->

    <button class="place-order">
        Place Order
    </button>

</form>



</div>

</div>

</div>

</div>

</body>
</html>