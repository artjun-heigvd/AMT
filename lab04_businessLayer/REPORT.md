# Report

## Exercise 1 - Transaction, Renting a DVD

**Report**  
* Describe an alternative strategy that could achieve a similar result to the one implemented.

Another strategy to achieve a similar result without table lock would be to use optimistic locking. This would require to add a version column to the `Inventory` table and to check the version before updating the row. If the version has changed, the transaction would be rolled back and the user would have to try again. This would prevent the lost update problem and would allow multiple transactions to run concurrently.

* Explain why managing the concurrency using [@Lock](https://quarkus.io/guides/cdi-reference#container-managed-concurrency) or Java `synchronized` is not a solution.

Because `@Lock` and `synchronized` are not suitable for distributed applications with multiple instances. They will only work within a single JVM, for example, for an application instance with threads. This mean that if the application is scaled horizontally, the synchronization will not work and won't prevent concurrent access to the database. 

## Exercise 3 - Implement authentication for staff

**Report** Explain why the password storage in Sakila `Staff` table is insecure. Provide a proposal for a more secure password storage.

The current way we're hashing the password is highly unsecure. 

As we can see on Wikipedia : https://en.wikipedia.org/wiki/SHA-1#Attacks, this is an insecure algorithm and we should use something else.... And currently we're not even using salts. So we could just use rainbow tables to get the decrypted password after finding a way to drop the table.

The best thing to do then is to use a more robust way of encrypting our passwords.
If we go on OWASP, they have a page that explains the best practice when it comes to storing password in a database : https://cheatsheetseries.owasp.org/cheatsheets/Password_Storage_Cheat_Sheet.html

So instead of SHA-1, we probably should use Argon2id !

Another point that could be improved is the lack of password length check, 12345 wouldn't be accepted in any modern website....
We should enforce higher entropy passwords when the users are creating their accounts.



>**Report** Describe the flow of HTTP requests of the above test case and explain:

>* What is sent at step 2 to authenticate to the application and how it is transmitted.

In the first step we're navigating to the login page. We see that we haven't specified an encryption key in the logs of vert.x. What is then sent at step 2 is a POST to `/j_security_check` with the information contained in the form : 
```
j_username=Mike&j_password=12345
```
As we can see, since we're not in https, the information is sent in cleartext.

>* What is the content of the response at step 2 that his specific to the authentication flow.

In the log we observe this : 
```qute
2024-11-21 10:25:14,832 DEBUG [io.qua.ver.htt.run.sec.PersistentLoginManager] (vert.x-eventloop-thread-1) The new cookie will expire at Thu Nov 21 10:55:14 CET 2024



```
So since the password is right, we're setting up a new cookie that will last exactly 30 minutes.

So in summary the response includes : 
- HTTP Post Status: 302 Found
- On success: Redirect to /hello/me + cookie setup
- On failure: Redirect to /error.html

>* What is sent at step 3 to authenticate the user to the application and how it is transmitted.

To arrive at this page, we do a POST request with the cookies embedded in the request : 

```
GET /hello/me HTTP/1.1
Accept: text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.7
Accept-Encoding: gzip, deflate, br, zstd
Accept-Language: en-US,en;q=0.9,fr-CH;q=0.8,fr;q=0.7
Cache-Control: max-age=0
Connection: keep-alive
Cookie: quarkus-credential=DC3wEFTD6UYqWehAzSdx9WS11cmIu/D9a4IONFKasdwveSsyDTsRfkxNxLi1Wvg=
Host: localhost:8080
Referer: http://localhost:8080/login.html
Sec-Fetch-Dest: document
Sec-Fetch-Mode: navigate
Sec-Fetch-Site: same-origin
Sec-Fetch-User: ?1
Upgrade-Insecure-Requests: 1
User-Agent: Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/128.0.0.0 Safari/537.36
sec-ch-ua: "Chromium";v="128", "Not;A=Brand";v="24", "Google Chrome";v="128"
sec-ch-ua-mobile: ?0
sec-ch-ua-platform: "Linux"
```

The important part is the cookie that has the session credentials store in.

We can also see that in the logs we're also checking if the cookie is valid or not :

``` qute

2024-11-21 10:25:31,582 DEBUG [io.qua.ver.htt.run.sec.PersistentLoginManager] (vert.x-eventloop-thread-1) Current time: Thu Nov 21 10:25:31 CET 2024, Expire idle timeout: Thu Nov 21 10:55:14 CET 2024, expireIdle - now is: 1732182914832 - 1732181131582 = 1783250

2024-11-21 10:25:31,582 DEBUG [io.qua.ver.htt.run.sec.PersistentLoginManager] (vert.x-eventloop-thread-1) Is new cookie needed? ( 1800000 - ( 1732182914832 - 1732181131582)) > 60000 : false
```
We can also see that it sets up a new cookie if we visit the page a minute after the previous cookie was set.


>Explain why the above test authentication flow is considered insecure, if it was used in a productive environment as is, and what is required to make it secure.

Well firstly we have the issue linked with the hashing of the password as we discussed previously. 
We should probably force the use of HTTPS, since right now, anyone that is listening to the network could get the passwords that are sent to the website. Using ways to secure the cookie would be nice too, like with the flags http-only, secure and same-site set to strict.

A way to also make the authentication would be to add a 2 factors authentication by using authentication key. 

We can also see that in the current state of the app, we could brute force the code as we have no timeouts to stop that to happen, implementing a rate limiter for failed login attempts would be a way to fix this.

Finally, I'd add a CSRF token to the login so we can at least be protected from CSRF attacks.

## Exercise 5 - Implement a frontend for rentals

**Report** Discuss the pros and cons of using an approach such as the one promoted by htmx.

### Pros

#### Progressive Enhancement:
htmx allows you to progressively enhance your web pages. This means it is possible to start with a fully functional HTML page and 
add interactivity incrementally without having to create the whole page from the start.

#### Simplicity:
It simplifies the development process by allowing you to use HTML attributes to define behavior, reducing the need for 
complex JavaScript code. By using HTML attributes, htmx minimizes the amount of JavaScript you need to write and maintain, which can lead 
to cleaner and more maintainable code.

#### Server-Side Rendering:
htmx works well with server-side rendering, allowing you to keep your application logic on the server and send only the 
necessary HTML fragments to the client.

### Cons

#### Limited Ecosystem:
htmx is relatively new and has a smaller ecosystem compared to more established frameworks like React or Angular. This 
means fewer third-party libraries and community support.

#### Complex Interactions:
For very complex client-side interactions, htmx might not be sufficient, and you may still need to write custom 
JavaScript.

#### Learning Curve:
While htmx simplifies many aspects of development, it introduces its own concepts and attributes that developers need 
to learn.
