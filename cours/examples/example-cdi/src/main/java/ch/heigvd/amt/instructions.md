# Exercise

Use Jackarta CDI to represent a library.

Implement a **Book** object with a **title**, an **author**, and, optionally, a **borrower**.

Implement a Publisher bean that **publishes** two books:
- "The Lord of the Rings" by J.R.R. Tolkien qualified as "Heroic Fantasy"
- "The Hobbit" by J.R.R. Tolkien qualified as "Heroic Fantasy"

Implement a Shelf bean that **holds** books for the library.
- The shelf should be filled with the "Heroic Fantasy" books when it is created.
- The shelf should offer a method to retrieve books by title.

Implement a **Librarian** bean that offers services to **borrow** and **return** books.
- A client should be able to borrow a book to the librarian.
- A client should be able to return a book to the librarian.
- The librarian has access to the shelf.

Whenever a book is removed from the shelf, it should be stamped and marked as borrowed.
Implement this behaviour with a decorator on the shelf.

Whenever a book is borrowed or returned, a bell should ring in the library.
Implement this behaviour with an interceptor on the librarian.

The library **Manager** is a control freak and asks to be notified whenever a book is returned.
Implement this behaviour with an observer triggered by the librarian.

