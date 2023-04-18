DROP SCHEMA IF EXISTS beer_me_up;
CREATE SCHEMA beer_me_up;
USE beer_me_up;

CREATE TABLE utente(
	id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    pw VARCHAR(255) NOT NULL,
    username VARCHAR(255) NOT NULL,
    is_admin BOOLEAN NOT NULL,
    
    PRIMARY KEY(id)
);

CREATE TABLE indirizzo(
	id INT NOT NULL AUTO_INCREMENT,
    utente_id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    cognome VARCHAR(255) NOT NULL,
    telefono VARCHAR(15) NOT NULL,
    via VARCHAR(255) NOT NULL,
    civico VARCHAR(5) NOT NULL,
    cap VARCHAR(5) NOT NULL,
    citta VARCHAR(255) NOT NULL,
    nazione VARCHAR(255) NOT NULL,
    
    PRIMARY KEY(id),
    FOREIGN KEY(utente_id) REFERENCES utente(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE brewery(
	id INT NOT NULL AUTO_INCREMENT,
	brewery_name VARCHAR(255) NOT NULL,
    story VARCHAR(1000) NOT NULL,
    nation VARCHAR(255) NOT NULL,
    
    PRIMARY KEY(id)
);

CREATE TABLE style(
	id INT NOT NULL AUTO_INCREMENT,
    style_name VARCHAR(255) NOT NULL,
    traits VARCHAR(1000) NOT NULL,
    
    PRIMARY KEY(id)
);

CREATE TABLE birra(
	id INT NOT NULL AUTO_INCREMENT,
    produttore_id INT NOT NULL,
    stile_id INT NOT NULL,
    nome VARCHAR(255) NOT NULL,
    descrizione VARCHAR(1000) NOT NULL,
    colore VARCHAR (255) NOT NULL,
    ingredienti VARCHAR (255) NOT NULL,
    gradazione DECIMAL(3,1) NOT NULL CHECK(gradazione>=0),
    prezzo DECIMAL(9,2) NOT NULL CHECK(prezzo>0),
    iva DECIMAL(3,1) NOT NULL CHECK(iva>=0),
    stock INT NOT NULL CHECK(stock>=0),
    sconto INT NOT NULL CHECK(sconto>=0 AND sconto<100),
    
    PRIMARY KEY(id),
    FOREIGN KEY(produttore_id) REFERENCES brewery(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY(stile_id) REFERENCES style(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE recensione(
	id INT NOT NULL AUTO_INCREMENT,
    utente_id INT NOT NULL,
    birra_id INT NOT NULL,
    commenti VARCHAR(255) NOT NULL,
    punteggio INT NOT NULL CHECK(punteggio>=1 AND punteggio<=5),
    data_recensione DATE NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (utente_id) REFERENCES utente(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY (birra_id) REFERENCES birra(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE ordine(
	id INT NOT NULL AUTO_INCREMENT,
    utente_id INT NOT NULL,
    indirizzo_spedizione VARCHAR(255) NOT NULL,
    indirizzo_fatturazione VARCHAR(255) NOT NULL,
    info_pagamento VARCHAR(255) NOT NULL,
    stato VARCHAR(255) NOT NULL,
    totale DECIMAL(9,2) NOT NULL CHECK(totale>0),
    data_ordine DATE NOT NULL,
    
    PRIMARY KEY(id),
    FOREIGN KEY(utente_id) REFERENCES utente(id)
		ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE dettagli_ordine(
	ordine_id INT NOT NULL,
    birra_id INT NOT NULL,
    descrizione VARCHAR(255) NOT NULL,
    qta INT NOT NULL CHECK(qta>0),
    iva DECIMAL(3,1) NOT NULL CHECK(iva>=0),
    prezzo DECIMAL(9,2) NOT NULL CHECK(prezzo>0),
    
    PRIMARY KEY(ordine_id, birra_id),
    FOREIGN KEY(ordine_id) REFERENCES ordine(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY(birra_id) REFERENCES birra(id)
		ON DELETE NO ACTION
        ON UPDATE CASCADE
);

INSERT INTO brewery (brewery_name, story, nation)
VALUES ('Antikorpo', 'Antikorpo Brewing è un giovane birrificio italiano “nato nel bel mezzo di una pandemia”, come lo descrive il birraio stesso, Davide Galliussi che, 
con Cristina Mirizzi, ha creato il progetto nei primi mesi del 2020, da una costola dello storico birrificio Cittavecchia di Sgonico (TS).
In pochi anni il birrificio è cresciuto alla velocità della luce, producendo birre che ti invogliano ad un secondo sorso, dagli stili molto apprezzati e in voga, 
tra i quali ritroviamo per esempio Pacific Pale Ale, West Coast Double IPA, ma anche basse fermentazioni davvero ben riuscite. 
Oltre ad una particolare attenzione verso il mondo gluten free che, diciamocelo, gli riesce molto bene!
Birre dal forte carattere, indimenticabili, in lattina, dalla veste grafica fortemente riconoscibile, 
grazie anche all`eccentrica linea di grafiche realizzate da tatuatori di fama internazionale.' , 'Italia' );

INSERT INTO style (style_name, traits)
VALUES ('Stout' , 'Delicate, con un corpo medio ed una carbonatazione moderata, evidenziano note di cioccolato e caffè, 
con toni secondari di cacao o cereale torrefatto. 
La cremosità e il tenue fruttato/luppolato donano equilibrio a queste birre.');

INSERT INTO birra (produttore_id , stile_id, nome, descrizione, colore, ingredienti, gradazione, prezzo, iva, stock, sconto)
VALUES ('1', '1', 'See Ya In The Pit', 'Dal colore ebano e impenetrabile, posta alla luce del sole mostra suggestivi riflessi rubini sotto una sottile nuvola di schiuma color cappuccino.
La scorza d`arancia candita presente nella ricetta emerge all`olfatto in primissima battuta, accompagnata da note di liquirizia gommosa 
e da una chiaro ricordo di caffè espresso con pennellate balsamiche e floreali che richiamano l`anice verde e la violetta.' ,
'Ebano' , 'Orzo', '8' , '4.90', '22', '20', '0' );

INSERT INTO style (style_name, traits)
VALUES ('Pale Ale' , 'Originario della Gran Bretagna, ad oggi è uno dei principali stili birrai al mondo. 
Vede l’impiego di lievito ad alta fermentazione e malto prevalentemente chiaro che determina il colore dorato della birra. 
Differenti tecniche di produzione e luppolature, con una vasta gamma di gusti e gradazioni alcoliche.');

INSERT INTO birra (produttore_id , stile_id, nome, descrizione, colore, ingredienti, gradazione, prezzo, iva, stock, sconto)
VALUES ('1', '2', 'Impact Zone', 'Dal colore dorato chiaro, opalescente, fa sfoggio di una schiuma candida, compatta e fine.
Il bouquet presenta una sinergia tra sentori agrumati (mandarino e arancia tarocco), più calde evocazioni tropicali, 
con il passion fruit in prima fila, e di matura frutta estiva che giungono fino alla pesca sciroppata.
Non manca anche una rinfrescante vena resinosa a precedere l`emersione in superficie degli aromi legati alla base maltata, 
che si esprimono in uno sfondo di panificato chiaro punteggiato da un tocco di crosta di pan brioche.' ,
'Dorato Chiaro' , 'Orzo', '5' , '4.50', '22', '10', '0' );

select * from birra;
select * from brewery;
select * from style;

