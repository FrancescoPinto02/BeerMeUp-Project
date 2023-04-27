DROP SCHEMA IF EXISTS beer_me_up;
CREATE SCHEMA beer_me_up;
USE beer_me_up;



CREATE TABLE site_user(
	id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    pw VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    telephone VARCHAR(15) NOT NULL,
    is_admin BOOLEAN NOT NULL,
    
    PRIMARY KEY(id)
);

CREATE TABLE address(
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    street VARCHAR(255) NOT NULL,
    num VARCHAR(5) NOT NULL,
    cap VARCHAR(5) NOT NULL,
    city VARCHAR(255) NOT NULL,
    nation VARCHAR(255) NOT NULL,
    
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES site_user(id)
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

CREATE TABLE beer(
	id INT NOT NULL AUTO_INCREMENT,
    brewery_id INT NOT NULL,
    style_id INT NOT NULL,
    beer_name VARCHAR(255) NOT NULL,
    beer_description VARCHAR(1000) NOT NULL,
    color VARCHAR (255) NOT NULL,
    ingredients VARCHAR (255) NOT NULL,
    gradation DECIMAL(3,1) NOT NULL CHECK(gradation>=0 AND gradation<100),
    price DECIMAL(9,2) NOT NULL CHECK(price>0),
    iva DECIMAL(3,1) NOT NULL CHECK(iva>=0),
    stock INT NOT NULL CHECK(stock>=0),
    discount INT NOT NULL CHECK(discount>=0 AND discount<100),
    img LONGBLOB NOT NULL,
    
    PRIMARY KEY(id),
    FOREIGN KEY(brewery_id) REFERENCES brewery(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY(style_id) REFERENCES style(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE review(
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    beer_id INT NOT NULL,
    comments VARCHAR(255) NOT NULL,
    score INT NOT NULL CHECK(score>=1 AND score<=5),
    review_date DATE NOT NULL,
    
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES site_user(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY (beer_id) REFERENCES beer(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

CREATE TABLE site_order(
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
    shipping_address VARCHAR(255) NOT NULL,
    billing_address VARCHAR(255) NOT NULL,
    payment_info VARCHAR(255) NOT NULL,
    order_status VARCHAR(255) NOT NULL,
    total DECIMAL(9,2) NOT NULL CHECK(total>0),
    order_date DATE NOT NULL,
    
    PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES site_user(id)
		ON DELETE NO ACTION
        ON UPDATE CASCADE
);

CREATE TABLE order_details(
	order_id INT NOT NULL,
    beer_id INT NOT NULL,
    des VARCHAR(255) NOT NULL,
    qta INT NOT NULL CHECK(qta>0),
    iva DECIMAL(3,1) NOT NULL CHECK(iva>=0),
    price DECIMAL(9,2) NOT NULL CHECK(price>0),
    
    PRIMARY KEY(order_id, beer_id),
    FOREIGN KEY(order_id) REFERENCES site_order(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE,
	FOREIGN KEY(beer_id) REFERENCES beer(id)
		ON DELETE NO ACTION
        ON UPDATE CASCADE
);

GRANT ALL PRIVILEGES ON beer_me_up.* TO 'root'@'localhost';

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

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '1', 'See Ya In The Pit', 'Dal colore ebano e impenetrabile, posta alla luce del sole mostra suggestivi riflessi rubini sotto una sottile nuvola di schiuma color cappuccino.
La scorza d`arancia candita presente nella ricetta emerge all`olfatto in primissima battuta, accompagnata da note di liquirizia gommosa 
e da una chiaro ricordo di caffè espresso con pennellate balsamiche e floreali che richiamano l`anice verde e la violetta.' ,
'Ebano' , 'Orzo', '8' , '4.90', '22', '20', '0', LOAD_FILE('C:/birre/Beer1.PNG'));

INSERT INTO style (style_name, traits)
VALUES ('Pale Ale' , 'Originario della Gran Bretagna, ad oggi è uno dei principali stili birrai al mondo. 
Vede l’impiego di lievito ad alta fermentazione e malto prevalentemente chiaro che determina il colore dorato della birra. 
Differenti tecniche di produzione e luppolature, con una vasta gamma di gusti e gradazioni alcoliche.');

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '2', 'Impact Zone', 'Dal colore dorato chiaro, opalescente, fa sfoggio di una schiuma candida, compatta e fine.
Il bouquet presenta una sinergia tra sentori agrumati (mandarino e arancia tarocco), più calde evocazioni tropicali, 
con il passion fruit in prima fila, e di matura frutta estiva che giungono fino alla pesca sciroppata.
Non manca anche una rinfrescante vena resinosa a precedere l`emersione in superficie degli aromi legati alla base maltata, 
che si esprimono in uno sfondo di panificato chiaro punteggiato da un tocco di crosta di pan brioche.' ,
'Dorato Chiaro' , 'Orzo', '5' , '4.50', '22', '10', '0', LOAD_FILE('C:/birre/Beer2.PNG'));

select * from beer;
select * from brewery;
select * from style;

