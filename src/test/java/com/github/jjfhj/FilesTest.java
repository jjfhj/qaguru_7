package com.github.jjfhj;

import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FilesTest {

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
}
