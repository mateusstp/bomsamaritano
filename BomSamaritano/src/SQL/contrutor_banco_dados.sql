SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

DROP SCHEMA IF EXISTS `BomSamaritano` ;
CREATE SCHEMA IF NOT EXISTS `BomSamaritano` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci ;
USE `BomSamaritano` ;

-- -----------------------------------------------------
-- Table `BomSamaritano`.`Organizacao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Organizacao` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Organizacao` (
  `CNPJ` VARCHAR(15)  ,
  `Nome` VARCHAR(500)  ,
  PRIMARY KEY (`CNPJ`) )
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`Endereco`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Endereco` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Endereco` (
  `key_end` INT NOT NULL AUTO_INCREMENT ,
  `Rua` VARCHAR(500)  ,
  `Numero` VARCHAR(10)  ,
  `Complemento` VARCHAR(500) NULL ,
  `Bairro` VARCHAR(500)  ,
  `Cidade` VARCHAR(500)  ,
  `Estado` VARCHAR(500)  ,
  `Telefone` VARCHAR(15),
  `CEP` VARCHAR(15),
PRIMARY KEY (`key_end`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `BomSamaritano`.`EnderecoBoleto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`EnderecoBoleto` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`EnderecoBoleto` (
  `key_endBol` INT NOT NULL AUTO_INCREMENT ,
  `Rua` VARCHAR(500)  ,
  `Numero` VARCHAR(10)  ,
  `Complemento` VARCHAR(500) NULL ,
  `Bairro` VARCHAR(500)  ,
  `Cidade` VARCHAR(500)  ,
  `Estado` VARCHAR(500)  ,
  `Telefone` VARCHAR(15)  ,
  `CEP` VARCHAR(15),
  -- `MesmoEnderecoInterno` VARCHAR(4),
PRIMARY KEY (`key_endBol`) )
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `BomSamaritano`.`Interno`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Interno` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Interno` (
  `key_int` INT NOT NULL AUTO_INCREMENT ,
  `Numero` INT,
  `Nome` VARCHAR(700)  ,
  `Sexo` VARCHAR(100) ,
  `Idade` varchar(40) ,
  `DataNascimento` varchar(15)  ,
  `RG` VARCHAR(200)  ,
  `CPF` VARCHAR(140)  ,
  `EstadoCivil` VARCHAR(450)  ,
  `CTPSNumero` VARCHAR(500)  ,
  `PIS` VARCHAR(140) NULL ,
  `TituloEleitor` varchar(140) ,
  `Zona` varchar(100) NULL ,
  `Secao` varchar(100) NULL ,
  `Naturalidade` VARCHAR(500) NULL ,
-- `Organizacao_CNPJ` VARCHAR(15)  ,
  `ImagemAntes` VARCHAR(1000) NULL ,
  `ImagemDepois` VARCHAR(1000) NULL ,
  `key_end` INT NOT NULL ,
  `key_endBol` INT NOT NULL ,
  `ComQuemMoraAtual` VARCHAR(500),
  PRIMARY KEY (`key_int`,`Numero`) ,
  
  -- INDEX `fk_Interno_Organizacao1_idx` (`Organizacao_CNPJ` ASC) ,
  -- INDEX `fk_Interno_Endereco1_idx` (`Endereco_Identificador` ASC) ,
  INDEX `fk_Interno_Endereco1_idx` (`key_end` ASC) ,
    -- CONSTRAINT `fk_Interno_Organizacao1`
  --   FOREIGN KEY (`Organizacao_CNPJ` )
  --   REFERENCES `BomSamaritano`.`Organizacao` (`CNPJ` )
  --   ON DELETE NO ACTION
  --   ON UPDATE NO ACTION,
  CONSTRAINT `fk_Interno_Endereco1`
    FOREIGN KEY (`key_end` )
    REFERENCES `BomSamaritano`.`Endereco` (`key_end`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
INDEX `fk_Interno_Endereco1_idxBol` (`key_endBol` ASC) ,
CONSTRAINT `fk_Interno_EnderecoBol`
    FOREIGN KEY (`key_endBol` )
    REFERENCES `BomSamaritano`.`EnderecoBoleto` (`key_endBol`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;

-- -----------------------------------------------------
-- Table `BomSamaritano`.`Boleto`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Boleto` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Boleto` (
  `key_int` INT NOT NULL,
  `Responsavel` VARCHAR(600),
  `ResponsavelContato` VARCHAR(700) ,
  `VencimentoBoleto` VARCHAR(600) ,
  `MesmoEnderecoInterno` VARCHAR(10) ,
 INDEX `fk_Processo_Interno1_idx` (`key_int` ASC) ,
  CONSTRAINT `fk_Processo_Interno1`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;



-- -----------------------------------------------------
-- Table `BomSamaritano`.`Saúde`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Saude` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Saude` (
  `key_saude` int AUTO_INCREMENT NOT NULL ,
  `Sono` VARCHAR(400) NULL DEFAULT 'Normal' ,
  `Alimentacao` VARCHAR(400) NULL DEFAULT 'Normal' ,
  `AlucinacaoDelirios` VARCHAR(400) NULL DEFAULT 'Não Possui' ,
  `DesmaiosConvulsoes` VARCHAR(400) NULL DEFAULT 'Não Possui' ,
  `Medicacao` VARCHAR(150) NULL DEFAULT ' Não Toma' ,
  `AutoExterminio` VARCHAR(550) ,
  `ExamesEspecificos` VARCHAR(200) NULL DEFAULT 'Sem Pedido' ,
  `key_int` INT NOT NULL ,
  INDEX `fk_Saude_Interno1_idx` (`key_int` ASC) ,
  PRIMARY KEY (`key_saude`) ,
  CONSTRAINT `fk_Saúde_Interno1`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`Profissao`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Profissao` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Profissao` (
  `key_int` INT NOT NULL ,
  `TrabalhoAtual` VARCHAR(1000) NULL DEFAULT 'Não Trabalha atualmente' ,
  `TrabalhoCA` VARCHAR(1000) NULL DEFAULT 'Nunca trabalhou de carteira assinada' ,
  `Aposentado` VARCHAR(1000) NULL DEFAULT 'Não é aposentado' ,
  `AfastadoPS` VARCHAR(1000) NULL DEFAULT 'Não está afastado pela providência social' ,
  `SalarioDesemprego` VARCHAR(500) NULL DEFAULT 'Não recebe salario desemprego' ,
  `Escolaridade` VARCHAR(500) ,
  INDEX `fk_Profissao_Interno1_idx` (`key_int` ASC) ,
  CONSTRAINT `fk_Profissao_Interno1`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`Dependencia`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Dependencia` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Dependencia` (
  `Maconha` VARCHAR(200) ,
  `Alcool` VARCHAR(200) ,	
  `Inalantes` VARCHAR(200) ,
  `CocainaA` VARCHAR(200) ,
  `CocainaI` VARCHAR(200) ,
  `Crack` VARCHAR(200) ,  
  `Comprimidos` VARCHAR(200) ,
  `CigarroTabaco` VARCHAR(200) ,
  `OutrasDrogas` VARCHAR(200) ,
  `UsadaNoMomento` VARCHAR(1000) ,
  `TempoDeUso` VARCHAR(300) ,
  `key_int` INT NOT NULL,
  INDEX `fk_Dependencia_Interno1_idx` (`key_int` ASC) ,
  CONSTRAINT `fk_Dependencia_Interno1`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`FamiliaESocial`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`FamiliaESocial` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`FamiliaESocial` (
  `key_famsoc` int AUTO_INCREMENT NOT NULL ,
  `Parentesco` VARCHAR(1000) COMMENT 'Nome, Idade e Profissão.' ,
  `Nome` VARCHAR(1000) COMMENT 'Nome, Idade e Profissão' ,
  `EstadoCivil` VARCHAR(450),
  `CasoDeDrogas` VARCHAR(1000),
  `Idade` varchar(40) ,
  `Profissao` VARCHAR(450),
  `Relacionamento` VARCHAR(500) ,
  `TempoCasados` VARCHAR(100),
  `Falecido` VARCHAR(1000),
  -- `key_end` INT NOT NULL,
  `key_int` INT NOT NULL ,
  PRIMARY KEY (`key_famsoc`) ,
  -- INDEX `fk_FamiliaESocial_Endereco1_idx` (`key_end` ASC) ,
  INDEX `fk_FamiliaESocial_Interno1_idx` (`key_int` ASC) ,
  -- CONSTRAINT `fk_FamiliaESocial_Endereco1`
  --  FOREIGN KEY (`key_end` )
  --  REFERENCES `BomSamaritano`.`Endereco` (`key_end` )
  --  ON DELETE NO ACTION
  --  ON UPDATE NO ACTION,
  CONSTRAINT `fk_FamiliaESocial_Interno1interno`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`SituacaoEconomica`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`SituacaoEconomica` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`SituacaoEconomica` (
  `RendaFamilia` varchar(1000) ,
  `RendaFamiliaSeparados` varchar(1000) ,
  `RendaPessoal` varchar(1000) ,
  `key_int` INT NOT NULL ,
  `AjudaFinanceira` VARCHAR(1000),
  INDEX `fk_SituacaoEconomica_Interno1_idx` (`key_int` ASC) ,
  CONSTRAINT `fk_SituacaoEconomica_Interno1`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`Questionario`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Questionario` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Questionario` (
  `ConhecimentoColonia` VARCHAR(400) ,
  `ExpectativaTratamento` VARCHAR(300) ,
  `MotivoUsarDrogas` VARCHAR(1000) ,
  `Sexualidade` VARCHAR(450) ,
  `ProcessosJustica` VARCHAR(500) NULL DEFAULT 'Não possui processos' ,
  `Observacoes` VARCHAR(400) NULL ,
  `PossuiRelacionamentoAfetivo` VARCHAR(400) NULL ,
  `GrupoApoio` VARCHAR(400) NULL ,
  `RelacionamentoSocial` VARCHAR(400) NULL ,
  `key_int` INT NOT NULL ,
  INDEX `fk_Questionario_Interno1_idx` (`key_int` ASC) ,
  CONSTRAINT `fk_Questionario_Interno1`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`Processo`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Processo` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Processo` (
  `key_int` INT NOT NULL ,
  `EntrevistadoraUm` VARCHAR(700) ,
  `DataEntrevistaUm` VARCHAR(600) ,
  `EntrevistadoraDois` VARCHAR(700) ,
  `DataEntrevistaDois` VARCHAR(600) ,
  `EntrevistadoraInternamento` VARCHAR(700) ,
  `DataEntrevistaInternamento` VARCHAR(600) ,
  `EntrevistadoraSaida` VARCHAR(700) ,
  `DataEntrevistaSaida` VARCHAR(600) ,
  `Status` VARCHAR(600) ,
  INDEX `fk_Processo_Interno1_idx_processo` (`key_int` ASC) ,
  CONSTRAINT `fk_Processo_Interno1_processo`
    FOREIGN KEY (`key_int` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`TratamentoAnteriores`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`TratamentoAnteriores` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`TratamentoAnteriores` (
  `Local` VARCHAR(500) ,
  `DataEntrada` VARCHAR(15) ,
  `DataSaida` VARCHAR(15) ,
  `MotivoSaida` VARCHAR(500) ,
  -- `Terapias` VARCHAR(100) ,
  -- `Outros` VARCHAR(300) NULL ,
  -- `EnderecoClinica` VARCHAR(400) NULL ,
  `key_saude` int NOT NULL ,
  INDEX `fk_TratamentoAnteriores_Saude1_idx` (`key_saude` ASC) ,
  CONSTRAINT `fk_TratamentoAnteriores_Saude1`
    FOREIGN KEY (`key_saude` )
    REFERENCES `BomSamaritano`.`Saude` (`key_saude` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `BomSamaritano`.`Interno_has_Interno`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `BomSamaritano`.`Interno_has_Interno` ;

CREATE  TABLE IF NOT EXISTS `BomSamaritano`.`Interno_has_Interno` (
  `Interno_Novo` INT NOT NULL ,
  `Interno_Parente` INT ,
  `GrauDeParentesco` VARCHAR(450) ,
  INDEX `fk_Interno_has_Interno_Interno2_idx` (`Interno_Parente` ASC) ,
  INDEX `fk_Interno_has_Interno_Interno1_idx` (`Interno_Novo` ASC) ,
  CONSTRAINT `fk_Interno_has_Interno_Interno1`
    FOREIGN KEY (`Interno_Novo` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `fk_Interno_has_Interno_Interno2`
    FOREIGN KEY (`Interno_Parente` )
    REFERENCES `BomSamaritano`.`Interno` (`key_int` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION)
ENGINE = InnoDB;





USE `BomSamaritano` ;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


