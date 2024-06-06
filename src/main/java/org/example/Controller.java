package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Data
public class Controller {
    private String url;
    private List<Sidebar> sidebars;

    public void getSidebar() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(0, 0));
        driver.get(url);
//        driver.close();
        System.out.println("start---------------------->");
        try {
            System.out.println();
            WebElement stable = driver.findElement(By.className("sidebar--links"));
            List<WebElement> list = stable.findElements(By.tagName("a"));
            int count = 1;
            sidebars = new ArrayList<>();
            for (var a : list) {
                System.out.println("menu " + count +"---------------------->");
//                if (a.getText().equals("Home")) continue;
                Sidebar sidebar = new Sidebar();
                sidebar.setId(count);
                sidebar.setName(a.getAttribute("innerText"));
                sidebar.setPath(a.getAttribute("href").replace("https://react-icons.github.io/", ""));
                System.out.println(sidebar.getName());
                if (!a.getAttribute("innerText").equals("Home")){
                    var icons = getIcons(sidebar, sidebar.getPath());
                    sidebar.setIcons(icons);
                }
                sidebars.add(sidebar);
                System.out.println("menu end " + count +"---------------------->\n\n\n");
                count++;
            }

            ObjectMapper mapper = new ObjectMapper();
//            String jsonString = mapper.writeValueAsString(sidebars);
            mapper.writeValue(new File("person.json"), sidebars);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }


    }

    private List<Icon> getIcons(Sidebar sidebar, String path) {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless=new");
        WebDriver driver = new ChromeDriver(options);
        driver.manage().window().setSize(new Dimension(0, 0));
        List<Icon> icons = new ArrayList<>();
        try {
            driver.get("https://react-icons.github.io/" + path);
            System.out.println(path);
            var info = driver.findElement(By.className("iconset--info"));
            System.out.println("has info: " + path);
            var listA = info.findElements(By.tagName("a"));
            System.out.println("has list information: " + path);
            sidebar.setLicense(listA.get(0).getAttribute("innerText"));
            sidebar.setLicenseHref(listA.get(0).getAttribute("href"));
            sidebar.setProjectName(listA.get(1).getAttribute("innerText"));
            var code = driver.findElement(By.className("astro-code"));
            var line = code.findElement(By.className("line"));
            sidebar.setImportSyntax(line.getText());
//            sidebar.setIcons(new ArrayList<>());
            var iconsElement = driver.findElement(By.className("icons")).findElements(By.className("item"));
            System.out.println("iconsElement: " + "okkkkkkkkkkkkkkkkkkk");


            for (int j = 0; j < iconsElement.size(); j++) {
                var i = iconsElement.get(j);
                Icon icon = new Icon();
                System.out.println(j + ":_______ " + i.getAttribute("innerText"));
                var text = i.findElement(By.className("name")).getAttribute("innerText");
                icon.setName(text);
                String[] texts = text.split("\\s+");
                String use = texts[0].substring(0, 1).toUpperCase() + texts[0].substring(1) + texts[1];
                String code1 = "import { " + use + " } from \"react-icons/" + texts[0] + "\";";
                icon.setCode(code1);
                icon.setUse("<" + use+ "/>");
                icons.add(icon);
                System.out.println(path + ": " + use);
            }
            return icons;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
        return icons;
    }
}
