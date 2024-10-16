import Util.StringUtiles;
import enumTipo.Entidades;
import enumTipo.TipoDocumento;
import enumTipo.TipoTarjeta;
import model.Cabecera;
import model.DatosEeccDni;
import model.MefTrama;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
 import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class EntryPoint {

    static Logger log = Logger.getLogger(EntryPoint.class);
    public static void main(String[] args) throws Exception {


        String pathTXT = "C:\\Users\\pra_msanchezs\\Desktop\\Pruebas\\EECC_SET24_TXT\\07711999561638-20241002.txt";
        String pathXML = "C:\\Users\\pra_msanchezs\\Desktop\\Pruebas\\MyStmtDetail-20230811-1000044161-76437048-07706521963749.xml";


        listaTXT(pathTXT);
        ListaXML(pathXML);

















    }

     public static void listaTXT(String path) throws Exception {
         List<DatosEeccDni> listDatosEeccDni = new ArrayList<DatosEeccDni>();
         log.debug("Procesando archivo: " + path);
         String strAccountNumber = "";
         String strDNI = "";
         String strCCI = "";
         String strNombres = "";
         String strApellidos = "";
         String strFechaInicial = "";
         String strFechaFinal = "";
         String strSaldoInicial = "";
         String strSaldoFinal = "";
         Locale locale = new Locale("en", "US");
         SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", locale);
         SimpleDateFormat sdfIn = new SimpleDateFormat("yyyyMMdd", locale);
         boolean existeSaldoParte1 = false;
         String saldoParte1 = "";
         String saldoParte2 = "";
         DatosEeccDni datosEeccDni = null;
         int id = 0;
         BufferedReader bufferedReader = null;
         try {
             bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(path), Charset.forName("UTF-8")));
             String line = null;
             while ((line = bufferedReader.readLine()) != null) {
                 if (line.indexOf("Account Number") > -1 &&
                         strAccountNumber.equals("")) {
                     strNombres = line.substring(2, 45).trim();
                     log.info("nombres:'" + strNombres + "'");
                     if (strNombres.equals("")) {
                         log.error("NOMBRES VACIO - ARCHIVO: " + path);
                         lanzarError("NOMBRES VACIO - ARCHIVO: " + path);
                     }
                     strAccountNumber = line.substring(66, 80).trim();
                     log.info("numeroCuenta:'" + strAccountNumber + "'");
                     if (strAccountNumber.equals("")) {
                         log.error("NRO CUENTA VACIO - ARCHIVO: " + path);
                         lanzarError("NRO CUENTA VACIO - ARCHIVO: " + path);
                     }
                     if (line.indexOf("DNI") > -1 &&
                             strDNI.equals("")) {
                         strDNI = line.substring(94, 102).trim();
                         log.info("dni:'" + strDNI + "'");
                         if (strDNI.equals("")) {
                             log.error("NRO DNI VACIO - ARCHIVO: " + path);
                             lanzarError("NRO DNI VACIO - ARCHIVO: " + path);
                         }
                     }
                     continue;
                 }
                 if (line.indexOf("CCI") > -1 &&
                         strCCI.equals("")) {
                     try {
                         strApellidos = line.substring(2, 45).trim();
                         log.info("apellidos:'" + strApellidos + "'");
                         if (strApellidos.equals("")) {
                             log.error("APELLIDOS VACIO - ARCHIVO: " + path);
                             lanzarError("APELLIDOS VACIO - ARCHIVO: " + path);
                         }
                         strCCI = line.substring(94, 114).trim();
                         log.info("CCI:'" + strCCI + "'");
                         if (strCCI.equals("")) {
                             log.error("NRO CCI VACIO - ARCHIVO: " + path);
                             lanzarError("NRO CCI VACIO - ARCHIVO: " + path);
                         }
                     } catch (Exception e) {
                         log.error("ERROR EXTRAER CCI - path: " + path, e);
                         throw e;
                     }
                     continue;
                 }
                 if (line.indexOf("From Date :") > -1 &&
                         strFechaInicial.equals("")) {
                     try {
                         strFechaInicial = line.substring(66, 77).trim();
                         log.info("FechaInicial:'" + strFechaInicial + "'");
                     } catch (Exception e) {
                         log.error("ERROR EXTRAER FechaInicial - path: " + path, e);
                         throw e;
                     }
                     continue;
                 }
                 if (line.indexOf("To Date :") > -1 &&
                         strFechaFinal.equals("")) {
                     try {
                         strFechaFinal = line.substring(66, 77).trim();
                         log.info("FechaFinal:'" + strFechaFinal + "'");
                     } catch (Exception e) {
                         log.error("ERROR EXTRAER FechaFinal - path: " + path, e);
                         throw e;
                     }
                     continue;
                 }
                 if (line.indexOf("Brought Forward") > -1 &&
                         strSaldoInicial.equals("")) {
                     try {
                         datosEeccDni = new DatosEeccDni();
                         id++;
                         log.info("Row id: " + id);
                         datosEeccDni.setF01_INDEX(String.valueOf(id));
                         Date fechaInicial = sdf.parse(strFechaInicial);
                         datosEeccDni.setF01_FECPROC(sdfIn.format(fechaInicial));
                         Calendar calendar = Calendar.getInstance();
                         calendar.setTime(fechaInicial);
                         datosEeccDni.setF01_ANIO(String.valueOf(calendar.get(1)));
                         datosEeccDni.setF01_MES(String.valueOf(calendar.get(2) + 1));
                         datosEeccDni.setF01_REFERENCIA(null);
                         datosEeccDni.setF01_CONCEPTO("Saldo Inicial");
                         datosEeccDni.setF01_CARGO(null);
                         datosEeccDni.setF01_ABONO(null);
                         strSaldoInicial = line.substring(62, 85).trim().replace(",", "");
                         log.info("SaldoInicial:'" + strSaldoInicial + "'");
                         datosEeccDni.setF01_SALDO(strSaldoInicial.equals("") ? "0" : strSaldoInicial);
                         datosEeccDni.setF01_CCUENTA(strAccountNumber);
                         datosEeccDni.setF01_CCI(strCCI);
                         datosEeccDni.setF01_DNI(leftZerosDni(strDNI));
                         datosEeccDni.setF03_NOM_COMPLETO(String.valueOf(strApellidos) + " " + strNombres);
                         System.out.println(datosEeccDni.toString());
                         listDatosEeccDni.add(datosEeccDni);
                     } catch (Exception e) {
                         log.error("ERROR EXTRAER SaldoInicial - path: " + path, e);
                         throw e;
                     }
                     continue;
                 }
                 if (line.indexOf("Closing Balance :") > -1 &&
                         strSaldoFinal.equals("")) {
                     try {
                         datosEeccDni = new DatosEeccDni();
                         id++;
                         log.info("Row id: " + id);
                         datosEeccDni.setF01_INDEX(String.valueOf(id));
                         Date fechaFinal = sdf.parse(strFechaFinal);
                         datosEeccDni.setF01_FECPROC(sdfIn.format(fechaFinal));
                         Calendar calendar = Calendar.getInstance();
                         calendar.setTime(fechaFinal);
                         datosEeccDni.setF01_ANIO(String.valueOf(calendar.get(1)));
                         datosEeccDni.setF01_MES(String.valueOf(calendar.get(2) + 1));
                         datosEeccDni.setF01_REFERENCIA(null);
                         datosEeccDni.setF01_CONCEPTO("Saldo Final");
                         datosEeccDni.setF01_CARGO(null);
                         datosEeccDni.setF01_ABONO(null);
                         strSaldoFinal = line.substring(65, 85).trim().replace(",", "");
                         log.info("SaldoFinal:'" + strSaldoFinal + "'");
                         datosEeccDni.setF01_SALDO(strSaldoFinal.equals("") ? "0" : strSaldoFinal);
                         datosEeccDni.setF01_CCUENTA(strAccountNumber);
                         datosEeccDni.setF01_CCI(strCCI);
                         datosEeccDni.setF01_DNI(leftZerosDni(strDNI));
                         datosEeccDni.setF03_NOM_COMPLETO(String.valueOf(strApellidos) + " " + strNombres);
                         listDatosEeccDni.add(datosEeccDni);
                     } catch (Exception e) {
                         log.error("ERROR EXTRAER SaldoFinal - path: " + path, e);
                         throw e;
                     }
                     continue;
                 }
                 if (existeSaldoParte1) {
                     saldoParte2 = line.substring(0).trim();
                     String saldo = (String.valueOf(saldoParte1) + saldoParte2).trim().replace(",", "");
                     log.info(" saldo:'" + saldo + "'");
                     datosEeccDni.setF01_SALDO(saldo.equals("") ? null : saldo);
                     datosEeccDni.setF01_CCUENTA(strAccountNumber);
                     datosEeccDni.setF01_CCI(strCCI);
                     datosEeccDni.setF01_DNI(leftZerosDni(strDNI));
                     datosEeccDni.setF03_NOM_COMPLETO(String.valueOf(strApellidos) + " " + strNombres);
                     listDatosEeccDni.add(datosEeccDni);
                     existeSaldoParte1 = false;
                     saldoParte1 = "";
                     saldoParte2 = "";
                     continue;
                 }
                 if (line.length() > 11) {
                     String fechaMov = line.substring(0, 11).trim();
                     try {
                         if (fechaMov.matches("^\\d{2}\\s[a-zA-Z]{3}\\s\\d{4}$")) {
                             datosEeccDni = new DatosEeccDni();
                             id++;
                             log.info("Row id: " + id);
                             datosEeccDni.setF01_INDEX(String.valueOf(id));
                             log.info("fechaMov:'" + fechaMov + "'");
                             Date fechaProceso = sdf.parse(fechaMov);
                             datosEeccDni.setF01_FECPROC(sdfIn.format(fechaProceso));
                             Calendar calendar = Calendar.getInstance();
                             calendar.setTime(fechaProceso);
                             datosEeccDni.setF01_ANIO(String.valueOf(calendar.get(1)));
                             datosEeccDni.setF01_MES(String.valueOf(calendar.get(2) + 1));
                             String referencia = line.substring(12, 36).trim();
                             log.info(" referencia:'" + referencia + "'");
                             datosEeccDni.setF01_REFERENCIA(referencia);
                             String descripcion = line.substring(37, 70).trim();
                             log.info(" descripcion:'" + descripcion + "'");
                             datosEeccDni.setF01_CONCEPTO(descripcion);
                             String cargo = line.substring(83, 101).trim().replace(",", "");
                             log.info(" cargo:'" + cargo + "'");
                             datosEeccDni.setF01_CARGO(cargo.equals("") ? null : cargo);
                             String abono = line.substring(102, 120).trim().replace(",", "");
                             log.info(" abono:'" + abono + "'");
                             datosEeccDni.setF01_ABONO(abono.equals("") ? null : abono);
                             saldoParte1 = line.substring(121).trim();
                             existeSaldoParte1 = true;
                         }
                     } catch (Exception e) {
                         log.error("ERROR EXTRAER DATOS - path:" + path, e);
                         throw e;
                     }
                 }
             }
         } catch (Exception e) {
             log.error("ERROR path:" + path, e);
             throw e;
         } finally {
             if (bufferedReader != null)
                 bufferedReader.close();
         }
    }
     public static void ListaXML(String path) throws Exception {

         List<DatosEeccDni> listDatosEeccDni = new ArrayList<DatosEeccDni>();
         log.debug("Procesando archivo: " + path);
         boolean doInternalCustomerAddress1 = false;
         boolean accountId = false;
         boolean accountCCI = false;
         boolean customerId = false;
         boolean customerFirstName = false;
         boolean customerSurname = false;
         boolean row = false;
         boolean statementEntriesBookingDate = false;
         boolean statementEntriesTxnReference = false;
         boolean statementEntriesNarrative = false;
         boolean statementEntriesLodgement = false;
         boolean statementEntriesWithdrawal = false;
         boolean statementEntriesBalance = false;
         String strDoInternalCustomerAddress1 = "";
         String strAccountId = "";
         String strAccountCCI = "";
         String strCustomerId = "";
         String strCustomerFirstName = "";
         String strCustomerSurname = "";
         XMLInputFactory factory = XMLInputFactory.newInstance( );
         System.out.println("XMLInputFactory implementation: " + factory.getClass().getName());

         InputStream inputStream = new FileInputStream(path);
         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);

         XMLEventReader eventReader = factory.createXMLEventReader(inputStreamReader);
          String id = "";
         DatosEeccDni datosEeccDni = null;
         while (eventReader.hasNext()) {
             StartElement startElement;
             String qName;
             Characters characters;
             EndElement endElement;
             XMLEvent event = eventReader.nextEvent();
             switch (event.getEventType()) {
                 case 1:
                     startElement = event.asStartElement();
                     qName = startElement.getName().getLocalPart();
                     if (qName.equalsIgnoreCase("DoInternalCustomerAddress-1")) {
                         doInternalCustomerAddress1 = true;
                     } else if (qName.equalsIgnoreCase("AccountId")) {
                         accountId = true;
                     } else if (qName.equalsIgnoreCase("AccountCCI")) {
                         accountCCI = true;
                     } else if (qName.equalsIgnoreCase("CustomerId")) {
                         customerId = true;
                     } else if (qName.equalsIgnoreCase("CustomerFirstName")) {
                         customerFirstName = true;
                     } else if (qName.equalsIgnoreCase("CustomerSurname")) {
                         customerSurname = true;
                     } else if (qName.equalsIgnoreCase("Row")) {
                         datosEeccDni = new DatosEeccDni();
                         row = true;
                         log.info("Start Element : Row");
                         Iterator<Attribute> attributes = startElement.getAttributes();
                         id = ((Attribute)attributes.next()).getValue();
                         log.info("Row id: " + id);
                         datosEeccDni.setF01_INDEX(id);
                     }
                     if (row) {
                         if (qName.equalsIgnoreCase("StatementEntriesBookingDate")) {
                             statementEntriesBookingDate = true;
                             continue;
                         }
                         if (qName.equalsIgnoreCase("StatementEntriesTxnReference")) {
                             statementEntriesTxnReference = true;
                             continue;
                         }
                         if (qName.equalsIgnoreCase("StatementEntriesNarrative")) {
                             statementEntriesNarrative = true;
                             continue;
                         }
                         if (qName.equalsIgnoreCase("StatementEntriesLodgement")) {
                             statementEntriesLodgement = true;
                             continue;
                         }
                         if (qName.equalsIgnoreCase("StatementEntriesWithdrawal")) {
                             statementEntriesWithdrawal = true;
                             continue;
                         }
                         if (qName.equalsIgnoreCase("StatementEntriesBalance"))
                             statementEntriesBalance = true;
                     }
                 case 4:
                     characters = event.asCharacters();
                     if (doInternalCustomerAddress1) {
                         log.info("DoInternalCustomerAddress-1: " + characters.getData());
                         strDoInternalCustomerAddress1 = characters.getData().trim().equals("") ? null : characters.getData();
                         doInternalCustomerAddress1 = false;
                     }
                     if (accountId) {
                         log.info("AccountId: " + characters.getData());
                         if (characters.getData().trim().equals("")) {
                             log.error("NRO CUENTA VACIO - ARCHIVO: " + path);
                             lanzarError("NRO CUENTA VACIO - ARCHIVO: " + path);
                         }
                         strAccountId = characters.getData().trim();
                         accountId = false;
                     }
                     if (accountCCI) {
                         log.info("AccountCCI: " + characters.getData());
                         if (characters.getData().trim().equals("")) {
                             log.error("NRO CCI VACIO - ARCHIVO: " + path);
                             lanzarError("NRO CCI VACIO - ARCHIVO: " + path);
                         }
                         strAccountCCI = characters.getData().trim();
                         accountCCI = false;
                     }
                     if (customerId) {
                         log.info("CustomerId: " + characters.getData());
                         if (characters.getData().trim().equals("")) {
                             log.error("NRO DNI VACIO - ARCHIVO: " + path);
                             lanzarError("NRO DNI VACIO - ARCHIVO: " + path);
                         }
                         strCustomerId = characters.getData().trim();
                         customerId = false;
                     }
                     if (customerFirstName) {
                         log.info("CustomerFirstName: " + characters.getData());
                         if (characters.getData().trim().equals("")) {
                             log.error("NOMBRE DE CLIENTE VACIO - ARCHIVO: " + path);
                             lanzarError("NOMBRE DE CLIENTE VACIO - ARCHIVO: " + path);
                         }
                         strCustomerFirstName = characters.getData().trim();
                         customerFirstName = false;
                     }
                     if (customerSurname) {
                         log.info("CustomerSurname: " + characters.getData());
                         if (characters.getData().trim().equals("")) {
                             log.error("APELLIDOS DE CLIENTE VACIO - ARCHIVO: " + path);
                             lanzarError("APELLIDOS DE CLIENTE VACIO - ARCHIVO: " + path);
                         }
                         strCustomerSurname = characters.getData().trim();
                         customerSurname = false;
                     }
                     if (row) {
                         if (statementEntriesBookingDate) {
                             log.info("StatementEntriesBookingDate: " + characters.getData());
                             datosEeccDni.setF01_FECPROC(characters.getData().trim().equals("") ? null : characters.getData());
                             datosEeccDni.setF01_ANIO(characters.getData().trim().equals("") ? null : characters.getData().substring(0, 4));
                             datosEeccDni.setF01_MES(characters.getData().trim().equals("") ? null : characters.getData().substring(4, 6));
                             statementEntriesBookingDate = false;
                         }
                         if (statementEntriesTxnReference) {
                             log.info("StatementEntriesTxnReference: " + characters.getData());
                             datosEeccDni.setF01_REFERENCIA(characters.getData().trim().equals("") ? "" : characters.getData());
                             statementEntriesTxnReference = false;
                         }
                         if (statementEntriesNarrative) {
                             log.info("StatementEntriesNarrative: " + characters.getData());
                             if (characters.getData().trim().equalsIgnoreCase("Opening Balance")) {
                                 datosEeccDni.setF01_CONCEPTO("Saldo Inicial");
                             } else if (characters.getData().trim().equalsIgnoreCase("Closing Balance")) {
                                 datosEeccDni.setF01_CONCEPTO("Saldo Final");
                             } else {
                                 datosEeccDni.setF01_CONCEPTO(characters.getData().trim().equals("") ? "" : characters.getData());
                             }
                             statementEntriesNarrative = false;
                         }
                         if (statementEntriesLodgement) {
                             datosEeccDni.setF01_ABONO(characters.getData().trim().equals("") ? null : characters.getData());
                             log.info("StatementEntriesLodgement: " + characters.getData());
                             statementEntriesLodgement = false;
                         }
                         if (statementEntriesWithdrawal) {
                             datosEeccDni.setF01_CARGO(characters.getData().trim().equals("") ? null : characters.getData());
                             log.info("StatementEntriesWithdrawal: " + characters.getData());
                             statementEntriesWithdrawal = false;
                         }
                         if (statementEntriesBalance) {
                             datosEeccDni.setF01_SALDO(characters.getData().trim().equals("") ? "0" : characters.getData());
                             log.info("StatementEntriesBalance: " + characters.getData());
                             statementEntriesBalance = false;
                         }
                     }
                 case 2:
                     endElement = event.asEndElement();
                     if (endElement.getName().getLocalPart().equalsIgnoreCase("Row")) {
                         datosEeccDni.setF01_CCUENTA(strAccountId);
                         datosEeccDni.setF01_CCI(strAccountCCI);
                         datosEeccDni.setF01_DNI(leftZerosDni(strCustomerId));
                         datosEeccDni.setF03_NOM_COMPLETO(String.valueOf(strCustomerSurname) + " " + strCustomerFirstName);
                         System.out.println(datosEeccDni.toString());
                         listDatosEeccDni.add(datosEeccDni);
                         row = false;
                     }
             }
         }
     }

         public static String leftZerosDni(String dni) {
            if (dni == null || dni.trim().equals(""))
                return null;
            return String.format("%08d", new Object[] { Integer.valueOf(Integer.parseInt(dni)) });
        }

    private static void lanzarError(String message) throws Exception {
        throw new Exception(message);
    }
}
