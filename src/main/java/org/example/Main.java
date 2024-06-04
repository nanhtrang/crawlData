package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") +
                "\\lib\\chromedriver-win64\\chromedriver.exe");


        String url = "https://react-icons.github.io/react-icons/";

        Controller controller = new Controller();
        controller.setUrl(url);
        controller.getSidebar();

    }
}