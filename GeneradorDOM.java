import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.parsers.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.Scanner;


public class GeneradorDOM {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {


        try{

            Scanner sc = new Scanner(System.in);

            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            Document document = builder.parse(new File("C:\\Users\\Uziel\\OneDrive\\Escritorio\\ArchivosTareas\\sales.xml"));

            NodeList saleRecords = document.getElementsByTagName("sale_record");

            clear();
            System.out.println("Departamentos: Health, Computers, Sports, Music, Movies, Electronics, Garden, Toys, Tools, Home, Books, Industrial, Automotive, Baby, Outdoors, Jewerly, Kids, Beauty, Clothing, Games, Shoes, Grocery");
            System.out.print("Ingrese el nombre del departamento (Colocar mayuscula al inicio, de lo contrario no realizara el aumento): ");
            String departamento = sc.nextLine().trim();

            System.out.print("Ingrese el porcentaje de incremento (5-15%): ");
            double increase = Double.parseDouble(sc.nextLine().trim());

            if (increase < 5 || increase > 15) {

                clear();
                System.out.println("Porcentaje fuera del rango permitido.");
                return;
                
            }

                for (int i = 0; i < saleRecords.getLength(); i++) {
                    Element saleRecord = (Element) saleRecords.item(i);
                    String currentDepartament = saleRecord.getElementsByTagName("department").item(0).getTextContent();

                    if (currentDepartament.equals(departamento)) {

                        double currentValue = Double.parseDouble(saleRecord.getElementsByTagName("sales").item(0).getTextContent());

                        double newValue = currentValue * (1 + increase / 100);

                        if (!Double.isNaN(newValue) && !Double.isInfinite(newValue)) {

                            saleRecord.getElementsByTagName("sales").item(0).setTextContent(String.format("%.2f", newValue));
                            } else System.out.println("El nuevo valor no es válido.");
                    }
                }

                    TransformerFactory transformerFactory = TransformerFactory.newInstance();
                    Transformer transformer = transformerFactory.newTransformer();
                    DOMSource source = new DOMSource(document);
                    StreamResult streamResult = new StreamResult(new File("sales_modificado.xml"));
                    transformer.transform(source, streamResult);
                    System.out.println("El documento XML ha sido modificado y se guardo en nu nuevo documento XML con el nombre: sales_modificado.xml.");

        }catch (ParserConfigurationException | IOException | SAXException | TransformerException e) {
            System.out.println("Error al procesar el archivo" + e.getMessage());
            e.printStackTrace();
        }
    }
    private static void clear(){
        System.out.print("\033[H\033[2J");  
    }
}
