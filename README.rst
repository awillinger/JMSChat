#######
JMSChat
#######

======
Angabe
======


Implementieren Sie eine Chatapplikation mit Hilfe des Java Message Service. 
Verwenden Sie Apache ActiveMQ (http://activemq.apache.org) als Message Broker 
Ihrer Applikation. Das Programm soll folgende Funktionen beinhalten:

- Benutzer meldet sich mit einem Benutzernamen und dem Namen des Chatrooms an. 
  Beispiel für einen Aufruf:

.. code:: 

	vsdbchat <ip_message_broker> <benutzername> <chatroom>

- Der Benutzer kann in dem Chatroom (JMS Topic) Nachrichten an alle Teilnehmer 
  eine Nachricht senden und empfangen.
  Die Nachricht erscheint in folgendem Format:

.. code:: 

	<benutzername> [<ip_des_benutzers>]: <Nachricht>

- Zusätzlich zu dem Chatroom kann jedem Benutzer eine Nachricht in einem 
  persönlichen Postfach (JMS Queue) hinterlassen werden. Der Name des Postfachs
  ist die IP Adresse des Benutzers (Eindeutigkeit).

- Nachricht an das Postfach senden:

.. code:: 

	MAIL <ip_des_benutzers> <nachricht>

- Eignes Postfach abfragen:

.. code:: 

	MAILBOX

- Der Chatraum wird mit den Schlüsselwort EXIT verlassen. Der Benutzer 
  verlaesst den Chatraum, die anderen Teilnehmer sind davon nicht betroffen.

Gruppenarbeit: Die Arbeit ist in einer 2er-Gruppe zu lösen und über das 
Netzwerk zu testen! Abnahmen, die nur auf localhost basieren sind unzulässig 
und werden mit 6 Minuspunkten benotet!

================
Designüberlegung
================

- GUI

  - Großes Textfeld (mitte, oben) für alle Nachrichten in diesem Topic
  - Einzeiliges Textfeld unten für Benutzereingabe
  - Button rechts von Eingabefeld zum Absenden
- Channel (JMS-Topic)

  - Sender & Receiver laufen in einem extra Thread, um die GUI nicht zu blockieren
  - Verwendung des Observer Patterns mithilfe von onMessage (bei MessageConsumer)
    Wenn Nachricht ankommt: Aktualisierung des großen Textfeldes in der GUI
  - Extra Thread stellt Methode zum Senden von Nachrichten zur Verfügung, Controller hat Referenz auf dessen Objekt
- Mailbox (JMS-Queue)

  - Sender & Receiver laufen wie bei Topic in einem extra Thread
  - Wenn eine neue Nachricht (über onMessage) empfangen wurde, wird diese in einer ArrayList abgelegt
  - Bei Abfrage der Mailbox (mithilfe des Kommandos "MAILBOX" in der GUI), wird die ArrayList ausgelesen und deren 
    Inhalt im großen Textfeld farbig hinterlegt angezeigt.
  - Da es auf einer IP auch mehrere Benutzer geben kann, muss der Benutzername beim Senden einer Nachricht mit 
    angegeben werden.
  Aufruf demnach: MAIL <benutzername>@<ip_des_benutzers> <nachricht>
- Beenden
  
  Beim Beenden müssen alle offenen Verbindungen in den jeweiligen Threads geschlossen werden, anschließend kann 
  erst das Programm beendet werden.

================
Aufwandschätzung
================

+-------------------------------+---------------+-------------+--------------------+
| Arbeitspaket                  | Geplante Zeit |   Aufwand   | Wer                |
+-------------------------------+---------------+-------------+--------------------+
|                               |     [H:MM]    |             |                    |
+===============================+===============+=============+====================+
| UML                           |      1:30     |   Mittel    | Jakob Klepp        |
+-------------------------------+---------------+-------------+--------------------+
| GUI                           |      1:00     |   Gering    | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+
| Chat Befehle                  |      0:30     |   Gering    | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+
| Channel (JMS-Topic)           |      2:00     |   Mittel    | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+
| Option parsing                |      0:40     |   Gering    | Jakob Klepp        |
+-------------------------------+---------------+-------------+--------------------+
| Mailbox (JMS-Queue)           |      2:00     |   Mittel    | Jakob Klepp        |
+-------------------------------+---------------+-------------+--------------------+
| Testing                       |      0:30     |   Gering    | Jakob Klepp /      |
|                               |               |             | Andreas Willinger  |
+-------------------------------+---------------+-------------+--------------------+

================
Zeitaufzeichnung
================

+----------------------------+--------------+---------+---------+-----------+--------------------+
| Arbeitspaket               | Datum        | Start   | Ende    | Dauer     | Wer                |
+----------------------------+--------------+---------+---------+-----------+--------------------+
|                            | [YYYY-MM-DD] | [HH:MM] | [HH:MM] |    [H:MM] |                    |
+============================+==============+=========+=========+===========+====================+
| UML                        |  2013-02-11  |  13:00  |  13:40  |     0:40  | Jakob Klepp        |
+----------------------------+--------------+---------+---------+-----------+--------------------+

=========
Umsetzung
=========

~~~~~~~~
ActiveMQ
~~~~~~~~

Wir verwenden als Host für ActiveMQ einen von uns gemieteten vServer in Deutschland, da das Schulnetzwerk bei VMs
sehr unzuverlässig ist.

Zu aller erst muss Java installiert werden:

.. code:: bash

    apt-get install openjdk-7-jre-headless

Nun kann ActiveMQ heruntergeladen & entpackt werden.
Wir verwenden die bereits vorkompilierte (binäre) Variante.

.. code:: bash

    mkdir /root/activemq && cd /root/activemq
    wget http://tweedo.com/mirror/apache/activemq/apache-activemq/5.9.0/apache-activemq-5.9.0-bin.tar.gz
    tar xfvz apache-activemq-5.9.0-bin.tar.gz

Anschließend wird noch die Standard Konfigurationsdatei angelegt und wie folgt bearbeitet:
Dies wird benötigt, da Java standardmäßig IPv6 benutzt, falls es verfügbar ist.

.. code:: bash

    cd apache-activemq-5.9.0/bin/
    ./activemq setup /etc/default/activemq
    
    vim /etc/default/activemq

.. code:: plain

    [..]
    ACTIVEMQ_OPTS="$ACTIVEMQ_OPTS -Djava.net.preferIPv4Stack=true"
    [..]
    
Zum Schluss kann ActiveMQ gestartet werden:

.. code:: bash

    ./activemq start

**Ausgabe**:

.. code:: bash

    INFO: Loading '/etc/default/activemq'
    INFO: Using java '/usr/bin/java'
    INFO: Starting - inspect logfiles specified in logging.properties and log4j.properties to get details
    INFO: pidfile created : '/root/activemq/apache-activemq-5.9.0/data/activemq-mail.f-o-g.eu.pid' (pid '2136')
    INFO: Loading '/etc/default/activemq'
    INFO: Using java '/usr/bin/java'
    ActiveMQ is running (pid '2136')

=======
Testing
=======

=======
Quellen
=======


.. _1:

[1]  Homepage ActiveMQ
     http://activemq.apache.org/index.html
     zuletzt besucht am: 

.. _2:

[2]  
     http://www.academictutorials.com/jms/jms-introduction.asp
     zuletzt besucht am: 

.. _3:

[3]  
     http://docs.oracle.com/javaee/1.4/tutorial/doc/JMS.html#wp84181
     zuletzt besucht am: 

.. _4:

[4]  
     http://www.openlogic.com/wazi/bid/188010/How-to-Get-Started-with-ActiveMQ
     zuletzt besucht am: 

.. _5:

[5]  
     http://jmsexample.zcage.com/index2.html
     zuletzt besucht am: 

.. _6:

[6]  http://www.onjava.com/pub/a/onjava/excerpt/jms_ch2/index.html
     zuletzt besucht am: 

.. _7:

[7]  http://www.oracle.com/technetwork/systems/middleware/jms-basics-jsp-135286.html
	 zuletzt besucht am: 

.. _8:

[8]  Java JMS With A Queue Programming Reference and Examples
     http://www.fluffycat.com/Java/JMS-With-A-Queue/
     zuletzt besucht am: 10.02.2014

.. _9:

[9]  Java Message Service: Chapter 2: Developing a Simple Example
     http://oreilly.com/catalog/javmesser/chapter/ch02.html
     zuletzt besucht am: 10.02.2014

.. header::

    +-------------+-------------------+------------+
    | Titel       | Autor             | Date       |
    +=============+===================+============+
    | ###Title### | Andreas Willinger | 10.02.2014 |
    |             | -- Jakob Klepp    |            |
    +-------------+-------------------+------------+

.. footer::

    ###Page### / ###Total###
