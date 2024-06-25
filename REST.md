# Progetto di Sistemi Distribuiti 2023-2024:

---

### Descrizione:
Documentazione delle API RESTfull del progetto Registra Domini, fornite dal server web.
Ogni risorsa (domains, users, whois) fornita dal server ha un path dedicato.

---

### `./domains`

> #### POST `./domains`
> - **Descrizione**: Permette di riservare un dominio per un acquisto in corso da parte di un utente
> - **Parametri**: `-`
> - **Body**:
	`
	{
		"type":"acquisition",
		"domain":"sementi-denditriche.com"
	}
	`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
> - **Response**: Fornisce l'esito della richiesta di acquisizione di un dominio:
	- `{"code":"200", "response":"Domain available"}`
	- `{"code":"200", "response":"Domain unavailable"}`
	- `{"code":"400", "response":"Request format incorrect"}`

> #### POST `./domains`
> - **Descrizione**: Permette di registrare un dominio per un determinato periodo da parte di un utente
> - **Parametri**: `-`
> - **Body**:
	`
	{
		"type":"registration",
		"domain":"sementi-denditriche.com",
		"months":12
	}
	`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
	- 404: Not Found
> - **Response**: Fornisce l'esito della richiesta di registrazione di un dominio:
	- `{"code":"200", "response":"Domain registered"}`
	- `{"code":"400", "response":"Request format incorrect"}`
	- `{"code":"400", "response":"Period in number of months not supported"}`
	- `{"code":"404", "response":"Cannot register a domain that not exist yet"}`

> #### GET `./domains/{domain}`
> - **Descrizione**: Permette di ottenere le informazioni di un dominio
> - **Parametri**: `domain`
> - **Body**: `-`
> - **Response Status Code**:
	- 200: OK
	- 404: Not Found
> - **Response**: Fornisce le informazioni del dominio richiesto:
	- `{"code":"200", "response":{"value":"sementi-denditriche.com", "status":"available", "montlyCost":0.30}}`
	- `{"code":"404", "response":"Domain not found"}`

> #### POST `./domains/{domain}`
> - **Descrizione**: Permette di estendere il periodo di registrazione di un dominio
> - **Parametri**: `domain`
> - **Body**:
	`
	{
		"months":12
	}
	`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
	- 404: Not Found
> - **Response**: Fornisce l'esito dell'operazione di rinnovo del dominio:
	- `{"code":"200", "response":"Domain renewed"}`
	- `{"code":"400", "response":"Request format incorrect"}`
	- `{"code":"400", "response":"Period in number of months not supported"}`
	- `{"code":"404", "response":"Domain not found"}`

<br>

### `./users`

> #### GET `./users`
> - **Descrizione**: Permette di effettuare l'accesso ad un account precedentemente registrato
> - **Parametri**: `-`
> - **Body**:
	`
	{
		"email":"sementi@denditri.che",
		"password":"sementi-denditriche"
	}
	`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
	- 409: Conflict
> - **Response**: Fornisce l'esito della richiesta di accesso all'account utente:
	- `{"code":"200", "response":{"id":"30", "name":"sementi", "surname":"denditriche", "email":"sementi@denditri.che"}}`
	- `{"code":"400", "response":"Request format incorrect"}`
	- `{"code":"409", "response":"Email or password may be incorrect"}`

> #### POST `./users`
> - **Descrizione**: Permette di registrare un nuovo account utente
> - **Parametri**: `-`
> - **Body**:
	`
	{
		"name":"sementi",
		"surname":"denditriche",
		"email":"sementi@denditri.che",
		"password":"sementi-denditriche"
	}
	`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
	- 404: Not Found
> - **Response**: Fornisce la motivazione dell'esito sul dominio richiesto:
	- `{"code":"200", "response":"Signup succeded"}`
	- `{"code":"400", "response":"Request format incorrect"}`
	- `{"code":"409", "response":"Email already registered"}`

> #### GET `./users/{id}/domains`
> - **Descrizione**: Permette di ottenere la lista dei domini registrati (attivi e scaduti non ancora registrati)
> - **Parametri**: `id`
> - **Body**: `-`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
> - **Response**: Fornisce la motivazione dell'esito sul dominio richiesto:
	- `{"code":"200", "response":[{"value":"sementi-denditriche.com", "status":"expired", "montlyCost":0.30}]}`
	- `{"code":"400", "response":"Request format incorrect"}`

<br>

### `./whois`

> #### GET `./whois`
> - **Descrizione**: Permette di ottenere la lista di operazioni svolte nel sistema
> - **Parametri**: `-`
> - **Body**:
	`
	{
		"userId":"30",
		"domain":"",
		"data":""
	}
	`
> - **Response Status Code**:
	- 200: OK
	- 400: Bad Request
> - **Response**: Fornisce la motivazione dell'esito sul dominio richiesto:
	- `{"code":"200", "response":[{"domain":"sementi-denditriche.com", "type":"registration", "data":"2024-06-24"}]}`
	- `{"code":"400", "response":"Request format incorrect"}`