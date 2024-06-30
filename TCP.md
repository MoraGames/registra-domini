# Progetto di Sistemi Distribuiti 2023-2024:

---

### Descrizione:
Documentazione del protocollo TCP del progetto Registra Domini, fornito dal database.
Il protocollo è di tipo testuale e accetta determinate linee di comando in un preciso ordine per lo svolgimento delle operazioni sul database.
Essendo che le risorse documentali sono divise per collezioni (un file .json per ogni collezione) e che ogni collezione riporta il campo usato come chiave dei singoli documenti contenuti e l'array data di documenti stessi, il protocollo prevede sempre 1 o 2 operazioni, a seconda della richiesta svolta.

---

### Connessione:
Un client si connette al server del database stabilendo una connessione alla porta 3030, che gli assegnerà un thread per lo svolgimento della query.

### Modello richiesta-risposta:
Il thread, tramite il suo stream buffer di input riceverà la query inviatagli dal client, la comporrà tramite la struttura dati Query per poi eseguirla una volta terminata la ricezione dei comandi.
La terminazione dell'input sul buffer è indicato da un comando stesso del protocollo.
Dopo aver processato la query sulle risorse contenute (collections e documenti), il thread si occuperà di costruire la risposta in forma testuale.
Una volta preparato l'esito dell'operazione verrà utilizzato lo stream di output per l'invio della risposta al client.

---

### Descrizione del protocollo:
Per una maggior chiarezza dividiamo in capitoli la descrizione del protocollo:
- Composizione di una query
    - Comandi sulle collezioni
    - Comandi sui documenti
- Composizione di una risposta

### `Composizione di una query`
- **Descrizione**: Una query di tipo testuale deve essere composta da 2 o 3 linee, ciascuna contenente un comando.
Il primo comando, indicato nella prima riga, specifica l'azione da intraprendere sulla collezione e può essere CREATE, SELECT o DELETE.
Il secondo comando, indicato nella seconda riga, specifica l'azione da intraprendere sul documento e può essere INSERT, SEARCH, REMOVE. Questa riga è da indicarsi solo se il primo comando è SELECT.
L'ultima riga deve terminare il comando di conferma, ovvero COMMIT. Tramite questa parola chiave il protocollo comprende che è terminata la query trasmessa.

> #### Collection `CREATE`
> - **Descrizione**: Permette di creare una nuova collezione.
> - **Composizione**: `CREATE <collection-name> ON-KEY <data-field>`
> - **Parametri**: `<collection-name>, <data-field>`
> - **Note**: Il nome della collezione che verrà poi utilizzato per la creazione del file. Deve essere contenuto tra doppi apici (") e non può contenere spazi. Il nome del campo utilizzato come chiave all'interno dei documenti, anch'esso contenuto tra doppi apici.

> #### Collection `SELECT`
> - **Descrizione**: Permette di selezionare una collezione pre-esistente.
> - **Composizione**: `SELECT <collection-name>`
> - **Parametri**: `<collection-name>`
> - **Note**: Il nome della collezione che verrà utilizzato per la ricerca e l'apertura del file .json indicante la collezione e contenente tutti i documenti della stessa.

> #### Collection `DELETE`
> - **Descrizione**: Permette di eliminare una collezione pre-esistente.
> - **Composizione**: `DELETE <collection-name>`
> - **Parametri**: `<collection-name>`
> - **Note**: Il nome della collezione che verrà utilizzato per la ricerca e l'eliminazione del file .json indicante la collezione e contentente tutti i documenti della stessa.