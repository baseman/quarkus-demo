INSERT INTO "debts"
SELECT
  i,
  i
FROM generate_series(1, 5) as t(i);

INSERT INTO "payment_plans"
SELECT
  i,
  i,
  i,
  i::text,
  i,
  i
FROM generate_series(1, 2) as t(i);

INSERT INTO "payments"
SELECT
  i,
  i,
  i
FROM generate_series(1, 2) as t(i);