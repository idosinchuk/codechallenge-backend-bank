CREATE TABLE IF NOT EXISTS "account" (
  "id" TEXT NOT NULL,
  "balance" NUMERIC NOT NULL,
  CONSTRAINT account_pkey PRIMARY KEY (id)
  );

CREATE TABLE IF NOT EXISTS "transaction" (
  "id" TEXT NOT NULL,
  "account_id" TEXT,
  "date" TEXT NOT NULL,
  "amount" NUMERIC NOT NULL,
  "fee" NUMERIC NOT NULL,
  "description" TEXT NULL,
  CONSTRAINT transaction_pkey PRIMARY KEY (id),
  CONSTRAINT transaction_account_fkey FOREIGN KEY (account_id) REFERENCES account (id)
  );

--
-- Example values to test in tables
--

INSERT INTO account VALUES
('ES6004871262385886192518', 2345.60),
('ES7731906327109246678897', 3467.50);

