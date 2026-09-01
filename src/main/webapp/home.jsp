<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.tap.model.User" %>
<%
response.setHeader("Cache-Control","no-cache, no-store, must-revalidate");
response.setHeader("Pragma","no-cache");
response.setDateHeader("Expires", 0);

User loggedInUser = (User) session.getAttribute("loggedInUser");
boolean isLoggedIn = loggedInUser != null;
String profileLetter = "";
if (isLoggedIn && loggedInUser.getUserName() != null && !loggedInUser.getUserName().trim().isEmpty()) {
    profileLetter = String.valueOf(Character.toUpperCase(loggedInUser.getUserName().trim().charAt(0)));
}
%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>FoodHub</title>

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

a{
    text-decoration:none;
}

.container{
    width:90%;
    margin:auto;
}

/* ================= NAVBAR ================= */

header{
    width:100%;
    padding:18px 0;
}

nav{
    display:flex;
    justify-content:space-between;
    align-items:center;
}

.logo{
    font-size:30px;
    font-weight:700;
    color:#ff4d6d;
}

.logo span{
    color:white;
}

.nav-links{
    display:flex;
    gap:28px;
    align-items:center;
}

.nav-links a{
    color:white;
    transition:.3s;
}

.nav-links a:hover{
    color:#ff4d6d;
}

.login{
      background:#ff4d6d;
    padding:10px 22px;
    border-radius:30px;
    color:white;
    transition:.3s;
}

.signup{

    background:#ff4d6d;
    padding:10px 22px;
    border-radius:30px;
    color:white;
    transition:.3s;
}

.signup:hover{
    background:#ff6f87;
}

.logout{
   
    color:white !important;
    padding:10px 20px;
    border-radius:30px;
    font-weight:600;
    transition:.3s;
}

.logout:hover{
   background:#ff6f87;
    transform:translateY(-2px);
}

.profile{

    display:flex;
    align-items:center;
    gap:8px;
}

.profile img{

    width:38px;
    height:38px;
    border-radius:50%;
}

.profile-initial{

    width:38px;
    height:38px;
    border-radius:50%;
    background:#ff4d6d;
    color:white;
    display:flex;
    align-items:center;
    justify-content:center;
    font-weight:700;
    font-size:16px;
}

/* ================= HERO ================= */

.hero{

    display:flex;
    justify-content:space-between;
    align-items:center;
    margin-top:70px;
}

.hero-left{

    width:58%;
}

.hero-left h1{

    font-size:58px;
    line-height:75px;
    margin-bottom:20px;
}

.hero-left h1 span{

    color:#ff4d6d;
}

.hero-left p{

    color:#bdbdbd;
    width:80%;
    line-height:30px;
    margin-bottom:35px;
}

.search-box{

    width:500px;
    background:#1b1b2b;
    border-radius:40px;
    display:flex;
    overflow:hidden;
}

.search-box input{

    flex:1;
    border:none;
    outline:none;
    background:transparent;
    color:white;
    padding:18px;
    font-size:15px;
}

.search-box button{

    width:130px;
    border:none;
    background:#ff4d6d;
    color:white;
    cursor:pointer;
    font-size:15px;
    transition:.3s;
}

.search-box button:hover{

    background:#ff6785;
}

/* ================= OFFER CARD ================= */

.hero-right{

    width:330px;
}

.offer-card{

    background:#1a1b2e;
    border-radius:22px;
    overflow:hidden;
    box-shadow:0 0 20px rgba(0,0,0,.3);
}

.offer-card img{

    width:100%;
    height:220px;
    object-fit:cover;
}

.offer-content{

    padding:20px;
}

.offer-content h2{

    margin-bottom:10px;
}

.offer-content p{

    color:#bdbdbd;
}

/* ================= SECTION ================= */

.section-title{

    margin-top:90px;
    margin-bottom:40px;
}

.section-title h2{

    font-size:36px;
}

.section-title p{

    color:#bdbdbd;
    margin-top:10px;
}

/* ================= RESTAURANTS ================= */

.restaurant-container{

    display:grid;
    grid-template-columns:repeat(auto-fit,minmax(250px,1fr));
    gap:30px;
}

.restaurant-card{

    background:#1a1b2e;
    border-radius:20px;
    overflow:hidden;
    transition:.35s;
    cursor:pointer;
}

.restaurant-card:hover{

    transform:translateY(-12px);
    box-shadow:0 20px 40px rgba(0,0,0,.4);
}

.restaurant-card img{

    width:100%;
    height:180px;
    object-fit:cover;
}

.restaurant-content{

    padding:18px;
}

.restaurant-content h3{

    margin-bottom:10px;
}

.restaurant-content p{

    color:#c6c6c6;
    font-size:14px;
}

.details{

    display:flex;
    justify-content:space-between;
    margin-top:18px;
    color:#ffb703;
}

.rating{

    background:#2ecc71;
    padding:4px 10px;
    border-radius:20px;
    color:white;
    font-size:13px;
}

.menu-btn{
    display:block;
    width:100%;
    margin-top:18px;
    padding:10px;
    background:#ff4d6d;
    color:white;
    text-align:center;
    border-radius:8px;
    font-weight:600;
    transition:.3s;
}

.menu-btn:hover{
    background:#ff6f87;
    transform:scale(1.02);
}



/* ================= RESPONSIVE ================= */

@media(max-width:900px){

.hero{

flex-direction:column;
gap:50px;
}

.hero-left{

width:100%;
}

.hero-right{

width:100%;
}

.search-box{

width:100%;
}

.hero-left h1{

font-size:42px;
line-height:60px;
}

.nav-links{

display:none;
}

}



</style>

</head>

<body>

<div class="container">

<header>

<nav>

<div class="logo">
Food<span>Hub</span>
</div>

<div class="nav-links">

<a href="home.jsp">Home</a>
<a href="cart.jsp">Cart</a>

<%
if (isLoggedIn) {
%>

<a href="profile.jsp" class="profile" style="text-decoration:none;color:white;">

<span class="profile-initial"><%= profileLetter %></span>
<span>Profile</span>

</a>

<a href="logout" class="logout">↩ Logout</a>

<%
} else {
%>

<a href="login.html" class="login">Sign In</a>
<a href="register.html" class="signup">Sign Up</a>

<%
}
%>

</div>

</nav>

</header>

<!-- HERO -->

<section class="hero">

<div class="hero-left">

<h1>

Discover the Best
<br>
<span>Restaurants</span> Near You

</h1>

<p>

Order your favorite food from top-rated restaurants with fast delivery,
delicious taste, and a smooth online food ordering experience.

</p>

<div class="search-box">

<form action="home.jsp" method="get" class="search-box">

    <input
        id="search"
        type="text"
        name="search"
        autocomplete="off"
        placeholder="Search Restaurant..."
        value="<%= request.getParameter("search")==null ? "" : request.getParameter("search") %>">

    <button type="submit">
        Search
    </button>

</form>



</div>

</div>

<div class="hero-right">

<div class="offer-card">

<img src="images/pexels-saba-foods-2148476407-30119016.jpg" alt="">

<div class="offer-content">

<h2>🔥 Hot Deals Today</h2>

<p>

Get up to <b>50% OFF</b> on selected restaurants.

</p>

</div>

</div>

</div>

</section>

<!-- POPULAR RESTAURANTS -->


<%@ page import="java.util.*,com.tap.DAOImpl.RestaurantDAOImpl,com.tap.model.Restaurant" %>

<%
RestaurantDAOImpl restaurantDAO = new RestaurantDAOImpl();

String search = request.getParameter("search");

List<Restaurant> restaurants;

if(search != null && !search.trim().isEmpty()){
    restaurants = restaurantDAO.searchRestaurant(search);
}else{
    restaurants = restaurantDAO.getAllRestaurant();
}
%>


<div class="section-title">

<h2>Popular Restaurants</h2>

<p>
Choose from the best restaurants around your location.
</p>

</div>
<div class="restaurant-container">

<%
if(restaurants != null){

    for(Restaurant restaurant : restaurants){
%>


<div class="restaurant-card"
     data-name="<%= restaurant.getName().toLowerCase() %>"
     data-cuisine="<%= restaurant.getCuisineType().toLowerCase() %>">

    <img src="<%= restaurant.getImagePath() %>"
         alt="<%= restaurant.getName() %>">

    <div class="restaurant-content">

        <h3><%= restaurant.getName() %></h3>

        <p><%= restaurant.getCuisineType() %></p>

        <div class="details">

            <span>🚚 <%= restaurant.getDeliveryTime() %> mins</span>

            <span class="rating">
                ⭐ <%= restaurant.getRating() %>
            </span>

        </div>
        
        <a class="menu-btn"
           href="menu.jsp?restaurantId=<%= restaurant.getRestaurantId() %>">
            View Menu →
        </a>

    </div>

</div>



<%
    }
}
else{
%>

<h2>No Restaurants Available</h2>

<%
}
%>

</div>

   
</div>

<!-- Footer -->

<footer style="margin-top:80px;padding:35px 0;text-align:center;color:#bdbdbd;border-top:1px solid #2b2b38;">

    <h2 style="color:#ff4d6d;margin-bottom:10px;">
        Food<span style="color:white;">Hub</span>
    </h2>

    <p>
        Delicious food delivered to your doorstep.
    </p>

    <br>

    <p style="font-size:14px;">
        © 2026 FoodHub. All Rights Reserved.
    </p>

</footer>

</div>


<script>

const search = document.getElementById("search");

search.addEventListener("keyup", function () {

    let keyword = this.value.toLowerCase();

    let cards = document.querySelectorAll(".restaurant-card");

    cards.forEach(function(card){

        let name = card.getAttribute("data-name");
        let cuisine = card.getAttribute("data-cuisine");

        if(name.includes(keyword) || cuisine.includes(keyword)){
            card.style.display = "block";
        }
        else{
            card.style.display = "none";
        }

    });

});

</script>

</body>
</html>