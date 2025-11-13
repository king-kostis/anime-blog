<h1 align="center">ANIME NEWS BLOG</h1>

![alt text](/src/main/resources/static/images/website.png?raw=true)

A blog website that displays anime news from a telegram channel using RSS feeds
> This is my first ever full stack application so please pardon the messy code and architecture

---

## Features
- Streams telegram posts from an anime news channel as rss feeds displays them
- Allows users to send emails to the host of the website through it
- Stores rss feed into a text file for persistence
- Loads feeds from text file after persistence

---

## Tech Stack
- **Language:** Java, HTML, CSS, JavaScript
- **Frameworks:** Spring Boot, Thymeleaf
- **Build Tool:** Maven

---

## Setup & Installation
> [!NOTE]
> - To receive emails from the app you first have to have <b>two google accounts</b> for this to work.<br><br>
> - One of the accounts should have an <b>App Password</b> which you can generate for your google account [here](https://myaccount.google.com/apppasswords).<br><br>
> - For more info or a guide on google app passswords you can check [here](https://support.google.com/mail/answer/185833?hl=en).<br><br>
> - Once the password is generated for whatever account you used, use it as the value for your <em>MAIL_PASSWORD</em> environment variable and use the gmail account you used to generate it as your <em>MAIL_USERNAME</em> value.<br><br>
> - Now you can use whatever personal email you have to receive the emails, hence it would be your <em>MAIL_RECEIVE</em> value.<br><br>
> - Sorry for the excessive steps but due to smtp is, I wanted to avoid any robust authenticatication for this small web app so I chose this round about way of sending emails the users of the app write to my self.<br><br>
> - So with these cautions in mind you can proceed with the installation.

<br>

### 1. Clone the Repository
```bash
git clone https://github.com/king-kostis/anime-blog.git
cd anime-blog
```

### 2. Set environment variables

### Windows
For temporary environment variables for testing(Lasts only during terminal session)
```bash
set MAIL_HOST = mailClient.smtp.com
set MAIL_PORT = 587 or 465
set MAIL_USERNAME = senderEmail@gmail.com
set MAIL_PASSWORD = yourAppPassword
set MAIL_RECEIVE = receiverEmail
```
For permanent environment variables
```bash
setx MAIL_HOST = mailClient.smtp.com
setx MAIL_PORT = 587 or 465
setx MAIL_USERNAME = senderEmail@gmail.com
setx MAIL_PASSWORD = yourAppPassword
setx MAIL_RECEIVE = receiverEmail
```

### Linux 
```bash
export MAIL_HOST = "mailClient.smtp.com"
export MAIL_PORT = "587" or "465" [Some ISPs may strict with TLS connection requests(from your port 587) so use 465 to be safe] 
export MAIL_USERNAME = "senderEmail@gmail.com"
export MAIL_PASSWORD = "yourAppPassword"
export MAIL_RECEIVE = "receiverEmail"
```

### 2. Configure the application properties
Open `src/main/resources/application.properties` and set up mail configurations and rss url
```properties
spring.mail.host = ${MAIL_HOST}
spring.mail.port = ${MAIL_PORT}
spring.mail.username = ${MAIL_USERNAME}
spring.mail.password = ${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.starttls.enable=false
app.mail.receiver = ${MAIL_RECEIVE}
rss.feed.url = yourRssFeedUrl.com
```

### 3. Build and Run the App
> Make sure you have maven installed or have its binaries path as a <em>PATH<em> variable.

```bash
mvn clean install
```
then
```bash
mvn spring-boot:run
```

---

## Author
Author Name: **Jesse Quartey** <br>
[LinkedIn](https://www.linkedin.com/in/jesse-quartey-3777722a5/)

---

## License
This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
