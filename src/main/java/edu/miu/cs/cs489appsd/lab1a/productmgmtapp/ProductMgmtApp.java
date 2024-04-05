package edu.miu.cs.cs489appsd.lab1a.productmgmtapp;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import edu.miu.cs.cs489appsd.lab1a.productmgmtapp.model.Product;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Comparator;

public class ProductMgmtApp {

    public static void main(String[] args) throws JsonProcessingException {

        Product[] products = new Product[3];

        products[0] = new Product(3128874119L, "Banana", LocalDate.of(2023, 1, 24), 124, 0.55);
        products[1] = new Product(2927458265L, "Apple", LocalDate.of(2022, 12, 9), 18, 1.09);
        products[2] = new Product(9189927460L, "Carrot", LocalDate.of(2023, 3, 31), 89, 2.99);

        printProducts(products);
    }

    private static void printProducts(Product[] products) throws JsonProcessingException {
        // Sort the array by name (ascending order)
        Arrays.sort(products, Comparator.comparing(Product::getName));

        ObjectWriter ow = new ObjectMapper()
                .registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writer().withDefaultPrettyPrinter();
        String json = ow.writeValueAsString(products);
        System.out.println("--------------------------------------");
        System.out.println("Printed in JSON Format");
        System.out.println(json);

        System.out.println("--------------------------------------");
        System.out.println("Printed in XML Format");
        ObjectWriter xmlObjectWriter = new XmlMapper()
                .registerModule(new JavaTimeModule())
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writer().withDefaultPrettyPrinter();
        String xml = xmlObjectWriter.writeValueAsString(products);
        System.out.println(xml);

        CsvMapper csvMapper = new CsvMapper();
        CsvSchema csvSchema = csvMapper.schemaFor(Product.class);

        ObjectWriter csvObjectWriter = csvMapper
                .registerModule(new JavaTimeModule())
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .setDateFormat(new SimpleDateFormat("yyyy-MM-dd"))
                .writer(csvSchema).withDefaultPrettyPrinter();

        System.out.println("--------------------------------------");
        System.out.println("Printed in Comma-Seperated Value(CSV) Format");
        String csv = csvObjectWriter.writeValueAsString(products);
        System.out.println(csv);
    }
}