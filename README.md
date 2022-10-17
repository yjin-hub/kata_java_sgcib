**Starting the program**

1) mvn clean install

2) mvn exec:java -Dexec.mainClass="com.katasgcib.app.App"

You can change java version in 'pom.xml' before according to your environment.

Then you will be invited to enter commands to do some actions.

**Explanations of architecture**

I choose an 3 tier architecture for application because
this architecture is efficient and scalable. Also, each tier can be updated
separately.



**Note**

In our context, as it is a basic application, we do not manage transactions
and we do not handle amount conversion with currency (only check if same currency)

