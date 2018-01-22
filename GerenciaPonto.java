/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontoeletronico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class GerenciaPonto extends InteracaoUsuario {

    void cadastroPonto() {
        boolean flag = false;
        String resposta = "";
        while (!flag) {
            pergunta("\n Digite a matricula de quem deseja bater o ponto");
            flag = verificaRegex((resposta = resposta()), "[0-9]+");

        }

        String res = buscaMatricula(resposta);
        if (res == null) {
            pergunta("A matricula " + resposta + " não existe"
                    + "\n Deseja realizar a busca novamente? S/N");
            String denovo = resposta();
            if ("s".equals(denovo.toLowerCase())) {
                cadastroPonto();
            } else if ("n".equals(denovo.toLowerCase())) {
                telaPrincipal();
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
            );

            String denovo = verificaDataHora();
            denovo = denovo.replace('-', '#');
            GA.gravarArquivo(1, (informacoes[0] + "#" + denovo), true);
            pergunta("Registrado");
            telaPrincipal();
        }
    }

    private String verificaDataHora() {

        boolean flag = false;
        String resposta = "";
        while (!flag) {
            pergunta("digite hora e data no formato HH:MM-DD/MM/AA");
            resposta = resposta();
            String[] horaData = resposta.split("-");

            boolean regex = verificaRegex(horaData[0], "^([0-9]|[0-1][0-9]|2[0-3]):[0-5][0-9]$");
            if (regex) {
                flag = validaData(horaData[1]);
            }
        }
        return resposta;
    }

    public boolean validaData(String data) {
        java.sql.Date DataEntrada = null;
        boolean flag = false;
        SimpleDateFormat dataEntr = new SimpleDateFormat("dd/MM/yy");
        if (data.length() > 0) {
            try {
                DataEntrada = new java.sql.Date(dataEntr.parse(data).getTime());
                String dataEntrada = String.valueOf(DataEntrada);
                if (dataEntrada != null) {
                    String ano = dataEntrada.substring(0, 4);
                    String mes = dataEntrada.substring(5, 7);
                    String dia = dataEntrada.substring(8, 10);
                    dataEntrada = dia + "/" + mes + "/" + ano;

                }
            } catch (ParseException ex) {
                pergunta(data+" esta no formato errado.");
            }

        }
        return flag;
    }

}
