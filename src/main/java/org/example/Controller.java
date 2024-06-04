package org.example;

import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.ArrayList;
import java.util.List;

@Data
public class Controller {
    private String url;
    private List<Sidebar> sidebars;

    public void getSidebar() {
        WebDriver driver = new ChromeDriver();
        driver.get(url);
        WebElement stable = driver.findElement(By.className("sidebar--links"));
        List<WebElement> list = stable.findElements(By.tagName("a"));
        int count = 1;
        sidebars = new ArrayList<>();

        for (var a : list) {
            if (a.getText().equals("Home")) continue;
            Sidebar sidebar = Sidebar.builder()
                    .id(count)
                    .name(a.getText())
                    .path(a.getAttribute("href").replace("https://react-icons.github.io/", ""))
                    .build();
            getIcons(sidebar, sidebar.getPath());
            sidebars.add(sidebar);
            count++;
        }
        driver.quit();


    }

    private void getIcons(Sidebar sidebar, String path) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://react-icons.github.io/" + path);
        Icon icon = new Icon();

        var info = driver.findElement(By.className("iconset--info"));
        var listA = info.findElements(By.tagName("a"));
        sidebar.setLicense(listA.get(0).getText());
        sidebar.setLicenseHref(listA.get(1).getAttribute("href"));
        sidebar.setName(listA.get(0).getText());
        var code = driver.findElement(By.className("astro-code"));
        var line = code.findElement(By.className("line"));
        sidebar.setImportSyntax(line.getText());

        driver.quit();
    }
}
