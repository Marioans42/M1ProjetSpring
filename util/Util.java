/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m1.film.util;

import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.sql.Time;

/**
 *
 * @author user
 */
public class Util {
    public static String generateTitreImage(String titre,Integer id){
        String name = titre.toLowerCase()
                        .replace('é', 'e')
                        .replace('.', '-')
                        .replace('ç', 'c')
                        .replace('è', 'e')
                        .replace('à', 'a')
                        .replace('ï', 'i')
                        .replace(" - ", " ")
                        .replace(' ', '-');
        System.err.println("**************************");
        System.err.println(name);
        return String.format("%s-%d.jpg",name,id);
    }
    
    public static String formatDouble(Double valeur) {
        NumberFormat format = NumberFormat.getInstance();
        format.setMaximumFractionDigits(2);
        return format.format(valeur);
    }
    
     public static String dateToString(Date date){
        SimpleDateFormat formater = new SimpleDateFormat("dd/MM/yyyy");
        return formater.format(date);
    }
     
    public static Date parserDateAllFormat(String date) throws Exception{
       Date dateParser = new Date();
       String[] formats = new String[]{
            "[0-3]{0,1}\\d{1}\\/{1}[0-1]{0,1}\\d{1}\\/{1}\\d{4}",
            "[0-3]{0,1}\\d{1}-{1}[0-1]{0,1}\\d{1}-{1}\\d{4}",
            "[0-3]{0,1}\\d{1}\\.{1}[0-1]{0,1}\\d{1}\\.{1}\\d{4}",
            "[0-3]{0,1}\\d{1}\\s{1}[0-1]{0,1}\\d{1}\\s{1}\\d{4}"};
       String[] parseFormat =  new String[]{"dd/MM/yyyy","dd-MM-yyyy","dd.MM.yyyy","dd MM yyyy"};
       boolean parser = false;
       for(int i=0;i<formats.length;i++){
           Pattern patern = Pattern.compile(formats[i]);
           Matcher matcher = patern.matcher(date);
           if(matcher.matches()){
               parser = true;
               SimpleDateFormat formater = new SimpleDateFormat(parseFormat[i]);
               dateParser = formater.parse(date);
               if(i ==4 || i==5 || i==6 || i==7){
                   Calendar cal = Calendar.getInstance();
                   cal.setTime(dateParser);
                   cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
                   dateParser = cal.getTime();
                   System.out.println(cal.getTime().toString());
               }
               break;
           }
       }
       if(!parser){
           return null;
           //throw new Exception("Date non valide ou vide");
       }
       return dateParser;
    }
    
    public static Double stringFormatToDouble(String montant) throws ParseException {
        NumberFormat format = NumberFormat.getInstance();
        return format.parse(montant).doubleValue();
    }
    
    public static Time stringToTime(String debut){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            long ms = sdf.parse(debut).getTime();
            return new Time(ms);
        }catch(Exception e){
            return null;
        }
    }
    
}
