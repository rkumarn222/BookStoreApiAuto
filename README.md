# BookStore API Test Automation

This project helps us automatically test the BookStore application's API. Think of an API as a way for different computer programs to talk to each other. We're making sure that this communication works correctly.

We use this project to test things like:
* Creating new user accounts
* Logging in and out
* Adding new books to the store
* Getting a list of all books
* Updating book information
* Deleting books

By automating these tests, we can quickly find and fix any problems, making sure the BookStore app is reliable.

---

## What's Inside?

This project uses a few tools to make the testing process easier:

* **Java:** The programming language used to write the tests
* **Maven:** A tool that helps manage the project and its dependencies (other tools it needs to work)
* **TestNG:** A testing framework that helps us organize and run our tests
* **Cucumber:** A tool that lets us write our tests in a way that's easy for anyone to read, even if they're not a programmer
* **RestAssured:** A tool that makes it easy to test APIs in Java
* **Allure Reports:** A tool that creates easy-to-read reports of our test results, so we can see what passed and what failed

---

## How to Get Started

To run these tests on your own computer, you'll need a few things first:

* **Java (Version 21):** You can download it from the official Java website
* **Maven:** You can download it from the official Maven website

Once you have those installed, you can run the tests by following these steps:

1. **Download the project:** You can download the project files as a ZIP file and unzip them, or use a tool called Git to clone the project

2. **Run the tests:** Open a command prompt or terminal, navigate to the project's folder, and type the following command:
   ```bash
   mvn clean test
   ```
   This will run all the tests in the project

3. **See the results:** After the tests are finished, you can see a beautiful report of the results by typing this command:
   ```bash
   mvn allure:report
   ```
   And then this command to open the report in your web browser:
   ```bash
   allure serve allure-results
   ```

---

## What We Test

Our tests cover both the good scenarios (when everything works correctly) and the bad scenarios (when something goes wrong). This helps us make sure the application handles all situations properly.

### Good Scenarios (Positive Tests):
* ✅ User signs up successfully
* ✅ User logs in successfully
* ✅ User creates a new book
* ✅ User gets a list of all books
* ✅ User updates a book's information
* ✅ User deletes a book

### Bad Scenarios (Negative Tests):
* ❌ User tries to log in with wrong password
* ❌ User tries to sign up with an email that already exists
* ❌ User tries to access features without being logged in
* ❌ User tries to create a book with missing information
* ❌ User tries to access a book that doesn't exist
* ❌ User tries to update or delete a book that doesn't exist

---

## How it Works with CI/CD (for developers)

CI/CD is a way to automate the process of building, testing, and deploying software. This project can be set up to run automatically whenever a developer makes a change to the BookStore application's code.

This means that every time a developer changes something, we can be sure that it doesn't break any of the existing features. This helps us to catch problems early and fix them quickly.

We've included an example of how to set this up using a tool called Jenkins. Jenkins can be configured to watch for changes in the BookStore application's code, and then automatically run the tests in this project. If any of the tests fail, Jenkins will notify the developers so they can fix the problem.

---

## Why We Use These Tools

### Allure Reports
* **Easy to understand:** Creates beautiful, easy-to-read reports
* **Shows what went wrong:** When tests fail, it shows exactly what happened
* **Helps with debugging:** Shows the requests and responses that were sent
* **Good for teams:** Everyone can understand what's happening, not just programmers

### TestNG
* **Organized testing:** Helps us group and organize our tests
* **Better control:** Gives us more control over how tests run
* **Works well with other tools:** Integrates smoothly with our other testing tools

### Cucumber
* **Easy to read:** Tests are written in plain English
* **Good for everyone:** Non-programmers can understand what the tests do
* **Clear documentation:** The test files serve as documentation of what the application should do

---

## Troubleshooting

If you run into problems:

1. **Make sure Java is installed:** Type `java -version` in your terminal to check
2. **Make sure Maven is installed:** Type `mvn -version` in your terminal to check
3. **Check the project structure:** Make sure all the files are in the right places
4. **Look at the error messages:** They usually tell you what's wrong

If you're still having trouble, you can:
* Check the project's documentation
* Look at the error logs
* Ask for help from someone who knows about the project

---

## Contributing

If you want to help improve this project:
1. Make sure you understand what the project does
2. Test your changes thoroughly
3. Make sure all tests still pass
4. Update the documentation if needed
5. Ask for a review of your changes

Remember: The goal is to make the BookStore application more reliable and easier to use!


#   B o o k S t o r e A P I 
 
 