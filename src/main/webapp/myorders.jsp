<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>

<%@ page import="java.util.List"%>
<%@ page import="com.tap.model.OrderTable"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>My Orders</title>
<style>
body{
    background:#11111b;
    color:white;
}

.container{
    width:85%;
    margin:40px auto;
    background:#1d1d2f;
    padding:30px;
    border-radius:15px;
}
.back{
    display:inline-block;
    margin-bottom:25px;
    text-decoration:none;
    color:#ff4d73;
    font-weight:bold;
    font-size:16px;
    transition:.3s;
}

.back:hover{
    color:#ff2f61;
}

.page-title{
    text-align:center;
    color:#ff4d73;
    margin-bottom:35px;
    font-size:34px;
}

.no-orders{
    background:#1d1d2f;
    padding:35px;
    border-radius:15px;
    text-align:center;
    font-size:20px;
    color:#d1d1d1;
    box-shadow:0 5px 15px rgba(0,0,0,.25);
}

.order-card{

    background:#1d1d2f;
    border-radius:15px;
    padding:25px;
    margin-bottom:25px;
    box-shadow:0 8px 20px rgba(0,0,0,.25);
    transition:.3s;
    border-left:6px solid #ff4d73;
}

.order-card:hover{
    transform:translateY(-4px);
    box-shadow:0 12px 25px rgba(0,0,0,.35);
}

.order-header{
    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-bottom:20px;
}

.order-id{
    font-size:22px;
    font-weight:bold;
    color:#ffffff;
}

.status{

    background:green;
    color:white;
    padding:8px 18px;
    border-radius:25px;
    font-size:14px;
    font-weight:bold;
}

.order-info{
    display:grid;
    grid-template-columns:repeat(2,1fr);
    gap:18px;
}

.info-box{
    background:#2b2b42;
    padding:15px;
    border-radius:10px;
}

.label{
    color:#bdbdc7;
    font-size:14px;
    margin-bottom:5px;
}

.value{
    font-size:18px;
    font-weight:bold;
    color:#ffffff;
}

.amount{
    color:#ff4d73;
    font-size:22px;
    font-weight:bold;
}

@media(max-width:768px){

.container{
    width:95%;
}

.order-info{
    grid-template-columns:1fr;
}

.order-header{
    flex-direction:column;
    align-items:flex-start;
    gap:12px;
}

}</style>

</head>

<body>

<div class="container">
<a href="profile.jsp" class="back">
    ⬅ Back to Profile
</a>

<h1 class="page-title">📦 My Orders</h1>

<%
List<OrderTable> orders =
(List<OrderTable>)request.getAttribute("orders");

if(orders==null || orders.isEmpty()){
%>

<div class="no-orders">
    <h2>🛍️ No Orders Yet</h2>
    <p>You haven't placed any orders yet.</p>
</div>

<%
}else{

for(OrderTable order : orders){
%>

<div class="order-card">

    <div class="order-header">

        <div class="order-id">
            Order #<%= order.getOrderId() %>
        </div>

        <div class="status">
            <%= order.getStatus() %>
        </div>

    </div>

    <div class="order-info">

        <div class="info-box">
            <div class="label">📅 Order Date</div>
            <div class="value"><%= order.getOrderDate() %></div>
        </div>

        <div class="info-box">
            <div class="label">💳 Payment</div>
            <div class="value"><%= order.getPaymentMethod() %></div>
        </div>

        <div class="info-box">
            <div class="label">💰 Total Amount</div>
            <div class="amount">
                ₹ <%= String.format("%.2f", order.getTotalAmount()) %>
            </div>
        </div>

        <div class="info-box">
            <div class="label">🏪 Restaurant ID</div>
            <div class="value">
                <%= order.getRestaurantId() %>
            </div>
        </div>

    </div>

</div>

<%
}

}
%>

</div>

</body>

</html>