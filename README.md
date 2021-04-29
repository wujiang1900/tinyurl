# tinyurl
a small program to shorten URLs for users to generate tiny url, which they can visit later to be redirected to the original url.
Your need Apache Maven to build and need java 1.8+ to run the application.

## How to build

```shell
$ mvn clean package
```

## How to test

```shell
$ mvn clean test
```

## How to run application

```shell
mvn spring-boot:run
```
You can then generate a tiny url from http://localhost:8080/.
You can use your tiny url to be redirected to your original url by visiting http://localhost:8080/{tinyUrl} 


## Design/Implementation notes:

1. An in-memory H2 DB is utilized to store the tiny urls. The application can be easily configured to work with any DB. 
	By using a DB to store data, application can be easily scaled by adding more instances, and can handle fail-over (fault tolerant). Load balancing can be easily achieved.

2. Two in-memory hashmap data structure are utilized to implement a cache service, in an effort to reduce calls to DB therefore improve performance/response.
	However, this implementation is subject to the available memory space in the system, and therefore might crash the app when the cache memory usage exceeds system availability.
	A more sophisticated implementation could be a distributed and scalable cache solution, e.g. redis cache. 
	Interfaces are utilized to implement the application therefore another implementation of cache service can be easily configured to provide the needed functionality.

3. Asynchronous programming are utilized to implement cache service in an effort to further improve performance. 

4. The algorithm to generate tiny urls are implemented as a separate service/module, and again interface is utilized, so that a different implementation can be easily configured to provide the functionality.

5. Comprehensive test cases are developed to provide unit testing and integration testing to ensure code quality and facilitate future enhancement/maintenance. 

6. Spring properties/configuration/injection are utilized to ease future enhancement/maintenance efforts. 


### Requirements Description:

URL Shortener Code Exercise
 
Here’s the coding task that we would like you to complete as part of our assessment. Please be sure to read and follow all of the directions. The team is looking for a thorough and thoughtful submission as a zip or Git archive (preferred).
 
 
[Architecture question] URL Shortener
 
We'd like you to build a website that functions as a URL Shortener:
 
1. A user should be able to load the index page of your site and be presented with an input field where they can enter a URL.
2. Upon entering the URL, a "shortened" version of that url is created and shown to the user as a URL to the site you're building.
3. When visiting that "shortened" version of the URL, the user is redirected to the original URL.
4. Additionally, if a URL has already been shortened by the system, and it is entered a second time, the first shortened URL should be given back to the user.
 
For example, if I enter http://www.apple.com/iphone-7/ into the input field, and I'm running the app locally on port 9000, I'd expect to be given back a URL that looked something like http://localhost:9000/1. Then when I visit http://localhost:9000/1, I am redirected to http://www.apple.com/iphone-7/.
 
Expectations
•	Straight HTML. No need for any CSS.
•	Submit the project as a Git archive. Do not include artifacts. We might look at the history.
•	A small README is appreciated. A few words about your approach? How to compile/launch your application?
•	You may have to make some assumptions. It is expected. Please explain them.
•	Good developers write unit tests.
•	We do use Scala but it is not necessary here. Use the JVM stack you are most comfortable with. During the on-site interview, we will expect you to be an expert.
•	No project is ever done/complete. Your job is to convince us that you understood the problem and that we want to discuss your approach with you.
•	You really want to use a hash function? Think twice and motivate your choice.
•	You may want to use external dependencies e.g. database. It's totally fine! Just tell us more about your choice.
 
 
