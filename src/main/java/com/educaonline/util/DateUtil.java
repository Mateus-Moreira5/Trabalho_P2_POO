package main.java.com.educaonline.util;

public class DateUtil {

}
package main.java.com.educaonline.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public final class DateUtil {

    public static final String FORMATO_DATA_BR = "dd/MM/yyyy";
    public static final String FORMATO_DATA_HORA_BR = "dd/MM/yyyy HH:mm:ss";
    public static final String FORMATO_DATA_ISO = "yyyy-MM-dd";
    public static final String FORMATO_DATA_HORA_ISO = "yyyy-MM-dd HH:mm:ss";
    
    private DateUtil() {
        throw new IllegalStateException("Classe utilitária - não instanciar");
    }
  
    private static SimpleDateFormat criarFormatter(String formato) {
        SimpleDateFormat sdf = new SimpleDateFormat(formato);
        sdf.setLenient(false);
        return sdf;
    }

    public static String formatarData(Date data, String formato) {
        if (data == null) {
            return "";
        }
        try {
            return criarFormatter(formato).format(data);
        } catch (Exception e) {
            System.err.println("Erro ao formatar data: " + e.getMessage());
            return "";
        }
    }

    public static Date parseData(String dataStr, String formato) {
        if (dataStr == null || dataStr.trim().isEmpty()) {
            return null;
        }
        
        try {
            return criarFormatter(formato).parse(dataStr.trim());
        } catch (ParseException e) {
            System.err.println("Erro ao converter data: '" + dataStr + "' - Formato esperado: " + formato);
            return null;
        }
    }

    public static Date getDataAtual() {
        return new Date();
    }

    public static Date adicionarDias(Date data, int dias) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.DAY_OF_MONTH, dias);
        return cal.getTime();
    }

    public static Date adicionarMeses(Date data, int meses) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.add(Calendar.MONTH, meses);
        return cal.getTime();
    }

    public static long diferencaEmDias(Date data1, Date data2) {
        long diff = Math.abs(data2.getTime() - data1.getTime());
        return TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
    }

    public static boolean estaNoPeriodo(Date data, Date inicio, Date fim) {
        return !data.before(inicio) && !data.after(fim);
    }

    public static boolean isDataFutura(Date data) {
        return data.after(getDataAtual());
    }

    public static boolean isDataPassada(Date data) {
        return data.before(getDataAtual());
    }

    public static Date getPrimeiroDiaMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        return cal.getTime();
    }

    public static Date getUltimoDiaMes(Date data) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        return cal.getTime();
    }

    public static String formatarDataAmigavel(Date data) {
        if (data == null) {
            return "Data não informada";
        }
        
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE, dd 'de' MMMM 'de' yyyy");
        return sdf.format(data);
    }

    public static int calcularIdade(Date dataNascimento) {
        if (dataNascimento == null) {
            return 0;
        }
        
        Calendar nasc = Calendar.getInstance();
        nasc.setTime(dataNascimento);
        Calendar hoje = Calendar.getInstance();
        
        int idade = hoje.get(Calendar.YEAR) - nasc.get(Calendar.YEAR);
        
        if (hoje.get(Calendar.MONTH) < nasc.get(Calendar.MONTH) ||
            (hoje.get(Calendar.MONTH) == nasc.get(Calendar.MONTH) && 
             hoje.get(Calendar.DAY_OF_MONTH) < nasc.get(Calendar.DAY_OF_MONTH))) {
            idade--;
        }
        
        return idade;
    }

    public static boolean validarData(String dataStr, String formato) {
        return parseData(dataStr, formato) != null;
    }

    public static boolean isMesmaData(Date data1, Date data2) {
        if (data1 == null || data2 == null) {
            return false;
        }
        
        Calendar cal1 = Calendar.getInstance();
        Calendar cal2 = Calendar.getInstance();
        cal1.setTime(data1);
        cal2.setTime(data2);
        
        return cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR) &&
               cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH) &&
               cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
    }

    public static String getNomeDiaSemana(Date data) {
        if (data == null) {
            return "";
        }
        
        String[] dias = {"Domingo", "Segunda-feira", "Terça-feira", "Quarta-feira", 
                        "Quinta-feira", "Sexta-feira", "Sábado"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(data);
        int diaSemana = cal.get(Calendar.DAY_OF_WEEK);
        
        return dias[diaSemana - 1];
    }
}