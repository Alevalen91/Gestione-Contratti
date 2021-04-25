# Web Application per l'inserimento, la consultazione e l'analisi di dati contrattuali


## Strumenti utilizzati
* Java Servlet per la gestione del back-end e del Crud su Db
* Javascript, HTML e CSS per la gestione del lato client e della visualizzazione dei dati
* MYSql per la gestione del DB.


## Funzionamento

### Inserimento/ Consultazione
L'inserimento e la consultazione dei dati contrattuali è implementata attraverso delle servlet e delle pagine html che utilizzano form e javascript per l'interrogazione del server e la visualizzazione dei risultati (in formato JSON) forniti. Ad ogni "ambito" contrattuale corrisponde una servlet, una pagina html e una tabella MySql dedicata. In questo caso gli "ambiti" contrattuali si riferiscono a tre diversi servizi forniti dai venditori. 

### Gestione degli agenti di vendita
Ogni agente viene inserito in una apposita tabella e recuperato in fase di inserimento/ aggiornamento dei contratti per fornire una lista bloccata di agenti in fase di compilazione dei form. Questo risponde sia all'esigenza di operare in seguito un'analisi corretta del punteggio raggiunto da ogni agente di vendita, sia per garantire la univocità e l'ordine nell'inserimento dei record. È possibile inserire un nuovo agente di vendita o eliminarne uno già presente grazie a degli appositi form. 

### Analisi sui dati dei contratti
La parte di analisi dei dati consiste nell'attribuire un punteggio diverso alle varie tipologie di contratto in base a determinati parametri aziendali. I contratti vengono richiamati in base al quarter annuale selezionato, gli agenti vengono mappati e ad ogni contratto inserito ricevono un punteggio in base alla tipologia di contratto. Verrano mostrati quindi due grafici, a barre ed a torta, per visualizzare il punteggio ottenuto da ogni agente di vendita. Infine, è possibile inserire un KPI complessivo e calcolare la percentuale di raggiungimento dell'azienda in base alla somma dei punteggi ottenuti dai vari agenti di vendita.

### Attivazione dei contratti e note
In fase di inserimento, la voce "Attivazione" è impostata di default su "No".
È possibile attivare i contratti quando il cliente ottiene definitivamente l'erogazione del servizio inserendo semplicemente l'account del cliente attivato e cliccando sul pulsante "attiva". È possibile inoltre inserire ed aggiornare delle note relative ad ogni record attraverso la stessa meccanica. 

### Aggiornamento/eliminazione dei record
È possibile aggiornare un record inserendo l'account esistente e ricompilando i dati corretti. Per l'eliminazione di un record, invece, è possibile utilizzare un apposito form ed inserire solamente l'account da eliminare. 

