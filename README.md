# Restaurant voting system

## Task:

REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

Build a voting system for deciding where to have lunch.

* 2 types of users: admin and regular users
* Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
* Menu changes each day (admins do the updates)
* Users can vote on which restaurant they want to have lunch at
* Only one vote counted per user
* If user votes again the same day:
    - If it is before 11:00 we asume that he changed his mind.
    - If it is after 11:00 then it is too late, vote can't be changed
* Each restaurant provides new menu each day.

### API Documentation:
http://localhost:8080/swagger-ui.html

### **The menu API**
* _Get all menus of all restaurants to date_
  - **GET /api/v1/menus**
<pre>
curl -s http://localhost:8080/api/v1/menus --user admin:admin
</pre>

* _Get all menus by restaurant to date_
  - **GET /api/v1/menus/restaurant/{restaurantId}**
<pre>
curl -s -X GET http://localhost:8080/api/v1/menus/100002 --user admin:admin
</pre>

* _Get menu by id and restaurant_
  - **GET /api/v1/menus/restaurant/{restaurantId}/menu/{id}**
<pre>
curl -s -X GET http://localhost:8080/api/v1/menus/restaurant/100002/menu/100004 --user admin:admin
</pre>

* _Create menu for restaurant_
  - **POST /api/v1/menus/restaurant/{restaurantId}**
<pre>
echo {"date": "2021-02-11","dishSet": [{"name": "Eggs","price": 10},{"name": "Milk","price": 11}]} | curl -s -X POST -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/menus/restaurant/100002 --user admin:admin
</pre>

* _Update menu for restaurant_
  - **PUT /api/v1/menus/restaurant/{restaurantId}/menu/{id}**
<pre>
echo {"date": "2021-02-10","dishSet": [{"name": "Eggs","price": 100},{"name": "Milk","price": 110}]} | curl -s -X PUT -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/menus/restaurant/100002/menu/100005 --user admin:admin
</pre>

* _Delete menu for restaurant_
  - **DELETE /api/v1/menus/restaurant/{restaurantId}/menu{id}**
<pre>
curl -X DELETE http://localhost:8080/api/menus/restaurant/100002/menu/100005 --user admin:admin
</pre>

### **The restaurant API**
* _Get all restaurants_
  - **GET /api/v1/restaurants**
<pre>
curl -s http://localhost:8080/api/v1/restaurants --user admin:admin
</pre>

* _Get restaurant by id_
  - **GET /api/v1/restaurants/{id}**
<pre>
curl -s http://localhost:8080/api/v1/restaurants/100002 --user admin:admin
</pre>

* _Create restaurant_
  - **POST /api/v1/restaurants**
<pre>
echo {"name": "Praga"} | curl -s -X POST -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/restaurants --user admin:admin
</pre>

* _Update restaurant_
  - **PUT /api/v1/restaurants/{id}**
<pre>
echo {"name": "PragaNew", "new": "false"} | curl -s -X PUT -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/restaurants/100002 --user admin:admin
</pre>

* _Delete restaurant by id_
  - **DELETE /api/v1/restaurants/{id}**
<pre>
curl -s -X DELETE http://localhost:8080/api/v1/restaurants/100002 --user admin:admin
</pre>

### **The user API**
* _Get all users_
  - **GET /api/v1/users**
<pre>
curl -s http://localhost:8080/api/v1/users --user admin:admin
</pre>

* _Get user by id_
  - **GET /api/v1/users/{id}**
<pre>
curl -s http://localhost:8080/api/v1/users/100004 --user admin:admin
</pre>

* _Create user_
  - **POST /api/v1/users**
<pre>
echo {"name":"userOne", "password":"securePass", "roleSet":["ROLE_USER"]} | curl -s -X POST -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/users --user admin:admin
</pre>

* _Update user_
  - **PUT /api/v1/users/{id}**
<pre>
echo {"name":"userOne", "password":"notSecurePass", "roleSet":["ROLE_USER"]} | curl -s -X PUT -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/users/100004 --user admin:admin
</pre>

* _Delete user_
  - **DELETE /api/v1/users/{id}**
<pre>
curl -s -X DELETE http://localhost:8080/api/v1/users/100004 --user admin:admin
</pre>

* _Get authorized user_
  - **GET /api/v1/authorized**
<pre>
curl -s -X GET http://localhost:8080/api/v1/authorized --user admin:admin
</pre>

* _Update authorized user_
  - **PUT /api/v1/authorized**
<pre>
echo {"name":"admin", "password":"admins", "roleSet":["ROLE_USER", "ROLE_ADMIN"]} | curl -s -X PUT -d @- -H "Content-Type: application/json" http://localhost:8080/api/v1/authorized --user admin:admin
</pre>

* _Delete authorized user_
  - **DELETE /api/v1/authorized**
<pre>
curl -s -X DELETE http://localhost:8080/api/v1/authorized --user admin:admin
</pre>

### **The vote API**
* _Get all votes by date_
  - **GET /api/v1/votes**
<pre>
curl -s -X GET http://localhost:8080/api/v1/votes?date=2021-03-12
</pre>

* _Create vote for restaurant_
  - **POST /api/v1/votes/restaurant/{restaurantId}**
<pre>
curl -s -X POST http://localhost:8080/api/v1/votes/restaurant/100002 --user admin:admin
</pre>

