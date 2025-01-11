CREATE TABLE IF NOT EXISTS t_currencies
(
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    c_code      VARCHAR(3),
    c_full_name VARCHAR(50),
    c_sign      VARCHAR(3)
);

CREATE UNIQUE INDEX IF NOT EXISTS unique_code_idx ON t_currencies (c_code);

CREATE TABLE IF NOT EXISTS t_exchange_rates
(
    id                   INTEGER PRIMARY KEY AUTOINCREMENT,
    c_base_currency_id   INTEGER,
    c_target_currency_id INTEGER,
    c_rate               REAL,
    FOREIGN KEY (c_base_currency_id) REFERENCES t_currencies (id),
    FOREIGN KEY (c_target_currency_id) REFERENCES t_currencies (id)
);

CREATE UNIQUE INDEX IF NOT EXISTS unique_base_currency_id_target_currency_id_idx
    ON t_exchange_rates (c_base_currency_id, c_target_currency_id);