/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pontoeletronico;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Funcionario {

    private String matricula;
    private String nome;
    private String[] horarios = new String[4];
    private ArrayList<String> pontoEletronico = new ArrayList();

    public Funcionario(String matricula, String nome, String[] horario) {
        this.matricula = matricula;
        this.nome = nome;
        this.horarios = horario;
    }

    public void adicionarHorario(String batida) {
        this.pontoEletronico.add(batida);
    }

    public String geraFormato() {
        String formatado = "FUNCIONARIO: " + this.nome + " Matricula: " + this.matricula
                + "\nHorario de trabalho: " + horarios[0] + " ÀS " + horarios[1] + " - " + horarios[2] + " ÀS " + horarios[3]
                + "\n_____________________________________________________________________________________________"
                + "\n ENTRADA SAIDA ENTRADA SAIDA \n"
                + "\n---------------------------------------------------------------------------------------------"
                + "\n";
        Collections.sort(pontoEletronico, new Comparator() {
            public int compare(Object o1, Object o2) {
                java.util.Date DataEntrada1 = null;
                java.util.Date DataEntrada2 = null;

                try {
                    SimpleDateFormat dataEntr = new SimpleDateFormat("dd/MM/yy HH:mm");
                    DataEntrada1 = dataEntr.parse(((String) o1).replaceAll("#", " "));
                    DataEntrada2 = dataEntr.parse(((String) o2).replaceAll("#", " "));

                } catch (ParseException ex) {
                    Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
                }
                return DataEntrada1.compareTo(DataEntrada2);
            }
        }
        );
        String data = "               ";
        for (int i = 0; i < pontoEletronico.size(); i++) {
            String linha = pontoEletronico.get(i);
            if (!(data.substring(0, 8)).equals(linha.substring(0, 8))) {
                data = linha;
                formatado += "\n" + data.substring(0, 8) + " - ";
            }
            formatado += linha.substring(9) + " ";
        }

        return formatado;
    }

    public String faltas() {
        Collections.sort(pontoEletronico, new Comparator() {
            public int compare(Object o1, Object o2) {
                java.util.Date DataEntrada1 = null;
                java.util.Date DataEntrada2 = null;

                try {
                    SimpleDateFormat dataEntr = new SimpleDateFormat("dd/MM/yy HH:mm");
                    DataEntrada1 = dataEntr.parse(((String) o1).replaceAll("#", " "));
                    DataEntrada2 = dataEntr.parse(((String) o2).replaceAll("#", " "));

                } catch (ParseException ex) {
                    Logger.getLogger(Funcionario.class.getName()).log(Level.SEVERE, null, ex);
                }
                return DataEntrada1.compareTo(DataEntrada2);
            }
        });
        int presenca=0;
        int falta= 22;
        String data = "               ";
        for (int i = 0; i < pontoEletronico.size(); i++) {
            String linha = pontoEletronico.get(i);
            if (!(data.substring(0, 8)).equals(linha.substring(0, 8))) {
                data = linha;
                presenca++;
            }
            
                
            
        }
        falta=falta - presenca;
        String texto="Funcionario: "+this.nome+" Faltou: "+falta+" Dias";
        return texto;
    }

    public boolean comparaMatricula(String matricula) {
        return matricula.equals(this.matricula);
    }
}
