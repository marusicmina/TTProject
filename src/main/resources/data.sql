-- Unos podataka u tabelu STOCK_ORDER
/*INSERT INTO STOCK_ORDER (type, price, amount) VALUES ('buy', 1000.0, 10);
INSERT INTO STOCK_ORDER (type, price, amount) VALUES ('sell', 1500.0, 5);
INSERT INTO STOCK_ORDER (type, price, amount) VALUES ('buy', 1200.0, 20);
INSERT INTO STOCK_ORDER (type, price, amount) VALUES ('sell', 1300.0, 15);
*/
/*
-- Ubacivanje podataka u tabelu stock_order pri pokretanju aplikacije
INSERT INTO stock_order (type, price, amount) VALUES ('buy', 1000.0, 10);
INSERT INTO stock_order (type, price, amount) VALUES ('sell', 1500.0, 5);
INSERT INTO stock_order (type, price, amount) VALUES ('buy', 1200.0, 20);
INSERT INTO stock_order (type, price, amount) VALUES ('sell', 1300.0, 15);
*/
INSERT INTO trade_order (order_type, price, amount, created_at)
VALUES ('BUY', 100.50, 10, CURRENT_TIMESTAMP);
 INSERT INTO trade_order (order_type, price, amount, created_at)
VALUES ('BUY', 102.75, 15, CURRENT_TIMESTAMP);
  INSERT INTO trade_order (order_type, price, amount, created_at)
VALUES ('SELL', 98.30, 5, CURRENT_TIMESTAMP);
  INSERT INTO trade_order (order_type, price, amount, created_at)
VALUES ('SELL', 99.99, 20, CURRENT_TIMESTAMP);
    