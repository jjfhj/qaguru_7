package com.github.jjfhj;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class FilesTest {

    @Test
    @DisplayName("Загрузка файла формата rtf")
    void checkingFilenameAfterUpload() {
        open("https://demoqa.com/upload-download");
        $("#uploadFile").uploadFromClasspath("example.rtf");
        $("#uploadedFilePath").shouldHave(text("example.rtf"));
    }
}
