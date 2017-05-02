# USER

##query users
API:
````
| Method  | URI                              | Desc                      |
|:------- |:---------------------------------|:--------------------------|
| POST    | /ship/user/userList              | get all users             |
````
Request data: 
````
{
    "page": number,
    "limit": number
}
````

Response data:
````
{
     "pageCount": 4,
     "curPage": 1,
     "limit": 15,
     "total": 100000,
     "data": [
        {
          "id": 1,
          "name": "admin",
          "is_admin": true
        }
     ]
}
````
##query one user
API:
````
| Method  | URI                              | Desc                      |
|:------- |:---------------------------------|:--------------------------|
| GET     | /ship/user?name={name}           | get one user              |
````
Request data: none

Response data:
```$xslt
{
    "id": int,
    "name": string
    "is_admin": bool
}
```
##mod user password
API:
```$xslt
| Method  | URI                              | Desc                      |
|:------- |:---------------------------------|:--------------------------|
| PUT     | /ship/user                       | mod user pwd              |
```
Request data: 
```$xslt
{
    "name": string,
    "old_pwd": string //use sha256 encrypt
    "new_pwd": string //use sha256 encrypt
}
```
Response data:
```$xslt
{
    "id": int,
    "name": string
    "is_admin": bool
}
```
