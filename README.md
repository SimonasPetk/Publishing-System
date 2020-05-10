COM2008/3008

Systems Design Project

Academic Publishing System

## UML Use Case Diagram
![UML Use case diagram](https://user-images.githubusercontent.com/44678221/81497257-f3db9000-92da-11ea-9b3b-d5bf00262fab.png)

## UML Class Diagram of the Initial Information Model
![Initial Information Model](https://user-images.githubusercontent.com/44678221/81497249-e3c3b080-92da-11ea-82fb-1f7f5f18939c.png)

## UML Class Diagram of the Normalised Database Model
![Normalised Database Model](https://user-images.githubusercontent.com/44678221/81497203-95161680-92da-11ea-9219-32d426753dd0.png)

## Screenshots

#### Main Window.
![main window](https://user-images.githubusercontent.com/44678221/81496946-f1783680-92d8-11ea-906b-24f26f74284d.png)

#### Reviewer choosing the articles to review.
![reviewer choosing articles to review](https://user-images.githubusercontent.com/44678221/81496966-0a80e780-92d9-11ea-8c18-11f537b227c4.png)

#### Author screen - can see reviews and who sent them.
![author screen](https://user-images.githubusercontent.com/44678221/81496976-19679a00-92d9-11ea-97b4-107e812af472.png)

#### Reviewer final article accept/reject view
![reviewer final article accept/reject view](https://user-images.githubusercontent.com/44678221/81496985-2a181000-92d9-11ea-8d43-eb51c1b25f10.png)

#### Chief editor can transfer the role to other editors.
![Chief editor transfer role](https://user-images.githubusercontent.com/44678221/81496995-38fec280-92d9-11ea-8b0d-fa1b3b9e1fee.png)

#### The author submitting responses
![Author submitting responses](https://user-images.githubusercontent.com/44678221/81497005-4b78fc00-92d9-11ea-95c9-63a4e340cfb5.png)

#### Reviewer giving critisism
![reviewer giving critisicm](https://user-images.githubusercontent.com/44678221/81497024-5df33580-92d9-11ea-8822-18a429f685ce.png)

#### Editor view - seeing all of the submitted articles to your edited journals.
![Editor view](https://user-images.githubusercontent.com/44678221/81497029-6b102480-92d9-11ea-95d8-38ca5b4f0288.png)

#### Journal view - seeing all the articles in a journal.
![Journal view](https://user-images.githubusercontent.com/44678221/81497036-795e4080-92d9-11ea-8c7b-228d5832b88d.png)

## Security Features

We sanitized our SQL queries with prepare statement to defend our
database from SQL injections. When a person enters his or her wanted
password it is not shown in the text field and changed into icons. To
keep our users' password safe we only allow passwords that are at least
8 characters long and we hash it with salt in our database as it is not
safe to store plain versions of them. Once we are done with dealing with
the password variable, the password's variable is cleared from the
system.

To add extra security from spammers we do not allow blank registration
fields to be submitted. Also, we added email validation and only allows
to register people that submit emails in a format
'yyy\@yyy\[\[.\]{.underline}\](mailto:xxx@xxx.xxx)yyy' (at least one 'at'
sign and at least one 'dot').

We don't allow to add multiple entries of the same co-author, this is a
prevention measure that spammers couldn't easily spam the system and
crash it.

Furthermore, we do not allow to submit journals with the same titles and
journals with the same ISSN numbers as we assumed they are unique
entries.

 

## Team Effort

### Ross Mansfield:
Creating UML class diagram of the initial information model and the UML state machine diagram for the interface. Programming Chief editor and editor functionality as well as many database functions and addJournal. Adding validation and making change password. Testing, debugging. Screenshots and tables in team report.                                                           

### Simonas Petkevičius:
Designing and editing all of the GUI windows, attaching functionality. Made getArticles that represents all of the articles in ArticleWindow, Journal validation, manual testing of the system, fixing errors, SQL statement sanitization, Team/Slack management (Admin work), team report writing.                                                                                

### Manas Sarpatwar:
Completed UML Use Case Diagram, improved UML Initial Information Model diagram and UML Normalised Database diagram, wrote skeleton code for all main classes according to the class diagram, wrote password encryption algorithm which would encrypt the password and produce a salt, wrote SQL Insert statements for Author and Reviewer roles, wrote methods to retrieve Objects from the database including retrieving the roles of an academic when he/she logs in, completed Author and Reviewer backend and front end, wrote methods for PDF upload and download, completed publishing of editions in for Chief Editor, automatic temporary retirement and reinstatement of an editor in case of a clash in affiliation, helped with Editor GUI content updates 

### Alex Hall:
The functionality of login and registration windows. Ensured journal window shows all journals in the database. Allowed Editor to view all submissions and final verdicts. Added functionality to automatically accept/reject a submission, including giving the editor the option to manually accept or reject an article if it cannot be automatically accepted. Added relative methods to database files to facilitate these implementations.
