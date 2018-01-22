/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontoeletronico;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GerenciaFuncionario extends InteracaoUsuario {

    public void cadastroUsuario() {

        String opcao = "";
        while (!(opcao.matches("[0-4]"))) {
            pergunta(imprensa
                    + "\n CADASTRO DE FUNCIONARIO"
                    + "\n 1 - INCLUSÃO"
                    + "\n 2 - ALTERAÇÃO"
                    + "\n 3 - CONSULTA"
                    + "\n 4 - EXCLUSÃO"
                    + "\n 0 - RETORNAR"
                    + "\n digite a opção para Prosseguir ");
            opcao = resposta();

        }
        switch (opcao) {
            case "1":

                incluirFuncionario();
                cadastroUsuario();
                break;
            case "2":
                pergunta(imprensa
                        + "\n ALTERAÇÃO DE FUNCIONARIO"
                );
                alterarFuncionario();
                cadastroUsuario();
                break;
            case "3":
                consultarFuncionario();
                break;
            case "4":
                excluirFuncionario();
                break;
            case "0":
                telaPrincipal();
                break;

        }
    }

    

    private void incluirFuncionario() {
        String[] saida = new String[6];
        pergunta(imprensa + "\n INCLUSAO FUNCIONARIO");
        saida[0] = verificacao("\n MATRICULA:", 0);
        saida[1] = verificacao("\n NOME", 1);
        saida[2] = verificacao("\n Horario De Entrada:", 2);
        saida[3] = verificacao("\n Horario De Saida Do Almoço:", 2);
        saida[4] = verificacao("\n Horario De Entrada Do Almoço:", 2);
        saida[5] = verificacao("\n Horario De Saida:", 2);
        boolean flag = false;

        String resposta = "";
        while (!flag) {
            pergunta("Deseja Cadastrar? S ou N");
            resposta = resposta().toLowerCase();
            if (resposta.equals("n")) {
                telaPrincipal();
                flag = true;
            } else if (resposta.equals("s")) {
                flag = true;
                String grava = saida[0] + "#" + saida[1] + "#" + saida[2] + "#" + saida[3] + "#" + saida[4] + "#" + saida[5];
                GA.gravarArquivo(0, grava, true);
            }
        }
    }

    private void consultarFuncionario() {
        boolean flag = false;
        String resposta = "";
        while (!flag) {
            pergunta("\n Digite a matricula de quem deseja alterar");
            flag = verificaRegex((resposta = resposta()), "[0-9]+");

        }

        String res = buscaMatricula(resposta);
        if (res == null) {
            pergunta("A matricula " + resposta + " não existe"
                    + "\n Deseja realizar a busca novamente? S/N");
            String denovo = resposta();
            if ("s".equals(denovo.toLowerCase())) {
                consultarFuncionario();
            } else if ("n".equals(denovo.toLowerCase())) {
                cadastroUsuario();
            } else {
                telaPrincipal();
            }

        } else {
            String[] informacoes = res.split("#");
            pergunta("INFORMAÇOES DO USUARIO"
                    + "\n Matricula:" + informacoes[0] + ""
                    + "\n Nome:" + informacoes[1] + ""
                    + "\n Hórario Trabalho" + ""
                    + "\n Entrada:" + informacoes[2]
                    + "\n Saida Para Almoço:" + informacoes[3]
                    + "\n Entrada Do Almoço:" + informacoes[4]
                    + "\n Saida:" + informacoes[5]
                    + "\n Deseja realizar a busca novamente? S/N");
            String denovo = resposta();
            if ("s".equals(denovo.toLowerCase())) {
                consultarFuncionario();
            } else if ("n".equals(denovo.toLowerCase())) {
                cadastroUsuario();
            } else {
                telaPrincipal();
            }
        }
    }

    private void alterarFuncionario() {

        boolean flag = false;
        String resposta = "";
        while (!flag) {
            pergunta("\n Digite a matricula de quem deseja alterar");
            flag = verificaRegex((resposta = resposta()), "[0-9]+");

        }

        String res = buscaMatricula(resposta);
        if (res == null) {
            pergunta("A matricula " + resposta + " não existe"
                    + "\n Deseja realizar a busca novamente? S/N");
            String denovo = resposta();
            if ("s".equals(denovo.toLowerCase())) {
                alterarFuncionario();
            } else if ("n".equals(denovo.toLowerCase())) {
                cadastroUsuario();
            } else {
                telaPrincipal();
            }

        } else {
            String[] informacoes = res.split("#");
            boolean confirmacao = true;
            while (confirmacao) {

                pergunta("Qual Informação Deseja Alterar?"
                        + "\n 1 - Nome:" + informacoes[1] + ""
                        + "\n  - Hórario Trabalho - " + ""
                        + "\n 2 - Entrada:" + informacoes[2]
                        + "\n 3 - Saida para Almoço:" + informacoes[3]
                        + "\n 4 - Entrada Do Almoço:" + informacoes[4]
                        + "\n 5 - Saida:" + informacoes[5]
                        + "\n 6 - Retornar e SALVAR"
                        + "\n 0 - Retornar");
                resposta = resposta();
                switch (resposta) {
                    case "1":
                        informacoes[1] = verificacao("\n digite o novo nome", 1);
                        
                        break;
                    case "2":
                        informacoes[2] = verificacao("\n digite o novo Entrada", 2);
                        
                        break;
                    case "3":
                        informacoes[3] = verificacao("\n digite o novo Saida para Almoço", 2);
                        
                        break;
                    case "4":
                        informacoes[4] = verificacao("\n digite o novo Entrada do Almoço", 2);
                        
                        break;
                    case "5":
                        informacoes[5] = verificacao("\n digite o novo Saida", 2);
                        
                        break;
                    case "6":
                        confirmacao=false;
                        GA.alterarLinha(informacoes);
                        break;
                    case "0":
                        confirmacao=false;
                        break;
                }

            }
        }
    }

    private void excluirFuncionario() {
         boolean flag = false;
        String resposta = "";
        while (!flag) {
            pergunta("\n Digite a matricula de quem deseja alterar");
            flag = verificaRegex((resposta = resposta()), "[0-9]+");

        }

        String res = buscaMatricula(resposta);
        if (res == null) {
            pergunta("A matricula " + resposta + " não existe"
                    + "\n Deseja realizar a busca novamente? S/N");
            String denovo = resposta();
            if ("s".equals(denovo.toLowerCase())) {
                excluirFuncionario();
            } else if ("n".equals(denovo.toLowerCase())) {
                cadastroUsuario();
            } else {
                telaPrincipal();
            }

        } else {
            String[] informacoes = res.split("#");
            pergunta("INFORMAÇOES DO USUARIO"
                    + "\n Matricula:" + informacoes[0] + ""
                    + "\n Nome:" + informacoes[1] + ""
                    + "\n Hórario Trabalho" + ""
                    + "\n Entrada:" + informacoes[2]
                    + "\n Saida Para Almoço:" + informacoes[3]
                    + "\n Entrada Do Almoço:" + informacoes[4]
                    + "\n Saida:" + informacoes[5]
                    + "\n Deseja  realmente deletar?? S/N");
            String denovo = resposta();
            if ("s".equals(denovo.toLowerCase())) {
                GA.deletarLinha(informacoes[0]);
                cadastroUsuario();
            } else if ("n".equals(denovo.toLowerCase())) {
                cadastroUsuario();
            } else {
                telaPrincipal();
            }
        } }

}
