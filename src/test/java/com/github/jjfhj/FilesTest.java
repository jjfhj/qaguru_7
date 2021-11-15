package com.github.jjfhj;

import com.codeborne.pdftest.PDF;
import com.codeborne.selenide.Configuration;
import com.codeborne.xlstest.XLS;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FilesTest {

    @BeforeAll
    static void beforeAll() {
        Configuration.browserSize = "1920x1080";
    }

    @Test
    @DisplayName("Загрузка файла формата rtf")
    void checkingFilenameAfterUpload() {
        open("https://demoqa.com/upload-download");
        $("#uploadFile").uploadFromClasspath("example.rtf");
        $("#uploadedFilePath").shouldHave(text("example.rtf"));
    }

    @Test
    @DisplayName("Скачивание текстового файла и проверка его содержимого")
    void checkingContentOfDownloadedTextFile() throws IOException {
        open("http://www.sseu.ru/otdel-organizacii-praktik-i-stazhirovok/dogovor-na-praktiku");
        File download = $(byText("Договор о практической подготовке (долгосрочный)")).download();
        String fileContent = IOUtils.toString(new FileReader(download));
        assertFalse(fileContent.isEmpty());
    }

    @Test
    @DisplayName("Скачивание PDF файла и проверка его содержимого")
    void checkingContentOfDownloadedPDFFile() throws IOException {
        open("https://canadamania.ru/uslugi/platnaya-onlayn-konsultatsiya/");
        File pdf = $("[title='Initial Consultation Template']").download();
        PDF parsedPdf = new PDF(pdf);
        Assertions.assertEquals("Mihaela", parsedPdf.author);
        Assertions.assertEquals(1, parsedPdf.numberOfPages);
    }

    @Test
    @DisplayName("Скачивание XLS файла и проверка его содержимого")
    void checkingContentOfDownloadedXLSFile() throws FileNotFoundException {
        open("http://drgorka.ru/prices/");
        File xls = $("[title='скачать прайс-лист в формате Excel']").download();
        XLS parsedXls = new XLS(xls);
        Assertions.assertEquals(4, parsedXls.excel.getNumberOfSheets());
        Assertions.assertEquals("Контакты", parsedXls.excel.getSheetAt(3).getSheetName());
        Assertions.assertEquals(43, parsedXls.excel.getSheetAt(2).getLastRowNum());
    }

    @Test
    @DisplayName("Парсинг и проверка названия ZIP файла")
    void checkingFileNameInZipArchive() throws IOException {
        ClassLoader classLoader = this.getClass().getClassLoader();
        try (InputStream is = classLoader.getResourceAsStream("price_1c.zip");
             ZipInputStream zis = new ZipInputStream(is)) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Assertions.assertEquals("price_1c.xls", entry.getName());
                System.out.println(entry.getName());
            }
        }
    }
}
