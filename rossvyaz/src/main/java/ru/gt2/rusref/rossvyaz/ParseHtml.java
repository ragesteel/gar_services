package ru.gt2.rusref.rossvyaz;

import com.google.common.collect.Lists;
import lombok.extern.java.Log;

import javax.xml.bind.ValidationException;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.logging.Level;

/**
 * Разбор HTML с кодами телефонов с сайта РосСвязи.
 * http://rossvyaz.ru/activity/num_resurs/registerNum/
 *
 * http://rossvyaz.ru/docs/articles/DEF-9x.html
 * http://rossvyaz.ru/docs/articles/ABC-3x.html
 * http://rossvyaz.ru/docs/articles/ABC-4x.html
 * http://rossvyaz.ru/docs/articles/ABC-8x.html
 *
 * http://grigory-panov.blogspot.ru/2012/08/def-9x.html
 *
 */
@Log
public class ParseHtml {
    static final String TR = "tr";
    static final String TD = "td";

    private static void parseHTML(InputStream is, List<PhoneRange> result) {
        try {
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(is, "windows-1251");
            String[] buff = new String[6];
            int count = 0;
            int ind = 0;
            while (eventReader.hasNext()) {
                try {
                    XMLEvent event = eventReader.nextEvent();

                    if (event.isStartElement()) {
                        StartElement startElement = event.asStartElement();
                        // start new row
                        if (TR.equals(startElement.getName().getLocalPart())) {
                            buff = new String[6];
                            count++;
                            ind = 0;
                        }

                        if (TD.equals(event.asStartElement().getName().getLocalPart())) {
                            event = eventReader.nextEvent();
                            buff[ind++] = event.asCharacters().getData();
                            continue;
                        }
                    }

                    if (event.isEndElement()) {
                        String endElementName = event.asEndElement().getName().getLocalPart();
                        if (TR.equals(endElementName)) {
                            if (count != 1) { // пропускаем первую строку с заголовком
                                result.add(validateRow(count, buff));
                            }
                        }
                        if ("html".equals(endElementName)) {
                            break;
                        }
                    }
                } catch (XMLStreamException e) { // вероятнее всего это незакрытый тег, игнорируем ошибку
                    log.log(Level.SEVERE, "skip error", e);
                } catch(Exception e){ // вероятнее всего файл просто закончился, завершаем обработку
                    log.log(Level.SEVERE, "skip error, break", e);
                    break;
                }
            }
            eventReader.close();

        } catch (XMLStreamException e) { // что-то не так с кодировкой или структурой файла
            e.printStackTrace();
        }
    }

    private static PhoneRange validateRow(int counter, String[] nextLine) throws ValidationException {
        // здесь проводится проверка строки на валидность и если
        // строка не валидна, выбрасывается эксепшн с номером строки
        PhoneRange result = new PhoneRange();
        result.setCode(Integer.valueOf(nextLine[0].trim()));
        result.setBegin(Integer.valueOf(nextLine[1].trim()));
        result.setEnd(Integer.valueOf(nextLine[2].trim()));
        result.setCount(Integer.valueOf(nextLine[3].trim()));
        result.setOperator(nextLine[4]);
        result.setRegion(nextLine[5]);
        return result;
    }

    public static void main(String[] args) throws FileNotFoundException {
        List<PhoneRange> list = Lists.newArrayList();
        InputStream inputStream = new FileInputStream("/home/rage/projects/rus-ref/rossvyaz/DEF-9x.html");
        parseHTML(inputStream, list);
    }
}
