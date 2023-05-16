DROP SCHEMA IF EXISTS beer_me_up;
CREATE SCHEMA beer_me_up;
USE beer_me_up;



CREATE TABLE site_user(
	id INT NOT NULL AUTO_INCREMENT,
    email VARCHAR(255) NOT NULL,
    pw VARCHAR(255) NOT NULL,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
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
    telephone VARCHAR(15) NOT NULL,
    
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
VALUES ('Antikorpo', 'Antikorpo Brewing è un giovane birrificio italiano “nato nel bel mezzo di una pandemia”, come lo descrive il birraio stesso, Davide Galliussi che, con Cristina Mirizzi, ha creato il progetto nei primi mesi del 2020, da una costola dello storico birrificio Cittavecchia di Sgonico (TS). In pochi anni il birrificio è cresciuto alla velocità della luce, producendo birre che ti invogliano ad un secondo sorso, dagli stili molto apprezzati e in voga, tra i quali ritroviamo per esempio Pacific Pale Ale, West Coast Double IPA, ma anche basse fermentazioni davvero ben riuscite. Oltre ad una particolare attenzione verso il mondo gluten free che, diciamocelo, gli riesce molto bene! Birre dal forte carattere, indimenticabili, in lattina, dalla veste grafica fortemente riconoscibile, grazie anche all`eccentrica linea di grafiche realizzate da tatuatori di fama internazionale.' , 'Italia' );

INSERT INTO brewery (brewery_name, story, nation)
VALUES ('Impavida', 'Impavida non è soltanto il nome del birrificio artigianale, ma è anche l’aggettivo che descrive meglio il carattere e la personalità delle due donne che gli hanno dato vita in un luogo speciale tra le montagne del nord Italia: Arco di Trento. In uno spazio industriale, recuperato grazie ad un progetto firmato dallo studio milanese Genuizzi Banal, è nato un punto di ritrovo, una destinazione, una meta per tutti gli appassionati della buona birra artigianale ma anche un luogo sincero e informale dove passare il tempo con gli amici davanti al bancone della Tap Room.' , 'Italia' );

INSERT INTO style (style_name, traits)
VALUES ('Stout' , 'Delicate, con un corpo medio ed una carbonatazione moderata, evidenziano note di cioccolato e caffè, con toni secondari di cacao o cereale torrefatto. La cremosità e il tenue fruttato/luppolato donano equilibrio a queste birre.');

INSERT INTO style (style_name, traits)
VALUES ('Pale Ale' , 'Originario della Gran Bretagna, ad oggi è uno dei principali stili birrai al mondo. Vede l’impiego di lievito ad alta fermentazione e malto prevalentemente chiaro che determina il colore dorato della birra. Differenti tecniche di produzione e luppolature, con una vasta gamma di gusti e gradazioni alcoliche.');

INSERT INTO style (style_name, traits)
VALUES ('Pilsner' , 'La birra Pils, anche nota come Pilsner, è stata creata per la prima volta nella città di Plzen, nella regione della Boemia, nell`allora Impero austro-ungarico (oggi Repubblica Ceca). La birra Pils fu originariamente prodotta nel 1842 dalla birreria Pilsner Urquell, come risposta alla crescente domanda di birra leggera e dorata.');

INSERT INTO style (style_name, traits)
VALUES ('India Pale Ale' , 'La birra IPA, acronimo di India Pale Ale , è uno stile birraio della famiglia delle ALE. Con questo termine si indicano le birre ad alta fermentazione, preparata a temperature tra i 15° e i 25° circa. La birra IPA ha un colore giallo ambrato e una schiuma compatta mediamente persistente. Il gusto si caratterizza per la buona corposità, amara e con ridotta effervescenza. Emergono note di cacao ed un aroma erbaceo. Equilibrata e pungente.');

INSERT INTO style (style_name, traits)
VALUES ('American Pale Ale' , 'Le American Pale Ale si presentano fresche, chiare e luppolate, con un’adeguata quantità di malto che le rende equilibrate e bevibili. La forte presenza del luppolo conferisce alle varietà americane un ampio spettro di caratteristiche gustative. Sono birre di media gradazione, tendenti al luppolato ma più accessibili rispetto alle IPA americane moderne. L’aroma generalmente include note fruttate, floreali e speziate, il luppolo caratteristico si percepisce al naso. Malto: sostiene la decisa luppolatura e può mostrare note speciali (pane, biscotto o caramello). Il dry-hopping (se presente) generalmente non domina il gusto e può aggiungere note erbacee.');

INSERT INTO style (style_name, traits)
VALUES ('Lager' , 'Le Lager sono birre a bassa fermentazione, prodotte utilizzando lieviti del ceppo Saccharomyces carlsbergensis, scoperto in Danimarca da parte del produttore di birra Carlsberg (da cui il nome) e del ceppo Saccharomices pastorianus isolato dal tedesco Max Rees nel 1870. Questi lieviti hanno la caratteristica di essere attivi a partire da temperature più basse rispetto ai Saccharomyces cerevisiae, ossia intorno a 10 °C. Inoltre durante la fermentazione i lieviti si depositano sul fondo del tino, da cui il termine bassa fermentazione (“bottom fermentation“) all’opposto delle birre ad alta fermentazione i cui lieviti durante il processo risalgono sulla superficie del tino (“top fermentation“). Alla famiglia delle birre Lager appartengono molti stili di birra, come ad esempio le Pils sia Ceche che Tedesche, le Märzen e molte altre.');

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '1', 'See Ya In The Pit', 'Dal colore ebano e impenetrabile, posta alla luce del sole mostra suggestivi riflessi rubini sotto una sottile nuvola di schiuma color cappuccino. La scorza d`arancia candita presente nella ricetta emerge all`olfatto in primissima battuta, accompagnata da note di liquirizia gommosa e da una chiaro ricordo di caffè espresso con pennellate balsamiche e floreali che richiamano l`anice verde e la violetta.' ,'Rossa' , 'Orzo', '8' , '4.90', '22', '20', '10', LOAD_FILE('C:/birre/Beer1.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '2', 'Impact Zone', 'Dal colore dorato chiaro, opalescente, fa sfoggio di una schiuma candida, compatta e fine. Il bouquet presenta una sinergia tra sentori agrumati (mandarino e arancia tarocco), più calde evocazioni tropicali, con il passion fruit in prima fila, e di matura frutta estiva che giungono fino alla pesca sciroppata. Non manca anche una rinfrescante vena resinosa a precedere l`emersione in superficie degli aromi legati alla base maltata, che si esprimono in uno sfondo di panificato chiaro punteggiato da un tocco di crosta di pan brioche.' , 'Bionda' , 'Orzo', '5' , '4.50', '22', '10', '0', LOAD_FILE('C:/birre/Beer2.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '3', 'Home Spot', 'One Shot del birrificio Antikorpo, Home Spot è una Bohemian Pils abbondantemente luppolata con Saaz e prodotta con malto 100% pils coltivato nelle pianure friulane e maltato in Austria. Fresca e leggera, l`idea dietro a questa birra è il ritorno al gusto semplice e schietto. Piacevolmente amara nel finale.' , 'Bionda' , 'Orzo', '4.7' , '4.20', '22', '15', '0', LOAD_FILE('C:/birre/Beer3.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '2', 'In The Flesh', 'La Antikorpo In The Flesh è una DDH Ipa, di colore biondo chiaro e di media gradazione. La sua luppolatura è intensa e molto particolare, in quanto i luppoli utilizzati sono un abbinamento esplosivo di sapori Ekuanot, Sabro, Idaho7, Styrian Wold e Mosaic.' ,'Bionda' , 'Orzo', '6.5' , '4.20', '22', '20', '20', LOAD_FILE('C:/birre/Beer4.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '4', 'Lowrider', 'La Antikorpo Lowrider è una birra in stile Ipa completamente gluten free. I luppoli regalano a questa birra profumi e sapori resinosi e balsamici, lasciando però anche spazio alle calde note tropicali tipiche dello stile: guava e maracuja la fanno da padrona. È una birra dissetante, gustosa e con una buona gradazione. Il suo finale si contraddistingue per un amaro deciso che ricorda il  pompelmo.' ,'Bionda' , 'Orzo', '5.8' , '4.70', '22', '25', '0', LOAD_FILE('C:/birre/Beer5.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '5', 'Moxie', 'La sua ricetta si ispira ai classici della Craft Beer Revolution. Presenta una luppolatura che unisce le moderne Apa e le pale ale old school con le caratteristiche note agrumate e resinose. Dal tipico colore ambrato, in bocca si distingue per la scorrevolezza e l’equilibrio tra una nota di caramello e l’apporto dei luppoli. Moxie significa Grinta, la quintessenza di Impavida: da bere sempre proprio come una buona APA dovrebbe essere.' , 'Rossa' , 'Orzo, Frumento', '5.5' , '4.60', '22', '25', '0', LOAD_FILE('C:/birre/Beer6.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '4', 'Chain Breaker', 'È la nostra India Pale Ale: una birra caratterizzata da un blend di luppoli americani classici e moderni, che uniscono le note agrumate e resinose con la parte tropicale delle IPA di ultima generazione. Il tutto innestato in una base di malto pale che le conferisce un corpo adeguato a supportarne la luppolatura. Non farti ingannare dal suo colore dorato, la Chain Breaker è una vera rivoluzione. È nata per rompere gli schemi, fallo anche tu!' , 'Bionda' , 'Orzo', '6.1' , '5.50', '22', '20', '10', LOAD_FILE('C:/birre/Beer7.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '4', 'Free Solo', 'Una Session IPA con una luppolatura di nuova generazione, gentile ma decisa, che presenta delle note tropicali su una base di malto caratterizzata dall’uso di frumento, fiocchi d’orzo e malto d’avena. Un sorprendente mix di cereali e luppoli che lasciano in bocca il sapore della libertà, dei viaggi e delle grandi imprese, di quelle che si celebrano al pub.' , 'Bionda' , 'Orzo, Frumento, Avena', '3.5' , '6.00', '22', '30', '20', LOAD_FILE('C:/birre/Beer8.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '3', 'Peler', 'La nostra Italian Pilsner punta sull’equilibrio tra la parte maltata, le tipiche note di crosta di pane e miele, e una luppolatura fatta di sole varietà mitteleuropee. Come il vento del Garda dal quale prende il nome, le sue note floreali ed erbacee enfatizzate da un leggero dry hopping, ti trasporteranno in una nuova esperienza da bere. Tu spiega le vele, il resto lo farà lei!.' , 'Bionda' , 'Orzo', '4.8' , '4.50', '22', '20', '0', LOAD_FILE('C:/birre/Beer9.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '6', 'Vivienne', 'Ne evoca il carattere, lo stile ed anche il nome. Il suo colore ambrato carico di note rossastre racchiude un bouquet di profumi che vanno dal caramello alla frutta secca, passando per un’invitante fragranza di biscotti. Un omaggio all’impavida regina della moda punk inglese.' , 'Scura' , 'Orzo', '4.8' , '4.50', '22', '20', '10', LOAD_FILE('C:/birre/Beer10.PNG'));



INSERT INTO site_user (email, pw, first_name, last_name, is_admin) 
VALUES ('beermeup@gmail.com' , '1234', 'admin', 'admin', TRUE);
    
INSERT INTO address (user_id, street, num, cap, city, nation, telephone)
VALUES ('1' , 'Via Indirizzo Admin' , '1', '81081' , 'Roma' , 'Italia', '1234567890');