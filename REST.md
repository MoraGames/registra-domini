# Progetto Sistemi Distribuiti 2022-2023 - API REST

Documentazione delle API REST del progetto Gestione Domini, fornite dal server web. Ogni risorsa (domains,users,whois) fornita dal server ha un path dedicato.

## `/domains`

### GET

**Descrizione**: questo metodo restituisce un array JSON contentente tutti i domini presenti nel database

**Parametri**: non previsti

**Body richiesta**: vuota

**Risposta**: viene restituito un array JSON contente i domini che hanno le seguenti proprieta':
1. value (univoco)
2. status (disponibilità)
3. monthlyCost 

**Codici di stato restituiti**:

* 200 OK
* 404 Not Found

### POST

**Descrizione**: aggiunge un dominio al database.

**Parametri**: ci deve essere l'header `Content-Type: application/json`.

**Body richiesta**: rappresentazione in formato JSON del dominio con i campi indicati precedentemente.

**Risposta**: in caso di successo il body è vuoto e la risorsa creata è indicata nell'header `Location`.

**Codici di stato restituiti**:

* 201 Created
* 400 Bad Request: c'è un errore del client (JSON, campo mancante o altro).

## `/domains/{domain}`

### GET

**Descrizione**: restituisce l'informazioni di un dominio tramite il suo valore

**Parametri**: un parametro nel percorso `domain` che rappresenta l'identificativo del dominio da restituire.

**Body richiesta**: vuoto.

**Risposta**: In caso di successo la rappresentazione in JSON del dominio.

**Codici di stato restituiti**:

* 200 OK
* 400 Bad Request: c'è un errore del client (ID non valido).
* 404 Not Found: film non trovato.



## `/users`

### GET

**Descrizione**: questo metodo restituisce un array JSON contentente tutti gli utenti presenti nel database

**Parametri**: non previsti

**Body richiesta**: vuota

**Risposta**: viene restituito un array JSON contenente gli utenti che hanno le seguenti proprieta':
1. nome
2. cognome
3. email


**Codici di stato restituiti**:

* 200 OK
* 404 Not Found## `/users`

### POST

**Descrizione**: aggiunge un utente al database.

**Parametri**: ci deve essere l'header `Content-Type: application/json`.

**Body richiesta**: rappresentazione in formato JSON dell'utente con i campi indicati precedentemente.

**Risposta**: viene restituito un array JSON contenente il codice identificativo dell'utente.

**Codici di stato restituiti**:

* 201 Created
* 400 Bad Request: c'è un errore del client (JSON, campo mancante o altro).


## `/users/{id}`

### GET

**Descrizione**: restituisce l'utente con l'id fornito. 

**Parametri**: un parametro nel percorso `id` che rappresenta l'identificativo dell'utente da restituire. 

**Body richiesta**: vuoto.

**Risposta**: In caso di successo la rappresentazione in JSON dell'utente.

**Codici di stato restituiti**:

* 200 OK
* 400 Bad Request: c'è un errore del client (ID non valido).
* 404 Not Found: utente non trovato.

## `/users/{id}/domains`

### GET

**Descrizione**: restituisce lo storico di tutti i domini acquistati dall'utente. 

**Parametri**: un parametro nel percorso `id` che rappresenta l'identificativo dell'utente da restituire. 

**Body richiesta**: vuoto.

**Risposta**: viene restituito un array JSON contenente i domini registrati dall'utente 

**Codici di stato restituiti**:

* 200 OK
* 400 Bad Request: c'è un errore del client (ID non valido).
* 404 Not Found: utente non trovato.

## `/whois/{domain}/{user}/{date}` 

### GET

**Descrizione**: aggiorna la scadenza del dominio.  

**Parametri**: 
un paramentro nel percorso `domain` che rappresenta il dominio da aggiornare, 
un parametro `user` che rappresetna l'utente a cui il dominio appartiene,  
un parametro `date` la nuova data da impostare

**Body richiesta**: vuoto.

**Risposta**: messaggio di conferma nell'aggiornamento

**Codici di stato restituiti**:

* 200 OK
* 400 Bad Request: c'è un errore del client.
* 404 Not Found: utente non trovato.
