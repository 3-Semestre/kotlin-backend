insert into nicho (nome) values
('INFANTIL'), ('BUSINESS'), ('TECNICO'), ('TESTES_INTERNACIONAIS'), ('INICIANTE'), ('INTERMEDIARIO'), ('AVANCADO');

insert into nivel_acesso (nome) values
('ALUNO'), ('PROFESSOR_AUXILIAR'), ('REPRESENTANTE_LEGAL');

insert into nivel_ingles (nome) values
('A1'), ('A2'), ('B1'), ('B2'), ('C1'), ('C2');

insert into Usuario (nome_completo, cpf, telefone, email, senha, nivel_acesso_id) values
('Christian', '300.261.160-30', '11092378173', 'christian@email.com', 'Cleber123', 3);

insert into usuario_nicho (usuario_id, nicho_id, nivel_ingles_id) values
(1, 6, 5);
