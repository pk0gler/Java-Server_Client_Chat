# Java-Server_Client_Chat

### Client
    - Übernimmt IP-Adresse und Port des Ziel-Hosts als Kommandozeilenparameter
    - Verbindet sich mithilfe der angegebenen Parameter mit dem Server
    - Liest über die Konsole zeilenweise Benutzereingaben als Strings ein
    - Diese Strings werden an den Server übertragen, welcher sie an alle anderen verbundenen Clients weiterleitet

### Server
    - Ist ebenfalls eine Konsolenanwendung und übernimmt den Port als Kommandozeilenparameter
    - Horcht auf eingehende Verbindungen von Clients
    - Startet für neue Clients einen neuen Thread
    - Verwaltet die verbundenen Clients in einer threadsicheren Collection
    - Protokolliert die gesendeten Nachrichten in einem gemeinsamen Logfile, wobei das Loggen in einer threadsicheren Methode passiert
    - Verteilt empfangene Strings an alle anderen Clients