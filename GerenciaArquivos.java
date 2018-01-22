/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontoeletronico;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;


public class GerenciaArquivos {

    String destino = "";

    public GerenciaArquivos() {
        File gambiarra = new File(".");
        try {

            destino = gambiarra.getCanonicalPath() + "\\data";
        } catch (IOException ex) {
            Logger.getLogger(GerenciaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        File dados = new File(destino + "\\Ponto.txt");
        File funcionario = new File(destino + "\\Funcionario.txt");
        File data = new File(destino);
        if (!data.exists()) {
            data.mkdir();

        }

        try {
            if (!(dados.exists())) {
                dados.createNewFile();
            }
            if (!funcionario.exists()) {
                funcionario.createNewFile();
            }
        } catch (IOException ex) {
            System.out.println("GB");
        }

    }
    
    public void criarRelatorio(ArrayList<Funcionario> pessoas){
        File gambiarra = new File(".");
        try {

            destino = gambiarra.getCanonicalPath() + "\\data";
        } catch (IOException ex) {
            Logger.getLogger(GerenciaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        File relatorio = new File(destino + "\\Relatorio.txt");
        if(!(relatorio.exists())){
            try {
                relatorio.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GerenciaArquivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        gravarArquivo(2,"JOEBA PRODUTOS E SERVIÇOS LTDA",false);
        gravarArquivo(2,"SISTEMA DE CONTROLE DE PONTO",true);
        gravarArquivo(2,"MêS/ANOS MAPA DE FREQUENCIA PG 009",true);
        for(int i=0;i<pessoas.size();i++){
        Funcionario Fun=pessoas.get(i);
        String texto=Fun.geraFormato();
        String[] linha=texto.split("\\n");
        for(int j=0;j<linha.length;j++){
            gravarArquivo(2,linha[j],true);
        }
    }
        
    }
    public void CriarBalanco(ArrayList<Funcionario> pessoas){
        File gambiarra = new File(".");
        try {

            destino = gambiarra.getCanonicalPath() + "\\data";
        } catch (IOException ex) {
            Logger.getLogger(GerenciaArquivos.class.getName()).log(Level.SEVERE, null, ex);
        }
        File balanco = new File(destino + "\\balanço.txt");
        if(!(balanco.exists())){
            try {
                balanco.createNewFile();
            } catch (IOException ex) {
                Logger.getLogger(GerenciaArquivos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        gravarArquivo(3,"JOEBA PRODUTOS E SERVIÇOS LTDA",false);
        gravarArquivo(3,"SISTEMA DE CONTROLE DE PONTO",true);
        gravarArquivo(3,"MêS/ANOS BALANÇO DE FALTAS PG 009",true);
        gravarArquivo(3,"FUNCIONARIO NO. FALTAS",true);
        for(int i=0;i<pessoas.size();i++){
        Funcionario Fun=pessoas.get(i);
        String texto = Fun.faltas();
       gravarArquivo(3,texto,true);
        }
    }
    

    public BufferedReader lerArquivo(int arquivo) {
        String arquivo_ = escolheDestino(arquivo);

        BufferedReader arquivoAberto = null;
        try {
            arquivoAberto = new BufferedReader(new FileReader(new File(destino + "\\" + arquivo_)));
        } catch (IOException e) {
            System.out.println("Nao abriu arquivo para leitura!");
        }
        return arquivoAberto;
    }

    public String escolheDestino(int arquivo) {
        String arquivo_ = "";
        if (arquivo == 0) {
            arquivo_ = "funcionario.txt";
        } else if (arquivo == 1) {
            arquivo_ = "ponto.txt";
        }else if (arquivo == 2){
            arquivo_="Relatorio.txt";
        }else if (arquivo == 3){
            arquivo_="balanço.txt";
        }
        return arquivo_;
    }

    public void gravarArquivo(int arquivo, String frase, boolean habilitar) {
        String arquivo_ = escolheDestino(arquivo);
        OutputStream bytes;
        try {
            bytes = new FileOutputStream(destino + "\\" + arquivo_, habilitar);
            OutputStreamWriter chars = new OutputStreamWriter(bytes);
            BufferedWriter strings = new BufferedWriter(chars);
            strings.write(frase);
            if (!frase.equals("")) {
                strings.newLine();
            }
            strings.close();

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } // passado "true" para gravar no mesmo arquivo
        catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    void alterarLinha(String[] informacoes) {
        deletarLinha(informacoes[0]);
        String linha = informacoes[0] + "#" + informacoes[1] + "#" + informacoes[2] + "#" + informacoes[3] + "#" + informacoes[4] + "#" + informacoes[5];

        gravarArquivo(0, linha, true);

    }

    void deletarLinha(String matricula) {
        BufferedReader dados = lerArquivo(0);
        String linha = "";

        ArrayList<String> pessoas = new ArrayList();
        try {
            while ((linha = dados.readLine()) != null) {
                String[] linha_ = linha.split("#");
                if (!matricula.equals(linha_[0])) {
                    pessoas.add(linha);
                }
            }

        } catch (IOException ex) {
            Logger.getLogger(GerenciaFuncionario.class.getName()).log(Level.SEVERE, null, ex);
        }
        gravarArquivo(0, "", false);
        for (int i = 0; pessoas.size() > i; i++) {
            gravarArquivo(0, pessoas.get(i), true);
        }
    }
}
