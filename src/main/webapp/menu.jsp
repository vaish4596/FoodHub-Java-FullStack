<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires", 0);
%>
  <%@ page import="java.util.*,com.tap.DAOImpl.MenuDAOImpl,com.tap.model.Menu,com.tap.model.Cart,com.tap.model.CartItem" %>

<%
int restaurantId = Integer.parseInt(request.getParameter("restaurantId"));

MenuDAOImpl menuDAO = new MenuDAOImpl();

List<Menu> menuList = menuDAO.getMenuByRestaurantId(restaurantId);
Cart cart = (Cart) session.getAttribute("cart");
int cartCount = (cart != null) ? cart.getTotalItemCount() : 0;

boolean showCartConflict = "1".equals(request.getParameter("cartConflict"))
        && session.getAttribute("cartRestaurantConflict") != null;

Integer pendingMenuId = (Integer) session.getAttribute("pendingMenuId");
Integer pendingRestaurantId = (Integer) session.getAttribute("pendingRestaurantId");

if (!showCartConflict) {
    session.removeAttribute("cartRestaurantConflict");
    session.removeAttribute("pendingMenuId");
    session.removeAttribute("pendingRestaurantId");
}
%>
<!DOCTYPE html>
<html>

<head>

<meta charset="UTF-8">

<title>Restaurant Menu</title>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@300;400;500;600;700&display=swap" rel="stylesheet">

<style>



*{
margin:0;
padding:0;
box-sizing:border-box;
font-family:Poppins,sans-serif;
}

body{

background:#111119;
color:white;
}

.container{

width:90%;
margin:auto;
padding:40px 0;
}

h1{

text-align:center;
margin-bottom:40px;
color:#ff4d6d;
}

.menu-container{

display:grid;
grid-template-columns:repeat(auto-fit,minmax(270px,1fr));
gap:30px;
}

.menu-card{

background:#1b1b2b;
border-radius:18px;
overflow:hidden;
transition:.3s;
}

.menu-card:hover{

transform:translateY(-10px);
box-shadow:0 10px 30px rgba(0,0,0,.5);
}

.menu-card img{

width:100%;
height:200px;
object-fit:cover;
}

.content{

padding:18px;
}

.content h3{

margin-bottom:10px;
}

.content p{

color:#cfcfcf;
font-size:14px;
margin-bottom:12px;
}

.price{

font-size:22px;
font-weight:bold;
color:#ffb703;
}

.available{

margin-top:10px;
color:#2ecc71;
font-weight:bold;
}

.notavailable{

margin-top:10px;
color:red;
font-weight:bold;
}

button{

width:100%;
padding:12px;
margin-top:15px;
border:none;
border-radius:30px;
background:#ff4d6d;
color:white;
font-size:15px;
cursor:pointer;
transition:.3s;
}

button:hover{

background:#ff6b87;
}

</style>

</head>

<body>

<div class="container">

<a href="home.jsp"
   style="display:inline-block;margin-bottom:20px;color:white;text-decoration:none;background:#2b2b3f;padding:10px 20px;border-radius:10px;">
    ⬅ Back to Restaurants
</a>

<div style="text-align:right;margin-bottom:20px;">
    <a href="cart.jsp"
       style="background:#ff4d6d;
              color:white;
              padding:12px 20px;
              border-radius:8px;
              text-decoration:none;
              font-weight:bold;">
        Cart 🛒 <%= cartCount %>
    </a>
</div>

<%
if (showCartConflict && pendingMenuId != null && pendingRestaurantId != null) {
%>
<div style="background:#2b2b3f;border:1px solid #ff4d6d;border-radius:12px;padding:18px;margin-bottom:25px;">
    <p style="margin-bottom:12px;">
        You already have items from another restaurant. Please clear your cart before adding items from this restaurant.
    </p>
    <div style="display:flex;gap:12px;flex-wrap:wrap;">
        <a href="cart.jsp"
           style="background:#2b2b3f;color:white;padding:10px 18px;border-radius:8px;text-decoration:none;border:1px solid #666;">
            Continue with existing cart
        </a>
        <form action="cart" method="post" style="display:inline;">
            <input type="hidden" name="action" value="clearAndAdd">
            <input type="hidden" name="menuId" value="<%= pendingMenuId %>">
            <input type="hidden" name="restaurantId" value="<%= pendingRestaurantId %>">
            <input type="hidden" name="quantity" value="1">
            <input type="hidden" name="source" value="menu">
            <button type="submit"
                    style="background:#ff4d6d;color:white;padding:10px 18px;border:none;border-radius:8px;cursor:pointer;">
                Clear cart and add this item
            </button>
        </form>
    </div>
</div>
<%
    session.removeAttribute("cartRestaurantConflict");
    session.removeAttribute("pendingMenuId");
    session.removeAttribute("pendingRestaurantId");
}
%>

<h1>Restaurant Menu</h1>
<div class="menu-container">

<%
for(Menu menu : menuList){
%>

<div class="menu-card">

    <img src="<%= menu.getImagePath() %>"
         alt="<%= menu.getItemName() %>">

    <div class="content">

        <h3><%= menu.getItemName() %></h3>

        <p><%= menu.getDescription() %></p>

        <h2>₹ <%= menu.getPrice() %></h2>

        <% if(menu.isAvailable()){ %>

            <p style="color:limegreen;">Available</p>

        <% } else { %>

            <p style="color:red;">Not Available</p>

        <% } %>

      <%
CartItem cartItem = null;

if(cart != null){
    cartItem = cart.getItems().get(menu.getMenuId());
}

if(cartItem == null){
%>

<form action="cart" method="post">

    <input type="hidden" name="menuId"
           value="<%= menu.getMenuId() %>">

    <input type="hidden" name="restaurantId"
           value="<%= menu.getRestaurantId() %>">

    <input type="hidden" name="quantity"
           value="1">

    <input type="hidden" name="action"
           value="add">

    <input type="hidden" name="source" value="menu">

    <button type="submit">Add to Cart</button>

</form>

<%
}
else{
%>

<div style="display:flex;justify-content:center;align-items:center;gap:10px;margin-top:15px;">

    <form action="cart" method="post">

        <input type="hidden" name="action" value="update">

        <input type="hidden" name="source" value="menu">

        <input type="hidden" name="menuId"
               value="<%= menu.getMenuId()%>">

        <input type="hidden" name="restaurantId"
               value="<%= restaurantId%>">

        <input type="hidden" name="quantity"
               value="-1">

        <button style="width:45px;">-</button>

    </form>

    <h3><%= cartItem.getQuantity() %></h3>

    <form action="cart" method="post">

        <input type="hidden" name="action" value="update">

        <input type="hidden" name="source" value="menu">

        <input type="hidden" name="menuId"
               value="<%= menu.getMenuId()%>">

        <input type="hidden" name="restaurantId"
               value="<%= restaurantId%>">

        <input type="hidden" name="quantity"
               value="1">

        <button style="width:45px;">+</button>

    </form>

</div>

<%
}
%>
    </div>

</div>

<%
}
%>

</div>

</div>

</body>

</html>


