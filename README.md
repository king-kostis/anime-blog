<h1 align="center">ANIME NEWS BLOG</h1>

![website_image](/src/main/resources/static/images/website.png?raw=true)

A blog website that displays anime news posts from a telegram channel using RSS feeds
> This is my first ever full stack application so please pardon my architectural choices

---

## Features
- Streams telegram posts from an anime news channel as rss feeds displays them
- Allows users to send emails to the host of the website through it
- Persists rss feed into a text file for offline use
- Reloads feeds data automatically from saved file

---

## Tech Stack
- **Language:** Java, HTML, CSS, JavaScript
- **Frameworks:** Spring Boot, Thymeleaf
- **Build Tool:** Maven

---

## Setup & Installation
> [!NOTE]
> - You’ll need two Gmail accounts, preferrably of your own, to enable the email feature.
> - One will act as the sender, and the other will receive messages.
> - You’ll also need to generate a Google App Password for the sender account.
> - For more info or a guide on google app passswords you can check [here](https://support.google.com/mail/answer/185833?hl=en).
<br>

## Google App Password Setup
- Generate your google app password [here](https://myaccount.google.com/apppasswords).
- Set your gmail account as <em>MAIL_USERNAME</em> and the generated password as <em>MAIL_PASSWORD</em> as environment variables.
- Set your personal email (the one you want to receive messages with) as <em>MAIL_RECEIVE</em>
  
> I chose this setup to avoid the complexity of OAUTH authentication since this is a simple web app.
> So with these steps done you can now proceed with the installation.

### 1. Clone the repository
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
set MAIL_USERNAME = senderEmail@gmail.com.
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
export MAIL_PORT = "587" or "465"
export MAIL_USERNAME = "senderEmail@gmail.com"
export MAIL_PASSWORD = "yourAppPassword"
export MAIL_RECEIVE = "receiverEmail"
```

### 3. Configure the application properties
Open `src/main/resources/application.properties` and set up mail configurations and rss url
```properties
spring.mail.host = ${MAIL_HOST}
spring.mail.port = ${MAIL_PORT}
spring.mail.username = ${MAIL_USERNAME}
spring.mail.password = ${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.ssl.enable=true
spring.mail.properties.mail.smtp.starttls.enable=false
app.mail.receiver = ${MAIL_RECEIVE}
rss.feed.url = yourRssFeedUrl.xml
```

> [!NOTE]
> Some ISPs may be strict with TLS connection requests(from your port 587) so use 465 for MAIL_PORT to be safe.<br><br>
> If you still want to use port 587, set "<b><em>spring.mail.properties.mail.smtp.starttls.enable</em></b>" to "<b><em>true</em></b>"<br>
> in your <b>application.properties</b> file.<br><br>
> Then remove "<b><em>spring.mail.properties.mail.smtp.ssl.enable=true</em></b>".<br>
> Make sure <em>MAIL_PORT</em> is set to 587.
```properties
spring.mail.host = ${MAIL_HOST}
spring.mail.port = ${MAIL_PORT}
spring.mail.username = ${MAIL_USERNAME}
spring.mail.password = ${MAIL_PASSWORD}
spring.mail.properties.mail.smtp.starttls.enable=true
app.mail.receiver = ${MAIL_RECEIVE}
rss.feed.url = yourRssFeedUrl.xml
```

### 4. Build and run the app
> Make sure you have maven installed or have its binaries path as a <em>PATH<em> variable.
> The app runs on port 3000. You can change it in the application.properties file.

```bash
mvn clean install
```
then
```bash
mvn spring-boot:run
```

---

## Future Plans
- Dockerize app for deployment
---

## Author
Author Name: **Jesse Quartey** <br>
[LinkedIn](https://www.linkedin.com/in/jesse-quartey-3777722a5/)

---

## License
This project is licensed under the MIT License – see the [LICENSE](LICENSE) file for details.
