<%@ page language="java"
contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires", 0);
%>

<%@ page import="com.tap.model.Cart"%>
<%@ page import="com.tap.model.CartItem"%>
<%@ page import="java.util.*"%>

<%
Cart cart = (Cart)session.getAttribute("cart");

double grandTotal = 0;
%>

<!DOCTYPE html>
<html>
<head>

<meta charset="UTF-8">

<title>My Cart</title>

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
margin-bottom:30px;
color:#ff4d6d;
}

table{

    width:100%;

    background:white;

    border-radius:15px;

    overflow:hidden;

    box-shadow:0 8px 20px rgba(0,0,0,.12);

}

th{
background:#ff4d6d;
color:white;
padding:15px;
}

td{
padding:15px;
text-align:center;
border-bottom:1px solid #ddd;
}

.total{
margin-top:20px;
font-size:24px;
font-weight:bold;
text-align:right;
}

.checkout{

display:inline-block;

margin-top:20px;

padding:12px 25px;

background:#ff4d6d;

color:white;

text-decoration:none;

border-radius:8px;

float:right;

}

.checkout:hover{

background:#ff6785;

}

.empty{

text-align:center;

font-size:25px;

margin-top:80px;

}

.empty-actions{

text-align:center;
margin-top:25px;

}

.empty-actions a{

display:inline-block;
padding:12px 25px;
background:#ff4d6d;
color:white;
text-decoration:none;
border-radius:8px;
font-size:18px;

}

.cart-item-image{

width:56px;
height:56px;
object-fit:cover;
border-radius:8px;
vertical-align:middle;
}

.cart-item-image-placeholder{

display:inline-flex;
width:56px;
height:56px;
border-radius:8px;
background:#eee;
align-items:center;
justify-content:center;
font-size:22px;
vertical-align:middle;
}

button{

padding:8px 14px;

border:none;

border-radius:6px;

cursor:pointer;

background:#ff4d6d;

color:white;

}

button:hover{

background:#ff6785;

}
.bill-summary{

    width:100%;
    margin:0;
    background:#1d1d2e;
    color:white;
    padding:30px;
    border-radius:15px;
}

.bill-summary h2{

    text-align:center;
    color:#ff4d6d;
    margin-bottom:25px;
}

.bill-summary .row{

    display:flex;
    justify-content:space-between;
    margin:15px 0;
    font-size:18px;
}

.bill-summary hr{

    margin:20px 0;
}

.bill-summary .grand{

    font-size:28px;
    font-weight:bold;
    color:#ff4d6d;
}


.add-more{

    display:block;
    width:100%;
    margin-top:20px;
    text-align:center;

    background:#28a745;
    color:white;

    padding:14px;
    border-radius:10px;

    text-decoration:none;
    font-size:18px;
    font-weight:bold;
}

.add-more:hover{

    background:#218838;
}


.cart-layout{

    display:flex;
    gap:35px;
    align-items:flex-start;
    margin-top:30px;

}

.cart-left{

    flex:2.2;

}

.cart-right{

    flex:1;

    position:sticky;
    top:30px;

}


.checkout-btn{

    width:100%;
    margin-top:20px;
    padding:15px;

    border:none;

    border-radius:10px;

    background:#ff4d6d;

    color:white;

    font-size:18px;

    cursor:pointer;

    font-weight:bold;

}

.checkout-btn:hover{

    background:#ff6785;

}
</style>

</head>

<body>

<div class="container">

<h1>🛒 My Cart</h1>

<%

if(cart==null || cart.getItems().isEmpty()){

%>

<div class="empty">

Your Cart is Empty

</div>

<div class="empty-actions">
    <a href="home.jsp">Add Items</a>
</div>

<%

}
else{
	
	int restaurantId = cart.getItems().values().iterator().next().getRestaurantId();


%>

<div class="cart-layout">

    <!-- Left Column -->
    <div class="cart-left">
<table>

<tr>

<th>Item</th>
<th>Price</th>
<th>Quantity</th>
<th>Total</th>
<th>Action</th>

</tr>

<%
for(CartItem item : cart.getItems().values()){

double total=item.getPrice()*item.getQuantity();

%>

<tr>

<td style="text-align:left;">

<%
if(item.hasImage()){
%>
<img class="cart-item-image" src="<%= item.getImagePath() %>" alt="<%= item.getName() %>">
<%
} else {
%>
<span class="cart-item-image-placeholder" aria-hidden="true">🍽</span>
<%
}
%>

<span style="margin-left:10px;vertical-align:middle;"><%= item.getName() %></span>

</td>

<td>

₹ <%= item.getPrice() %>

</td>

<td>

<div style="display:flex;justify-content:center;align-items:center;gap:10px;">

<form action="cart" method="post">

<input type="hidden" name="action" value="update">

<input type="hidden" name="menuId"
value="<%= item.getMenuId()%>">

<input type="hidden" name="restaurantId"
value="<%= item.getRestaurantId()%>">

<input type="hidden" name="quantity"
value="-1">

<button>-</button>

</form>

<b>

<%= item.getQuantity() %>

</b>

<form action="cart" method="post">

<input type="hidden" name="action" value="update">

<input type="hidden" name="menuId"
value="<%= item.getMenuId()%>">

<input type="hidden" name="restaurantId"
value="<%= item.getRestaurantId()%>">

<input type="hidden" name="quantity"
value="1">

<button>+</button>

</form>

</div>

</td>

<td>

₹ <%= total %>

</td>

<td>

<form action="cart" method="post">

<input type="hidden" name="action"
value="delete">

<input type="hidden" name="menuId"
value="<%= item.getMenuId()%>">

<input type="hidden" name="restaurantId"
value="<%= item.getRestaurantId()%>">

<button
style="background:red;border:none;color:white;padding:8px 15px;border-radius:8px;">

🗑 Remove

</button>

</form>

</td>

</tr>

<%
}
%>

</table>

</div>

<!-- Right Column -->

<div class="cart-right">

<%

double subtotal=cart.getTotalPrice();

double delivery=40;

float gst=(float)(subtotal*0.05);

int grand=(int)Math.round(subtotal+delivery+gst);

%>

<div class="bill-summary">

    <h2>Bill Summary</h2>

    <div class="row">
        <span>Subtotal</span>
        <span>₹ <%= subtotal %></span>
    </div>

    <div class="row">
        <span>Delivery Charge</span>
        <span>₹ <%= delivery %></span>
    </div>

    <div class="row">
        <span>GST (5%)</span>
        <span>₹ <%= gst %></span>
    </div>

    <hr>

    <div class="row grand">
        <span>Grand Total</span>
        <span>₹ <%= grand %></span>
    </div>

</div>

<a href="menu.jsp?restaurantId=<%= restaurantId %>"
   class="add-more">
    🍽 Add More Items
</a>

<form action="checkout" method="post">

<button class="checkout-btn">

Proceed To Checkout

</button>

</form>
</div>

</div>
<%

}

%>

</div>

</body>

</html>