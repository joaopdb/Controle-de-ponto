/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontoeletronico;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class InteracaoUsuario {

    static final String imprensa = "JOEBA PRODUTOS E SERVIÇOS LTDA"
            + "\n SISTEMA DE CONTROLE DE PONTO";
    static GerenciaFuncionario GF;
    static GerenciaPonto GP;
    static GerenciaRelatorio GR;
    static GerenciaArquivos GA = new GerenciaArquivos();
    static Scanner entrada = new Scanner(System.in, "ISO-8859-1");

    void pergunta(String texto) {
        System.out.println(texto);
    }

    String resposta() {
        return entrada.nextLine();
    }

    boolean verificaRegex(String entrada, String regex) {
        boolean flag = true;
        String error = "A entrada Não Esta no formato Devido";

        if (!entrada.matches(regex)) {
            pergunta(error);
            flag = false;
        }
        return flag;
    }

    String buscaMatricula(String matricula) {
        BufferedReader dados = GA.lerArquivo(0);
        String linha = "";
        boolean flag = true;
        try {
            while (flag && (linha = dados.readLine()) != null) {
                String[] linha_ = linha.split("#");
                if (matricula.equals(linha_[0])) {
                    flag = false;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(GerenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        return linha;
    }

    boolean verificaMatricula(String matricula) {
        boolean flag = false;
        
            boolean regex =verificaRegex(matricula, "[0-9]+");
            if(regex){
                String linha=buscaMatricula(matricula);
               if(linha==null){
                  flag = true;
               }
            }

        return flag;
    }

    void telaPrincipal() {
        String opcao = "";
        while (!(opcao.matches("[0-3]"))) {
            pergunta(imprensa
                    + "\n MENU INICIAL:"
                    + "\n 1 - Cadastro de funcionario"
                    + "\n 2 - Registro de ponto "
                    + "\n 3 - Relatórios "
                    + "\n 0 - finalizar"
                    + "\n digite a opção para Prosseguir ");
            opcao = resposta();

        }
        switch (opcao) {
            case "1":
                if (GF == null) {
                    GF = new GerenciaFuncionario();
                }
                GF.cadastroUsuario();
                break;
            case "2":
                if (GP == null) {
                    GP = new GerenciaPonto();
                }
                GP.cadastroPonto();

                break;
            case "3":
                if (GR == null) {
                    GR = new GerenciaRelatorio();
                }
                GR.menuRelatorio();
                break;
            case "0":
                System.exit(0);
                break;

        }
    }

    public static void main(String[] args) {
        // TODO code application logic 
        new InteracaoUsuario().telaPrincipal();

    }
public String verificacao(String texto, int tipoVerificao) {
        boolean flag = false;
        String resposta = "";
        while (!flag) {
            pergunta(texto);
            resposta = resposta();
            if (tipoVerificao == 2) {
                //[^([0-9]|0[0-9]|1[0-9]|2[0-3]):[0-5][0-9]$]
                flag = verificaRegex(resposta, "^([0-9]|[0-1][0-9]|2[0-3]):[0-5][0-9]$");
            } else if (tipoVerificao == 0) {
                flag = verificaMatricula(resposta);
            } else if (tipoVerificao == 1) {
                flag = verificaRegex(resposta, "[A-Za-zÀ-ú ]+");
            }

        }
        return resposta;
    }
}
