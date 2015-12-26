# ENSKooKoo
ENS (Emergency Notification System)
 An emergency notification system refers to a collection of methods that facilitate the one-way dissemination or broadcast of messages to one or many groups of people “notifying” or alerting a group of individuals of pending or existing emergency situation.

 In a time critical situation (Specially for Women Safety) we need reliable notification system which is easy to use and manage, whose communication methods are highly portable to our day today requirements

 This developed prototype ENS satisfy all needs of such emergency situation.

# Features and benefits of ENS
~~~

Suitable for women safety
One ENS server for multiple emergencies
Create groups and define emergency number (Mobile no)
Two way communication
Set /Record emergency message on the fly
Easy to use and SMS based registration
Priority based broadcasting (Call, SMS, Twitter)
Highly Modular and Cloud based services
Easy to integrate with existing system
User can be group, society, private, public organization or individual
By sending an SMS, create emergency conference.
Easy to invite individual in an emergency conference.

~~~

# How to use

To use the developed prototype

~~~

Note: Must ensure, DND is not activated on your number as well as on all emergency numbers.
User has to first register their first name and 3 emergency numbers
Either by Sending an SMS
Or visiting to ENS portal (Remember Twitter based authentication required)
Registration via SMS
Type a new SMS, Format should be “HACKAR REG YourFirstName Number1 Number2 Number3” and send it to “09227507512”  ,
Please add “0” before all emergency mobile/landline numbers.
Example – “HACKAR REG Arun 08904111111 07979123456 07070654321” and send it to “09227507512”. 
If registration done, you will receive a confirmation message. In case of failure, it will ask you
to resend the SMS by following the above SMS format.

Create Emergency conference via SMS

Type a new SMS, Format should be “HACKAR CNF  Number1 Number2 Number3 NumberN” and send it to “09227507512”  ,
Please add “0” before all emergency mobile/landline numbers.
Example – “HACKAR CNF 08904111111 07979123456 07070654321 07070654321” and send it to “09227507512”. 
If message format is correct, It will dial to all numbers and will bring to a single virtual conference
where all can discuss over the phone and share about problems.

Registration via portal (Assumed, user has a twitter account)
Visit to http://ec2-52-34-119-27.us-west-2.compute.amazonaws.com/ENS/
Click on Connect with Twitter button.
It will redirect to Twitter authentication portal, Login with your twitter user id and password.
(note: this ENS portal is not collecting any your personal data, it is using for authentication)
On successful login, Twitter.com portal will redirect to ENS dashboard portal.
Portal Registration contd..
In dashboard, there is option to
Update your mobile number
And your emergency numbers.
Fill the details and Update it. 
Once done, you may logout from the portal.

How to trigger the emergency broadcast

From your registered number call to 
“08025149732”.
IVR will receive the call and it will ask you to 
Record your emergency message after the 
“beep” sound. Say your message and wait for few seconds
to disconnect the call.

How to trigger emergency contd…
After disconnecting the call, ENS will check for your identity, if your registration details found
It will dial to all your emergency numbers at same time and on receive of call, 
it will play your recorded voice message and ask them to take necessary action.
It will send  your registered details via an SMS to all emergency numbers saying your are in trouble.
Also, if you have authorized the ENS portal to access your twitter account, 
it will post recorded message to your Twitter wall.



~~~

