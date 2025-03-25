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
/*
DO $$ 
BEGIN
  -- Provera da li tabela trade_order ima podatke
  IF NOT EXISTS (SELECT 1 FROM trade_order) THEN
    INSERT INTO trade_order (order_type, price, amount, created_at) 
    VALUES 
      ('BUY', 100.50, 10, CURRENT_TIMESTAMP);
  END IF;

END $$;*/