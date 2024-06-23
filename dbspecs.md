### Specs

tutte info configurabili con un file di config

- porta: 6969
- hostname: 0.0.0.0 | localhost | ::
- authentication
  - username:
  - password:
  - role: admin | read-only | read-write (no operazioni sui database, puó eseguire operazioni crud )

### Database

Il sistema permette di creare un database con un **nome univoco**.
Si puó specificare il tipo di database che puó essere di tipo:

- "KV": key value, permette di memorizzare un valore ad una chiave, utile per operazioni di autenticazione ed operazioni veloci, di default il tipo KV gestisce le operazioni in memoria e se il database dovessese essere chiuso le informazioni verranno perse, per evitare ció si puó specificare il tipo di database "KV-PERST" che gestisce le informazioni sempre in memoria ma usa un Append-Only-Log (AOL) per memorizzare le operazioni eseguite e ricreare il database in caso di chiusura.

- "schemaless": permette di memorizzare dati senza schema, comodo per memorizzare info piú complicate come un utente o delle informazioni di una risorsa, le informazioni sono salvate sempre su disco e non in memoria.

un database

### Components

### Protocol

### Configurazione

```yaml
log:
  level: debug|info|warning|error
  format: text|json
  path: "/path/to/log/file.log" # se non specificato il server loggerá su stdout
  logAppend: true|false # se true il log verrá aggiunto al file, se false il file verrá sovrascritto
net:
  bind: 0.0.0.0
  port: 6969
persistance:
  path: "/path/to/persistance/folder"
```
