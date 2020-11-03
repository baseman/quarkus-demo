
CREATE TABLE debts  (
 id int PRIMARY KEY,
 amount real NOT NULL
 );
CREATE TABLE payment_plans (
 id int PRIMARY KEY,
 debt_id int NOT NULL,
 amount_to_pay real NOT NULL,
 installment_frequency text NOT NULL,
 installment_amount real NOT NULL,
 start_date double precision NULL,
 CONSTRAINT fk_debts FOREIGN KEY(debt_id) REFERENCES debts(id)
 );
CREATE TABLE payments (
 payment_plan_id int PRIMARY KEY,
 amount real NOT NULL,
 date double precision NOT NULL
 );
