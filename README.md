This package contains VorItem class and VorItemFactory + VorItemFactoryBuilder

## What is the problem it is trying to solve?


VorItem is a wrapper around Gson implementation that allows 
simple navigation and modification of its data hierarchy.

Using Gson library is sometimes very tedious - e.g. 
given very simple structure: 
```
{
   "data":{
        "item":"text"
   }
}
```

`Json` code to set is would be:
```
        JsonObject root = new JsonObject();
        JsonObject data = new JsonObject();
        root.add("data", data);
        data.addProperty("item", "text");
``` 
With `VorItem` you can replace it with:
```
VorIonItem root = VorIonFactory.empty();
root.set("data/item", "text");
```

Read operations are even more complex. In order to read this data using `Gson` you need to create function like:
```
    private Optional<String> readOptional(JsonElement root) {
        if (root != null && root.isJsonObject()) {
            JsonObject rootObject = root.getAsJsonObject();
            JsonElement dataElement = rootObject.get("data");
            if (dataElement != null && dataElement.isJsonObject()) {
                JsonObject dataObject = dataElement.getAsJsonObject();
                if (dataObject.has("item")) {
                    JsonElement item = dataObject.get("item");
                    if (item.isJsonPrimitive()) {
                        return Optional.of(item.getAsString());
                    }
                }
            }
        }
        return Optional.empty();
    }

```
Whereas with `VorItem` you can replace it with:
```
VorItem rootItem = VorItemFactory.from(root);
return rootItem.get("data/item").optionalString();
```
 
## VorItem 

#### Tenets:
* VorItem class is a **single type** that supports all Json data types (LIST, STRUCT, STRING, ...)
* data access functions **always return non-null values** even, if underlying data doesn't exist (*)   
* data navigation can use chained sequence or when always accessing same set it can be further simplified to path:
  * root.get("data").get("list").get(2).get("list_element").get("value").getS()
  * root.get("data/list:2/list_element/value").getS()
  * (alternative path notation) root.get("data.list[2].list_element.value").getS()
* data navigation methods **never change** underlying Json data.
* minimize performance penalty by dynamically creating Objects for accessed elements only - if Json structure contains 
hundreds of nodes, and we access only few of them only few VorItem instances will be 
created - all data is stored in Json structure.


(*) System leniency can be set by lenientStructure flag in VorItemFactoryBuilder
 * lenientStructure == true => allow any data reads/writes  
 * lenientStructure == false => enforce data type validation - you cannot change type of data or access **existing** data as other type (e.g. treat list as struct).
 
 ##  VorItemFactory 
 
VorItemFactory is the only mechanism to create an instance of VorIonItem.

VorItemFactory itself must be created using VorItemFactoryBuilder that
allows setting path parser format ("a/b/c:n" vs "a.b.c[n]"), IonSystem to be used,
and VorItem behaviour (leniency flags)

## VorItemFactoryBuilder

VorItemFactoryBuilder allows you to build VorItemFactory.

## Examples

### Read data from Json

Given sample data :
```
{
   "users": [
    {
        "id":1,
        "name": "John Smith",
        "personal_information": {
            "age": 45
        }
    },
    {
        "id":2,
        "name": "Fred M",
        "personal_information": {
            "age": 66
        }
   },
    {
        "id":3,
        "name": "Jane X"
   }
}
```

Task:

Get list of users that are than 50 years old. Skip users with no age information. 

```java
...
    List<String> readUserName(JsonElement usersJson) {
        // single factory can be shared and re-used  
        VorItemFactory factory = VorItemFactoryBuilder.standard().build();

        VorItem users = factory.from(usersJson);

        return users.get("users").list().stream()
                .filter(user -> user.get("personal_information/age").exists() 
                             && user.get("personal_information/age").getI() > 50)
                .map(user -> user.get("name").getS())
                .collect(Collectors.toList());
...
```
 
### Write data to Json

given some data structure - e.g.
```java
class User {
    int Id;
    String name;
    Optional<Integer> age;
}
```

we need to save Json as above.

```
...
    JsonElement addUsers(List<User> list) {
        VorItemFactory factory = VorItemFactoryBuilder.standard().build();
    
        VorItem users = factory.empty();
        list.forEach(user -> {
            VorItem next = factory.empty(); 
            next.set("id", user.id);             
            next.set("name", user.name);             
            user.age.ifPresent(age -> next.set("personal_information/age", age));
            users.get("users").add(next);             
        }
        return users.asJsonElement();
    }
...

```

### Transform Json for Gson

given some data structure - e.g.
```java
class User {
    int Id;
    String name;
    String role;
    Optional<Integer> age;
}
```
and external source in a different format e.g. 
```json
{
  "user_info": {
    "first_name" : "John",
    "last_name" : "Smith",
    "user_id": 1,
    "employment_records":[
      {
        "position": "developer",
        "started": "2018/03/21"
      }
    ],
    "age": 23    
  }
}
```
We want to convert json to User class using Gson object mapper.

```java
...
    VorItemFactory factory = VorItemFactoryBuilder.standard().build();

    VorItem user = factory.from(userJson);
    VorItem transformed = factory.empty();

    // set fields directly using alias to rename it.
    user.select(
        "age", 
        "user_id=id"
        "employment_records:0/position=role"
        ).stream().forEach(transformed::set);

    // combine name
    String name = user.get("first_name") + " " + user.get("last_name");
    transformed.set("name", name);

    // convert with Gson
    User user = gson.fromJson(transformed.asHsonElement(), User.class);
...
``` 


