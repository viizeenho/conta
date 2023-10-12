insert into Conta (tipo_conta, numero_conta, data_abertura_conta, saldo_dolar, saldo_real, status)
values (
  'Conta-Corrente',
  '1',
  '2023-09-30',
  90000000.00,
  100000000.00,
  'Ativa'
);

insert into Titular_Conta (id, cpf_cnpj, nome, conta_id)
values (1, '904000888000142', 'Banco Santander Brasil', 1);