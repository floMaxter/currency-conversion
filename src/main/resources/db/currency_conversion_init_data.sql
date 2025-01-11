insert into t_currencies (c_code, c_full_name, c_sign)
values ('USD', 'US Dollar', '$'),
       ('RUB', 'Russian Ruble', '₽');

insert into t_exchange_rates (c_base_currency_id, c_target_currency_id, c_rate)
values (2, 1, 0.0098);