package com.asmkt.sample.utils;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import org.apache.commons.collections4.CollectionUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;

public class CsvUtils {

    public static void writeCsvFile(String filePath, String[] headers, List<String[]> data) {
        CsvWriter csvWriter = null;
        try {
            csvWriter = new CsvWriter(filePath, ',', StandardCharsets.UTF_8);
            if (headers != null) {
                csvWriter.writeRecord(headers);
            }
            if (CollectionUtils.isNotEmpty(data)) {
                for (String[] line :data) {
                    csvWriter.writeRecord(line);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            //
        } finally {
            if (csvWriter != null) {
                csvWriter.close();
            }
        }
    }

    public static void readCsvFile(String filePath) {
        CsvReader csvReader = null;
        try {
            csvReader = new CsvReader(filePath, ',', StandardCharsets.UTF_8);
            //csvReader.readHeaders();
            while(csvReader.readRecord()) {
                String line = csvReader.getRawRecord();
                System.out.println(line);

                String[] values = csvReader.getValues();
                System.out.println(values);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (csvReader != null){
                csvReader.close();
            }
        }

    }
}
