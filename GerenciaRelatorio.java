/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontoeletronico;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GerenciaRelatorio extends InteracaoUsuario {

    public void menuRelatorio() {

        String opcao = "";
        while (!(opcao.matches("[0-2]"))) {
             pergunta(imprensa+"\n Sistema de controle de ponto"
            + "\n Relatorios"
            + "\n 1 - Mapa de frequencia"
            + "\n 2 - balanço de faltas"
            + "\n 0 - retornar");
            opcao = resposta();

        }
        switch (opcao) {
            case "1":
                mapaRelatorio();
                break;
            case "2":
                balancoFalta();
                break;
            case "0":
                telaPrincipal();
                break;

        }
    }
    
    public void mapaRelatorio(){
       BufferedReader bancoDeFuncionarios= GA.lerArquivo(0);
        String linha = "";
        
        ArrayList<Funcionario> pessoas = new ArrayList();
        try {
            while ((linha = bancoDeFuncionarios.readLine()) != null) {
                String[] linha_ = linha.split("#");
                String[] horario ={linha_[2],linha_[3],linha_[4],linha_[5]};
                Funcionario Fun = new Funcionario(linha_[0],linha_[1],horario);
                pessoas.add(Fun);
                
            }
            bancoDeFuncionarios = GA.lerArquivo(1);
            while ((linha = bancoDeFuncionarios.readLine()) != null) {
                
                String[] linha_ = linha.split("#");
                boolean flag=false;
                for(int i=0;i<pessoas.size();i++){
                   if(pessoas.get(i).comparaMatricula(linha_[0])){
                       pessoas.get(i).adicionarHorario(linha_[2]+"#"+linha_[1]);
                       i=pessoas.size();
                       flag=true;
                   }
                }
                if(!flag){
                    pergunta("não foi encontrado funcionario com essa matricula:\n"+linha);
                    
                }
                
            }
            GA.criarRelatorio(pessoas);
        } catch (IOException ex) {
            Logger.getLogger(GerenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
       telaPrincipal();
        
    }

    private void balancoFalta() {
      BufferedReader bancoDeFuncionarios= GA.lerArquivo(0);
        String linha = "";
        
        ArrayList<Funcionario> pessoas = new ArrayList();
        try {
            while ((linha = bancoDeFuncionarios.readLine()) != null) {
                String[] linha_ = linha.split("#");
                String[] horario ={linha_[2],linha_[3],linha_[4],linha_[5]};
                Funcionario Fun = new Funcionario(linha_[0],linha_[1],horario);
                pessoas.add(Fun);
                
            }
            bancoDeFuncionarios = GA.lerArquivo(1);
            while ((linha = bancoDeFuncionarios.readLine()) != null) {
                
                String[] linha_ = linha.split("#");
                boolean flag=false;
                for(int i=0;i<pessoas.size();i++){
                   if(pessoas.get(i).comparaMatricula(linha_[0])){
                       pessoas.get(i).adicionarHorario(linha_[2]+"#"+linha_[1]);
                       i=pessoas.size();
                       flag=true;
                   }
                }
                if(!flag){
                    pergunta("não foi encontrado funcionario com essa matricula:\n"+linha);
                    
                }
                
            }
            GA.CriarBalanco(pessoas);
        } catch (IOException ex) {
            Logger.getLogger(GerenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
       telaPrincipal();
        
    }
}