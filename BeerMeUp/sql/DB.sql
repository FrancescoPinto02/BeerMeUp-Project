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
        ON UPDATE CASCADE
);

CREATE TABLE payment_method(
	id INT NOT NULL AUTO_INCREMENT,
    user_id INT NOT NULL,
	card_owner VARCHAR(255) NOT NULL,
	card_number VARCHAR(20)NOT NULL ,
	cvv VARCHAR(3) NOT NULL CHECK(cvv>0),
	expiration_date DATE NOT NULL,
    
	PRIMARY KEY(id),
    FOREIGN KEY(user_id) REFERENCES site_user(id)
		ON DELETE CASCADE
        ON UPDATE CASCADE
);

GRANT ALL PRIVILEGES ON beer_me_up.* TO 'root'@'localhost';

INSERT INTO brewery (brewery_name, story, nation)
VALUES ('Antikorpo', 'Antikorpo Brewing è la scheggia impazzita del birrificio Cittavecchia di Trieste. Il genio alla guida è Davide Galliussi, fenomeno locale della tavola da surf: ed ecco che le nostre prime lattine sanno di mare, di onde e di surf. Come se non bastasse c’è poi Cristina a rendere le birre indimenticabili con una eccentrica linea di grafiche realizzate da tatuatori di fama internazionale.' , 'Italia' );

INSERT INTO brewery (brewery_name, story, nation)
VALUES ('Impavida', 'Impavida non a soltanto il nome del birrificio artigianale, ma a anche l`aggettivo che descrive meglio il carattere e la personalita delle due donne che gli hanno dato vita in un luogo speciale tra le montagne del nord Italia: Arco di Trento. In uno spazio industriale, recuperato grazie ad un progetto firmato dallo studio milanese Genuizzi Banal, a nato un punto di ritrovo, una destinazione, una meta per tutti gli appassionati della buona birra artigianale ma anche un luogo sincero e informale dove passare il tempo con gli amici davanti al bancone della Tap Room.' , 'Italia' );

INSERT INTO style (style_name, traits)
VALUES ('Stout' , 'Delicate, con un corpo medio ed una carbonatazione moderata, evidenziano note di cioccolato e caffe, con toni secondari di cacao o cereale torrefatto. La cremosita  e il tenue fruttato/luppolato donano equilibrio a queste birre.');

INSERT INTO style (style_name, traits)
VALUES ('Pale Ale' , 'Originario della Gran Bretagna, ad oggi a uno dei principali stili birrai al mondo. Vede l''impiego di lievito ad alta fermentazione e malto prevalentemente chiaro che determina il colore dorato della birra. Differenti tecniche di produzione e luppolature, con una vasta gamma di gusti e gradazioni alcoliche.');

INSERT INTO style (style_name, traits)
VALUES ('Pilsner' , 'La birra Pils, anche nota come Pilsner, a stata creata per la prima volta nella citta  di Plzen, nella regione della Boemia, nell`allora Impero austro-ungarico (oggi Repubblica Ceca). La birra Pils fu originariamente prodotta nel 1842 dalla birreria Pilsner Urquell, come risposta alla crescente domanda di birra leggera e dorata.');

INSERT INTO style (style_name, traits)
VALUES ('India Pale Ale' , 'La birra IPA, acronimo di India Pale Ale , a uno stile birraio della famiglia delle ALE. Con questo termine si indicano le birre ad alta fermentazione, preparata a temperature tra i 15° e i 25° circa. La birra IPA ha un colore giallo ambrato e una schiuma compatta mediamente persistente. Il gusto si caratterizza per la buona corposita , amara e con ridotta effervescenza. Emergono note di cacao ed un aroma erbaceo. Equilibrata e pungente.');

INSERT INTO style (style_name, traits)
VALUES ('American Pale Ale' , 'L`American IPA è caratterizzata da un sapore e un aroma intenso di luppolo, che può variare dalle note di agrumi, frutta tropicale, resina, fiori e pino. La maggior parte delle American IPA ha un contenuto di alcol tra il 6% e l 8%, ma ci sono anche versioni più forti.');

INSERT INTO style (style_name, traits)
VALUES ('Lager' , 'È una birra leggera con un volume alcolico intorno ai 4% e 5.5% e regala aromi di malto, cereali, miele e pane. Ha un colore chiaro, limpido e trasparente ed una schiuma bianca, con bollicine che salgono verso la superficie');

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '1', 'See Ya In The Pit', 'Dal colore ebano e impenetrabile, posta alla luce del sole mostra suggestivi riflessi rubini sotto una sottile nuvola di schiuma color cappuccino. La scorza d`arancia candita presente nella ricetta emerge all`olfatto in primissima battuta, accompagnata da note di liquirizia gommosa e da una chiaro ricordo di caffÃ¨ espresso con pennellate balsamiche e floreali che richiamano l`anice verde e la violetta.' ,'Rossa' , 'Orzo', '8' , '4.90', '22', '20', '10', LOAD_FILE('C:/birre/Beer1.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '2', 'Impact Zone', 'Dal colore dorato chiaro, opalescente, fa sfoggio di una schiuma candida, compatta e fine. Il bouquet presenta una sinergia tra sentori agrumati (mandarino e arancia tarocco), piÃ¹ calde evocazioni tropicali, con il passion fruit in prima fila, e di matura frutta estiva che giungono fino alla pesca sciroppata. Non manca anche una rinfrescante vena resinosa a precedere l`emersione in superficie degli aromi legati alla base maltata, che si esprimono in uno sfondo di panificato chiaro punteggiato da un tocco di crosta di pan brioche.' , 'Bionda' , 'Orzo', '5' , '4.50', '22', '10', '0', LOAD_FILE('C:/birre/Beer2.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '3', 'Home Spot', 'One Shot del birrificio Antikorpo, Home Spot Ã¨ una Bohemian Pils abbondantemente luppolata con Saaz e prodotta con malto 100% pils coltivato nelle pianure friulane e maltato in Austria. Fresca e leggera, l`idea dietro a questa birra Ã¨ il ritorno al gusto semplice e schietto. Piacevolmente amara nel finale.' , 'Bionda' , 'Orzo', '4.7' , '4.20', '22', '15', '0', LOAD_FILE('C:/birre/Beer3.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '2', 'In The Flesh', 'La Antikorpo In The Flesh Ã¨ una DDH Ipa, di colore biondo chiaro e di media gradazione. La sua luppolatura Ã¨ intensa e molto particolare, in quanto i luppoli utilizzati sono un abbinamento esplosivo di sapori Ekuanot, Sabro, Idaho7, Styrian Wold e Mosaic.' ,'Bionda' , 'Orzo', '6.5' , '4.20', '22', '20', '20', LOAD_FILE('C:/birre/Beer4.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('1', '4', 'Lowrider', 'La Antikorpo Lowrider e una birra in stile Ipa completamente gluten free. I luppoli regalano a questa birra profumi e sapori resinosi e balsamici, lasciando anche spazio alle calde note tropicali tipiche dello stile: guava e maracuja la fanno da padrona. È una birra dissetante, gustosa e con una buona gradazione. Il suo finale si contraddistingue per un amaro deciso che ricorda il  pompelmo.' ,'Bionda' , 'Orzo', '5.8' , '4.70', '22', '25', '0', LOAD_FILE('C:/birre/Beer5.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '5', 'Moxie', 'Prodotta da Birra Impavida di Arco (TN), Moxie è una birra dal tenore alcolico medio. Trae ispirazione dalla categoria delle American Pale Ale, stile birrario proprio della cultura brassicola degli USA, considerabile come un evoluzione delle English Pale Ale adattata alle materie prime disponibili in patria e al gusto locale. Nel profilo aromatico si ritrovano note di agrumi, resina e frutta tropicale e successivamente tendenze riconducibili a biscotto, fiori, spezie, erba tagliata e crosta di pane.' , 'Rossa' , 'Orzo, Frumento', '5.5' , '4.60', '22', '25', '0', LOAD_FILE('C:/birre/Beer6.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '4', 'Chain Breaker', 'La nostra India Pale Ale: una birra caratterizzata da un blend di luppoli americani classici e moderni, che uniscono le note agrumate e resinose con la parte tropicale delle IPA di ultima generazione. Il tutto innestato in una base di malto pale che le conferisce un corpo adeguato a supportarne la luppolatura. Non farti ingannare dal suo colore dorato, la Chain Breaker e una vera rivoluzione. Nata per rompere gli schemi, fallo anche tu!' , 'Bionda' , 'Orzo', '6.1' , '5.50', '22', '20', '10', LOAD_FILE('C:/birre/Beer7.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '4', 'Free Solo', 'Prodotta a Arco (TN) dall azienda Birra Impavida, Free Solo è una birra con una gradazione alcolica pari al 3,5%. Si ispira alla tipologia delle Session IPA, versione meno alcolica delle American IPA, in cui la forza amaricante e aromatica dei luppoli (solitamente di origine americana) si unisce a una decisa bevibilità e un corpo alquanto scorrevole. Nel profilo aromatico si ritrovano note di frutta tropicale, agrumi e resina e a un analisi più approfondita pennellate riconducibili a fiori, melone e spezie.' , 'Bionda' , 'Orzo, Frumento, Avena', '3.5' , '6.00', '22', '30', '20', LOAD_FILE('C:/birre/Beer8.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '3', 'Peler', 'Pelèr è una produzione di Birra Impavida, marchio attivo dal 2020 e operante a Arco (TN). Prende a modello la categoria delle Pilsner, uno degli stili più destabilizzanti di sempre, nato a metà del 1800 nell attuale Repubblica Ceca e identificato da basse fermentazioni eleganti, bilanciate e facili da bere. Nel profilo aromatico si ritrovano note di miele, fiori e crosta di pane e successivamente tendenze riconducibili a spezie ed erba tagliata.' , 'Bionda' , 'Orzo', '4.8' , '4.50', '22', '20', '0', LOAD_FILE('C:/birre/Beer9.PNG'));

INSERT INTO beer (brewery_id , style_id, beer_name, beer_description, color, ingredients, gradation, price, iva, stock, discount, img)
VALUES ('2', '6', 'Vivienne', 'Prodotta da Birra Impavida di Arco (TN), Vivienne è una birra dal tenore alcolico medio. Prende a modello la categoria delle Vienna, antico stile creato per la prima volta nel 1841 da Anton Dreher e legato alla città di Vienna, costituito da birre ambrate tendenzialmente maltate, relativamente secche e mediamente alcoliche. Nel profilo aromatico si ritrovano note di miele di castagno, cereali e crosta di pane e a un analisi più approfondita tendenze assimilabili a caramello, fiori e tostato.' , 'Scura' , 'Orzo', '4.8' , '4.50', '22', '20', '10', LOAD_FILE('C:/birre/Beer10.PNG'));



INSERT INTO site_user (email, pw, first_name, last_name, is_admin) 
VALUES ('beermeup@gmail.com' , '1234', 'admin', 'admin', TRUE);
    
INSERT INTO address (user_id, street, num, cap, city, nation, telephone)
VALUES ('1' , 'Via Indirizzo Admin' , '1', '81081' , 'Roma' , 'Italia', '1234567890');