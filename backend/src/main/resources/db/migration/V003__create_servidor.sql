CREATE TABLE IF NOT EXISTS  servidor  (
     id INT PRIMARY KEY AUTO_INCREMENT,
     nome VARCHAR(60) NULL,
     matricula INT NULL,
     lotacao VARCHAR(45) NULL,
     foto VARCHAR(255) NULL,
     categoria_id  INT NOT NULL,
  INDEX  fk_tb_servidor_tb_categoria_idx  ( categoria_id  ASC) VISIBLE,
  CONSTRAINT  fk_tb_servidor_tb_categoria
    FOREIGN KEY ( categoria_id )
    REFERENCES  categoria  ( id )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
